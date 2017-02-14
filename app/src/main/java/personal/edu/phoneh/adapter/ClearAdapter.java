package personal.edu.phoneh.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import personal.edu.phoneh.R;
import personal.edu.phoneh.biz.CommonUtil;
import personal.edu.phoneh.entity.RubbishFileInfo;

/**
 * Created by 王小川 on 2016/12/9.
 */
public class ClearAdapter extends BaseAdapter {
    ArrayList<RubbishFileInfo> list;
    Context context;
    boolean isFiling;

    public ClearAdapter(Context context) {
        this.context = context;
        list=new ArrayList<RubbishFileInfo>();
        isFiling=false;
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

    public ArrayList<RubbishFileInfo> getList() {
        return list;
    }

    public boolean isFiling() {
        return isFiling;
    }

    public void setFiling(boolean filing) {
        isFiling = filing;
    }

    public void setList(ArrayList<RubbishFileInfo> list) {
        this.list = list;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder vh=null;
        if(view==null){
            vh=new ViewHolder();
            view= LayoutInflater.from(context).inflate(R.layout.list_item,null);
            vh.cb= (CheckBox) view.findViewById(R.id.list_item_check);
            vh.iv= (ImageView) view.findViewById(R.id.list_item_img);
            vh.left_top= (TextView) view.findViewById(R.id.list_item_left_top_tv);
            vh.left_bottom= (TextView) view.findViewById(R.id.list_item_left_bottom_tv);
            vh.right_top= (TextView) view.findViewById(R.id.list_item_right_top_tv);
            vh.right_bottom= (TextView) view.findViewById(R.id.list_item_right_bottom_tv);
            view.setTag(vh);
        }else{
            vh= (ViewHolder) view.getTag();
        }
        vh.cb.setVisibility(View.GONE);//隐藏哪个选择框
        RubbishFileInfo rubbishFileInfo=getList().get(position);
        vh.left_top.setText(rubbishFileInfo.getSoftChinesename());
        vh.right_bottom.setText(CommonUtil.getFileSize(rubbishFileInfo.getSize()));

        if(isFiling){
            vh.iv.setImageResource(R.mipmap.ic_launcher);
        }else{
            vh.iv.setImageDrawable(rubbishFileInfo.getIcon());
        }
        return view;
    }
    class ViewHolder{
        CheckBox cb;
        ImageView iv;
        TextView left_top,left_bottom,right_top,right_bottom;
    }
}
