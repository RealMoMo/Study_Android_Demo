/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.providers.settings;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.FullBackupDataOutput;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.NetworkPolicy;
import android.net.NetworkPolicyManager;
import android.net.Uri;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiManager;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.BackupUtils;
import android.util.Log;

import com.android.internal.widget.LockPatternUtils;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.zip.CRC32;

/**
 * Performs backup and restore of the System and Secure settings.
 * List of settings that are backed up are stored in the Settings.java file
 */
public class SettingsBackupAgent extends BackupAgentHelper {
    private static final boolean DEBUG = false;
    private static final boolean DEBUG_BACKUP = DEBUG || false;

    private static final String KEY_SYSTEM = "system";
    private static final String KEY_SECURE = "secure";
    private static final String KEY_GLOBAL = "global";
    private static final String KEY_LOCALE = "locale";
    private static final String KEY_LOCK_SETTINGS = "lock_settings";
    private static final String KEY_SOFTAP_CONFIG = "softap_config";
    private static final String KEY_NETWORK_POLICIES = "network_policies";
    private static final String KEY_WIFI_NEW_CONFIG = "wifi_new_config";

    // Versioning of the state file.  Increment this version
    // number any time the set of state items is altered.
    private static final int STATE_VERSION = 7;

    // Versioning of the Network Policies backup payload.
    private static final int NETWORK_POLICIES_BACKUP_VERSION = 1;


    // Slots in the checksum array.  Never insert new items in the middle
    // of this array; new slots must be appended.
    private static final int STATE_SYSTEM           = 0;
    private static final int STATE_SECURE           = 1;
    private static final int STATE_LOCALE           = 2;
    private static final int STATE_WIFI_SUPPLICANT  = 3;
    private static final int STATE_WIFI_CONFIG      = 4;
    private static final int STATE_GLOBAL           = 5;
    private static final int STATE_LOCK_SETTINGS    = 6;
    private static final int STATE_SOFTAP_CONFIG    = 7;
    private static final int STATE_NETWORK_POLICIES = 8;
    private static final int STATE_WIFI_NEW_CONFIG  = 9;

    private static final int STATE_SIZE             = 10; // The current number of state items

    // Number of entries in the checksum array at various version numbers
    private static final int STATE_SIZES[] = {
            0,
            4,              // version 1
            5,              // version 2 added STATE_WIFI_CONFIG
            6,              // version 3 added STATE_GLOBAL
            7,              // version 4 added STATE_LOCK_SETTINGS
            8,              // version 5 added STATE_SOFTAP_CONFIG
            9,              // version 6 added STATE_NETWORK_POLICIES
            STATE_SIZE      // version 7 added STATE_WIFI_NEW_CONFIG
    };

    // Versioning of the 'full backup' format
    // Increment this version any time a new item is added
    private static final int FULL_BACKUP_VERSION = 6;
    private static final int FULL_BACKUP_ADDED_GLOBAL = 2;  // added the "global" entry
    private static final int FULL_BACKUP_ADDED_LOCK_SETTINGS = 3; // added the "lock_settings" entry
    private static final int FULL_BACKUP_ADDED_SOFTAP_CONF = 4; //added the "softap_config" entry
    private static final int FULL_BACKUP_ADDED_NETWORK_POLICIES = 5; //added "network_policies"
    private static final int FULL_BACKUP_ADDED_WIFI_NEW = 6; // added "wifi_new_config" entry

    private static final int INTEGER_BYTE_COUNT = Integer.SIZE / Byte.SIZE;

    private static final byte[] EMPTY_DATA = new byte[0];

    private static final String TAG = "SettingsBackupAgent";

    private static final String[] PROJECTION = {
            Settings.NameValueTable.NAME,
            Settings.NameValueTable.VALUE
    };

    // the key to store the WIFI data under, should be sorted as last, so restore happens last.
    // use very late unicode character to quasi-guarantee last sort position.
    private static final String KEY_WIFI_SUPPLICANT = "\uffedWIFI";
    private static final String KEY_WIFI_CONFIG = "\uffedCONFIG_WIFI";

    // Keys within the lock settings section
    private static final String KEY_LOCK_SETTINGS_OWNER_INFO_ENABLED = "owner_info_enabled";
    private static final String KEY_LOCK_SETTINGS_OWNER_INFO = "owner_info";

