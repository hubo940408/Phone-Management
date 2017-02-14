package personal.edu.phoneh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;

import personal.edu.phoneh.R;
import personal.edu.phoneh.biz.CommonUtil;
import personal.edu.phoneh.biz.FileManager1;
import personal.edu.phoneh.biz.FileTypeUtil;
import personal.edu.phoneh.entity.FileInfo;

/**
 * 文件管理界面
 * Created by Administrator on 2016/11/28 0028.
 */
public class Activity_Paper extends AppCompatActivity implements View.OnClickListener{
    ImageView im;
    FileManager1 fileManage;
    ImageView all_im,txt_im,video_im,andvio_im,image_im,zip_im,apk_im;
    RelativeLayout all_rl,document_rl,video_rl,frequency_rl,image_rl,package_rl,thepackage_rl;
    TextView name_tv,xinxi_tv,all_tv,document_tv,video_tv,frequency_tv,image_tv,package_tv,thepackage_tv;
    ProgressBar pro_xinxi,pro_all,pro_document,pro_video,pro_frequency,pro_image,pro_package,pro_thepackage;
    // 文件搜索-----------------------------------------------------------------------
    /** 任意文件(非目录)集合 */
    private ArrayList<FileInfo> anyFileList = new ArrayList<FileInfo>();
    private long anyFileSize; // 任意文件总大小(在搜索过程中，实时迭加，计算总大小)
    /** 文档文件集合 */
    private ArrayList<FileInfo> txtFileList = new ArrayList<FileInfo>();
    private long txtFileSize; // 文档文件总大小(同上)
    /** 视频文件集合 */
    private ArrayList<FileInfo> videoFileList = new ArrayList<FileInfo>();
    private long videoFileSize; // 视频文件总大小(同上)
    /** 音乐文件集合 */
    private ArrayList<FileInfo> audioFileList = new ArrayList<FileInfo>();
    private long audioFileSize; // 音乐文件总大小(同上)
    /** 图像文件集合 */
    private ArrayList<FileInfo> imageFileList = new ArrayList<FileInfo>();
    private long imageFileSize; // 图像文件总大小(同上)
    /** ZIP文件集合 */
    private ArrayList<FileInfo> zipFileList = new ArrayList<FileInfo>();
    private long zipFileSize; // ZIP文件总大小(同上)
    /** APK文件集合 */
    private ArrayList<FileInfo> apkFileList = new ArrayList<FileInfo>();
    private long apkFileSize; // APK文件总大小(同上)


    private void initData() {
        Log.i("msg","回调初始化");
        anyFileSize = 0;
        txtFileSize = 0;
        videoFileSize = 0;
        audioFileSize = 0;
        imageFileSize = 0;
        zipFileSize = 0;
        apkFileSize = 0;
        anyFileList.clear();
        txtFileList.clear();
        videoFileList.clear();
        audioFileList.clear();
        imageFileList.clear();
        zipFileList.clear();
        apkFileList.clear();
    }

