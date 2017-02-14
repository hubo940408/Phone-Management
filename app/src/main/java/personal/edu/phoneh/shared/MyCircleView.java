package personal.edu.phoneh.shared;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2016/12/14 0014.
 */
public class MyCircleView extends View {
    Paint paint;    //画笔
    RectF oval;     //绘制区域
    float sweepArg1; //绘制角度
    float sweepArg2;
    boolean exe=false;

    public MyCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        intdata();
    }
    void intdata(){
        paint=new Paint();
        paint.setAntiAlias(true);
    }

    //计算布局宽高
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int viewWidth=MeasureSpec.getSize(widthMeasureSpec);
        int viewHight=MeasureSpec.getSize(heightMeasureSpec);
        oval=new RectF(0,0,viewWidth,viewHight);   //设置绘制区域
        setMeasuredDimension(viewWidth,viewHight);
    }

    //绘制
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画背景
        paint.setColor(android.graphics.Color.parseColor("#fa7a01"));
        canvas.drawArc(oval,-90,360,true,paint);
        //画扇形   第一个参数 绘制区域 其二个参数 起始角度，
        // 第三个参数 绘制角度，第四个参数 是否使用圆心，第五个参数 画笔
        if(exe) {
            paint.setColor(Color.GREEN);//绿色
            canvas.drawArc(oval, sweepArg2 - 90, sweepArg1, true, paint);
        }else {
            paint.setColor(android.graphics.Color.parseColor("#fa7a01"));
            canvas.drawArc(oval,-90,360,true,paint);
        }
        paint.setColor(android.graphics.Color.parseColor("#071369"));//蓝色
        canvas.drawArc(oval,-90,sweepArg2,true,paint);
    }

    public void setSweepArg(final int number, final int number2){
        final Timer timer=new Timer();
        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                if (sweepArg2 < number) {
                    sweepArg2 += 1;
                }else if (sweepArg1 <= number2) {
                    exe=true;
                    sweepArg1 += 1;
                }else{
                    timer.cancel();
                }
                postInvalidate();
            }
        };
            timer.schedule(task,20,20);
    }
}
