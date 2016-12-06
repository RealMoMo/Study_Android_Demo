package momo.com.day41_exer_material_design.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import momo.com.day41_exer_material_design.R;

public class TextInputLayout_Activity extends AppCompatActivity {


    TextInputLayout account,password;

    EditText  et_account,et_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_textinputlayout);

        setupView();
    }

    private void setupView() {
        account = (TextInputLayout) findViewById(R.id.textinput_account);
        password = (TextInputLayout) findViewById(R.id.textinput_password);

        //可以在代码设置hint属性，即使xml的edittext控件定义了hint属性。也只会显示java代码定义的hint属性
//        account.setHint("RealMo");

        //获取TextInputLayout下的输入框
        et_account = account.getEditText();
        et_password =password.getEditText();

    }

    //点击登录按钮
    public void login(View view){
        hideKeyboard();

        if(et_account.getText().length()<4){
            account.setErrorEnabled(true);
            account.setError("账号至少4位");
        }else{
            account.setErrorEnabled(false);
        }


        if(et_password.getText().length()<6){
            password.setErrorEnabled(true);
            password.setError("密码至少6位");
        }else{
            password.setErrorEnabled(false);
        }
    }


    //隐藏键盘的方法
    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }
}
