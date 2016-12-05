package momo.com.day40_myjoke_fromhttp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Administrator on 2016/12/2 0002.
 */
public interface IJokeInterface {
    @GET("8hr/page/{page}/?s=4935472")  //｛｝花括号
    Call<String> getData(@Path("page")int page);

    @GET("article/{detail}")
    Call<String> getDetail(@Path("detail")String detail);
}
