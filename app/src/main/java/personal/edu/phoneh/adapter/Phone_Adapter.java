package personal.edu.phoneh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import personal.edu.phoneh.R;
import personal.edu.phoneh.entity.Phone_mainOne;

/**
 * Created by Administrator on 2016/11/29 0029.
 */
public class Phone_Adapter extends BaseAdapter {
    Context context;
    ArrayList<Phone_mainOne>list;
    int[]color={R.drawable.phone_one,R.drawable.phone_two,R.drawable.phone_three};
    public Phone_Adapter(Context context) {
        this.context = context;
        list=new ArrayList<>();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    public ArrayList<Phone_mainOne> getList() {
        return list;
    }

    public void setList(ArrayList<Phone_mainOne> list) {
        this.list = list;
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
            convertView= LayoutInflater.from(context).inflate(R.layout.phone_item,null);
            vh.tv= (TextView) convertView.findViewById(R.id.phone_item_name);
            convertView.setTag(vh);
        }else{
            vh= (ViewHolder) convertView.getTag();
        }
        vh.tv.setText(list.get(position).getName());
        convertView.setBackgroundResource(color[position%3]);//设置背景
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.animation_demo);
        animation.setInterpolator(new LinearInterpolator());
        convertView.startAnimation(animation);
        return convertView;
    }

    class ViewHolder{
        TextView tv;

    }
}