    // Name of the temporary file we use during full backup/restore.  This is
    // stored in the full-backup tarfile as well, so should not be changed.
    private static final String STAGE_FILE = "flattened-data";

    private SettingsHelper mSettingsHelper;

    private WifiManager mWifiManager;

    @Override
    public void onCreate() {
        if (DEBUG_BACKUP) Log.d(TAG, "onCreate() invoked");

        mSettingsHelper = new SettingsHelper(this);
        mWifiManager = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        super.onCreate();
    }

    @Override
    public void onBackup(ParcelFileDescriptor oldState, BackupDataOutput data,
            ParcelFileDescriptor newState) throws IOException {

        byte[] systemSettingsData = getSystemSettings();
        byte[] secureSettingsData = getSecureSettings();
        byte[] globalSettingsData = getGlobalSettings();
        byte[] lockSettingsData   = getLockSettings();
        byte[] locale = mSettingsHelper.getLocaleData();
        byte[] softApConfigData = getSoftAPConfiguration();
        byte[] netPoliciesData = getNetworkPolicies();
        byte[] wifiFullConfigData = getNewWifiConfigData();

        long[] stateChecksums = readOldChecksums(oldState);

        stateChecksums[STATE_SYSTEM] =
                writeIfChanged(stateChecksums[STATE_SYSTEM], KEY_SYSTEM, systemSettingsData, data);
        stateChecksums[STATE_SECURE] =
                writeIfChanged(stateChecksums[STATE_SECURE], KEY_SECURE, secureSettingsData, data);
        stateChecksums[STATE_GLOBAL] =
                writeIfChanged(stateChecksums[STATE_GLOBAL], KEY_GLOBAL, globalSettingsData, data);
        stateChecksums[STATE_LOCALE] =
                writeIfChanged(stateChecksums[STATE_LOCALE], KEY_LOCALE, locale, data);
        stateChecksums[STATE_WIFI_SUPPLICANT] = 0;
        stateChecksums[STATE_WIFI_CONFIG] = 0;
        stateChecksums[STATE_LOCK_SETTINGS] =
                writeIfChanged(stateChecksums[STATE_LOCK_SETTINGS], KEY_LOCK_SETTINGS,
                        lockSettingsData, data);
        stateChecksums[STATE_SOFTAP_CONFIG] =
                writeIfChanged(stateChecksums[STATE_SOFTAP_CONFIG], KEY_SOFTAP_CONFIG,
                        softApConfigData, data);
        stateChecksums[STATE_NETWORK_POLICIES] =
                writeIfChanged(stateChecksums[STATE_NETWORK_POLICIES], KEY_NETWORK_POLICIES,
                        netPoliciesData, data);
        stateChecksums[STATE_WIFI_NEW_CONFIG] =
                writeIfChanged(stateChecksums[STATE_WIFI_NEW_CONFIG], KEY_WIFI_NEW_CONFIG,
                        wifiFullConfigData, data);

        writeNewChecksums(stateChecksums, newState);
    }

