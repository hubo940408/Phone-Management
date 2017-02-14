package personal.edu.phoneh.biz;

import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Environment;
import android.util.Log;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import personal.edu.phoneh.R;
import personal.edu.phoneh.entity.RubbishFileInfo;

/**
 * Created by 王小川 on 2016/12/9.
 */
public class DbClearPathManager {
    private static  String FILE_NAME = "clearpath.db"; // db文件名称
    //    private static final String PACKAGE_NAME = "edu.feicui.app.phone"; // 当前应用程序包名
    // 文件路径
    private static  String FILE_PATH ;

    private static ArrayList<RubbishFileInfo> softdetailInfos;

    /**
     * 创建文件 并且复制
     * @param context
     * @return
     */
    public static void createFile(Context context) throws IOException {
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            FILE_PATH= Environment.getExternalStorageDirectory() +File.separator+"MyFile";
        }else{
            FILE_PATH="data"+File.separator+"data"+File.separator+context.getPackageName()+File.separator+"myFile";
        }
        File file=new File(FILE_PATH+File.separator+FILE_NAME);
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();
        }
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i("msg",file.getPath());
            //复制文件
            copyFile("clearpath.db",file,context);
        }
    }
    /**
     * 复制本地数据库到手机文件中
     * @param path 本地路径
     * @param file 目标文件
     * @param context 上下文对象
     * @throws IOException
     */
    public static void copyFile(String path,File file,Context context) throws IOException {
        AssetManager am=context.getAssets();//获得管理器
        BufferedInputStream bis=null;//声明缓冲输入流
        BufferedOutputStream bos=null;//声明缓冲输出流
        byte[] b=new byte[1024];//声明数组
        int len=0;//声明每次的长度
        bis=new BufferedInputStream(am.open(path));
        bos=new BufferedOutputStream(new FileOutputStream(file));
        while((len=bis.read(b))!=-1){
            bos.write(b,0,len);
        }
        if(bis!=null){

            bis.close();
        }
        if(bos!=null){
            bos.flush();
            bos.close();
        }
    }
    public static ArrayList<RubbishFileInfo> getPhoneRubbishfile(Context context) {
        // #读取DB内softdetail表上的所有数据(225个)
        if (softdetailInfos == null) {
            softdetailInfos = readSoftdetailTable(); // 从Softdetail表内取出DB内有的所有缓存目录
        }
        // #从所有数据内，挑出手机上存在的
        ArrayList<RubbishFileInfo> phontSoftdetailInfos = new ArrayList<RubbishFileInfo>();
        for (RubbishFileInfo info : softdetailInfos) {
            File file = new File(info.getFilepath());
            if (file.exists()) {
                Drawable icon = null;
                try {
                    icon = context.getPackageManager().getApplicationIcon(info.getApkname());
                } catch (PackageManager.NameNotFoundException e) {
                    icon = context.getResources().getDrawable(R.mipmap.ic_launcher);
                }
                info.setIcon(icon);
                phontSoftdetailInfos.add(info);
            }
        }
        return phontSoftdetailInfos;
    }

    private static ArrayList<RubbishFileInfo> readSoftdetailTable() {
        ArrayList<RubbishFileInfo> softdetailInfos = new ArrayList<RubbishFileInfo>();
        String path = FILE_PATH + File.separator + FILE_NAME;
        SQLiteDatabase database = SQLiteDatabase.openOrCreateDatabase(path, null);
        String sql = "select * from softdetail"; // 查询softdetail表（缓存目录表）
        Cursor cursor = database.rawQuery(sql, null);
        if (cursor.moveToFirst()) {
            do {
                int _id = cursor.getInt(cursor.getColumnIndex("_id"));
                String softChinesename = cursor.getString(cursor.getColumnIndex("softChinesename"));
                String softEnglishname = cursor.getString(cursor.getColumnIndex("softEnglishname"));
                String apkname = cursor.getString(cursor.getColumnIndex("apkname"));
                String filepath = cursor.getString(cursor.getColumnIndex("filepath"));
                filepath = Environment.getExternalStorageDirectory().getPath() + filepath;
                RubbishFileInfo info = new RubbishFileInfo(_id, softChinesename, softEnglishname, apkname, filepath);
                softdetailInfos.add(info);
            } while (cursor.moveToNext());
        }
        cursor.close();
        database.close();
        return softdetailInfos;
    }


}
