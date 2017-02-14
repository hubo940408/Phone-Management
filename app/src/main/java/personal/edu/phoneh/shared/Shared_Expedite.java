package personal.edu.phoneh.shared;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.BaseAdapter;

import personal.edu.phoneh.adapter.Expedite_Adapter;

/**
 * Created by Administrator on 2016/12/5 0005.
 */
public class Shared_Expedite{
    public void outspf(Context context, String button){
        SharedPreferences spf=context.getSharedPreferences("number",Context.MODE_PRIVATE);
        SharedPreferences.Editor ed=spf.edit();
        ed.putString("number",button);
        ed.commit();
    }
    public String intspf(Context context){
        SharedPreferences spf=context.getSharedPreferences("number",Context.MODE_PRIVATE);
        return spf.getString("number","系统");
    }


}
