package com.retrotech.triage.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.retrotech.triage.R;

import java.util.Calendar;

public class MonthlyView extends View {

    // visible attirbutes
    private int foregroundColor;
    private int backgroundColor;
    private int accentColor;
    private float monthTextSize;
    private float dateTextSize;

    // internal attributes
    private Calendar workingCalendar = Calendar.getInstance();
    private String monthText;
    private float monthTextX;
    private float monthTextY;

    private float dayCellWidth;
    private float dayY;

    private float safeWidth;
    private float safeHeight;

    // paint
    private Paint backgroundPaint;
    private Paint monthTextPaint;
    private Paint dateTextPaint;

    // drawable variables
    private static final String[] DAYS = new String[] {"S","M","T","W","T","F","S"};
    private float[] dayXPositions;


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


    }

    @Override
    protected void onSizeChanged(int width, int height, int oldWidth, int oldHeight) {
        super.onSizeChanged(width, height, oldWidth, oldHeight);

        safeWidth = (float) width - getPaddingRight();
        safeHeight = (float) height - getPaddingBottom();

        float startX = (float) getPaddingLeft();
        float startY = (float) getPaddingTop();

        // monthText
        monthTextX = startX;
        monthTextY = startY + monthTextSize;

        // day text

        // day text cell width
        dayCellWidth = safeWidth / DAYS.length;

        dayXPositions = new float[7];
        for (int i = 0; i < dayXPositions.length; i++) {
            // calculate position
            dayXPositions[i] = (startX) + ((dayCellWidth) * i ); //centre of the text position
        }

        dayY = monthTextY + 96; //TODO: calculate using a given padding
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw background if needed
        canvas.drawRect(getPaddingLeft(), getPaddingTop(), safeWidth, safeHeight, backgroundPaint);

        // draw current day background indicator

        // draw month header
        canvas.drawText(monthText, monthTextX, monthTextY, monthTextPaint);

        // draw day header
        for (int i = 0; i < DAYS.length; i++) {
            canvas.drawText(DAYS[i], dayXPositions[i], dayY, dateTextPaint);
        }

        // draw dates


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
