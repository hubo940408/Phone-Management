package personal.edu.phoneh.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import personal.edu.phoneh.R;
import personal.edu.phoneh.shared.Shared_Main;
import personal.edu.phoneh.shared.Shared_Right;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class Activity_Mean_Right extends AppCompatActivity implements View.OnClickListener{
    CheckBox start_cb,notice_cb,xiaoxi_cb;
    ImageView im;
    TextView name_tv;
    Shared_Right sr;
    RelativeLayout help_rl,about_us;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mean_right);
        inClick();
        getfind();
    }
    void inClick(){//初始化控件
        about_us= (RelativeLayout) findViewById(R.id.right_aboutus_rl);
        im= (ImageView) findViewById(R.id.top_left);
        im.setImageResource(R.mipmap.btn_homeasup_default);
        name_tv= (TextView) findViewById(R.id.top_name_tv);
        name_tv.setText("设置");
        start_cb= (CheckBox) findViewById(R.id.right_start_cb);
        notice_cb= (CheckBox) findViewById(R.id.right_notice_cb);
        xiaoxi_cb= (CheckBox) findViewById(R.id.right_xiaoxi_cb);
        help_rl= (RelativeLayout) findViewById(R.id.set_help_rl);
        sr=new Shared_Right();
        getShared();
    }

    void getfind(){//控件监听
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.from_left,R.anim.to_right);
            }
        });
        notice_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                sr.outnoitce(Activity_Mean_Right.this,isChecked);
                if(isChecked){
                    show();
                }else{
                    notificationManager.cancel(1);
                }
            }
        });
        start_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sr.outstart(Activity_Mean_Right.this,isChecked);
            }
        });
        xiaoxi_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sr.outxiaoxi(Activity_Mean_Right.this,isChecked);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        help_rl.setOnClickListener(this);
        start_cb.setOnClickListener(this);
        about_us.setOnClickListener(this);
    }
   void getShared(){
       boolean noitce=sr.intnoitce(this);
       if (noitce){
           show();
       }
       notice_cb.setChecked(noitce);
       boolean start=sr.intstart(this);
       start_cb.setChecked(start);
       boolean xiaoxi=sr.intxiaoxi(this);
       xiaoxi_cb.setChecked(xiaoxi);
   }
    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case R.id.right_aboutus_rl:
                intent=new Intent(this,Activity_Mean_Left.class);
                startActivity(intent);
                break;
            case R.id.set_help_rl:
                intent=new Intent(Activity_Mean_Right.this,Activity_Guide.class);
                Shared_Main sm=new Shared_Main();
                sm.outspf(this,0);
                sm.outactivity(this,1);
                startActivity(intent);
                break;
        }
    }
    void show(){
         //通知栏管理器
        NotificationManager notificationManager= (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        //通知栏构造器
        NotificationCompat.Builder no=new NotificationCompat.Builder(this);

        Intent intent=new Intent(this,Activity_Mean.class);
        //第一个参数上下文对象，第二个参数。标识，第三个  intent对象，4标签
        no.setContentTitle("手机保护");//设置标题
        no.setContentText("手机管家正在保护你的手机...");//通知内容
        no.setSmallIcon(R.mipmap.app);//设置提示小图标
        no.setAutoCancel(true);//设置点击后是否消失
        no.setDefaults(Notification.DEFAULT_ALL);
        no.setWhen(System.currentTimeMillis());//设置时间
        PendingIntent pendingIntent=PendingIntent.getActivity(this,1,intent,Notification.FLAG_AUTO_CANCEL);
        no.setContentIntent(pendingIntent);//设置通知的点击效果
        Notification notification=no.build();
        notification.flags=Notification.FLAG_AUTO_CANCEL;
        notificationManager.notify(1, notification);
    }
}
