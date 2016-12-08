package momo.com.day44_qbreader;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.google.zxing.client.android.CaptureActivity;


/**
 * 到github下载zxing
 * github:
 * https://github.com/zxing/zxing
 *
 * 1.将\zxing-master\zxing-master\android文件夹作为一个module导入到项目中
 * 2.修改导入后的build.gradle文件
 * 3.将\zxing-master\zxing-master\android-core\src\main\java下的com文件夹拷贝到上面导的模块的java目录下
 * 4.引用module，编译工程
 * 5.修改zxingLibrary的gradle文件，使之成为一个库
 * 6.修改zxingLibrary的清单文件，删除application节点的logo、icon(可选)属性,同时删除CaptureActivity的作为启动项设置的intent-filter
 * 7.添加core-3.3.0.jar到zxingLibrary中
 * 8.编译项目，修改错误
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void btnClick1(View view){
        //启动zxing自带的CaptureActivity
        startActivity(new Intent(this, CaptureActivity.class));
    }
}
