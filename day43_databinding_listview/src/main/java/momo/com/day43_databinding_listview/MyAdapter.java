package momo.com.day43_databinding_listview;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

/**
 * Created by Administrator on 2016/12/7 0007.
 */
public class MyAdapter<T> extends BaseAdapter {

    private List<T> list;
    private int layoutResId;
    private Context context;
    private LayoutInflater inflater;
    //根据我们的实体类,variableId是系统自动生成的,直接从外部传入即可
    private int variableId;

    public MyAdapter(List<T> list, int layoutResId, Context context, int variableId) {
        this.list = list;
        this.layoutResId = layoutResId;
        this.context = context;
        this.variableId = variableId;
        this.inflater = LayoutInflater.from(context);
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
        ViewDataBinding binding = null;
        if (convertView == null) {
            binding = DataBindingUtil.inflate(inflater, layoutResId, parent, false);

        }else{
            binding = DataBindingUtil.getBinding(convertView);
        }

        binding.setVariable(variableId,list.get(position));

        return binding.getRoot();
    }
}
