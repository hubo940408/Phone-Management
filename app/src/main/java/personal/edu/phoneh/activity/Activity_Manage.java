package personal.edu.phoneh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import personal.edu.phoneh.R;
import personal.edu.phoneh.adapter.Manage_Adapter;
import personal.edu.phoneh.biz.CommonUtil;
import personal.edu.phoneh.biz.MemoryManager;
import personal.edu.phoneh.entity.Object;
import personal.edu.phoneh.shared.MyCircleView;
import personal.edu.phoneh.shared.Shared_Number;

/**
 * 软件管理界面
 * Created by Administrator on 2016/11/28 0028.
 */
public class Activity_Manage extends AppCompatActivity {
    ImageView im;
    MyCircleView manage_cv;
    ListView lv;
    Manage_Adapter ma;
    ArrayList<Object>list;
    ProgressBar pro_out,pro_int;
    TextView int_tv,out_tv,name_tv;

    void outClick(){
        list=new ArrayList<>();
        list.add(new Object("所有软件",R.mipmap.jiantou));
        list.add(new Object("系统软件",R.mipmap.jiantou));
        list.add(new Object("用户软件",R.mipmap.jiantou));
    }
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        outClick();
        setContentView(R.layout.activity_manage);
        inClick();
        getfind();
        progressBar();
    }
    public void progressBar(){//进度条
        long intall=MemoryManager.getPhoneSelfSize();
        long intsum=MemoryManager.getPhoneSelfFreeSize();
        long outall=MemoryManager.getPhoneOutSDCardSize(this);
        long outsum=MemoryManager.getPhoneOutSDCardFreeSize(this);
        int blue= (int) (intall*360/(intall+outall));
        int green=360-blue;
        manage_cv.setSweepArg(blue,green);
        if(outall==0){
            int_tv.setText("可用：" + CommonUtil.getFileSize(intsum) + "/" + CommonUtil.getFileSize(intall));
            out_tv.setText("可用：0B/0B");
            pro_int.setProgress(100-(int) (intsum*100/intall));
            pro_out.setProgress(0);
        }else {
            int_tv.setText("可用：" + CommonUtil.getFileSize(intsum) + "/" + CommonUtil.getFileSize(intall));
            out_tv.setText("可用：" + CommonUtil.getFileSize(outsum) + "/" + CommonUtil.getFileSize(outall));
            pro_int.setProgress(100-(int) (intsum*100/intall));
            pro_out.setProgress(100-(int) (outsum*100/outall));
        }
    }

    void inClick(){//初始化控件
        manage_cv= (MyCircleView) findViewById(R.id.manage_myview_cv);
        int_tv= (TextView) findViewById(R.id.manage_within_tv);
        out_tv= (TextView) findViewById(R.id.manage_outer_tv);
        name_tv= (TextView) findViewById(R.id.top_name_tv);
        name_tv.setText("软件管理");
        pro_out= (ProgressBar) findViewById(R.id.manage_outer_sd);
        pro_int= (ProgressBar) findViewById(R.id.manage_within_sd);
        im= (ImageView) findViewById(R.id.top_left);
        im.setImageResource(R.mipmap.btn_homeasup_default);
        lv= (ListView) findViewById(R.id.manage_list_lv);
        ma=new Manage_Adapter(this);
        ma.setList(list);
        lv.setAdapter(ma);
    }

    void getfind(){//控件监听
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.in_down,R.anim.to_down);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(Activity_Manage.this,Activity_ManageMessage.class);
                Shared_Number sm=new Shared_Number();
                sm.outspfname(Activity_Manage.this,list.get(position).getName());
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
            }
        });
    }
}
