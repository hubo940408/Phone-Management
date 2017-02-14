package personal.edu.phoneh.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import personal.edu.phoneh.R;
import personal.edu.phoneh.adapter.Phone_One_Adapter;
import personal.edu.phoneh.biz.FileManager;
import personal.edu.phoneh.entity.Phone_mainOne;
import personal.edu.phoneh.shared.Shared_Number;
import personal.edu.phoneh.entity.Phone_One_main;

/**
 * Created by Administrator on 2016/11/29 0029.
 */
public class Activity_Phone_One extends AppCompatActivity {
    TextView tv;
    ListView one_lv;
    ImageView iv;
    Shared_Number sn;
    Phone_One_Adapter poa;
    List<Phone_One_main> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getArraylist();
        setContentView(R.layout.activity_phone_one);
        inClick();
        getback();
    }
    void inClick(){
        tv= (TextView) findViewById(R.id.top_name_tv);
        tv.setText(sn.intspfname(this));
        one_lv= (ListView) findViewById(R.id.phone_one_listview_lv);
        iv= (ImageView) findViewById(R.id.top_left);
        iv.setImageResource(R.mipmap.btn_homeasup_default);
        poa=new Phone_One_Adapter(this);
        poa.setList(list);
        one_lv.setAdapter(poa);

    }
    void getback(){
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.from_left,R.anim.to_right);
            }
        });
        one_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_CALL);//系统的拨号
                intent.setData(Uri.parse("tel:"+list.get(position).getNumber()));//传递电话号码
                startActivity(intent);
            }
        });
    }
    void getArraylist(){
        sn=new Shared_Number();
        list=new ArrayList<>();
        File file=null;
        try {
            file= FileManager.createFile(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String name1=sn.intspf(this);
        String name="table"+name1;
        list=FileManager.getList(file,name);
    }


}
