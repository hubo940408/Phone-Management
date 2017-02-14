package personal.edu.phoneh.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import personal.edu.phoneh.R;
import personal.edu.phoneh.biz.FileManager;
import personal.edu.phoneh.shared.Shared_Main;
import personal.edu.phoneh.adapter.View_Adapter;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class Activity_Guide extends AppCompatActivity {
    TextView skip_tv;
    RelativeLayout rl_one,rl_two,rl_three;
    boolean flag=true;
    File file;
    ViewPager vp;
    ImageView []imageView=new ImageView[3];
    ImageView one_im,two_im,three_im;
    View_Adapter va;
    ArrayList<View>list;
    RelativeLayout[]rl=new RelativeLayout[3];
    Shared_Main sm;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        outtext();
        getClick();
    }
    void outtext(){
        sm=new Shared_Main();
        int x=sm.intspf(this);
        if(x!=0){
            Intent intent=new Intent(Activity_Guide.this,Activity_Mean.class);
            startActivity(intent);
            finish();
        }else {
            sm.outspf(this,1);

            vp = (ViewPager) findViewById(R.id.guide_vp);

            one_im = (ImageView) findViewById(R.id.guide_one_im);
            two_im = (ImageView) findViewById(R.id.guide_two_im);
            three_im = (ImageView) findViewById(R.id.guide_three_im);
            skip_tv = (TextView) findViewById(R.id.guide_skip_im);
            rl_one = (RelativeLayout) findViewById(R.id.guide_rl1_rl);
            rl_two = (RelativeLayout) findViewById(R.id.guide_rl2_rl);
            rl_three = (RelativeLayout) findViewById(R.id.guide_rl3_rl);
            imageView[0] = one_im;
            imageView[1] = two_im;
            imageView[2] = three_im;
            rl[0] = rl_one;
            rl[1] = rl_two;
            rl[2] = rl_three;
            list = new ArrayList<>();
            View view = LayoutInflater.from(this).inflate(R.layout.guide_item, null);
            view.setBackgroundResource(R.mipmap.one);
            list.add(view);
            View view1 = LayoutInflater.from(this).inflate(R.layout.guide_item, null);
            view1.setBackgroundResource(R.mipmap.two);
            list.add(view1);
            View view2 = LayoutInflater.from(this).inflate(R.layout.guide_item, null);
            view2.setBackgroundResource(R.mipmap.three);
            list.add(view2);
            va = new View_Adapter(this);
            va.setList(list);
            vp.setAdapter(va);
            vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    inClick(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                    switch (state) {
                        case 0:
                            Intent intent;
                            if (flag) {
                                int name=sm.intactivity(Activity_Guide.this);
                                if(name==1){
                                    overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
                                    finish();
                                }else{
                                    intent= new Intent(Activity_Guide.this, Activity_Mean.class);
                                    startActivity(intent);
                                    overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
                                    finish();
                                }


                            }
                            break;
                        case 1:
                            flag = true;
                            break;
                        case 2:
                            flag = false;
                            break;
                    }

                }
            });
        }
    }

    public void onClick() {
        for (int i=0;i<rl.length;i++){
            final int a=i;
            rl[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    inClick(a);
                    vp.setCurrentItem(a);
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        onClick();
        onText();
    }

    void onText(){
        skip_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                int name=sm.intactivity(Activity_Guide.this);
                if(name==1){
                    overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
                    finish();
                }else {
                    intent = new Intent(Activity_Guide.this, Activity_Mean.class);
                    startActivity(intent);
                    overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                    finish();

                }
            }
        });
    }

    public void inClick(int position){
        if(position==list.size()-1){
            skip_tv.setVisibility(View.VISIBLE);
            Log.i("msg","跳过");
        }else{
            skip_tv.setVisibility(View.GONE);
        }
        for(int i=0;i<imageView.length;i++) {
            if (i==position) {
                imageView[i].setBackgroundResource(R.mipmap.colorlv);
            }else {
                imageView[i].setBackgroundResource(R.mipmap.colorhui);
            }
        }
    }
    void getClick(){
        try {
            file= FileManager.createFile(this);
        } catch (IOException e) {
//            e.printStackTrace();
            Log.i("msg","文件创建异常");
        }
        try {
            FileManager.copyFile(this,file,"commonnum.db");
        } catch (IOException e) {
//            e.printStackTrace();
            Log.i("msg","文件复制异常");
        }
    }
}