    @Override
    public void onRestore(BackupDataInput data, int appVersionCode,
            ParcelFileDescriptor newState) throws IOException {

        HashSet<String> movedToGlobal = new HashSet<String>();
        Settings.System.getMovedToGlobalSettings(movedToGlobal);
        Settings.Secure.getMovedToGlobalSettings(movedToGlobal);
        byte[] restoredWifiSupplicantData = null;
        byte[] restoredWifiIpConfigData = null;

        while (data.readNextHeader()) {
            final String key = data.getKey();
            final int size = data.getDataSize();
            switch (key) {
                case KEY_SYSTEM :
                    restoreSettings(data, Settings.System.CONTENT_URI, movedToGlobal);
                    mSettingsHelper.applyAudioSettings();
                    break;

                case KEY_SECURE :
                    restoreSettings(data, Settings.Secure.CONTENT_URI, movedToGlobal);
                    break;

                case KEY_GLOBAL :
                    restoreSettings(data, Settings.Global.CONTENT_URI, null);
                    break;

                case KEY_WIFI_SUPPLICANT :
                    restoredWifiSupplicantData = new byte[size];
                    data.readEntityData(restoredWifiSupplicantData, 0, size);
                    break;

                case KEY_LOCALE :
                    byte[] localeData = new byte[size];
                    data.readEntityData(localeData, 0, size);
                    mSettingsHelper.setLocaleData(localeData, size);
                    break;

                case KEY_WIFI_CONFIG :
                    restoredWifiIpConfigData = new byte[size];
                    data.readEntityData(restoredWifiIpConfigData, 0, size);
                    break;

                case KEY_LOCK_SETTINGS :
                    restoreLockSettings(data);
                    break;

                case KEY_SOFTAP_CONFIG :
                    byte[] softapData = new byte[size];
                    data.readEntityData(softapData, 0, size);
                    restoreSoftApConfiguration(softapData);
                    break;

                case KEY_NETWORK_POLICIES:
                    byte[] netPoliciesData = new byte[size];
                    data.readEntityData(netPoliciesData, 0, size);
                    restoreNetworkPolicies(netPoliciesData);
                    break;

                case KEY_WIFI_NEW_CONFIG:
                    byte[] restoredWifiNewConfigData = new byte[size];
                    data.readEntityData(restoredWifiNewConfigData, 0, size);
                    restoreNewWifiConfigData(restoredWifiNewConfigData);
                    break;

                default :
                    data.skipEntityData();

            }
        }

        // Do this at the end so that we also pull in the ipconfig data.
        if (restoredWifiSupplicantData != null) {
            restoreSupplicantWifiConfigData(
                    restoredWifiSupplicantData, restoredWifiIpConfigData);
        }
    }

    @Override
    public void onFullBackup(FullBackupDataOutput data)  throws IOException {
        byte[] systemSettingsData = getSystemSettings();
        byte[] secureSettingsData = getSecureSettings();
        byte[] globalSettingsData = getGlobalSettings();
        byte[] lockSettingsData   = getLockSettings();
        byte[] locale = mSettingsHelper.getLocaleData();
        byte[] softApConfigData = getSoftAPConfiguration();
        byte[] netPoliciesData = getNetworkPolicies();
        byte[] wifiFullConfigData = getNewWifiConfigData();

        // Write the data to the staging file, then emit that as our tarfile
        // representation of the backed-up settings.
        String root = getFilesDir().getAbsolutePath();
        File stage = new File(root, STAGE_FILE);
        try {
            FileOutputStream filestream = new FileOutputStream(stage);
            BufferedOutputStream bufstream = new BufferedOutputStream(filestream);
            DataOutputStream out = new DataOutputStream(bufstream);

            if (DEBUG_BACKUP) Log.d(TAG, "Writing flattened data version " + FULL_BACKUP_VERSION);
            out.writeInt(FULL_BACKUP_VERSION);

            if (DEBUG_BACKUP) Log.d(TAG, systemSettingsData.length + " bytes of settings data");
            out.writeInt(systemSettingsData.length);
            out.write(systemSettingsData);
            if (DEBUG_BACKUP) {
                Log.d(TAG, secureSettingsData.length + " bytes of secure settings data");
            }
            out.writeInt(secureSettingsData.length);
            out.write(secureSettingsData);
            if (DEBUG_BACKUP) {
                Log.d(TAG, globalSettingsData.length + " bytes of global settings data");
            }
            out.writeInt(globalSettingsData.length);
            out.write(globalSettingsData);
            if (DEBUG_BACKUP) Log.d(TAG, locale.length + " bytes of locale data");
            out.writeInt(locale.length);
            out.write(locale);
            if (DEBUG_BACKUP) Log.d(TAG, lockSettingsData.length + " bytes of lock settings data");
            out.writeInt(lockSettingsData.length);
            out.write(lockSettingsData);
            if (DEBUG_BACKUP) Log.d(TAG, softApConfigData.length + " bytes of softap config data");
            out.writeInt(softApConfigData.length);
            out.write(softApConfigData);
            if (DEBUG_BACKUP) Log.d(TAG, netPoliciesData.length + " bytes of net policies data");
            out.writeInt(netPoliciesData.length);
            out.write(netPoliciesData);
            if (DEBUG_BACKUP) {
                Log.d(TAG, wifiFullConfigData.length + " bytes of wifi config data");
            }
            out.writeInt(wifiFullConfigData.length);
            out.write(wifiFullConfigData);

            out.flush();    // also flushes downstream

            // now we're set to emit the tar stream
            fullBackupFile(stage, data);
        } finally {
            stage.delete();
        }
    }

