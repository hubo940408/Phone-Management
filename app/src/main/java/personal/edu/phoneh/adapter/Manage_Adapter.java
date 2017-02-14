package personal.edu.phoneh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import personal.edu.phoneh.R;
import personal.edu.phoneh.entity.Object;

/**
 * Created by Administrator on 2016/11/30 0030.
 */
public class Manage_Adapter extends BaseAdapter {
    ArrayList<Object> list;
    Context context;

    public ArrayList<Object> getList() {
        return list;
    }

    public void setList(ArrayList<Object> list) {
        this.list = list;
    }

    public Manage_Adapter(Context context) {
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
            convertView= LayoutInflater.from(context).inflate(R.layout.manage_item,null);
            vh.tv= (TextView) convertView.findViewById(R.id.manage_item_name);
            vh.iv= (ImageView) convertView.findViewById(R.id.manage_item_boult);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        Object object=list.get(position);
        vh.tv.setText(object.getName());
        vh.iv.setBackgroundResource(object.getImageView());
        return convertView;
    }
    class ViewHolder{
        TextView tv;
        ImageView iv;
    }
}