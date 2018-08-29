package com.realmo.fingerprintdemo;

import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;


import com.realmo.fingerprintdemo.fragment.FingerPrintFragment;

import java.security.KeyStore;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;


/**
 * FingerPrintDemo ： 指纹验证Demo （api>=23）
 * TODO:待适配Android9
 * FingerprintManager在最新的Android 9.0系统上已经被废弃了。
 * 因为Android 9.0系统提供了更加强大的生物识别认证功能，包括指纹识别、面部识别、甚至是虹膜识别等等，
 * 因此仅仅只能用于指纹识别的FingerprintManager已经不能满足新系统的强大需求了。
 *
 * 1.添加权限 <uses-permission android:name="android.permission.USE_FINGERPRINT" />
 * 2.验证设备是否支持指纹认证功能
 * 3.生成一个对称加密的Key
 * 4.生成一个Cipher对象 （直译：暗号）
 * 5.开启验证
 * 6.释放验证资源
 */
public class MainActivity extends AppCompatActivity {

    private static final String DEFAULT_KEY_NAME = "default_key";

    KeyStore keyStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (supportFingerprint()) {
            initKey();
            initCipher();
        }
    }

    //判断当前设备是否支持指纹认证功能
    public boolean supportFingerprint() {
        if (Build.VERSION.SDK_INT < 23) {
            Toast.makeText(this, "您的系统版本过低，不支持指纹功能", Toast.LENGTH_SHORT).show();
            return false;
        } else {
            KeyguardManager keyguardManager = getSystemService(KeyguardManager.class);
            FingerprintManager fingerprintManager = getSystemService(FingerprintManager.class);
            if (!fingerprintManager.isHardwareDetected()) {
                Toast.makeText(this, "您的手机不支持指纹功能", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!keyguardManager.isKeyguardSecure()) {
                Toast.makeText(this, "您还未设置锁屏，请先设置锁屏并添加一个指纹", Toast.LENGTH_SHORT).show();
                return false;
            } else if (!fingerprintManager.hasEnrolledFingerprints()) {
                Toast.makeText(this, "您至少需要在系统设置中添加一个指纹", Toast.LENGTH_SHORT).show();
                return false;
            }
        }
        return true;
    }
    //当设备支持指纹认证的时候，再分为两步，第一步生成一个对称加密的Key.
    @TargetApi(23)
    private void initKey() {
        try {
            keyStore = KeyStore.getInstance("AndroidKeyStore");
            keyStore.load(null);
            KeyGenerator keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(DEFAULT_KEY_NAME,
                    KeyProperties.PURPOSE_ENCRYPT |
                            KeyProperties.PURPOSE_DECRYPT)
                    .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                    .setUserAuthenticationRequired(true)
                    .setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
            keyGenerator.init(builder.build());
            keyGenerator.generateKey();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    //第二步生成一个Cipher对象，第一第二步都是Android指纹认证API要求的标准用法。
    @TargetApi(23)
    private void initCipher() {
        try {
            SecretKey key = (SecretKey) keyStore.getKey(DEFAULT_KEY_NAME, null);
            Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/"
                    + KeyProperties.BLOCK_MODE_CBC + "/"
                    + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            showFingerPrintDialog(cipher);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void showFingerPrintDialog(Cipher cipher) {
        FingerPrintFragment fragment = new FingerPrintFragment();
        fragment.setCipher(cipher);
        fragment.show(getSupportFragmentManager(), "fingerprint");
    }

    public void onAuthenticated() {
        Intent intent = new Intent(this, SuccessActivity.class);
        startActivity(intent);

    }

}
