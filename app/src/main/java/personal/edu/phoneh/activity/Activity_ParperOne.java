package personal.edu.phoneh.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;

import personal.edu.phoneh.R;
import personal.edu.phoneh.adapter.Expedite_Adapter;
import personal.edu.phoneh.adapter.Parper_Adapter;
import personal.edu.phoneh.biz.CommonUtil;
import personal.edu.phoneh.biz.FileManager;
import personal.edu.phoneh.biz.FileManager1;
import personal.edu.phoneh.biz.FileTypeUtil;
import personal.edu.phoneh.entity.FileInfo;

/**
 * Created by Administrator on 2016/12/5 0005.
 */
public class Activity_ParperOne extends AppCompatActivity {
    ImageView return_rl;
    Parper_Adapter pea;
    ArrayList<FileInfo>list;
    String  title;
    int number;//个数
    long size;//大小
    TextView top_name,number_tv,size_tv;
    Button button;
    ListView lv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parper_one);
        inClick();
        find();
        getlist();

    }

    void inClick(){
        number_tv= (TextView) findViewById(R.id.parper_one_number_tv);
        size_tv= (TextView) findViewById(R.id.parper_one_size_tv);
        lv= (ListView) findViewById(R.id.parper_one_lv);
        button= (Button) findViewById(R.id.parper_one_button);
        top_name= (TextView) findViewById(R.id.top_name_tv);
        return_rl= (ImageView) findViewById(R.id.top_left);
        return_rl.setImageResource(R.mipmap.btn_homeasup_default);
    }



    void find(){
        Intent intent=getIntent();
        String topname=intent.getStringExtra("name");
        top_name.setText(topname);
        button.setText("删除所选文件");
    }

    void getfind(){
        return_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(1);
                finish();
            }
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<FileInfo> fileInfos=pea.getList();
                ArrayList<FileInfo> delFileInfos=new ArrayList<FileInfo>();
                for (int i=0;i<fileInfos.size();i++){
                    FileInfo fi=fileInfos.get(i);
                    if(fi.isSelect()){
                        delFileInfos.add(fi);
                    }
                }

                // 删除所选中的文件
                for (int i = 0; i < delFileInfos.size(); i++) {
                    FileInfo fileInfo = delFileInfos.get(i);
                    File file = fileInfo.getFile();
                    long filesize = file.length();
                    if (file.delete()) {
                        pea.getList().remove(fileInfo);
                        FileManager1.getFileManager().getAnyFileList().remove(fileInfo);
                        FileManager1.getFileManager().setAnyFileSize(FileManager1.getFileManager().
                                getAnyFileSize() - filesize);
                        size=FileManager1.getFileManager().getAnyFileSize();

                        switch (fileInfo.getFileType()) {
                            case FileTypeUtil.TYPE_TXT:
                                FileManager1.getFileManager().getTxtFileList().remove(fileInfo);
                                FileManager1.getFileManager().setTxtFileSize(FileManager1.getFileManager()
                                        .getTxtFileSize() - filesize);
                                size=FileManager1.getFileManager().getTxtFileSize();
                                break;
                            case  FileTypeUtil.TYPE_VIDEO:
                                FileManager1.getFileManager().getVideoFileList().remove(fileInfo);
                                FileManager1.getFileManager().setVideoFileSize(FileManager1.getFileManager()
                                        .getVideoFileSize() - filesize);
                                size=FileManager1.getFileManager().getVideoFileSize();
                                break;
                            case  FileTypeUtil.TYPE_AUDIO:
                                FileManager1.getFileManager().getAudioFileList().remove(fileInfo);
                                FileManager1.getFileManager().setAudioFileSize(FileManager1.getFileManager()
                                        .getAudioFileSize() - filesize);
                                size=FileManager1.getFileManager().getAudioFileSize();
                                break;
                            case  FileTypeUtil.TYPE_IMAGE:
                                FileManager1.getFileManager().getImageFileList().remove(fileInfo);
                                FileManager1.getFileManager().setImageFileSize(FileManager1.getFileManager()
                                        .getImageFileSize() - filesize);
                                size=FileManager1.getFileManager().getImageFileSize();
                                break;
                            case  FileTypeUtil.TYPE_APK:
                                FileManager1.getFileManager().getApkFileList().remove(fileInfo);
                                FileManager1.getFileManager().setApkFileSize(FileManager1.getFileManager()
                                        .getApkFileSize() - filesize);
                                size=FileManager1.getFileManager().getApkFileSize();
                                break;
                            case  FileTypeUtil.TYPE_ZIP:
                                FileManager1.getFileManager().getZipFileList().remove(fileInfo);
                                FileManager1.getFileManager().setZipFileSize(FileManager1.getFileManager()
                                        .getZipFileSize() - filesize);
                                size=FileManager1.getFileManager().getZipFileSize();
                                break;
                        }
                    }
                }
                if(title.equals("全部")){
                    size=FileManager1.getFileManager().getAnyFileSize();
                }
                pea.notifyDataSetChanged();
                //获取文件数量
                number = pea.getList().size();
                //显示
                number_tv.setText(number + "个");
                //大小更新
                size_tv.setText(CommonUtil.getFileSize(size));

            }
        });


        lv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                switch (scrollState){
                    case AbsListView.OnScrollListener.SCROLL_STATE_FLING: // 快速滑动时
                        pea.setFiling(true);
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL:// 滑动时
                        pea.setFiling(false);
                        break;
                    case AbsListView.OnScrollListener.SCROLL_STATE_IDLE: // 停止滑动时
                        pea.setFiling(false);
                        pea.notifyDataSetChanged();
                        break;
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.i("msg","进入点击效果");
                FileInfo fileInfo = pea.getList().get(position);
                File file = fileInfo.getFile();
                // 取出此文件的后缀名　－> MIMEType
                String type = FileTypeUtil.getMIMEType(file);
                // 打开这个文件
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(file), type);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getfind();
        number_tv.setText(fileInfos.size()+"");
    }
    ArrayList<FileInfo> fileInfos;
    void getlist(){
        list=new ArrayList<>();
        Intent intent=getIntent();
        FileManager1 fileManager1=FileManager1.getFileManager();
         title=intent.getStringExtra("name");
        switch (title) {
            case "全部":
                list = fileManager1.getAnyFileList();
                size_tv.setText(CommonUtil.getFileSize(fileManager1.getAnyFileSize()));
                break;
            case "文档":
                list = fileManager1.getTxtFileList();
                size_tv.setText(CommonUtil.getFileSize(fileManager1.getTxtFileSize()));
                break;
            case "视频":
                list = fileManager1.getVideoFileList();
                size_tv.setText(CommonUtil.getFileSize(fileManager1.getVideoFileSize()));
                break;
            case "音频":
                list = fileManager1.getAudioFileList();
                size_tv.setText(CommonUtil.getFileSize(fileManager1.getAudioFileSize()));
                break;
            case "图像":
                list = fileManager1.getImageFileList();
                Log.i("msg",list.size()+"");
                size_tv.setText(CommonUtil.getFileSize(fileManager1.getImageFileSize()));
                break;
            case "压缩包":
                list = fileManager1.getZipFileList();
                size_tv.setText(CommonUtil.getFileSize(fileManager1.getZipFileSize()));
                break;
            case "程序包":
                list = fileManager1.getApkFileList();
                size_tv.setText(CommonUtil.getFileSize(fileManager1.getApkFileSize()));
                break;
        }
        //重构bug
        fileInfos=new ArrayList<>();
        for(FileInfo fileInfo:list){
            if (fileInfo.getFile().length()!=0){
                fileInfos.add(fileInfo);
            }
        }
        pea=new Parper_Adapter(this);
        pea.setList(fileInfos);
        lv.setAdapter(pea);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.i("msg",keyCode+"");
        setResult(1);
        return super.onKeyDown(keyCode, event);

    }



}
