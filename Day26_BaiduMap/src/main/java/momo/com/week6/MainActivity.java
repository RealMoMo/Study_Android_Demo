package momo.com.week6;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatus;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiIndoorResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.route.BikingRouteResult;
import com.baidu.mapapi.search.route.DrivingRouteResult;
import com.baidu.mapapi.search.route.IndoorRouteResult;
import com.baidu.mapapi.search.route.MassTransitRouteLine;
import com.baidu.mapapi.search.route.MassTransitRoutePlanOption;
import com.baidu.mapapi.search.route.MassTransitRouteResult;
import com.baidu.mapapi.search.route.OnGetRoutePlanResultListener;
import com.baidu.mapapi.search.route.PlanNode;
import com.baidu.mapapi.search.route.RoutePlanSearch;
import com.baidu.mapapi.search.route.TransitRouteResult;
import com.baidu.mapapi.search.route.WalkingRouteResult;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements BDLocationListener {

    //声明地图控件
    MapView mapView;
    //声明地图对象
    BaiduMap mBaiduMap;

    //声明定位
    public LocationClient mLocationClient = null;

    //分别对应用户点击的点位点，兴趣点
    private Marker marker, poiMarker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //初始化地图控件
        mapView = (MapView) findViewById(R.id.mapview);

        mBaiduMap = mapView.getMap();
        //初始化定位
        mLocationClient = new LocationClient(getApplicationContext());
        //初始化定位
        initLocation();
        //注册监听函数
        mLocationClient.registerLocationListener(this);
        //开始定位，定位完成后，不管是否成功，都会调用上面的监听函数中的方法
        mLocationClient.start();

        //设置地图的点击事件
        setMapListenter();


    }

    //规划路线的对象
    RoutePlanSearch mSearch;

    //点击，规划两点的路径
    public void routeSearch(View view) {
        //1.获取实例
        mSearch = RoutePlanSearch.newInstance();
        //2.设置监听
        mSearch.setOnGetRoutePlanResultListener(new OnGetRoutePlanResultListener() {

            /*步行检索回调*/
            @Override
            public void onGetWalkingRouteResult(WalkingRouteResult walkingRouteResult) {

            }

            @Override
            public void onGetTransitRouteResult(TransitRouteResult transitRouteResult) {

            }

            /*公交车检索回调*/
            @Override
            public void onGetMassTransitRouteResult(MassTransitRouteResult massTransitRouteResult) {
//                    Log.d("Tag","onGetMassTransitRouteResult:"+massTransitRouteResult.toString());
                int i = 0;
                //解析路线，显示
                //路线集合
                final List<MassTransitRouteLine> lines = massTransitRouteResult.getRouteLines();
                final ArrayList<String> data = new ArrayList<String>();
                for (MassTransitRouteLine line : lines) {
                    i++;
                    data.add("路线" + i);
                }
                //构建Adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, data);


                //点击，显示路径详情
                DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //消失对话框
                        dialog.dismiss();
                        //获取当前的路线
                        MassTransitRouteLine line = lines.get(which);
                        //获取当前路线的总步数
                        List<List<MassTransitRouteLine.TransitStep>> steps = line.getNewSteps();
                        //弹出对话框
                        ArrayList<String> stepData = new ArrayList<String>();
                        for (List<MassTransitRouteLine.TransitStep> tmp : steps) {
                            stepData.add(tmp.get(0).getInstructions());
                        }
                        //构建Adapter
                        ArrayAdapter<String> stepAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, stepData);
                        //显示路径
                        showDialog(data.get(which), stepAdapter, null);
                    }
                };
                //显示
                showDialog("路线选择", adapter, listener);
            }

            @Override
            public void onGetDrivingRouteResult(DrivingRouteResult drivingRouteResult) {

            }

            @Override
            public void onGetIndoorRouteResult(IndoorRouteResult indoorRouteResult) {

            }

            @Override
            public void onGetBikingRouteResult(BikingRouteResult bikingRouteResult) {

            }
        });

        //3.创建两点
        PlanNode startNode = PlanNode.withLocation(marker.getPosition());
        PlanNode endNode = PlanNode.withLocation(poiMarker.getPosition());
        //4.发起请求
        mSearch.masstransitSearch(new MassTransitRoutePlanOption().from(startNode).to(endNode));

    }


    //POI检索 兴趣点实例
    PoiSearch poiSearch;

    //点击，搜索美食
    public void searchFood(View view) {
        //1.获取实例
        poiSearch = PoiSearch.newInstance();
        //2.设置监听
        poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {

            /*基本兴趣点检索*/
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                Log.d("Tag", "onGetPoiResult:" + poiResult.toString());
                //详情检索
                //poiSearch.searchPoiDetail(new PoiDetailSearchOption().poiUid());
                //解析PoiResult
                final List<PoiInfo> pois = poiResult.getAllPoi();
                //
                ArrayList<String> data = new ArrayList<String>();
                for (PoiInfo info : pois) {
                    data.add(info.name);
                }
                //把pois转成ArrayAdapter
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, data);

                DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                    //对话框点击回调方法
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        //先移除地图上的之前的标注点
                        if (poiMarker != null) {
                            poiMarker.remove();
                        }
                        // 对话框消失
                        dialog.dismiss();

                        //在地图上显示当前Poi的位置
                        //获取当前被点击的Poi
                        PoiInfo info = pois.get(which);
                        //获取LatLng
                        LatLng location = info.location;
                        //添加标注
                        //构建Marker图标
                        BitmapDescriptor bitmap = BitmapDescriptorFactory
                                .fromResource(R.mipmap.icon_markb);
                        //构建MarkerOption，用于在地图上添加Marker
                        OverlayOptions option = new MarkerOptions()
                                .position(location)
                                //设置不可拖动marker
                                .draggable(false)
                                .icon(bitmap);
                        //在地图上添加Marker，并显示,保存到poiMarker
                        poiMarker = (Marker) mBaiduMap.addOverlay(option);


                    }
                };

                //弹出对话框，显示兴趣点
                showDialog("周边美食", adapter, listener);

            }

            /*详情检索*/
            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {
//                Log.d("Tag","onGetPoiDetailResult:"+poiDetailResult.toString());
            }

            /*室内检索*/
            @Override
            public void onGetPoiIndoorResult(PoiIndoorResult poiIndoorResult) {
//                Log.d("Tag","onGetPoiIndoorResult:"+poiIndoorResult.toString());
            }
        });

        //3.发起检索请求
        poiSearch.searchNearby(new PoiNearbySearchOption().keyword("美食").pageNum(10).radius(5000).location(marker.getPosition()));
    }


    //弹出对话框
    private void showDialog(String title, BaseAdapter adapter, DialogInterface.OnClickListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setAdapter(adapter, listener);
        builder.show();


    }

    private void setMapListenter() {
        //地图点击监听
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {

            /**
             * 地图点击方法
             * */
            @Override
            public void onMapClick(LatLng latLng) {

                //添加标注之前，先清空(clear清除所有覆盖物，不止是标注哦~~~)
                // mBaiduMap.clear();

                //先移除先前的覆盖物
                if (marker != null) {
                    marker.remove();
                }

                //添加覆盖物
                //定义Maker坐标点
//                LatLng latLng1=new LatLng(2f,3f);

                //构建Marker图标
                BitmapDescriptor bitmap = BitmapDescriptorFactory
                        .fromResource(R.mipmap.myloc);
                //构建MarkerOption，用于在地图上添加Marker
                OverlayOptions option = new MarkerOptions()
                        .position(latLng)
                        //设置可拖动marker
                        .draggable(true)
                        .icon(bitmap);
                //在地图上添加Marker，并显示
                marker = (Marker) mBaiduMap.addOverlay(option);
            }


            /**
             *
             * 地图上的兴趣点的点击事件
             * */
            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });

        //覆盖物的拖动监听
        mBaiduMap.setOnMarkerDragListener(new BaiduMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDrag(Marker marker) {
                // Log.d("Tag","onMarkerDrag:"+marker.getPosition().latitude);
            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                //Log.d("Tag","onMarkerDragEnd:"+marker.getPosition().latitude);
                //拖动结束后，根据用户选择的地方进行业务处理

            }

            @Override
            public void onMarkerDragStart(Marker marker) {
                // Log.d("Tag","onMarkerDragStart:"+marker.getPosition().latitude);
            }
        });

    }

    private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Hight_Accuracy
        );//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
        int span = 1000;
        option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setOpenGps(true);//可选，默认false,设置是否使用gps
        option.setLocationNotify(true);//可选，默认false，设置是否当GPS有效时按照1S/1次频率输出GPS结果
        option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setIgnoreKillProcess(false);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤GPS仿真结果，默认需要
        mLocationClient.setLocOption(option);

    }

    //点击事件，切换地图
    public void changeMap(View view) {
        //根据id
        switch (view.getId()) {
            case R.id.rb1: {
                //普通地图
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);

            }
            break;
            case R.id.rb2: {
                //卫星地图
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);

            }
            break;
        }

    }

    //点击，切换交通开关
    public void changeJT(View view) {
        //把view强转成CheckBox
        CheckBox cb = (CheckBox) view;
        //获取CheckBox的状态
        //设置
        //开启/关闭交通图
        mBaiduMap.setTrafficEnabled(cb.isChecked());

    }

    //点击，切换热力开关
    public void changeheatMap(View view) {
        CheckBox cb = (CheckBox) view;
        //开启/关闭热力地图
        mBaiduMap.setBaiduHeatMapEnabled(cb.isChecked());
    }


    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        //释放POI检索
        if (poiSearch != null) {
            poiSearch.destroy();
        }
        //释放释放路径检索
        if (mSearch != null) {
            mSearch.destroy();
        }
    }


    /*
    *
    * 定位返回结果
    *
    * 失败或成功，都会返回
    * */
    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        //查看定位结果
        Log.d("Tag", "定位返回----" + bdLocation.getAddrStr());

        //定位成功后，把定位的点，设置地图的中心点
        //包装经纬,从定位信息中，获取经纬度
        LatLng currentLatLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());

        //构建地图状态
        MapStatus status = new MapStatus.Builder()
                //包装经纬度
                .target(currentLatLng)
                //缩放等级
                .zoom(15)
                //构建
                .build();
        //通过地图工厂设置地图状态
        MapStatusUpdate update = MapStatusUpdateFactory.newMapStatus(status);
        //更新地图界面
        mBaiduMap.setMapStatus(update);

    }
}
