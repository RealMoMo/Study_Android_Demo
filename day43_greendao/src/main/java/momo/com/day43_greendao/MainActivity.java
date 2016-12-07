package momo.com.day43_greendao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.greenrobot.greendao.database.Database;

import java.util.List;

import momo.com.day43_greendao.greendao.DaoMaster;
import momo.com.day43_greendao.greendao.DaoSession;
import momo.com.day43_greendao.greendao.UserEntityDao;

/**
 * greenDao
 *
 * 相对ActiveAndroid数据库框架更好（个人认为）
 *
 * github:
 * https://github.com/greenrobot/greenDAO
 *
 * 1.引入greenDAO,project的gradle文件
 * allprojects加mavenCentral()
 *
 * dependencies加classpath 'org.greenrobot:greendao-gradle-plugin:3.1.0'
 *
 * 和module的gradle文件一共修改4个地方
 * apply plugin: 'org.greenrobot.greendao'
 *
 * compile 'org.greenrobot:greendao:3.2.0'
 *
 *
 * 2.在module的gradle文件中配置数据库版本号、生成代码的位置等参数
      android加
      greendao{
     //配置数据库版本号
     schemaVersion 1
     targetGenDir 'src/main/java'
     daoPackage 'momo.com.day43_greendao.greendao'
     }
     若最后配置数据版本号报错，在project的gradle文件：
     建议修改greendao版本号（降低）;
     另一种方法修改project的build:gradle版本号（提高）
 *
 *
 * 3.创建实体类
 * 4.增删改查
 *
 */
public class MainActivity extends AppCompatActivity {

    UserEntityDao userEntityDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //第2参数：数据库文件名
        DaoMaster.DevOpenHelper dbHepler = new DaoMaster.DevOpenHelper(this,"momo.db");
        Database db = dbHepler.getWritableDb();
        DaoSession daoSession = new DaoMaster(db).newSession();
        userEntityDao = daoSession.getUserEntityDao();

    }



    public void addData(View view){
            userEntityDao.save(new UserEntity(null,"momo","123456"));
            userEntityDao.save(new UserEntity(null,"realmo","1234567"));
    }

    public void queryData(View view){
        List<UserEntity> list = userEntityDao.queryBuilder().where(UserEntityDao.Properties.Username.eq("momo")).list();
        if(list!=null && list.size()>0){
            for(UserEntity userEntity:list){
                Log.d("Tag", "queryData: " + userEntity.toString());
            }
        }
    }

    public void deleteData(View view){
        //删除所有数据
//        userEntityDao.deleteAll();
        List<UserEntity> list = userEntityDao.queryBuilder().where(UserEntityDao.Properties.Password.eq("1234567")).list();
        if(list!=null &&list.size()>0){
            for(UserEntity userEntity:list){
                userEntityDao.delete(userEntity);
            }
        }

    }

    public void updateData(View view){
        List<UserEntity> list = userEntityDao.queryBuilder().list();
        if (list != null && list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                UserEntity userEntity = list.get(i);
                userEntity.setUsername("haha");
                userEntityDao.update(userEntity);
            }
        }

    }
}
