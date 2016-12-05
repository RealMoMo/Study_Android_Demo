package momo.com.day29_slidingmenu_demo;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.view.KeyEvent;
import android.view.MenuItem;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;

public class MainActivity extends SlidingFragmentActivity {

    SlidingMenu sliding;
    ContentFragment contentFragment;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //设置侧滑布局加载到那个布局
        setBehindContentView(R.layout.left_menu);
        //创建侧滑内容 Fragment
        LeftMenuFragment left = new LeftMenuFragment();
        //把创建的Fragment添加到侧滑布局中
        getSupportFragmentManager().beginTransaction().replace(R.id.leftmenu_layout,left).commit();

        //设置侧滑属性----------------------------------

        //获取侧滑菜单对象
        sliding = getSlidingMenu();
        //侧滑模式:左
        sliding.setMode(SlidingMenu.LEFT);
        //触摸模式：边缘
        sliding.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
        //设置侧滑菜单的宽度(是距离另一侧的宽度)
        //获取屏幕宽度
        int screenW = getResources().getDisplayMetrics().widthPixels;
        sliding.setBehindOffset(screenW/3*2);
        //设置侧滑阴影宽度
        sliding.setShadowWidth(20);
        //设置侧滑阴影图片
        sliding.setShadowDrawable(R.mipmap.ic_launcher);
        //设置渐入渐出
        sliding.setFadeDegree(0.35f);

        contentFragment = new ContentFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.contentLayout,contentFragment).commit();
//初始时，加载百度，在此时加载，ContentFragment界面还没绘制出来，WebView是null(程序会崩)
//        contentFragment.setUrl("http://www.baidu.com");


        //设置侧滑菜单的点击
        left.setItemClick(new LeftMenuFragment.ItemClick() {
            @Override
            public void itemClick(String url) {
                //调用ContentFragment的setUrl方法,加载另一个网页
                contentFragment.setUrl(url);
                //侧滑菜单消失
                sliding.showContent();
            }
        });


        //设置ActionBar(改了源码继承关系，才有actionbar)
        initActionBar();



    }

    private void initActionBar() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);


    }


    //导航按钮的点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{
                //显示左铡菜单，如果正在显示，就显示内容，菜单消失
                if (getSlidingMenu().isMenuShowing()) {
                    getSlidingMenu().showContent();
                } else {
                    //如果没有显示菜单，就显示菜单
                    getSlidingMenu().showMenu();
                }


            }break;
        }
        return super.onOptionsItemSelected(item);
    }


    /*
    * WebView是否返回
    *
    * */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //判断是否按下返回键
        if(keyCode==KeyEvent.KEYCODE_BACK){
            //判断WebView能否返回
            if(contentFragment.getWebView().canGoBack()){
                //让WebView返回到上一界面
                contentFragment.getWebView().goBack();
                //返回true，不用上级消费此触摸事件
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }
}
