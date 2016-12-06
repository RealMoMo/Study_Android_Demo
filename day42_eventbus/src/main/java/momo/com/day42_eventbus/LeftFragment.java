package momo.com.day42_eventbus;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

/**
 * 发送方
 */
public class LeftFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view=inflater.inflate(R.layout.left_layout,container,false);
        Button btn = (Button) view.findViewById(R.id.left_btn);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().post("Hello EventBus");
                EventBus.getDefault().post(new MsgBean());
                Log.d("Tag", "run: 发送：" + Thread.currentThread().getName());
            }
        });

        Button btn1 = (Button) view.findViewById(R.id.left_btn2);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(){
                    @Override
                    public void run() {
                        super.run();
                        EventBus.getDefault().post("Hello EventBus");

                        Log.d("Tag", "run: 发送：" + Thread.currentThread().getName());
                    }
                }.start();


            }
        });

        return view;
    }
}
