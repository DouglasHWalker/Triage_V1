package com.retrotech.triage.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.retrotech.triage.R;

import java.util.Calendar;

public class MonthlyView extends View {

    // visible attirbutes
    private int foregroundColor;
    private int backgroundColor;
    private int accentColor;
    private float monthTextSize;
    private float dateTextSize;
    private float viewPadding; // TODO: make visible

    // internal attributes
    private Calendar workingCalendar = Calendar.getInstance();
    private String monthText;
    private float monthTextX;
    private float monthTextY;

    private float dateTextY;
    private float dayCellDimension;
    private float monthHeaderHeight;

    private float safeWidth;
    private float safeHeight;

    // paint
    private Paint backgroundPaint;
    private Paint monthTextPaint;
    private Paint dateTextPaint;

    // drawable variables
    private static final String[] DAYS = new String[] {"S","M","T","W","T","F","S"};
    private String[] DATES; // TODO: allocate dynamnically using a calendar
    private float[] dateXPositions = new float[7];
    private float[] dateYPositions = new float[7];



    public MonthlyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // TODO: use built in padding variables

        // visible attributes
        TypedArray attributes = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.MonthlyView,
                0,
                0);

        try {
            foregroundColor = attributes.getColor(R.styleable.MonthlyView_foregroundColor, 0);
            backgroundColor = attributes.getColor(R.styleable.MonthlyView_backgroundColor, 0);
            accentColor = attributes.getColor(R.styleable.MonthlyView_accentColor, 0);
            monthTextSize = attributes.getFloat(R.styleable.MonthlyView_monthTextSize, 21);
            dateTextSize = attributes.getFloat(R.styleable.MonthlyView_dateTextSize, 16);
            viewPadding =  52; // TODO: call visible resource
        } finally {
            attributes.recycle();
        }

        // internal attributes TODO: move this into own method, write a date interpreter
        monthText = "September 2020"; // DateInterpreter.interpretDate(workingCalendar.get(Calendar.MONTH));



        init();
    }

    //    Initialization
    private void init() {

        // background
        backgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        backgroundPaint.setColor(backgroundColor);


        // TextPaint
        monthTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        monthTextPaint.setColor(foregroundColor);
        monthTextPaint.setTextSize(monthTextSize);
        monthTextPaint.setTypeface(Typeface.DEFAULT);

        dateTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        dateTextPaint.setColor(foregroundColor);
        dateTextPaint.setTextSize(dateTextSize);
        dateTextPaint.setTypeface(Typeface.DEFAULT);
        dateTextPaint.setTextAlign(Paint.Align.CENTER);

        DATES = new String[(6 * 7)];
        for(int i = 0; i < DATES.length; i++){
            DATES[i] = String.valueOf(i);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        safeWidth = (float) getMeasuredWidth() - getPaddingRight() - getPaddingLeft();
        safeHeight = (float) getMeasuredHeight() - getPaddingBottom();

        float startX = (float) getPaddingLeft();
        float startY = (float) getPaddingTop();

        // day text

        // day text cell width
        dayCellDimension = safeWidth / DAYS.length;

        // monthText
        monthTextX = getPaddingLeft() + (dayCellDimension /2) - (dateTextSize / 2);
        monthTextY = getPaddingTop() + monthTextSize;

        for (int i = 0; i < dateXPositions.length; i++) {
            // calculate position
            dateXPositions[i] = getPaddingLeft() + (dayCellDimension / 2) + (dayCellDimension * i ); //centre of the text position
        } // TODO: fix up to use only variables no get padding calls etc

        monthHeaderHeight = getPaddingTop() + monthTextSize + viewPadding; //TODO: calculate using a given padding
        float textYOffset = (dayCellDimension / 2) + (dateTextSize / 2) - 2; // centre text in cell

        for(int i = 0; i < dateYPositions.length; i++){
            // calculate y positions
            dateYPositions[i] = (dayCellDimension * i) + monthHeaderHeight + textYOffset;
        }

        float viewHeight = monthHeaderHeight + (dateYPositions.length * dayCellDimension) + getPaddingBottom();

        setMeasuredDimension(getMeasuredWidth(), (int)viewHeight);

    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw background if needed
        backgroundPaint.setColor(backgroundColor);
        canvas.drawRect(getPaddingLeft(), getPaddingTop(), safeWidth, safeHeight, backgroundPaint);

        backgroundPaint.setColor(accentColor);
        // draw current day background indicator

        // draw month header
        canvas.drawText(monthText, monthTextX, monthTextY, monthTextPaint);

        canvas.drawRect(getPaddingLeft(), monthHeaderHeight + dayCellDimension, getPaddingLeft() + dayCellDimension, monthHeaderHeight + dayCellDimension * 2, backgroundPaint);
        // draw day header
        for (int i = 0; i < DAYS.length; i++) {
            canvas.drawText(DAYS[i], dateXPositions[i], dateYPositions[0], dateTextPaint);
            //canvas.drawText(DAYS[i], 100, 220, dayXPositions[i], monthHeaderHeight, dateTextPaint);
        }

        // draw dates
        // rows
        for (int i = 1; i < 7; i++){
            // columns
            for(int d = 0; d < 7; d++){
               canvas.drawText(DATES[i*d], dateXPositions[d], dateYPositions[i], dateTextPaint);
            }
        }

        // draw schedule event indicators

        // draw change month indicators

    }

    //    Accessor Methods
    // getters
    public int getAccentColor() {
        return accentColor;
    }

    public int getForegroundColor() {
        return foregroundColor;
    }

    public int getBackgroundColor() {
        return backgroundColor;
    }

    // setters
    public void setForegroundColor(int foregroundColor) {
        this.foregroundColor = foregroundColor;
        invalidate();
        requestLayout();
    }

    @Override
    public void setBackgroundColor(int backgroundColor) {
        this.backgroundColor = backgroundColor;
        invalidate();
        requestLayout();
    }

    public void setAccentColor(int accentColor) {
        this.accentColor = accentColor;
        invalidate();
        requestLayout();
    }
}
