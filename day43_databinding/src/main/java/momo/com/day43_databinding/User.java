package momo.com.day43_databinding;

import android.databinding.BindingAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/12/7 0007.
 */
public class User {

    private String username;

    private String password;

    private String userface;


    /**
     * 只有方法名是随意的
     * 必须public static
     * 参数1：加载图片的控件
     * 参数2：网络图片的地址
     *
     * @param iv
     * @param url
     */
    @BindingAdapter("userface")
    public static void loadImage(ImageView iv, String url) {
        Picasso.with(iv.getContext()).load(url).into(iv);
    }

    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserface() {
        return userface;
    }

    public void setUserface(String userface) {
        this.userface = userface;
    }
}
