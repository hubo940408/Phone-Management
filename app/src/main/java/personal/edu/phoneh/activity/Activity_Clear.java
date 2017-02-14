package personal.edu.phoneh.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import personal.edu.phoneh.R;
import personal.edu.phoneh.adapter.ClearAdapter;
import personal.edu.phoneh.biz.CommonUtil;
import personal.edu.phoneh.biz.DbClearPathManager;
import personal.edu.phoneh.biz.FileManager1;
import personal.edu.phoneh.entity.RubbishFileInfo;

/**
 * 垃圾清理界面
 * Created by Administrator on 2016/11/28 0028.
 */
public class Activity_Clear extends AppCompatActivity {
    ImageView return_im;
    RelativeLayout loading_rl;//加载中的动画布局
    ListView lv;//下方列表布局
    TextView total_tv,name_tv;//顶部总大小的文本框
    Button clear_btn;//清理的按钮
    ClearAdapter ca;//适配器
    long totalsize;//垃圾总大小

    ArrayList<RubbishFileInfo> rubbishFileInfos;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        indata();
        setContentView(R.layout.activity_clear);
        inClick();
        getfind();
        setview();
    }

    void indata(){
        try {
            //先创建和复制文件
            DbClearPathManager.createFile(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //获取数据源
        rubbishFileInfos = DbClearPathManager.getPhoneRubbishfile(this);
        Log.i("msg",rubbishFileInfos.size()+"");
    }
    public void deltefile(File file){
        if(file.isFile()){
            file.delete();
        }else if(file.isDirectory()){
            File[] files=file.listFiles();
            for (int i=0;i<files.length;i++){
                if(files[i]!=null){
                    deltefile(files[i]);
                }
            }
        }
    }
    void setview(){
        ca = new ClearAdapter(Activity_Clear.this);
        lv.setAdapter(ca);
        try {
            loadData();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clear_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<RubbishFileInfo> list=ca.getList();
                for (int i=0;i<list.size();i++){
                    RubbishFileInfo rubbishFileInfo=list.get(i);
                    File file=new File(rubbishFileInfo.getFilepath());
                    Log.i("file",file.getPath());
//
                    deltefile(file);
                }

                try {
                    //重新加载数据
                    loadData();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void loadData() throws IOException {
        totalsize=0;
        loading_rl.setVisibility(View.VISIBLE);
        lv.setVisibility(View.GONE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                for (RubbishFileInfo rubbishFileInfo : rubbishFileInfos) {
                    File file = new File(rubbishFileInfo.getFilepath());
                    long size = FileManager1.getFileSize(file);
                    rubbishFileInfo.setSize(size);
                    // 更新全部大小
                    Message msg = handler.obtainMessage();//取出默认的消息
                    //等同于
//                    Message ms=new Message();
                    msg.what = 1;
                    msg.obj = size;
                    handler.sendMessage(msg);
                }
                // 全部加载完毕 更新UI
                Message msg = handler.obtainMessage();
                msg.what = 2;
                msg.obj = rubbishFileInfos;
                handler.sendMessage(msg);
            }
        }).start();
    }


    void inClick(){//初始化控件
        name_tv= (TextView) findViewById(R.id.top_name_tv);
        return_im= (ImageView) findViewById(R.id.top_left);

        loading_rl= (RelativeLayout) findViewById(R.id.clear_loading_rl);
        lv= (ListView) findViewById(R.id.clear_list);
        total_tv= (TextView) findViewById(R.id.clear_number_tv);
        clear_btn= (Button) findViewById(R.id.clear_btn);
        name_tv.setText("垃圾清理");
        return_im.setImageResource(R.mipmap.btn_homeasup_default);
    }

    void getfind(){//控件监听
        return_im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.in_down,R.anim.to_down);
            }
        });
    }
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    long size = (Long) msg.obj;
                    Log.i("nihao",size+"");
                    totalsize += size;
                    total_tv.setText(CommonUtil.getFileSize(totalsize));//设置上方状态
                    break;
                case 2:
                    total_tv.setText(CommonUtil.getFileSize(totalsize));//设置上方状态
                    rubbishFileInfos = (ArrayList<RubbishFileInfo>) msg.obj;
                    ArrayList<RubbishFileInfo> lists=new ArrayList<RubbishFileInfo>();
                    for(int i=0;i<rubbishFileInfos.size();i++){
                        RubbishFileInfo rubbishFileInfo=rubbishFileInfos.get(i);
                        Log.i("size",rubbishFileInfo.getSize()+"");
                        if(rubbishFileInfo.getSize()>0){
                            lists.add(rubbishFileInfo);
                        }
                    }
                    loading_rl.setVisibility(View.GONE);
                    lv.setVisibility(View.VISIBLE);
                    ca.setList(lists);
                    ca.notifyDataSetChanged();
                    //快速滑动监听
                    lv.setOnScrollListener(new AbsListView.OnScrollListener() {
                        @Override
                        public void onScrollStateChanged(AbsListView view, int scrollState) {
                            switch (scrollState){
                                case AbsListView.OnScrollListener.SCROLL_STATE_FLING: // 快速滑动时
                                    ca.setFiling(true);
                                    break;
                                case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滑动时
                                    ca.setFiling(false);
                                    break;
                                case AbsListView.OnScrollListener.SCROLL_STATE_IDLE: // 停止滑动时
                                    ca.setFiling(false);
                                    ca.notifyDataSetChanged();
                                    break;
                            }
                        }

                        @Override
                        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

                        }
                    });
                    break;
            }
        }
    };
}
