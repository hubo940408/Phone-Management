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
import personal.edu.phoneh.entity.Object;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class GridView_Adapter extends BaseAdapter {
    ArrayList<Object>list;
    Context context;


    public ArrayList<Object> getList() {
        return list;
    }

    public void setList(ArrayList<Object> list) {
        this.list = list;
    }

    public GridView_Adapter(Context context) {
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
            convertView= LayoutInflater.from(context).inflate(R.layout.mean_item,null);
            vh.tv= (TextView) convertView.findViewById(R.id.text_item_tv);
            vh.iv= (ImageView) convertView.findViewById(R.id.image_item_im);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        vh.tv.setText(list.get(position).getName());
        vh.iv.setImageResource(list.get(position).getImageView());
        Animation animation= AnimationUtils.loadAnimation(context,R.anim.animation_demo);
        animation.setInterpolator(new LinearInterpolator());
        convertView.startAnimation(animation);
        return convertView;
    }
    class ViewHolder{
        TextView tv;
        ImageView iv;
    }
}