    @Override
    public void onRestoreFile(ParcelFileDescriptor data, long size,
            int type, String domain, String relpath, long mode, long mtime)
            throws IOException {
        if (DEBUG_BACKUP) Log.d(TAG, "onRestoreFile() invoked");
        // Our data is actually a blob of flattened settings data identical to that
        // produced during incremental backups.  Just unpack and apply it all in
        // turn.
        FileInputStream instream = new FileInputStream(data.getFileDescriptor());
        DataInputStream in = new DataInputStream(instream);

        int version = in.readInt();
        if (DEBUG_BACKUP) Log.d(TAG, "Flattened data version " + version);
        if (version <= FULL_BACKUP_VERSION) {
            // Generate the moved-to-global lookup table
            HashSet<String> movedToGlobal = new HashSet<String>();
            Settings.System.getMovedToGlobalSettings(movedToGlobal);
            Settings.Secure.getMovedToGlobalSettings(movedToGlobal);

            // system settings data first
            int nBytes = in.readInt();
            if (DEBUG_BACKUP) Log.d(TAG, nBytes + " bytes of settings data");
            byte[] buffer = new byte[nBytes];
            in.readFully(buffer, 0, nBytes);
            restoreSettings(buffer, nBytes, Settings.System.CONTENT_URI, movedToGlobal);

            // secure settings
            nBytes = in.readInt();
            if (DEBUG_BACKUP) Log.d(TAG, nBytes + " bytes of secure settings data");
            if (nBytes > buffer.length) buffer = new byte[nBytes];
            in.readFully(buffer, 0, nBytes);
            restoreSettings(buffer, nBytes, Settings.Secure.CONTENT_URI, movedToGlobal);

            // Global only if sufficiently new
            if (version >= FULL_BACKUP_ADDED_GLOBAL) {
                nBytes = in.readInt();
                if (DEBUG_BACKUP) Log.d(TAG, nBytes + " bytes of global settings data");
                if (nBytes > buffer.length) buffer = new byte[nBytes];
                in.readFully(buffer, 0, nBytes);
                movedToGlobal.clear();  // no redirection; this *is* the global namespace
                restoreSettings(buffer, nBytes, Settings.Global.CONTENT_URI, movedToGlobal);
            }

            // locale
            nBytes = in.readInt();
            if (DEBUG_BACKUP) Log.d(TAG, nBytes + " bytes of locale data");
            if (nBytes > buffer.length) buffer = new byte[nBytes];
            in.readFully(buffer, 0, nBytes);
            mSettingsHelper.setLocaleData(buffer, nBytes);

            // Restore older backups performing the necessary migrations.
            if (version < FULL_BACKUP_ADDED_WIFI_NEW) {
                // wifi supplicant
                int supplicant_size = in.readInt();
                if (DEBUG_BACKUP) Log.d(TAG, supplicant_size + " bytes of wifi supplicant data");
                byte[] supplicant_buffer = new byte[supplicant_size];
                in.readFully(supplicant_buffer, 0, supplicant_size);

                // ip config
                int ipconfig_size = in.readInt();
                if (DEBUG_BACKUP) Log.d(TAG, ipconfig_size + " bytes of ip config data");
                byte[] ipconfig_buffer = new byte[ipconfig_size];
                in.readFully(ipconfig_buffer, 0, nBytes);
                restoreSupplicantWifiConfigData(supplicant_buffer, ipconfig_buffer);
            }

            if (version >= FULL_BACKUP_ADDED_LOCK_SETTINGS) {
                nBytes = in.readInt();
                if (DEBUG_BACKUP) Log.d(TAG, nBytes + " bytes of lock settings data");
                if (nBytes > buffer.length) buffer = new byte[nBytes];
                if (nBytes > 0) {
                    in.readFully(buffer, 0, nBytes);
                    restoreLockSettings(buffer, nBytes);
                }
            }
            // softap config
            if (version >= FULL_BACKUP_ADDED_SOFTAP_CONF) {
                nBytes = in.readInt();
                if (DEBUG_BACKUP) Log.d(TAG, nBytes + " bytes of softap config data");
                if (nBytes > buffer.length) buffer = new byte[nBytes];
                if (nBytes > 0) {
                    in.readFully(buffer, 0, nBytes);
                    restoreSoftApConfiguration(buffer);
                }
            }
            // network policies
            if (version >= FULL_BACKUP_ADDED_NETWORK_POLICIES) {
                nBytes = in.readInt();
                if (DEBUG_BACKUP) Log.d(TAG, nBytes + " bytes of network policies data");
                if (nBytes > buffer.length) buffer = new byte[nBytes];
                if (nBytes > 0) {
                    in.readFully(buffer, 0, nBytes);
                    restoreNetworkPolicies(buffer);
                }
            }
            // Restore full wifi config data
            if (version >= FULL_BACKUP_ADDED_WIFI_NEW) {
                nBytes = in.readInt();
                if (DEBUG_BACKUP) Log.d(TAG, nBytes + " bytes of full wifi config data");
                if (nBytes > buffer.length) buffer = new byte[nBytes];
                in.readFully(buffer, 0, nBytes);
                restoreNewWifiConfigData(buffer);
            }

            if (DEBUG_BACKUP) Log.d(TAG, "Full restore complete.");
        } else {
            data.close();
            throw new IOException("Invalid file schema");
        }
    }

