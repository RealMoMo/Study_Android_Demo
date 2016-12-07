package momo.com.day43_activeandroid;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;

/**
 * 继承ActiveAndroid的Model
 * Model自带一些数据库操作的方法
 */
//不定义@Table 表名默认是实体类的类名
//配置表名和_id字段
@Table(name = "user_table",id="_id")
public class UserEntity extends Model{
   //设置该属性作为数据表的一个字段
    @Column(name ="user_name")
    private String username;
    @Column
    private String nickname;
    @Column
    private String password;


    public UserEntity() {
    }

    public UserEntity(String username, String nickname) {
        this.username = username;
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
