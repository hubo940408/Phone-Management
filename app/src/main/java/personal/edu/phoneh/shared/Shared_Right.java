package personal.edu.phoneh.shared;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/12/13 0013.
 */
public class Shared_Right {
    public void outnoitce(Context context, boolean number){
        SharedPreferences spf=context.getSharedPreferences("noitce",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=spf.edit();
        ed.putBoolean("noitce",number);
        ed.commit();
    }
    public boolean intnoitce(Context context){
        SharedPreferences spf=context.getSharedPreferences("noitce",Context.MODE_PRIVATE);
        return spf.getBoolean("noitce",false);
    }

    public void outstart(Context context,boolean number){
        SharedPreferences spf=context.getSharedPreferences("start",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=spf.edit();
        ed.putBoolean("start",number);
        ed.commit();
    }
    public boolean intstart(Context context){
        SharedPreferences spf=context.getSharedPreferences("start",Context.MODE_PRIVATE);
        return spf.getBoolean("start",false);
    }

    public void outxiaoxi(Context context,boolean number){
        SharedPreferences spf=context.getSharedPreferences("xiaoxi",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=spf.edit();
        ed.putBoolean("xiaoxi",number);
        ed.commit();
    }
    public boolean intxiaoxi(Context context){
        SharedPreferences spf=context.getSharedPreferences("xiaoxi",Context.MODE_PRIVATE);
        return spf.getBoolean("xiaoxi",false);
    }
}
