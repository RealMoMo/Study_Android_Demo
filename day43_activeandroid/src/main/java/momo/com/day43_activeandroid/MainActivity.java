package momo.com.day43_activeandroid;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.activeandroid.query.Select;
import com.activeandroid.query.Update;

import java.util.List;


/**
 * ActiveAndroid
 *
 * github:
 * https://github.com/pardom/ActiveAndroid
 *
 * 1.引入ActiveAndroid，修改project的gradle文件
 *  mavenCentral()
 *  maven { url "https://oss.sonatype.org/content/repositories/snapshots/" }
 *  和module的gradle文件
 *  compile 'com.michaelpardo:activeandroid:3.1.0-SNAPSHOT'
 *
 * 2.在Application中初始化ActiveAndroid
 *
 * 3.在清单文件中配置数据库名称和版本号(可选,建议配置) 不配置，默认数据库名称Application.db
 * 设置在application节点内
 * <meta-data android:name="AA_DB_NAME" android:value="RealMo.db" />
 * <meta-data android:name="AA_DB_VERSION" android:value="1" />
 *
 * 4.创建实体类
 *
 * 5.增删改查
 *
 *
 * 数据库升级：
 * 1.修改实体类
 * 2.修改清单文件中数据库的版本号
 * 3.创建assets文件夹，在assets文件夹中创建migrations（迁移）文件夹,
 * 然后再migration文件夹中创建数据库迁移脚本，
 * 其名称为最新版本号.sql
 * 4.在该文件指定数据库修改内容
 *
 *
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    //添加数据
    public void addData(View view){
        UserEntity userEntity = new UserEntity("momo","realmo");
        userEntity.save();
        UserEntity userEntity2 = new UserEntity("rongrong","tangyuan");
        userEntity2.save();
    }

    //查找数据
    public void searchData(View view){

        Select select = new Select();
        List<UserEntity> list = select.from(UserEntity.class).where("user_name = ?", "momo").execute();

        if(list!=null&&list.size()>0){
            for(UserEntity user : list){
                Log.d("Tag",user.toString());
            }
        }
    }

    //修改数据
    public void updateData(View view){
        Update update = new Update(UserEntity.class);
        update.set("nickname=?","haha").where("nickname=?","realmo").execute();

    }

    //删除数据(通过查找所要删除的数据，再进行删除)
    public void deleteData(View view){
        Select select = new Select();
        List<UserEntity> list = select.from(UserEntity.class).where("user_name = ?", "momo").execute();
        if(list!=null&&list.size()>0) {
            for (UserEntity userEntity : list) {
                Log.d("Tag", "deleteData: " + userEntity.getId());
                userEntity.delete();
            }
        }
    }
}
