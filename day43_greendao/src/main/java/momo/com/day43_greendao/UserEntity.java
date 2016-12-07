package momo.com.day43_greendao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * 只需字段写出来
 * 点击编译，其他均由greenDao自动生成
 *
 * momo试过：toString方法不能自动生成
 */

@Entity(nameInDb = "user_table")
public class UserEntity {
    @Id
    private  Long id;//用Long包装类型，id能自动增长 而用long类型，不可以
    @Property
    private  String username;
    @Property(nameInDb = "mima")
    private  String password;

    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    @Generated(hash = 1672992372)
    public UserEntity(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }
    @Generated(hash = 1433178141)
    public UserEntity() {
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
