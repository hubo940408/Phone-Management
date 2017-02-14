package personal.edu.phoneh.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import personal.edu.phoneh.R;
import personal.edu.phoneh.adapter.ManageMessage_Adapter;
import personal.edu.phoneh.biz.AppInfoManager;
import personal.edu.phoneh.entity.AppInfo;
import personal.edu.phoneh.entity.Manage_Message;
import personal.edu.phoneh.shared.Shared_Number;

/**
 * Created by Administrator on 2016/11/30 0030.
 */
public class Activity_ManageMessage extends AppCompatActivity {
    ArrayList<AppInfo>list;
    ManageMessage_Adapter mma;
    MyReciver my;
    Myunload mu;
    int key=1;
    ListView lv;
    Shared_Number sm;
    RelativeLayout mesme_pb;
    ImageView rl_return;
    TextView topname;
    Button botbut;
    CheckBox botimage;
    void inClick(){
        mesme_pb= (RelativeLayout) findViewById(R.id.mesmes_pb_rl);
        botbut= (Button) findViewById(R.id.manage_message_button_bt);
        botimage= (CheckBox) findViewById(R.id.manage_message_botimage_iv);
        lv= (ListView) findViewById(R.id.manage_message_listview_lv);
        rl_return= (ImageView) findViewById(R.id.top_left);
        rl_return.setImageResource(R.mipmap.btn_homeasup_default);
        topname= (TextView) findViewById(R.id.top_name_tv);
        sm=new Shared_Number();
        String toname=sm.intspfname(Activity_ManageMessage.this);
        topname.setText(toname);
    }

    class Myset implements Runnable {
        @Override
        public void run() {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            sm=new Shared_Number();
            String toname=sm.intspfname(Activity_ManageMessage.this);
            Message ms=new Message();
            ms.what=1;
            ms.obj=toname;
            handler.sendMessage(ms);
        }
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            list=new ArrayList<>();
            AppInfoManager appInfoManager=AppInfoManager.getAppInfoManager(Activity_ManageMessage.this);
            switch (msg.what){
                case 1:
                    String toname= (String) msg.obj;

                    switch (toname) {
                        case "所有软件":
                            list = (ArrayList<AppInfo>) appInfoManager.getAllPackageInfo(true);
                            Log.i("msg", "所有软件个数：" + list.size());
                            break;
                        case "系统软件":
                            list = (ArrayList<AppInfo>) appInfoManager.getSystemPackageInfo(true);
                            Log.i("msg", "系统软件个数：" + list.size());
                            break;
                        case "用户软件":
                            list = (ArrayList<AppInfo>) appInfoManager.getUserPackageInfo(true);
                            Log.i("msg", "用户软件个数：" + list.size());
                            break;
                    }
                    mma=new ManageMessage_Adapter(Activity_ManageMessage.this);
                    mesme_pb.setVisibility(View.GONE);
                    lv.setVisibility(View.VISIBLE);
                    mma.setList(list);
                    lv.setAdapter(mma);
                    break;
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_message);
        inClick();
        new Thread(new Myset()).start();

    }

    void getclick(){
        my=new MyReciver();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("messall_cb");
        registerReceiver(my,intentFilter);
    }

    void getunload(){
        mu=new Myunload();
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction(Intent.ACTION_PACKAGE_REMOVED);
        intentFilter.addDataScheme("package");
        Log.i("msg","注册卸载广播");
        registerReceiver(mu,intentFilter);
    }
    @Override
    protected void onResume() {
        super.onResume();
        getfind();
        getclick();
        getunload();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(my);
        unregisterReceiver(mu);
    }

    void getfind(){//控件监听
        botimage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (key != 0) {
                    for (int i = 0; i < list.size(); i++) {
                        list.get(i).setDel(isChecked);
                    }
                    mma.notifyDataSetChanged();
                }
            }
        });
        //卸载
        botbut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<AppInfo> list=mma.getList();
                for (AppInfo appInfo:list){
                    if(appInfo.isDel()){
                        String packname=appInfo.getPackageInfo().packageName;
                        Intent intent=new Intent(Intent.ACTION_DELETE);
                        intent.setData(Uri.parse("package:"+packname));
                        startActivity(intent);
                    }
                }
            }
        });

        //快速滑动监听
        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState){
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING: // 快速滑动时

                        mma.setFling(true);
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滑动时
         ;
                        mma.setFling(false);
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE: // 停止滑动时

                        mma.setFling(false);
                        mma.notifyDataSetChanged();
                        break;
                }
            }
            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        rl_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    class MyReciver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String Action=intent.getAction();//获得Action
            if(Action.equals("messall_cb")){//判断Action
                key=0;
                key=intent.getIntExtra("messall",0);
                if (key==1){
                    botimage.setChecked(true);
                }else {
                    Log.i("msg","key:"+key);
                    botimage.setChecked(false);
                }
                key=1;
            }

        }
    }

    class Myunload extends BroadcastReceiver{
        @Override
        public void onReceive(Context context, Intent intent) {
            String action=intent.getAction();
            if(action.equals(Intent.ACTION_PACKAGE_REMOVED)){
                Log.i("msg","接受卸载广播");
                String packagename=intent.getDataString();
                for (int i=0;i<list.size();i++){
                    if(("package:"+list.get(i).getPackageInfo().packageName).equals(packagename)){
                        list.remove(i);
                        mma.notifyDataSetChanged();
                    }
                }
            }
        }
    }


}
