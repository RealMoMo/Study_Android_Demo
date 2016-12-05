package momo.com.day40_myjoke_fromhttp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Administrator on 2016/12/2 0002.
 */
public class JokeAdapter extends RecyclerView.Adapter<JokeAdapter.ViewHolder> {


    List<JokeBean> data;
    LayoutInflater inflater;
    Context context;

    public JokeAdapter(Context context,List<JokeBean> data) {
        this.data = data;
        this.inflater = LayoutInflater.from(context);
        this.context =context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.recyclerview_layout, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_content.setText(data.get(position).content);
        holder.view.setTag(position);

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        TextView tv_content;
        ImageView iv_thumb;
        View view;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_content = (TextView) itemView.findViewById(R.id.tv_content);
            view = itemView;
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = (int) v.getTag();
            Intent intent = new Intent(context,DetailActivity.class);
            intent.putExtra("url",data.get(position).contentHerf);
            context.startActivity(intent);
        }
    }
}
