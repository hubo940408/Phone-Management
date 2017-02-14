package personal.edu.phoneh.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import personal.edu.phoneh.biz.FileTypeUtil;
import personal.edu.phoneh.entity.FileInfo;

/**
 * Created by Administrator on 2016/12/6 0006.
 */
public class Parper_Adapter extends BaseAdapter{
    ArrayList<FileInfo> list;
    Context context;
    boolean Filing;
    Bitmap bitmap;

    public boolean isFiling() {
        return Filing;
    }

    public void setFiling(boolean filing) {
        Filing = filing;
    }

    public ArrayList<FileInfo> getList() {
        return list;
    }

    public void setList(ArrayList<FileInfo> list) {
        this.list = list;
    }

    public Parper_Adapter(Context context) {
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
        ViewHolder_parperOne vh;
        if(convertView==null){
            vh=new ViewHolder_parperOne();
            convertView= LayoutInflater.from(context).inflate(R.layout.manage_message_item,null);
            vh.cb_del= (CheckBox) convertView.findViewById(R.id.manage_message_item_cb_del);
            vh.icon= (ImageView) convertView.findViewById(R.id.manage_message_item_iv_app_icon);
            vh.lable= (TextView) convertView.findViewById(R.id.manage_message_tv_app_lable);
            vh.packagename= (TextView) convertView.findViewById(R.id.manage_message_tv_pankagename);
            vh.text= (TextView) convertView.findViewById(R.id.manage_message_tv_app_version);
            convertView.setTag(vh);
        }else{
            vh= (ViewHolder_parperOne) convertView.getTag();
        }
        FileInfo fileInfo= list.get(position);
        //单个监听
        String lablename= fileInfo.getFile().getName();
        vh.cb_del.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                list.get(position).setSelect(isChecked);
            }
        });
        vh.cb_del.setChecked(fileInfo.isSelect());
        vh.lable.setText(lablename);
        if(Filing) {
            vh.icon.setImageResource(R.mipmap.ic_launcher);
        }else{
            vh.icon.setImageBitmap(getFileIcon(fileInfo));
        }
        vh.packagename.setText(CommonUtil.getStrTime(list.get(position).getFile().lastModified()));

        vh.text.setText(CommonUtil.getFileSize(list.get(position).getFile().length()));
        return convertView;
    }

    private Bitmap getFileIcon(FileInfo fileInfo) {
        Bitmap bitmap = null;
        String fielPath = fileInfo.getFile().getPath();

        // 如果是图像，根据图像路径加载图像
        if (fileInfo.getFileType().equals(FileTypeUtil.TYPE_IMAGE)) {
            BitmapFactory.Options options = new BitmapFactory.Options();

            return BitmapFactory.decodeFile(fielPath, options);
        }
        // 剩下的用Res内的指定图像资源做为图标图像
        Resources res = context.getResources();
        int icon = res.getIdentifier(fileInfo.getIconName(), "drawable", context.getPackageName());
        if(icon<0){icon=R.drawable.icon_file;}
        bitmap = BitmapFactory.decodeResource(context.getResources(), icon);
//            Log.i("msg","内存指定");
        return bitmap;
    }



    class ViewHolder_parperOne{
        CheckBox cb_del;
        ImageView icon;
        TextView lable,packagename,text;
    }

}
