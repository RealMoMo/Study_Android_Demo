package momo.com.day40_myjoke_fromhttp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by Administrator on 2016/12/2 0002.
 */
public class DetailActivity extends AppCompatActivity {

    String url;

    TextView tv_detail;
    ImageView iv_detail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setupView();

        url = getIntent().getStringExtra("url");

        getData(url);
    }

    private void setupView() {

        tv_detail = (TextView) findViewById(R.id.tv_detail);
        iv_detail = (ImageView) findViewById(R.id.iv_detail);
    }

    private void getData(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl("http://www.qiushibaike.com/")
                        .addConverterFactory(ScalarsConverterFactory.create())
                        .build();

        IJokeInterface joke = retrofit.create(IJokeInterface.class);
        Call<String> call = joke.getDetail(url.substring(url.lastIndexOf("/")+1));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                String value = response.body();
                Log.d("Tag",value);
                Document document = Jsoup.parseBodyFragment(value);
                DetailBean bean = new DetailBean(document);
                tv_detail.setText(bean.content);
                Picasso.with(DetailActivity.this).load(bean.thumb).into(iv_detail);

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
}
