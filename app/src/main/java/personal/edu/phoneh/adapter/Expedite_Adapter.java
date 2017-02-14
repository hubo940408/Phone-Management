package personal.edu.phoneh.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import personal.edu.phoneh.R;
import personal.edu.phoneh.biz.CommonUtil;
import personal.edu.phoneh.entity.RuningAppInfo;

/**
 * Created by Administrator on 2016/12/5 0005.
 */
public class Expedite_Adapter extends BaseAdapter {
    ArrayList<RuningAppInfo>list;
    Context context;


    public ArrayList<RuningAppInfo> getList() {
        return list;
    }

    public void setList(ArrayList<RuningAppInfo> list) {
        this.list = list;
    }

    public Expedite_Adapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder_expedute vh;
        if(convertView==null){
            vh=new ViewHolder_expedute();
            convertView= LayoutInflater.from(context).inflate(R.layout.manage_message_item,null);
            vh.cb_del= (CheckBox) convertView.findViewById(R.id.manage_message_item_cb_del);
            vh.icon= (ImageView) convertView.findViewById(R.id.manage_message_item_iv_app_icon);
            vh.lable= (TextView) convertView.findViewById(R.id.manage_message_tv_app_lable);
            vh.packagename= (TextView) convertView.findViewById(R.id.manage_message_tv_pankagename);
            vh.text= (TextView) convertView.findViewById(R.id.manage_message_tv_app_version);
            convertView.setTag(vh);
        }else{
            vh= (ViewHolder_expedute) convertView.getTag();
        }

        RuningAppInfo runingAppInfo= list.get(position);
       //单个监听
        vh.cb_del.setTag(position);
        vh.cb_del.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(position).setClear(isChecked);
                Intent intent=new Intent();//广播
                int key = 0;
                int xx=0;
                for (int i=0;i<list.size();i++){

                    if(list.get(i).isClear()){
                       xx++;
                    }
                }
                if(list.size()==xx){
                    key=1;
                }
                intent.putExtra("all",key);
                intent.setAction("all_cb");//设置广播的Action
                context.sendBroadcast(intent);
            }
        });
        String lablename= runingAppInfo.getPackageName();
        vh.lable.setText(lablename);
        long memory=runingAppInfo.getSize();
        vh.packagename.setText("内存："+ CommonUtil.getFileSize(memory));
        vh.icon.setImageDrawable(runingAppInfo.getIcon());
        boolean text=runingAppInfo.isSystem();
        vh.text.setText(text?"系统进程":"用户进程");
        vh.cb_del.setChecked(runingAppInfo.isClear());

        return convertView;
    }


    class ViewHolder_expedute{
        CheckBox cb_del;
        ImageView icon;
        TextView lable,packagename,text;
    }

}
