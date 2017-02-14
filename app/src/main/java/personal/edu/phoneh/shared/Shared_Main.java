package personal.edu.phoneh.shared;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class Shared_Main {
    public void outspf(Context context,int number){
        SharedPreferences spf=context.getSharedPreferences("main",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=spf.edit();
        ed.putInt("number",number);
        ed.commit();
    }
    public int intspf(Context context){
        SharedPreferences spf=context.getSharedPreferences("main",Context.MODE_PRIVATE);
        return spf.getInt("number",0);
    }

    public void outactivity(Context context,int number){
        SharedPreferences spf=context.getSharedPreferences("activity",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=spf.edit();
        ed.putInt("one",number);
        ed.commit();
    }
    public int intactivity(Context context){
        SharedPreferences spf=context.getSharedPreferences("activity",Context.MODE_PRIVATE);
        return spf.getInt("one",0);
    }

}
