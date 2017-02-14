package personal.edu.phoneh.shared;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2016/11/29 0029.
 */
public class Shared_Number {
    public void outspf(Context context,String number){
        SharedPreferences spf=context.getSharedPreferences("number",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=spf.edit();
        ed.putString("number",number);
        ed.commit();
    }
    public String intspf(Context context){
        SharedPreferences spf=context.getSharedPreferences("number",Context.MODE_PRIVATE);
        return spf.getString("number","0");
    }



    public void outspfname(Context context,String name){
        SharedPreferences spf=context.getSharedPreferences("name",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=spf.edit();
        ed.putString("name",name);
        ed.commit();
    }
    public String intspfname(Context context){
        SharedPreferences spf=context.getSharedPreferences("name",Context.MODE_PRIVATE);
        return spf.getString("name","默认");
    }
}