    private long[] readOldChecksums(ParcelFileDescriptor oldState) throws IOException {
        long[] stateChecksums = new long[STATE_SIZE];

        DataInputStream dataInput = new DataInputStream(
                new FileInputStream(oldState.getFileDescriptor()));

        try {
            int stateVersion = dataInput.readInt();
            if (stateVersion > STATE_VERSION) {
                // Constrain the maximum state version this backup agent
                // can handle in case a newer or corrupt backup set existed
                stateVersion = STATE_VERSION;
            }
            for (int i = 0; i < STATE_SIZES[stateVersion]; i++) {
                stateChecksums[i] = dataInput.readLong();
            }
        } catch (EOFException eof) {
            // With the default 0 checksum we'll wind up forcing a backup of
            // any unhandled data sets, which is appropriate.
        }
        dataInput.close();
        return stateChecksums;
    }

    private void writeNewChecksums(long[] checksums, ParcelFileDescriptor newState)
            throws IOException {
        DataOutputStream dataOutput = new DataOutputStream(
                new BufferedOutputStream(new FileOutputStream(newState.getFileDescriptor())));

        dataOutput.writeInt(STATE_VERSION);
        for (int i = 0; i < STATE_SIZE; i++) {
            dataOutput.writeLong(checksums[i]);
        }
        dataOutput.close();
    }

    private long writeIfChanged(long oldChecksum, String key, byte[] data,
            BackupDataOutput output) {
        CRC32 checkSummer = new CRC32();
        checkSummer.update(data);
        long newChecksum = checkSummer.getValue();
        if (oldChecksum == newChecksum) {
            return oldChecksum;
        }
        try {
            if (DEBUG_BACKUP) {
                Log.v(TAG, "Writing entity " + key + " of size " + data.length);
            }
            output.writeEntityHeader(key, data.length);
            output.writeEntityData(data, data.length);
        } catch (IOException ioe) {
            // Bail
        }
        return newChecksum;
    }

    private byte[] getSystemSettings() {
        Cursor cursor = getContentResolver().query(Settings.System.CONTENT_URI, PROJECTION, null,
                null, null);
        try {
            return extractRelevantValues(cursor, Settings.System.SETTINGS_TO_BACKUP);
        } finally {
            cursor.close();
        }
    }

    private byte[] getSecureSettings() {
        Cursor cursor = getContentResolver().query(Settings.Secure.CONTENT_URI, PROJECTION, null,
                null, null);
        try {
            return extractRelevantValues(cursor, Settings.Secure.SETTINGS_TO_BACKUP);
        } finally {
            cursor.close();
        }
    }

    private byte[] getGlobalSettings() {
        Cursor cursor = getContentResolver().query(Settings.Global.CONTENT_URI, PROJECTION, null,
                null, null);
        try {
            return extractRelevantValues(cursor, Settings.Global.SETTINGS_TO_BACKUP);
        } finally {
            cursor.close();
        }
    }

