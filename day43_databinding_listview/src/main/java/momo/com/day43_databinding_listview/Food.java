package momo.com.day43_databinding_listview;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2016/12/7 0007.
 */
public class Food {

    private String name;
    private String img;

    public Food() {
    }

    public Food(String name, String img) {
        this.name = name;
        this.img = img;
    }

    @BindingAdapter("img")
    public static void loadImage(ImageView iv ,String url){
        Picasso.with(iv.getContext()).load(url).into(iv);
    }

    //绑定布局文件的点击事件
    public void onItemClick(View view){
        Toast.makeText(view.getContext(),name,Toast.LENGTH_SHORT).show();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "Food{" +
                "name='" + name + '\'' +
                ", img='" + img + '\'' +
                '}';
    }
}
