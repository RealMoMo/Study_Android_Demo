package momo.com.day57_observer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class CActivity extends AppCompatActivity implements View.OnClickListener {


    private Button btn_a,btn_b;

    private MyObserver observer =MyObserver.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_layout);

        setupViews();
    }

    private void setupViews() {
        btn_a = (Button) findViewById(R.id.btn_a);
        btn_b = (Button) findViewById(R.id.btn_b);

        btn_a.setOnClickListener(this);
        btn_b.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        MessageImage msg = new MessageImage();
        switch (v.getId()){
            case R.id.btn_a:{
                msg.setFromType(MessageImage.FROM_A);

            }break;
            case R.id.btn_b:{
                msg.setFromType(MessageImage.FROM_B);
            }break;
        }
        msg.setImgId(R.mipmap.bq4);
        observer.notifyUpdate(msg);
        msg =null;

    }
}
