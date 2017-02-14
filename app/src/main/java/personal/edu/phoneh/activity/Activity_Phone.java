package personal.edu.phoneh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import personal.edu.phoneh.R;
import personal.edu.phoneh.adapter.Phone_Adapter;
import personal.edu.phoneh.biz.FileManager;
import personal.edu.phoneh.biz.FileManager1;
import personal.edu.phoneh.shared.Shared_Number;
import personal.edu.phoneh.entity.Phone_mainOne;

/**
 * 通讯大全界面
 * Created by Administrator on 2016/11/28 0028.
 */
public class Activity_Phone extends AppCompatActivity {
    GridView gv;
    TextView name_tv;
    ArrayList<Phone_mainOne>list;
    Phone_Adapter p_a;
    Shared_Number sn;
    ImageView iv;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);
        inClick();
        getList();
        getgridview();
    }
    public void inClick(){
        iv= (ImageView) findViewById(R.id.top_left);
        iv.setImageResource(R.mipmap.btn_homeasup_default);
        name_tv= (TextView) findViewById(R.id.top_name_tv);
        name_tv.setText("通讯大全");
        gv= (GridView) findViewById(R.id.phone_gridview_gv);
        p_a=new Phone_Adapter(this);
        sn=new Shared_Number();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    void getgridview(){
        p_a.setList(list);
        gv.setAdapter(p_a);
        iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.in_down,R.anim.to_down);
            }
        });
        gv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(Activity_Phone.this,Activity_Phone_One.class);

                sn.outspf(Activity_Phone.this,(position+1)+"");
                sn.outspfname(Activity_Phone.this,list.get(position).getName());
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);

            }
        });
    }
    void getList(){  //读取集合数据
        File file = null;
        try {
            file= FileManager.createFile(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        list=new ArrayList<>();
        list= (ArrayList<Phone_mainOne>) FileManager.getClassList(file);
    }


}

