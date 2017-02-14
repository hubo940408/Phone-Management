package personal.edu.phoneh.biz;

import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import personal.edu.phoneh.entity.Phone_One_main;
import personal.edu.phoneh.entity.Phone_mainOne;

/**
 * 文件操作管理
 * Created by 王小川 on 2016/11/29.
 */
public class FileManager {
    /**
     * 创建文件
     * @param context 上下文对象
     * @return 返回的文件
     * @throws IOException
     */
    public static File createFile(Context context) throws IOException {
        //创建的时候 做了判断
        //没有会创建
        //存在 直接就使用哪个文件
        File file=null;//1.先声明一个文件

        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){//判断SD卡
            Log.i("msg","创建在SD卡");
            file=new File(Environment.getExternalStorageDirectory()+File.separator+"myFile"+File.separator+"number.db");

        }else{
            Log.i("msg","创建在手机本身存储控件");
            file=new File("data"+File.separator+"data"+File.separator+context.getPackageName()+File.separator+"myFile"+File.separator+"number.db");
        }

        //执行文件创建
        //1.判断父目录
        if(!file.getParentFile().exists()){
            file.getParentFile().mkdirs();

        }
        Log.i("msg",file.getPath());
        if(!file.exists()){
            file.createNewFile();

            //创建文件之后 马上复制数据

        }

        return file;

    }
    //复制数据
    public static void copyFile(Context context,File file,String filename) throws IOException {
        AssetManager am=context.getAssets();
        InputStream is=am.open(filename);//得到输入流
        //执行复制操作
        BufferedInputStream bis=null;//声明缓冲输入流
        BufferedOutputStream bos=null;//声明缓冲输出流
        byte[] b=new byte[1024];//声明数组
        int len=0;//声明每次的长度
        bis=new BufferedInputStream(is);
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
    public static List<Phone_mainOne> getClassList(File file){
        List<Phone_mainOne> list=new ArrayList<Phone_mainOne>();
        //通过文件获得数据库操作对象
        SQLiteDatabase database=SQLiteDatabase.openOrCreateDatabase(file,null);
        Cursor cursor=database.rawQuery("select * from classlist",null);
        if(cursor!=null){
            Phone_mainOne classList=null;
            while(cursor.moveToNext()){
                String name=cursor.getString(cursor.getColumnIndex("name"));
                int idx=cursor.getInt(cursor.getColumnIndex("idx"));
                classList=new Phone_mainOne(name,idx);
                list.add(classList);
            }

        }
        return list;

    }

    public static List<Phone_One_main> getList(File file, String namenu){
        List<Phone_One_main> list=new ArrayList<Phone_One_main>();
        //通过文件获得数据库操作对象
        SQLiteDatabase database=SQLiteDatabase.openOrCreateDatabase(file,null);
        Cursor cursor=database.rawQuery("select * from "+namenu,null);
        if(cursor!=null){
            Phone_One_main classList=null;
            while(cursor.moveToNext()){
                String name=cursor.getString(cursor.getColumnIndex("name"));
                String idx=cursor.getString(cursor.getColumnIndex("number"));
                classList=new Phone_One_main(name,idx);
                list.add(classList);
            }
        }
        return list;

    }

}
