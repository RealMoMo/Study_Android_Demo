package com.momo.reviewandroid;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by RealMo on 2017-02-08.
 */
public class SimpleAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private LayoutInflater mInflater;
    private Context mContext;
    private List<String> mDatas;

    public interface OnItemClickListener{
        void onItemClick(View view,int postion);
        void onItemLongClick(View view,int postion);
    }

    OnItemClickListener mOnItemClickListener;

    public void setmOnItemClickListener(OnItemClickListener listener){
        mOnItemClickListener = listener;
    }



    public SimpleAdapter(Context context, List<String>datas) {
        this.mContext = context;
        this.mDatas = datas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.item1_layout,parent,false);
        MyViewHolder viewHolder = new MyViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
            holder.tv.setText(mDatas.get(position));

            if(mOnItemClickListener!=null) {

                //click
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//布局中item的位置（因为添加删除我并没有使用setDataNotifyChange方法）
                         int layoutPostion = holder.getLayoutPosition();
                        mOnItemClickListener.onItemClick(holder.itemView, layoutPostion);

                    }
                });

                //longclick
                holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        //布局中item的位置（因为添加删除我并没有使用setDataNotifyChange方法）
                         int layoutPostion = holder.getLayoutPosition();
                        mOnItemClickListener.onItemLongClick(holder.itemView,layoutPostion);
                        return false;
                    }
                });
            }
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }


    public void addData(int pos){
        mDatas.add(pos,"Insert");
        notifyItemInserted(pos);
    }

    public void delData(int pos){
        mDatas.remove(pos);
        notifyItemRemoved(pos);
    }
}

class MyViewHolder extends RecyclerView.ViewHolder {

    TextView tv;

    public MyViewHolder(View itemView) {
        super(itemView);
        tv = (TextView) itemView.findViewById(R.id.item1_tv);
    }
}
