package com.hht.test;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.os.Process;
import android.util.Log;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


/**
 * @author Realmo
 * @version 1.0.0
 * @name Demo
 * @email momo.weiye@gmail.com
 * @time 2018/5/26 10:12
 * @describe 全局异常管理类
 */
public class CrashHandler implements UncaughtExceptionHandler {
    private static final String TAG = "CrashHandler";
    private static final String FILE_DIR = Environment.getExternalStorageDirectory().getPath()+"/HHT_Info/Log/";
    private static final String FILE_NAME = "crash";
    private static final String FILE_NAME_SUFFIX = ".trace";

    private static CrashHandler INSTANCE = new CrashHandler();// CrashHandler实例
    private UncaughtExceptionHandler mDefaultHandler;// 系统默认的UncaughtException处理类
    private Context mContext;


    private CrashHandler() {

    }

    public static CrashHandler getInstance() {
        return INSTANCE;
    }


    public void init(Context context) {
        mContext = context.getApplicationContext();
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();// 获取系统默认的UncaughtException处理器
        Thread.setDefaultUncaughtExceptionHandler(this);// 设置该CrashHandler为程序的默认处理器
    }

    /**
     * 当程序中有未被捕获的异常，系统将会自动调用#uncaughtException方法
     */
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {

        try{
            dumpExceptionToSDCard(ex);
            uploadExceptionToServer();
        }catch (IOException e){
            e.printStackTrace();
        }
        ex.printStackTrace();
        // 如果系统提供了默认的异常处理器，则交给系统去给结束程序，否则就由自己结束自己
        if (mDefaultHandler != null) {
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    private void dumpExceptionToSDCard(Throwable ex) throws IOException {
        if(!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            Log.w(TAG,"sdcard unmounted,skip dump exception.");
            return ;
        }

        File dir = new File(FILE_DIR);
        if(!dir.exists()){
            dir.mkdirs();
        }

        long current = System.currentTimeMillis();
        String time = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss").format(new Date(current));
        File file = new File(FILE_DIR + FILE_NAME  + time + FILE_NAME_SUFFIX);


        try{
            PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            pw.println(time);
            dumpDeviceInfo(pw);
            pw.println();
            ex.printStackTrace(pw);
            pw.close();
        }catch(Exception e){
            Log.e(TAG,"dump crash info failed");
        }

    }

    private void dumpDeviceInfo(PrintWriter pw) throws NameNotFoundException{
        PackageManager pm  = mContext.getPackageManager();
        PackageInfo pi = pm.getPackageInfo(mContext.getPackageName(),PackageManager.GET_ACTIVITIES);
        //app info
        pw.print("App Name: ");
        pw.println(mContext.getResources().getString(R.string.app_name));
        pw.print("App Version：");
        pw.print(pi.versionName);
        pw.print('_');
        pw.println(pi.versionCode);

        //android system info
        pw.print("OS Version:");
        pw.print(Build.VERSION.RELEASE);
        pw.print('_');
        pw.println(Build.VERSION.SDK_INT);

        //manufacturer info
        pw.print("Vendor: ");
        pw.println(Build.MANUFACTURER);

        //model info
        pw.print("Model: ");
        pw.println(Build.MODEL);

        //CPU info
        pw.print("CPU ABI: ");
        pw.println(Build.CPU_ABI);
    }

    private void uploadExceptionToServer(){
        //TODO upload exception message to your web server
    }






}