package com.example.calorycountapp;

import android.widget.Button;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.view.View;


public class CaloryLevelButton extends Button {


    private int circleCol, labelCol;
    private String circleText;
    private Paint circlePaint;


    public CaloryLevelButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        circlePaint = new Paint();

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs,
                R.styleable.CaloryLevelButton, 0, 0);
        try {

            circleText = a.getString(R.styleable.CaloryLevelButton_circleLabel);
            circleCol = a.getInteger(R.styleable.CaloryLevelButton_circleColor,0);
            labelCol = a.getInteger(R.styleable.CaloryLevelButton_labelColor, 0);

        } finally {
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int viewWidthHalf = this.getMeasuredWidth()/2;
        int viewHeightHalf = this.getMeasuredHeight()/2;

        int radius;
        if(viewWidthHalf>viewHeightHalf)
            radius=viewHeightHalf-10;
        else
            radius=viewWidthHalf-10;
        circlePaint.setStyle(Style.FILL);
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(circleCol);
        canvas.drawCircle(viewWidthHalf, viewHeightHalf, radius, circlePaint);

        circlePaint.setColor(labelCol);

        circlePaint.setTextAlign(Paint.Align.CENTER);
        circlePaint.setTextSize(90);

        canvas.drawText(circleText, viewWidthHalf, viewHeightHalf, circlePaint);
    }

    public int getCircleColor(){
        return circleCol;
    }

    public int getLabelColor(){
        return labelCol;
    }

    public String getLabelText(){
        return circleText;
    }

    public void setCircleColor(int newColor){
        circleCol=newColor;
        invalidate();
    }
    public void setLabelColor(int newColor){
        labelCol=newColor;
        invalidate();
    }

    public void setLabelText(String newLabel){
        circleText=newLabel;
        invalidate();
    }
}
