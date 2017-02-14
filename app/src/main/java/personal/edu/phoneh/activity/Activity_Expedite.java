package personal.edu.phoneh.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import personal.edu.phoneh.R;
import personal.edu.phoneh.adapter.Expedite_Adapter;
import personal.edu.phoneh.biz.AppInfoManager;
import personal.edu.phoneh.biz.CommonUtil;
import personal.edu.phoneh.biz.MemoryManager;
import personal.edu.phoneh.biz.PhoneManager;
import personal.edu.phoneh.entity.RuningAppInfo;
import personal.edu.phoneh.shared.Shared_Expedite;

/**
 * 手机加速页面
 * Created by Administrator on 2016/11/28 0028.
 */
public class Activity_Expedite extends AppCompatActivity implements View.OnClickListener{
    RelativeLayout gong_pro;
    MyReciver my;
    ImageView im;
    int key=1;
    Button top_but,botton_bt;
    Map<Integer, List<RuningAppInfo>> maplist;
    CheckBox all_cb;
    TextView phone_tv,phoneversions_tv,progress_tv,name_tv;
    ProgressBar progress_pb;
    ListView listView;
    ArrayList<RuningAppInfo>list;
    Expedite_Adapter ea;
    Shared_Expedite se;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acyivity_expedite);
        inClick();
        infind();
    }
    void inClick(){//初始化控件
        ea=new Expedite_Adapter(this);
        gong_pro= (RelativeLayout) findViewById(R.id.expedite_gong_pro);
        all_cb= (CheckBox) findViewById(R.id.expedite_allbotton_bot);
        name_tv= (TextView) findViewById(R.id.top_name_tv);
        name_tv.setText("手机加速");
        phone_tv= (TextView) findViewById(R.id.expedite_phone_tv);
        botton_bt= (Button) findViewById(R.id.expedite_botton_button_bt);
        phoneversions_tv= (TextView) findViewById(R.id.expedite_phone_versions_tv);
        progress_tv= (TextView) findViewById(R.id.expedite_progress_tv);
        top_but= (Button) findViewById(R.id.expedite_top_button_bt);
        progress_pb= (ProgressBar) findViewById(R.id.expedite_progress_pb);
        listView= (ListView) findViewById(R.id.expedite_listview_lv);
        im= (ImageView) findViewById(R.id.top_left);
        im.setImageResource(R.mipmap.btn_homeasup_default);
        se=new Shared_Expedite();
    }

    class myRunnablr implements Runnable {
        @Override
        public void run() {
            Message ms=new Message();
            ms.what=0;
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            handler.sendMessage(ms);
        }
        }


    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    gong_pro.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    break;
            }
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        myRunnablr mr=new myRunnablr();
        Thread th=new Thread(mr);
        Log.i("msg","开始子线程");
        th.start();
        getfind();
        top_but.setOnClickListener(this);
        all_cb.setOnClickListener(this);
        getClick();
        my=new MyReciver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("all_cb");
        registerReceiver(my,intentFilter);

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(my);
    }

    class MyReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String Action=intent.getAction();//获得Action
            if(Action.equals("all_cb")){//判断Action
                    key=0;
                if (intent!=null){
                    key=intent.getIntExtra("all",0);
                    if (key==1){
                        all_cb.setChecked(true);
                    }else {
                        Log.i("msg","key:"+key);
                        all_cb.setChecked(false);
                    }
                }
                key=1;
            }
        }
    }


    void getClick(){
        String phonename=PhoneManager.getPhoneName1().toUpperCase();
        phone_tv.setText(phonename);
        phoneversions_tv.setText(Build.MODEL+" Android "+PhoneManager.getPhoneSystemVersion());
        progress_tv.setText("已用内存："+ CommonUtil.getFileSize(PhoneManager.getPhoneTotalRamMemory()
                - MemoryManager.getPhoneFreeRamMemory(this))
                +"/"+CommonUtil.getFileSize(PhoneManager.getPhoneTotalRamMemory()));
        progress_pb.setProgress((int) ((PhoneManager.getPhoneTotalRamMemory()
                        - MemoryManager.getPhoneFreeRamMemory(this))*100/PhoneManager.getPhoneTotalRamMemory()));
        top_but.setText("一键清理");

    }
    void getfind(){//控件监听
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.in_down,R.anim.to_down);
            }
        });

        botton_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                list=new ArrayList<>();
                maplist=new HashMap<>();
                AppInfoManager appInfoManager=AppInfoManager.getAppInfoManager(Activity_Expedite.this);
                maplist=appInfoManager.getRuningAppInfos();
                String expeditename= se.intspf(Activity_Expedite.this);

                if(expeditename.equals("系统")){
                    all_cb.setChecked(false);
                    botton_bt.setText("只显示用户进程");
                    infind();
                    se.outspf(Activity_Expedite.this,"用户");
                }else{
                    botton_bt.setText("显示系统进程");
                    all_cb.setChecked(true);
                    infind();
                    se.outspf(Activity_Expedite.this,"系统");
                }

            }
        });
        all_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if(key!=0){
                    checkall(isChecked);
                    ea.setList(list);
                    ea.notifyDataSetChanged();
                }
            }
        });


    }
    public void checkall(boolean flag){
        for(int i=0;i<list.size();i++){
            list.get(i).setClear(flag);
            Log.i("msg",flag+"");
        }
    }

    void infind(){
        list=new ArrayList<>();
        maplist=new HashMap<>();
        AppInfoManager appInfoManager=AppInfoManager.getAppInfoManager(Activity_Expedite.this);
        maplist=appInfoManager.getRuningAppInfos();
        String expeditename= se.intspf(Activity_Expedite.this);
        if(expeditename.equals("系统")){
            se.outspf(Activity_Expedite.this,"用户");
            botton_bt.setText("只显示用户进程");
            list= (ArrayList<RuningAppInfo>) maplist.get(0);
        }else{
            se.outspf(Activity_Expedite.this,"系统");
            botton_bt.setText("显示系统进程");
            list= (ArrayList<RuningAppInfo>) maplist.get(1);
        }
        ea.setList(list);
        listView.setAdapter(ea);

    }

    @Override
    public void onClick(View v) {
        AppInfoManager appInfoManager=AppInfoManager.getAppInfoManager(Activity_Expedite.this);
        List<RuningAppInfo> appInfos=ea.getList();
        Log.i("msg",appInfos.size()+"");
        switch (v.getId()){
            case R.id.expedite_top_button_bt:      //一键清理
                String expeditename= se.intspf(this);
                if(expeditename.equals("系统")){
                    se.outspf(this,"系统");
                }else {
                    se.outspf(this,"用户");
                }
                for(RuningAppInfo runingAppInfo:appInfos){
                    if(runingAppInfo.isClear()){
                        Log.i("msg",runingAppInfo.getPackageName());
                        appInfoManager.killProcesses(runingAppInfo.getPackageName());
                    }
                }
                infind();
                ea.notifyDataSetChanged();
                Log.i("msg",appInfos.size()+"刷新");
                getClick();
                break;

        }
    }

}
