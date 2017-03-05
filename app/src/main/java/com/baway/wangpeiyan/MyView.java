package com.baway.wangpeiyan;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Region;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

/**
 * 自定义VIew
 */

public class MyView extends View {

    Region region;
    Path rectPath;
    Paint mDeafultPaint = new Paint();
    Paint mTextPaint = new Paint();
    private int textColor;
    private int viewColor;
    private int index = 0;
    private boolean flag = true;
    private int integer;

    public MyView(Context context) {
        super(context);
        initView(context,null);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context,attrs);
        //获得自定义属性
    }
    private void initView(Context context, AttributeSet attrs) {
        //获得自定义属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.MyView);
        textColor = ta.getColor(R.styleable.MyView_textBg, Color.GREEN);
        viewColor = ta.getColor(R.styleable.MyView_viewBg, Color.GREEN);
        integer = ta.getInteger(R.styleable.MyView_textsize, 20);

        region = new Region();
        rectPath = new Path();
    }
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        mDeafultPaint.setColor(viewColor);
        rectPath.addRect(20, 20, 150, 150, Path.Direction.CW);

        // ▼将剪裁边界设置为视图大小
        Region globalRegion = new Region(-w, -h, w, h);
        // ▼将 Path 添加到 Region 中
        region.setPath(rectPath, globalRegion);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        if(index == 0){
            mDeafultPaint.setColor(Color.parseColor("#800000"));
        } else if(index == 1){
            mDeafultPaint.setColor(Color.parseColor("#C71585"));
        }else if( index == 2){
            mDeafultPaint.setColor(viewColor);
            index = -1;
        }
        Path rect = rectPath;
        canvas.drawPath(rect, mDeafultPaint);

        mTextPaint.setColor(textColor);
        mTextPaint.setTextSize(integer);
        if(index == 0){
            canvas.drawText("#800000",0,7,50,90,mTextPaint);
        } else if(index == 1){
            canvas.drawText("#C71585",0,7,50,90,mTextPaint);
        }else if(index == -1){
            canvas.drawText("#8B4513",0,7,50,90,mTextPaint);
        }
        //进行重绘
        if(flag){
            index++;
            postInvalidateDelayed(3000);
        }
    }
     int num = 0;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                int x = (int) event.getX();
                int y = (int) event.getY();
                if(region.contains(x,y)){
                    if(num == 0){
                        Toast.makeText(getContext(), "关闭", Toast.LENGTH_SHORT).show();
                        flag = false;
                        num = 1;
                    }else{
                        flag = true;
                        num = 0;
                        Toast.makeText(getContext(), "开启", Toast.LENGTH_SHORT).show();
                        postInvalidate();
                    }
                }
                break;
        }
        return true;
    }
}
