package personal.edu.phoneh.activity;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import personal.edu.phoneh.R;
import personal.edu.phoneh.biz.PhoneManager;

/**
 * Created by Administrator on 2016/11/28 0028.
 */
public class Activity_Mean_Left extends AppCompatActivity {
    ImageView left_return;
    TextView left_tv,versions_tv,name_tv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mean_left);
        left_return= (ImageView) findViewById(R.id.top_left);
        left_return.setImageResource(R.mipmap.btn_homeasup_default);
        name_tv= (TextView) findViewById(R.id.top_name_tv);
        name_tv.setText("关于我们");
        versions_tv= (TextView) findViewById(R.id.left_versions_tv);
        left_tv= (TextView) findViewById(R.id.left_tv);
        intClick();
        left_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.in_from_right,R.anim.out_to_left);
            }
        });
    }

    void intClick(){
        PackageManager manager = this.getPackageManager();
        PackageInfo info = null;
        try {
            info = manager.getPackageInfo(this.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String version = info.versionName;
        ApplicationInfo applicationInfo = null;
        try {
            manager = getApplicationContext().getPackageManager();
            applicationInfo = manager.getApplicationInfo(getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            applicationInfo = null;
        }
        String applicationName =(String) manager.getApplicationLabel(applicationInfo);

        versions_tv.setText("V"+version);
        left_tv.setText(applicationName);

    }

}