    /**
     * Serialize the owner info settings
     */
    private byte[] getLockSettings() {
        final LockPatternUtils lockPatternUtils = new LockPatternUtils(this);
        final boolean ownerInfoEnabled = lockPatternUtils.isOwnerInfoEnabled(UserHandle.myUserId());
        final String ownerInfo = lockPatternUtils.getOwnerInfo(UserHandle.myUserId());

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(baos);
        try {
            out.writeUTF(KEY_LOCK_SETTINGS_OWNER_INFO_ENABLED);
            out.writeUTF(ownerInfoEnabled ? "1" : "0");
            if (ownerInfo != null) {
                out.writeUTF(KEY_LOCK_SETTINGS_OWNER_INFO);
                out.writeUTF(ownerInfo != null ? ownerInfo : "");
            }
            // End marker
            out.writeUTF("");
            out.flush();
        } catch (IOException ioe) {
        }
        return baos.toByteArray();
    }

    private void restoreSettings(BackupDataInput data, Uri contentUri,
            HashSet<String> movedToGlobal) {
        byte[] settings = new byte[data.getDataSize()];
        try {
            data.readEntityData(settings, 0, settings.length);
        } catch (IOException ioe) {
            Log.e(TAG, "Couldn't read entity data");
            return;
        }
        restoreSettings(settings, settings.length, contentUri, movedToGlobal);
    }

    private void restoreSettings(byte[] settings, int bytes, Uri contentUri,
            HashSet<String> movedToGlobal) {
        if (DEBUG) {
            Log.i(TAG, "restoreSettings: " + contentUri);
        }

        // Figure out the white list and redirects to the global table.
        final String[] whitelist;
        if (contentUri.equals(Settings.Secure.CONTENT_URI)) {
            whitelist = Settings.Secure.SETTINGS_TO_BACKUP;
        } else if (contentUri.equals(Settings.System.CONTENT_URI)) {
            whitelist = Settings.System.SETTINGS_TO_BACKUP;
        } else if (contentUri.equals(Settings.Global.CONTENT_URI)) {
            whitelist = Settings.Global.SETTINGS_TO_BACKUP;
        } else {
            throw new IllegalArgumentException("Unknown URI: " + contentUri);
        }

        // Restore only the white list data.
        int pos = 0;
        Map<String, String> cachedEntries = new HashMap<String, String>();
        ContentValues contentValues = new ContentValues(2);
        SettingsHelper settingsHelper = mSettingsHelper;
        ContentResolver cr = getContentResolver();

        final int whiteListSize = whitelist.length;
        for (int i = 0; i < whiteListSize; i++) {
            String key = whitelist[i];
            String value = cachedEntries.remove(key);

            // If the value not cached, let us look it up.
            if (value == null) {
                while (pos < bytes) {
                    int length = readInt(settings, pos);
                    pos += INTEGER_BYTE_COUNT;
                    String dataKey = length > 0 ? new String(settings, pos, length) : null;
                    pos += length;
                    length = readInt(settings, pos);
                    pos += INTEGER_BYTE_COUNT;
                    String dataValue = length > 0 ? new String(settings, pos, length) : null;
                    pos += length;
                    if (key.equals(dataKey)) {
                        value = dataValue;
                        break;
                    }
                    cachedEntries.put(dataKey, dataValue);
                }
            }

            if (value == null) {
                continue;
            }

            final Uri destination = (movedToGlobal != null && movedToGlobal.contains(key))
                    ? Settings.Global.CONTENT_URI
                    : contentUri;
            settingsHelper.restoreValue(this, cr, contentValues, destination, key, value);

            if (DEBUG) {
                Log.d(TAG, "Restored setting: " + destination + " : " + key + "=" + value);
            }
        }
    }

    /**
     * Restores the owner info enabled and owner info settings in LockSettings.
     *
     * @param buffer
     * @param nBytes
     */
    private void restoreLockSettings(byte[] buffer, int nBytes) {
        final LockPatternUtils lockPatternUtils = new LockPatternUtils(this);

        ByteArrayInputStream bais = new ByteArrayInputStream(buffer, 0, nBytes);
        DataInputStream in = new DataInputStream(bais);
        try {
            String key;
            // Read until empty string marker
            while ((key = in.readUTF()).length() > 0) {
                final String value = in.readUTF();
                if (DEBUG_BACKUP) {
                    Log.v(TAG, "Restoring lock_settings " + key + " = " + value);
                }
                switch (key) {
                    case KEY_LOCK_SETTINGS_OWNER_INFO_ENABLED:
                        lockPatternUtils.setOwnerInfoEnabled("1".equals(value),
                                UserHandle.myUserId());
                        break;
                    case KEY_LOCK_SETTINGS_OWNER_INFO:
                        lockPatternUtils.setOwnerInfo(value, UserHandle.myUserId());
                        break;
                }
            }
            in.close();
        } catch (IOException ioe) {
        }
    }

