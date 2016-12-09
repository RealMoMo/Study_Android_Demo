package momo.com.day45_listview_vedio;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2016/12/9 0009.
 */
public class MyAdapter extends BaseAdapter{

    private List<VideoBean> list;
    private Context context;
    private LayoutInflater inflater;
    private MediaPlayer player;
    //当前正在播放item的position，-1 表示当前没有item在播放
    private  int currentPosition = -1;

    public MyAdapter(List<VideoBean> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
        player = new MediaPlayer();
        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
            }
        });
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if(convertView == null){
            convertView = inflater.inflate(R.layout.lv_item,parent,false);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.cover = (ImageView) convertView.findViewById(R.id.cover);
            holder.surfaceView = (SurfaceView) convertView.findViewById(R.id.surface_view);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        VideoBean videoBean = list.get(position);
        ViewGroup.LayoutParams params = holder.cover.getLayoutParams();
        params.width = videoBean.getWidth();
        params.height = videoBean.getHeight();
        holder.cover.requestLayout();
        holder.surfaceView.setLayoutParams(params);
        Picasso.with(context).load(videoBean.getCoverUrl()).into(holder.cover);
        holder.title.setText(videoBean.getTitle());


        Object tag = holder.cover.getTag();
        //判断正在播放的视频的item是否滑出屏幕
        if(tag!=null){
            Integer tag1 = (Integer) tag;
            if(tag1==currentPosition && tag1 !=position){
                player.stop();
                currentPosition= -1;
            }
        }

        holder.cover.setTag(position);


        if(currentPosition == position){
            holder.surfaceView.setVisibility(View.VISIBLE);
            holder.cover.setVisibility(View.INVISIBLE);

            player.reset();

            try {
                player.setDisplay(holder.surfaceView.getHolder());
                player.setDataSource(videoBean.getVideoUrl());
                player.prepareAsync();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }else{
            holder.cover.setVisibility(View.VISIBLE);
            holder.surfaceView.setVisibility(View.INVISIBLE);
        }



        //图片的点击事件
        holder.cover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer index = (Integer) v.getTag();
                //设置当前播放位置
                currentPosition = index;
                if(player!=null&&player.isPlaying()){
                    player.stop();

                }
                //重走getview方法
                notifyDataSetChanged();
            }
        });


        return convertView;
    }

    class ViewHolder {
        SurfaceView surfaceView;
        ImageView cover;
        TextView title;
    }
}