    Thread thread;//搜索线程
    void loaddata(){
        fileManage=FileManager1.getFileManager();

        //开始显示
        fileManage.setSearchFileListener(searchFileListener);//回调
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                fileManage.searchSDCardFile();
            }
        });
        thread.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        thread.interrupt();
        thread=null;
    }
    private FileManager1.SearchFileListener searchFileListener=new FileManager1.SearchFileListener() {
        @Override
        public void searching(String typeName) {
            Message ms=hander.obtainMessage();
            ms.what=1;
            ms.obj=typeName;
            hander.sendMessage(ms);
        }

        @Override
        public void end(boolean isExceptionEnd) {
            if(isExceptionEnd){
                hander.sendEmptyMessage(2);
            }else {

            }

        }
//        fileManage.searchOUTSDCardFile();//获取外置sdcard存储数据
//        fileManage.searchINSDCardFile();//获取内置sdcard存储数据
    };

    private Handler hander=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:
                    xinxi_tv.setText(CommonUtil.getFileSize(fileManage.getAnyFileSize()));
                    all_tv.setText(CommonUtil.getFileSize(fileManage.getAnyFileSize()));
                    String typeName = (String) msg.obj;
                    switch (typeName) {
                        case FileTypeUtil.TYPE_APK:
                            thepackage_tv.setText(CommonUtil.getFileSize(fileManage.getApkFileSize()));
                            break;
                        case FileTypeUtil.TYPE_AUDIO:
                            frequency_tv.setText(CommonUtil.getFileSize(fileManage.getAudioFileSize()));
                            break;
                        case FileTypeUtil.TYPE_IMAGE:
                            image_tv.setText(CommonUtil.getFileSize(fileManage.getImageFileSize()));
                            break;
                        case FileTypeUtil.TYPE_TXT:
                            document_tv.setText(CommonUtil.getFileSize(fileManage.getTxtFileSize()));
                            break;
                        case FileTypeUtil.TYPE_VIDEO:
                            video_tv.setText(CommonUtil.getFileSize(fileManage.getVideoFileSize()));
                            break;
                        case FileTypeUtil.TYPE_ZIP:
                            package_tv.setText(CommonUtil.getFileSize(fileManage.getZipFileSize()));
                            break;
                    }
                    break;
                case 2:
                    all_im.setVisibility(View.VISIBLE);
                    pro_all.setVisibility(View.GONE);

                    txt_im.setVisibility(View.VISIBLE);
                    pro_document.setVisibility(View.GONE);

                    video_im.setVisibility(View.VISIBLE);
                    pro_video.setVisibility(View.GONE);

                    andvio_im.setVisibility(View.VISIBLE);
                    pro_image.setVisibility(View.GONE);

                    image_im.setVisibility(View.VISIBLE);
                    pro_frequency.setVisibility(View.GONE);

                    zip_im.setVisibility(View.VISIBLE);
                    pro_thepackage.setVisibility(View.GONE);

                    apk_im.setVisibility(View.VISIBLE);
                    pro_package.setVisibility(View.GONE);

                    xinxi_tv.setText(CommonUtil.getFileSize(fileManage.getAnyFileSize()));
                    all_tv.setText(CommonUtil.getFileSize(fileManage.getAnyFileSize()));
                    thepackage_tv.setText(CommonUtil.getFileSize(fileManage.getApkFileSize()));
                    frequency_tv.setText(CommonUtil.getFileSize(fileManage.getAudioFileSize()));
                    image_tv.setText(CommonUtil.getFileSize(fileManage.getImageFileSize()));
                    document_tv.setText(CommonUtil.getFileSize(fileManage.getTxtFileSize()));
                    video_tv.setText(CommonUtil.getFileSize(fileManage.getVideoFileSize()));
                    package_tv.setText(CommonUtil.getFileSize(fileManage.getZipFileSize()));

                    all_rl.setOnClickListener(Activity_Paper.this);
                    document_rl.setOnClickListener(Activity_Paper.this);
                    video_rl.setOnClickListener(Activity_Paper.this);
                    frequency_rl.setOnClickListener(Activity_Paper.this);
                    image_rl.setOnClickListener(Activity_Paper.this);
                    package_rl.setOnClickListener(Activity_Paper.this);
                    thepackage_rl.setOnClickListener(Activity_Paper.this);
                    break;
            }
        }
    };





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paper);
        initData();
        inClick();
        loaddata();
        getfind();

        Log.i("msg","创建");

    }
    @Override
    protected void onRestart() {
        super.onRestart();

    }

    void inClick(){//初始化控件
        im= (ImageView) findViewById(R.id.top_left);
        im.setImageResource(R.mipmap.btn_homeasup_default);
        name_tv= (TextView) findViewById(R.id.top_name_tv);
        name_tv.setText("文件管理");
        all_rl= (RelativeLayout) findViewById(R.id.parper_all_rl);
        document_rl= (RelativeLayout) findViewById(R.id.parper_document_rl);
        video_rl= (RelativeLayout) findViewById(R.id.parper_video_rl);
        frequency_rl= (RelativeLayout) findViewById(R.id.parper_frequency_rl);
        image_rl= (RelativeLayout) findViewById(R.id.parper_image_rl);
        package_rl= (RelativeLayout) findViewById(R.id.parper_package_rl);
        thepackage_rl= (RelativeLayout) findViewById(R.id.parper_thepackage_rl);


        pro_all= (ProgressBar) findViewById(R.id.parper_pro_all);
        pro_document= (ProgressBar) findViewById(R.id.parper_pro_document);
        pro_video= (ProgressBar) findViewById(R.id.parper_pro_video);
        pro_frequency= (ProgressBar) findViewById(R.id.parper_pro_frequency);
        pro_image= (ProgressBar) findViewById(R.id.parper_pro_image);
        pro_package= (ProgressBar) findViewById(R.id.parper_pro_package);
        pro_thepackage= (ProgressBar) findViewById(R.id.parper_pro_thepackage);

        all_tv= (TextView) findViewById(R.id.parper_all_tv);
        document_tv= (TextView) findViewById(R.id.parper_document_tv);
        video_tv= (TextView) findViewById(R.id.parper_video_tv);
        frequency_tv= (TextView) findViewById(R.id.parper_frequencyo_tv);
        image_tv= (TextView) findViewById(R.id.parper_image_tv);
        package_tv= (TextView) findViewById(R.id.parper_package_tv);
        thepackage_tv= (TextView) findViewById(R.id.parper_thepackage_tv);

        all_im= (ImageView) findViewById(R.id.parper_all);
        txt_im= (ImageView) findViewById(R.id.parper_txt);
        video_im= (ImageView) findViewById(R.id.parper_video);
        andvio_im= (ImageView) findViewById(R.id.parper_audivo);
        image_im= (ImageView) findViewById(R.id.parper_image);
        zip_im= (ImageView) findViewById(R.id.parper_zip);
        apk_im= (ImageView) findViewById(R.id.parper_apk);


        xinxi_tv= (TextView) findViewById(R.id.parper_textview_xinxi);


    }


    void getfind(){//控件监听
        im.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.in_down,R.anim.to_down);
            }
        });
    }










    @Override
    public void onClick(View v) {
        Intent intent=new Intent(this,Activity_ParperOne.class);
        switch (v.getId()){
            case R.id.parper_all_rl:
                intent.putExtra("name","全部");
                break;
            case R.id.parper_document_rl:
                intent.putExtra("name","文档");
                break;
            case R.id.parper_video_rl:
                intent.putExtra("name","视频");
                break;
            case R.id.parper_frequency_rl:
                intent.putExtra("name","音频");
                break;
            case R.id.parper_image_rl:
                intent.putExtra("name","图像");
                break;
            case R.id.parper_package_rl:
                intent.putExtra("name","压缩包");
                break;
            case R.id.parper_thepackage_rl:
                intent.putExtra("name","程序包");
                break;
        }
        startActivityForResult(intent,1);
    }


    //回调更新
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==1){
            Log.i("msg","回调更新");
            xinxi_tv.setText(CommonUtil.getFileSize(fileManage.getAnyFileSize()));
            all_tv.setText(CommonUtil.getFileSize(fileManage.getAnyFileSize()));
            document_tv.setText(CommonUtil.getFileSize(fileManage.getTxtFileSize()));
            video_tv.setText(CommonUtil.getFileSize(fileManage.getVideoFileSize()));
            frequency_tv.setText(CommonUtil.getFileSize(fileManage.getAudioFileSize()));
            image_tv.setText(CommonUtil.getFileSize(fileManage.getImageFileSize()));
            package_tv.setText(CommonUtil.getFileSize(fileManage.getZipFileSize()));
            thepackage_tv.setText(CommonUtil.getFileSize(fileManage.getApkFileSize()));
        }else{
            Log.i("msg","回调更新2");
        }
    }
}

