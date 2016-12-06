package momo.com.day41_exer_material_design;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import momo.com.day41_exer_material_design.demo.CoordinatorLayoutActivity;
import momo.com.day41_exer_material_design.demo.FloatingActionButtonActivity;
import momo.com.day41_exer_material_design.demo.NavigationViewActivity;
import momo.com.day41_exer_material_design.demo.SnackBar_Activity;
import momo.com.day41_exer_material_design.demo.TextInputLayout_Activity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void toActivity(View view){
        Class activityClass=null;
        Intent intent = new Intent();

        switch (view.getId()){
            case R.id.btn_snackbar:{
                activityClass = SnackBar_Activity.class;
            }break;
            case R.id.btn_textinputLayout:{
                activityClass = TextInputLayout_Activity.class;
            }break;
            case R.id.btn_floatingactionButton:{
                activityClass = FloatingActionButtonActivity.class;
            }break;
            case R.id.btn_CoordinatorLayout:{
                activityClass = CoordinatorLayoutActivity.class;
            }break;
            case R.id.btn_NavigationView:{
                activityClass = NavigationViewActivity.class;
            }break;

        }

        intent.setClass(this,activityClass);
        startActivity(intent);

    }
}