    private void restoreLockSettings(BackupDataInput data) {
        final byte[] settings = new byte[data.getDataSize()];
        try {
            data.readEntityData(settings, 0, settings.length);
        } catch (IOException ioe) {
            Log.e(TAG, "Couldn't read entity data");
            return;
        }
        restoreLockSettings(settings, settings.length);
    }

    /**
     * Given a cursor and a set of keys, extract the required keys and
     * values and write them to a byte array.
     *
     * @param cursor A cursor with settings data.
     * @param settings The settings to extract.
     * @return The byte array of extracted values.
     */
    private byte[] extractRelevantValues(Cursor cursor, String[] settings) {
        final int settingsCount = settings.length;
        byte[][] values = new byte[settingsCount * 2][]; // keys and values
        if (!cursor.moveToFirst()) {
            Log.e(TAG, "Couldn't read from the cursor");
            return new byte[0];
        }

        // Obtain the relevant data in a temporary array.
        int totalSize = 0;
        int backedUpSettingIndex = 0;
        Map<String, String> cachedEntries = new HashMap<String, String>();
        for (int i = 0; i < settingsCount; i++) {
            String key = settings[i];
            String value = cachedEntries.remove(key);

            final int nameColumnIndex = cursor.getColumnIndex(Settings.NameValueTable.NAME);
            final int valueColumnIndex = cursor.getColumnIndex(Settings.NameValueTable.VALUE);

            // If the value not cached, let us look it up.
            if (value == null) {
                while (!cursor.isAfterLast()) {
                    String cursorKey = cursor.getString(nameColumnIndex);
                    String cursorValue = cursor.getString(valueColumnIndex);
                    cursor.moveToNext();
                    if (key.equals(cursorKey)) {
                        value = cursorValue;
                        break;
                    }
                    cachedEntries.put(cursorKey, cursorValue);
                }
            }

            // Intercept the keys and see if they need special handling
            value = mSettingsHelper.onBackupValue(key, value);

            if (value == null) {
                continue;
            }
            // Write the key and value in the intermediary array.
            byte[] keyBytes = key.getBytes();
            totalSize += INTEGER_BYTE_COUNT + keyBytes.length;
            values[backedUpSettingIndex * 2] = keyBytes;

            byte[] valueBytes = value.getBytes();
            totalSize += INTEGER_BYTE_COUNT + valueBytes.length;
            values[backedUpSettingIndex * 2 + 1] = valueBytes;

            backedUpSettingIndex++;

            if (DEBUG) {
                Log.d(TAG, "Backed up setting: " + key + "=" + value);
            }
        }

        // Aggregate the result.
        byte[] result = new byte[totalSize];
        int pos = 0;
        final int keyValuePairCount = backedUpSettingIndex * 2;
        for (int i = 0; i < keyValuePairCount; i++) {
            pos = writeInt(result, pos, values[i].length);
            pos = writeBytes(result, pos, values[i]);
        }
        return result;
    }

    private void restoreSupplicantWifiConfigData(byte[] supplicant_bytes, byte[] ipconfig_bytes) {
        if (DEBUG_BACKUP) {
            Log.v(TAG, "Applying restored supplicant wifi data");
        }
        mWifiManager.restoreSupplicantBackupData(supplicant_bytes, ipconfig_bytes);
    }

    private byte[] getSoftAPConfiguration() {
        try {
            return mWifiManager.getWifiApConfiguration().getBytesForBackup();
        } catch (IOException ioe) {
            Log.e(TAG, "Failed to marshal SoftAPConfiguration" + ioe.getMessage());
            return new byte[0];
        }
    }

    private void restoreSoftApConfiguration(byte[] data) {
        try {
            WifiConfiguration config = WifiConfiguration
                    .getWifiConfigFromBackup(new DataInputStream(new ByteArrayInputStream(data)));
            if (DEBUG) Log.d(TAG, "Successfully unMarshaled WifiConfiguration ");
            mWifiManager.setWifiApConfiguration(config);
        } catch (IOException | BackupUtils.BadVersionException e) {
            Log.e(TAG, "Failed to unMarshal SoftAPConfiguration " + e.getMessage());
        }
    }

