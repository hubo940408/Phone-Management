package personal.edu.phoneh.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.test.suitebuilder.annotation.Suppress;
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

import java.security.cert.CertPathBuilder;
import java.util.ArrayList;
import java.util.List;

import personal.edu.phoneh.R;
import personal.edu.phoneh.entity.AppInfo;

/**
 * Created by Administrator on 2016/12/2 0002.
 */
public class ManageMessage_Adapter extends BaseAdapter {

    Context context;
    ArrayList<AppInfo> list;
    boolean Fling;


    public boolean isFling() {
        return Fling;
    }

    public void setFling(boolean fing) {
        Fling = fing;
    }

    public ArrayList<AppInfo> getList() {
        return list;
    }

    public void setList(ArrayList<AppInfo> list) {
        this.list = list;
    }

    @SuppressLint("NewApi")
    public ManageMessage_Adapter(Context context) {
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
        ViewHolderManageMessage vh;
        if(convertView==null){
            vh=new ViewHolderManageMessage();
            convertView= LayoutInflater.from(context).inflate(R.layout.manage_message_item,null);
            vh.cb_del= (CheckBox) convertView.findViewById(R.id.manage_message_item_cb_del);
            vh.icon= (ImageView) convertView.findViewById(R.id.manage_message_item_iv_app_icon);
            vh.lable= (TextView) convertView.findViewById(R.id.manage_message_tv_app_lable);
            vh.packagename= (TextView) convertView.findViewById(R.id.manage_message_tv_pankagename);
            vh.version= (TextView) convertView.findViewById(R.id.manage_message_tv_app_version);
            convertView.setTag(vh);
        }else{
            vh= (ViewHolderManageMessage) convertView.getTag();
        }
         AppInfo appInfo=list.get(position);
        if(Fling){
            vh.icon.setImageResource(R.mipmap.ic_launcher);
        }else {
            vh.icon.setImageDrawable(appInfo.getPackageInfo().applicationInfo.
                    loadIcon(context.getPackageManager()));
        }
        String lablename=appInfo.getPackageInfo().applicationInfo.loadLabel
                (context.getPackageManager()).toString();
        String text=appInfo.getPackageInfo().packageName;
        String version=appInfo.getPackageInfo().versionName;

        vh.cb_del.setTag(position);
        vh.cb_del.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(position).setDel(isChecked);
            }
        });
        vh.cb_del.setChecked(appInfo.isDel());

        vh.lable.setText(lablename);
        vh.version.setText(version);
        vh.packagename.setText(text);
        //动画



        vh.cb_del.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(position).setDel(isChecked);
                Intent intent=new Intent();//广播
                int key = 0;
                int xx=0;
                for (int i=0;i<list.size();i++){

                    if(list.get(i).isDel()){
                        xx++;
                    }
                }
                if(list.size()==xx){
                    key=1;
                }
                intent.putExtra("messall",key);
                intent.setAction("messall_cb");//设置广播的Action
                context.sendBroadcast(intent);
            }
        });
        vh.cb_del.setChecked(appInfo.isDel());
        return convertView;
    }


    class ViewHolderManageMessage{
        CheckBox  cb_del;
        ImageView icon;
        TextView lable,packagename,version;
    }


}
