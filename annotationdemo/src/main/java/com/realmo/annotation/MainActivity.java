package com.realmo.annotation;


import android.support.annotation.WorkerThread;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.realmo.annotation.callsuper.Son;
import com.realmo.annotation.checkresult.CheckResultAnnotation;
import com.realmo.annotation.intdef.User;
import com.realmo.annotation.nullannotation.NullAnnotation;
import com.realmo.annotation.other.OtherAnnotation;
import com.realmo.annotation.res.ResAnnotation;
import com.realmo.annotation.thread.ThreadAnnotation;
import com.realmo.annotation.value_constraints.ValueConstraintsAnnotation;

import java.lang.reflect.Method;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }


    public void test(View view) {
        //1.IntDef & StringDef
        testIntDefAnnotation();

        //2.Res Annotation
        //testResAnnotation();

        //3.Null Annotation
        //testNullAnnotation();

        //4.Thread Annotaion
        //testThreadAnnotation();

        //5.Value Constraints Annotation
        //testValueConstraintsAnnotation();

        //6.CallSuper Annotation
        //testCallSuperAnnotation();

        //7.CheckResult Annotation
        //testCheckResultAnnotation();

        //8.Other Annotation
        //testOtherAnnotation();
    }


    private void testIntDefAnnotation(){
        User user = new User();
       // user.setUserType(1); //编译器会有警告提示，但不影响实际运行。
        user.setUserType(User.UserType.IT_WORKER);

        Log.d("realmo","intdef user type:"+user.getUserType()); //input 1

    }




    private void testResAnnotation(){
        ResAnnotation resAnnotation = new ResAnnotation();
    }

    private void testNullAnnotation(){
        NullAnnotation nullAnnotation = new NullAnnotation();

        nullAnnotation.setNonNull(null);  //编译器会有提示，但不影响实际运行。
        nullAnnotation.setNullable(null);
        //so @Nonnull @Nullable 只是看看
        Log.d("realmo","nonnull:"+nullAnnotation.getNonNull()); //null
        Log.d("realmo","nullable:"+nullAnnotation.getNullable()); //null

    }

    @WorkerThread  //加此注解 除WorkerThread其他均报警告提示，但不影响实际运行
    private void testThreadAnnotation(){
        ThreadAnnotation threadAnnotation = new ThreadAnnotation();
        threadAnnotation.workOnMainThread();        //log main
        threadAnnotation.workOnUiThread();          //log main
        threadAnnotation.workOnBinderThread();      //log main
        threadAnnotation.workOnWorkerThread();      //log main
    }


    private void testValueConstraintsAnnotation(){
        ValueConstraintsAnnotation vcAnnotation = new ValueConstraintsAnnotation();

        vcAnnotation.setSizeString("1");
        vcAnnotation.setSizeString("333"); //编译器会有提示，但不影响实际运行。

        vcAnnotation.setIntRange(10);
        vcAnnotation.setIntRange(11);      //编译器会有提示，但不影响实际运行。

        vcAnnotation.setFloatRange(10f);
        vcAnnotation.setFloatRange(10.1f);  //编译器会有提示，但不影响实际运行。
    }


    private void testCallSuperAnnotation(){
        //具体见Father&Son类
        Son son = new Son();
        son.getMemberName();
    }

    private void testCheckResultAnnotation(){
        CheckResultAnnotation crAnnotation = new CheckResultAnnotation();
    }


    private void testOtherAnnotation(){
        OtherAnnotation otherAnnotation = new OtherAnnotation();
    }


}
