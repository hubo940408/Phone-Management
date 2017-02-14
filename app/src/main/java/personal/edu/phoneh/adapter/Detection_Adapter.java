package personal.edu.phoneh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import personal.edu.phoneh.R;
import personal.edu.phoneh.entity.Detection_User;

/**
 * Created by Administrator on 2016/11/30 0030.
 */
public class Detection_Adapter extends BaseAdapter {
    int[]backcolor={R.mipmap.notification_information_progress_green,R.mipmap.notification_information_progress_red,R.mipmap.notification_information_progress_yellow};
    Context context;
    ArrayList<Detection_User>list;

    public Detection_Adapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    public ArrayList<Detection_User> getList() {
        return list;
    }

    public void setList(ArrayList<Detection_User> list) {
        this.list = list;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView==null){
            vh=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.detection_item,null);
            vh.im= (ImageView) convertView.findViewById(R.id.detection_item_photo_im);
            vh.im_bac= (ImageView) convertView.findViewById(R.id.detection_item_photo_im);
            vh.top= (TextView) convertView.findViewById(R.id.detection_item_topname_tv);
            vh.but= (TextView) convertView.findViewById(R.id.detection_item_botname_tv);
            convertView.setTag(vh);
        }else{
            vh= (ViewHolder) convertView.getTag();
        }
        Detection_User du=list.get(position);
        vh.top.setText(du.getTopname());
        vh.but.setText(du.getButname());
        vh.im.setImageResource(du.getPhoto());
        vh.im_bac.setBackgroundResource(backcolor[position%3]);
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.translation_demo);
        animation.setInterpolator(new LinearInterpolator());
        convertView.startAnimation(animation);
        return convertView;
    }

    class ViewHolder{
        ImageView im;
        TextView top,but;
        ImageView im_bac;
    }
}
