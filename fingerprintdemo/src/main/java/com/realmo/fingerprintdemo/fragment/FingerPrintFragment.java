package com.realmo.fingerprintdemo.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.realmo.fingerprintdemo.MainActivity;
import com.realmo.fingerprintdemo.R;

import javax.crypto.Cipher;

/**
 * @author Realmo
 * @version 1.0.0
 * @name Demo
 * @email momo.weiye@gmail.com
 * @time 2018/8/29 12:26
 * @describe
 */
@TargetApi(23)
public class FingerPrintFragment extends DialogFragment {

    private FingerprintManager fingerprintManager;

    private CancellationSignal mCancellationSignal;

    private Cipher mCipher;

    private MainActivity mActivity;

    private TextView errorMsg;

    /**
     * 标识是否是用户主动取消的认证。
     */
    private boolean isSelfCancelled;

    public void setCipher(Cipher cipher) {
        mCipher = cipher;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (MainActivity) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fingerprintManager = getContext().getSystemService(FingerprintManager.class);
        setStyle(DialogFragment.STYLE_NORMAL, android.R.style.Theme_Material_Light_Dialog);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fingerprint_dialog, container, false);
        errorMsg = v.findViewById(R.id.error_msg);
        TextView cancel = v.findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                stopListening();
            }
        });
        return v;
    }


    //在onResume()方法中调用了startListening()方法开始指纹认证监听。
    @Override
    public void onResume() {
        super.onResume();
        // 开始指纹认证监听
        startListening(mCipher);
    }

    /**
     * 在onPause()方法中调用了stopListening()方法停止指纹认证监听
     * why?
     * 因为指纹传感器和摄像头类似，是不能多个程序同时使用的，
     * 因此任何一个程序都不应该在非前台时刻占用着指纹传感器的资源，
     * 所以需要在onPause()方法中及时释放资源。
     */
    @Override
    public void onPause() {
        super.onPause();
        // 停止指纹认证监听
        stopListening();
    }

    private void startListening(Cipher cipher) {
        isSelfCancelled = false;
        mCancellationSignal = new CancellationSignal();
        //authenticate()方法来开启指纹指纹监听
        //第一个参数是CryptoObject对象。
        // 第二个参数是CancellationSignal对象，可以使用它来取消指纹认证操作。
        // 第三个参数是可选参数，官方的建议是直接传0就可以了。
        // 第四个参数用于接收指纹认证的回调，下述代码中我将所有的回调可能都进行了界面提示，方便大家观察。
        // 第五个参数用于指定处理回调的Handler，这里直接传null表示回调到主线程即可。
        fingerprintManager.authenticate(new FingerprintManager.CryptoObject(cipher), mCancellationSignal, 0, new FingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                if (!isSelfCancelled) {
                    errorMsg.setText(errString);
                    if (errorCode == FingerprintManager.FINGERPRINT_ERROR_LOCKOUT) {
                        Toast.makeText(mActivity, errString, Toast.LENGTH_SHORT).show();
                        dismiss();
                    }
                }
            }

            @Override
            public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
                errorMsg.setText(helpString);
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                Toast.makeText(mActivity, "指纹认证成功", Toast.LENGTH_SHORT).show();
                mActivity.onAuthenticated();
            }

            @Override
            public void onAuthenticationFailed() {
                errorMsg.setText("指纹认证失败，请再试一次");
            }
        }, null);
    }

    private void stopListening() {
        if (mCancellationSignal != null) {
            mCancellationSignal.cancel();
            mCancellationSignal = null;
            isSelfCancelled = true;
        }
    }

}
