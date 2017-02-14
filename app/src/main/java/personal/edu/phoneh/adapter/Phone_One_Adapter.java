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
import java.util.List;

import personal.edu.phoneh.R;
import personal.edu.phoneh.entity.Phone_One_main;
import personal.edu.phoneh.entity.Phone_mainOne;

/**
 * Created by Administrator on 2016/11/29 0029.
 */
public class Phone_One_Adapter extends BaseAdapter {
    Context context;
    List<Phone_One_main> list;

    public List<Phone_One_main> getList() {
        return list;
    }

    public void setList(List<Phone_One_main> list) {
        this.list = list;
    }

    public Phone_One_Adapter(Context context) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView==null){
            vh=new ViewHolder();
            convertView= LayoutInflater.from(context).inflate(R.layout.phone_one_item,null);
            vh.tv= (TextView) convertView.findViewById(R.id.phone_one_item_name);
            vh.tv1= (TextView) convertView.findViewById(R.id.phone_one_item_number);
            convertView.setTag(vh);
        }else{
            vh= (ViewHolder) convertView.getTag();
        }

        vh.tv.setText(list.get(position).getName());
        vh.tv1.setText(list.get(position).getNumber());
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.translation_demo);
        animation.setInterpolator(new LinearInterpolator());
        convertView.startAnimation(animation);
        return convertView;
    }

    class ViewHolder{
        TextView tv,tv1;

    }
}