    private byte[] getNetworkPolicies() {
        NetworkPolicyManager networkPolicyManager =
                (NetworkPolicyManager) getSystemService(NETWORK_POLICY_SERVICE);
        NetworkPolicy[] policies = networkPolicyManager.getNetworkPolicies();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        if (policies != null && policies.length != 0) {
            DataOutputStream out = new DataOutputStream(baos);
            try {
                out.writeInt(NETWORK_POLICIES_BACKUP_VERSION);
                out.writeInt(policies.length);
                for (NetworkPolicy policy : policies) {
                    if (policy != null) {
                        byte[] marshaledPolicy = policy.getBytesForBackup();
                        out.writeByte(BackupUtils.NOT_NULL);
                        out.writeInt(marshaledPolicy.length);
                        out.write(marshaledPolicy);
                    } else {
                        out.writeByte(BackupUtils.NULL);
                    }
                }
            } catch (IOException ioe) {
                Log.e(TAG, "Failed to convert NetworkPolicies to byte array " + ioe.getMessage());
                baos.reset();
            }
        }
        return baos.toByteArray();
    }

    private byte[] getNewWifiConfigData() {
        return mWifiManager.retrieveBackupData();
    }

    private void restoreNewWifiConfigData(byte[] bytes) {
        if (DEBUG_BACKUP) {
            Log.v(TAG, "Applying restored wifi data");
        }
        mWifiManager.restoreBackupData(bytes);
    }

    private void restoreNetworkPolicies(byte[] data) {
        NetworkPolicyManager networkPolicyManager =
                (NetworkPolicyManager) getSystemService(NETWORK_POLICY_SERVICE);
        if (data != null && data.length != 0) {
            DataInputStream in = new DataInputStream(new ByteArrayInputStream(data));
            try {
                int version = in.readInt();
                if (version < 1 || version > NETWORK_POLICIES_BACKUP_VERSION) {
                    throw new BackupUtils.BadVersionException(
                            "Unknown Backup Serialization Version");
                }
                int length = in.readInt();
                NetworkPolicy[] policies = new NetworkPolicy[length];
                for (int i = 0; i < length; i++) {
                    byte isNull = in.readByte();
                    if (isNull == BackupUtils.NULL) continue;
                    int byteLength = in.readInt();
                    byte[] policyData = new byte[byteLength];
                    in.read(policyData, 0, byteLength);
                    policies[i] = NetworkPolicy.getNetworkPolicyFromBackup(
                            new DataInputStream(new ByteArrayInputStream(policyData)));
                }
                // Only set the policies if there was no error in the restore operation
                networkPolicyManager.setNetworkPolicies(policies);
            } catch (NullPointerException | IOException | BackupUtils.BadVersionException e) {
                // NPE can be thrown when trying to instantiate a NetworkPolicy
                Log.e(TAG, "Failed to convert byte array to NetworkPolicies " + e.getMessage());
            }
        }
    }

    /**
     * Write an int in BigEndian into the byte array.
     * @param out byte array
     * @param pos current pos in array
     * @param value integer to write
     * @return the index after adding the size of an int (4) in bytes.
     */
    private int writeInt(byte[] out, int pos, int value) {
        out[pos + 0] = (byte) ((value >> 24) & 0xFF);
        out[pos + 1] = (byte) ((value >> 16) & 0xFF);
        out[pos + 2] = (byte) ((value >>  8) & 0xFF);
        out[pos + 3] = (byte) ((value >>  0) & 0xFF);
        return pos + INTEGER_BYTE_COUNT;
    }

    private int writeBytes(byte[] out, int pos, byte[] value) {
        System.arraycopy(value, 0, out, pos, value.length);
        return pos + value.length;
    }

    private int readInt(byte[] in, int pos) {
        int result = ((in[pos] & 0xFF) << 24)
                | ((in[pos + 1] & 0xFF) << 16)
                | ((in[pos + 2] & 0xFF) <<  8)
                | ((in[pos + 3] & 0xFF) <<  0);
        return result;
    }
}
