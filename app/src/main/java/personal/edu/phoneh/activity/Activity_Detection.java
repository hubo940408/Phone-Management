package personal.edu.phoneh.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import java.util.ArrayList;

import personal.edu.phoneh.R;
import personal.edu.phoneh.adapter.Detection_Adapter;
import personal.edu.phoneh.biz.CommonUtil;
import personal.edu.phoneh.biz.MemoryManager;
import personal.edu.phoneh.biz.PhoneManager;
import personal.edu.phoneh.entity.Detection_User;

/**
 * 手机检测界面
 * Created by Administrator on 2016/11/28 0028.
 */
public class Activity_Detection extends AppCompatActivity {
    MyReceive mr;
    RelativeLayout  rl_detection;
    LinearLayout run_rl;
    ImageView teturn_im;
    Detection_Adapter da;
    ProgressBar progressBar;
    ListView listView;
    ArrayList<Detection_User>list;
    Button detection_bt;
    TextView electricity_tv,dettection_tv,dettection_t_tv,name_tv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection);
        inClick();
        getfind();
    }
    void inClick(){//初始化控件
        PhoneManager phoneManager=new PhoneManager(this);
        detection_bt= (Button) findViewById(R.id.detection_queding_bt);
        name_tv= (TextView) findViewById(R.id.top_name_tv);
        name_tv.setText("手机检测");
        teturn_im= (ImageView) findViewById(R.id.top_left);
        electricity_tv= (TextView) findViewById(R.id.detection_tectview_tv);
        teturn_im.setImageResource(R.mipmap.btn_homeasup_default);
        run_rl= (LinearLayout) findViewById(R.id.detction_run_rl);
        progressBar= (ProgressBar) findViewById(R.id.dete_big);
        rl_detection= (RelativeLayout) findViewById(R.id.rl_detection_rl);
        dettection_tv= (TextView) findViewById(R.id.dettection_battery_tv);
        dettection_t_tv= (TextView) findViewById(R.id.dettection_t_battery_tv);
        mr=new MyReceive();
        list=new ArrayList<>();
        list.add(new Detection_User("设备名称："+ PhoneManager.getPhoneName1(),
                "系统版本：" + PhoneManager.getPhoneSystemVersion(),R.mipmap.photo1));
        list.add(new Detection_User("全部运行内存："+ CommonUtil.getFileSize(PhoneManager.getPhoneTotalRamMemory()),
                "剩余运行内存："+ CommonUtil.getFileSize(MemoryManager.getPhoneFreeRamMemory(this)),R.mipmap.photo2));
        list.add(new Detection_User("CPU名称："+PhoneManager.getPhoneCpuName(),
                "CPU数量："+PhoneManager.getPhoneCpuNumber(),R.mipmap.photo3));
        list.add(new Detection_User("手机分辨率："+phoneManager.getResolution(),
                "相机分辨率："+phoneManager.getMaxPhotoSize(),R.mipmap.photo4));
        list.add(new Detection_User("基带版本："+phoneManager.getPhoneSystemBasebandVersion(),
                "是否带ROOT:"+(phoneManager.isRoot()?"是":"否"),R.mipmap.photo5));
        listView= (ListView) findViewById(R.id.detection_listview_lv);
        da=new Detection_Adapter(this);
        da.setList(list);
        listView.setAdapter(da);
    }

    void getfind(){//控件监听
        teturn_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.in_down,R.anim.to_down);
            }
        });

        run_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            rl_detection.setVisibility(View.VISIBLE);
            }
        });

        detection_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("msg","隐藏");
                rl_detection.setVisibility(View.GONE);
            }
        });
    }
    //系统广播注册
    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(mr,filter);
    }
    //关闭广播
    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mr);
    }
    int setPro;
    int currttemp;

    class MyReceive extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Intent.ACTION_BATTERY_CHANGED)) {
                Bundle bundle = intent.getExtras();
                int maxBattery = (Integer) bundle.get(BatteryManager.EXTRA_SCALE); // 总电量
                setPro = (Integer) bundle.get(BatteryManager.EXTRA_LEVEL); // 当前电量
                currttemp = (Integer) bundle.get(BatteryManager.EXTRA_TEMPERATURE); // 电池温度
                progressBar.setMax(maxBattery);
                dettection_t_tv.setText("电池温度："+currttemp+"℉");
                dettection_tv.setText("当前电量："+setPro*100/maxBattery+"%");
                //开启子线程
               getpro(setPro,progressBar.getProgress());
            }

        }
    }
public void getpro(final int number,final int oldnumber){
            new Thread(new Runnable() {
                @Override
                public void run() {
                    int to_number=number;
                    int old_number=oldnumber;
                    if(to_number>old_number){//电量增加
                        while(old_number<to_number){
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            old_number++;

                            Message ms=new Message();
                            ms.obj=old_number;//传递过去的电量
                            ms.what=0;
                            handler.sendMessage(ms);
                        }
                    }else if(to_number<old_number){
                        while(to_number<old_number){
                            try {
                                Thread.sleep(50);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            old_number--;
                            //告知主线程进行修改
                            //发送消息
                            Message ms=new Message();
                            ms.obj=old_number;//传递过去的电量
                            ms.what=1;
                            handler.sendMessage(ms);
                        }
                    }
                }
            }).start();
}



    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    int aa= (int) msg.obj;
                    progressBar.setProgress(aa);
                    electricity_tv.setText(aa+"%");
                    break;
                case 1:
                    int aaa= (int) msg.obj;
                    progressBar.setProgress(aaa);
                    electricity_tv.setText(aaa+"%");
                    break;
            }
        }
    };

}
