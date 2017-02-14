package personal.edu.phoneh.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import personal.edu.phoneh.R;
import personal.edu.phoneh.biz.AppInfoManager;
import personal.edu.phoneh.biz.MemoryManager;
import personal.edu.phoneh.biz.PhoneManager;
import personal.edu.phoneh.entity.ClassUser;
import personal.edu.phoneh.adapter.GridView_Adapter;
import personal.edu.phoneh.entity.Object;
import personal.edu.phoneh.entity.RuningAppInfo;
import personal.edu.phoneh.shared.RoundProgressBar;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class Activity_Mean extends AppCompatActivity implements View.OnClickListener{

    ImageView imageView_left,imageView_right;
    RoundProgressBar mean_pb;
    TextView mean_number,mean_home,mean_top;
    Map<Integer, List<RuningAppInfo>> maplist;
    int number_home;
    GridView gv;
    boolean run=false;
    ArrayList<RuningAppInfo> appInfos;
    RelativeLayout photo_run,mean_rocket,right_rl;
    ArrayList<Object> list;
    ArrayList<ClassUser>classUsers;
    GridView_Adapter g_a;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mean);
        inClick();
        putclass();
        setClick();
        gethome();
        gv= (GridView) findViewById(R.id.mean_gridview_gv);
        g_a=new GridView_Adapter(this);
        g_a.setList(list);
        gv.setAdapter(g_a);
        getClick(1);
    }
    void gethome(){
        photo_run= (RelativeLayout) findViewById(R.id.mean_photo_run);
        photo_run.setBackgroundResource(R.mipmap.first);
        right_rl= (RelativeLayout) findViewById(R.id.top_right_rl);
        mean_top= (TextView) findViewById(R.id.top_name_tv);
        mean_home= (TextView) findViewById(R.id.mean_home_tv);
        mean_pb= (RoundProgressBar) findViewById(R.id.mean_round_pb);
        imageView_left= (ImageView) findViewById(R.id.top_left);
        imageView_right= (ImageView) findViewById(R.id.top_right);
        mean_rocket= (RelativeLayout) findViewById(R.id.mean_rocket_rl);
        mean_number= (TextView) findViewById(R.id.mean_number_tv);
        appInfos=new ArrayList<>();
        maplist=new HashMap<>();
        mean_top.setText("系统管家");
        right_rl.setBackgroundResource(R.drawable.mean_border);
        imageView_left.setImageResource(R.mipmap.app);
        imageView_right.setImageResource(R.mipmap.ic_child_configs);
        mean_number.setText(0+"");
        mean_pb.setMax(100);
        mean_pb.setProgress(0);
    }
    public void inClick(){
        list=new ArrayList<>();
        list.add(new Object("手机加速",R.mipmap.icon_rocket));
        list.add(new Object("软件管理",R.mipmap.icon_softmgr));
        list.add(new Object("手机检测",R.mipmap.icon_phonemgr));
        list.add(new Object("通讯大全",R.mipmap.icon_telmgr));
        list.add(new Object("文件管理",R.mipmap.icon_filemgr));
        list.add(new Object("垃圾清理",R.mipmap.icon_sdclean));
    }
    void putclass(){
        classUsers=new ArrayList<>();
        classUsers.add(new ClassUser(Activity_Expedite.class));//手机加速
        classUsers.add(new ClassUser(Activity_Manage.class));//软件管理
        classUsers.add(new ClassUser(Activity_Detection.class));//手机检测
        classUsers.add(new ClassUser(Activity_Phone.class));//通讯大全
        classUsers.add(new ClassUser(Activity_Paper.class));//文件管理
        classUsers.add(new ClassUser(Activity_Clear.class));//垃圾清理
    }

    @Override
    protected void onResume() {
        super.onResume();
        imageView_left.setOnClickListener(this);
        imageView_right.setOnClickListener(this);

        outClick();

    }
    void getClick(final int i){
         number_home = (int) ((PhoneManager.getPhoneTotalRamMemory()
                - MemoryManager.getPhoneFreeRamMemory(this)) * 100
                / PhoneManager.getPhoneTotalRamMemory());
        final Timer timer=new Timer();
         TimerTask task =new TimerTask() {
            @Override
            public void run() {

                  String num=mean_number.getText().toString();
                  int number=Integer.parseInt(num);
                  Message mss=new Message();
                if(i==1){
                    Log.i("msg","getClick(1)");
                    int numberb=number_home;
                    if(number==numberb){
                        run=false;
                        timer.cancel();
                    }
                    mss.what=2;
                }else if(i==2){
                    Log.i("msg","getClick(2)");
                    if(number==1){
                        timer.cancel();
                        getClick(1);
                    }
                    mss.what=3;
                }
                  mss.obj=number;
                  handler.sendMessage(mss);
            }
        };
        timer.schedule(task,10,10);
    }

    void setClick(){
        Timer timer=new Timer();
        TimerTask task =new TimerTask() {
            @Override
            public void run() {
                Message mss=new Message();
                mss.what=1;
                handler.sendMessage(mss);
            }
        };
        timer.schedule(task,2*1000);

    }

    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    Animation animation= AnimationUtils.loadAnimation(Activity_Mean.this,R.anim.animation_alpha);
                    photo_run.startAnimation(animation);
                    photo_run.setVisibility(View.GONE);
                    break;
                case 2:
                    int setnumber= (int) msg.obj;
                    mean_number.setText((setnumber+1)+"");
                    mean_pb.setProgress(setnumber);
                    break;
                case 3:
                    int setnum= (int) msg.obj;
                    mean_number.setText((setnum-1)+"");
                    mean_pb.setProgress(setnum);
                    break;
            }
        }
    };


    void outClick(){
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(Activity_Mean.this,classUsers.get(position).getaClass());
                startActivity(intent);
                overridePendingTransition(R.anim.bot_up,R.anim.to_bot_up);
            }
        });
        mean_rocket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!run){
                    getClick(2);
                    run=true;
                }
                AppInfoManager appInfoManager=AppInfoManager.getAppInfoManager(Activity_Mean.this);
                maplist=appInfoManager.getRuningAppInfos();
                appInfos= (ArrayList<RuningAppInfo>) maplist.get(1);
                for(RuningAppInfo runingAppInfo:appInfos){
                        Log.i("msg","sasd");
                        appInfoManager.killProcesses(runingAppInfo.getPackageName());
                }

            }
        });
        mean_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!run){
                    getClick(2);
                    run=true;
                }
                AppInfoManager appInfoManager=AppInfoManager.getAppInfoManager(Activity_Mean.this);
                maplist=appInfoManager.getRuningAppInfos();
                appInfos= (ArrayList<RuningAppInfo>) maplist.get(1);
                for(RuningAppInfo runingAppInfo:appInfos){
                    Log.i("msg","sasd");
                    appInfoManager.killProcesses(runingAppInfo.getPackageName());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.top_left:
                Intent intent=new Intent(this,Activity_Mean_Left.class);
                startActivity(intent);
                overridePendingTransition(R.anim.from_left,R.anim.to_right);
                break;
            case R.id.top_right:
                Intent intent1=new Intent(this,Activity_Mean_Right.class);
                startActivity(intent1);
                overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
                break;
        }
    }
    private  long time=0;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK&&event.getRepeatCount()==0){
            dialog();

        }
        return false;
//        return super.onKeyDown(keyCode, event);
    }

    void excit(){
        if((System.currentTimeMillis()-time)<1000){
            this.finish();
        }else{
            Toast.makeText(this,"再点击一次退出",Toast.LENGTH_SHORT).show();
            time=System.currentTimeMillis();
        }
    }

    void dialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setMessage("确定退出吗？");
        builder.setTitle("提示");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Activity_Mean.this.finish();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}


