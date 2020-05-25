package com.retrotech.triage.customviews;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

import com.retrotech.triage.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class MonthlyView extends View {

    // visible attirbutes
    private int foregroundColor;
    private int backgroundColor;
    private int accentColor;
    private int inactiveColor;
    private float monthTextSize;
    private float dateTextSize;
    private float viewPadding; // TODO: make visible

    // internal attributes
    private Calendar currentCalendar = Calendar.getInstance();
    private Calendar activeCalendar = Calendar.getInstance();
    private String[] activeCache;
    private Date activeDate = new Date(activeCalendar.getTimeInMillis());
    private String monthText;
    private float monthTextX;
    private float monthTextY;
    private int currentDayX;
    private int currentDayY;

    private float dateTextY;
    private float dayCellDimension;
    private float monthHeaderHeight;

    private float safeWidth;
    private float safeHeight;

    private int numDaysInDisplay;
    private int numRows;

    // paint
    private Paint backgroundPaint;
    private Paint monthTextPaint;
    private Paint dateTextPaint;
    private Paint currentDayBackgroundPaint;
    private Paint currentDayTextPaint;

    // drawable variables
    private static final String[] DAYS = new String[]{"S", "M", "T", "W", "T", "F", "S"};
    private float[] dateXPositions = new float[7];
    private float[] dateYPositions = new float[7];


    public MonthlyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // TODO: use built in padding variables

        // visible attributes
        TypedArray attributes = context.getTheme().obtainStyledAttributes(attrs, R.styleable.MonthlyView, 0, 0);
        try {
            foregroundColor = attributes.getColor(R.styleable.MonthlyView_foregroundColor, 0);
            backgroundColor = attributes.getColor(R.styleable.MonthlyView_backgroundColor, 0);
            accentColor = attributes.getColor(R.styleable.MonthlyView_accentColor, 0);
            inactiveColor = attributes.getColor(R.styleable.MonthlyView_inactiveColor, 0);
            monthTextSize = attributes.getFloat(R.styleable.MonthlyView_monthTextSize, 21);
            dateTextSize = attributes.getFloat(R.styleable.MonthlyView_dateTextSize, 16);
            viewPadding = 52; // TODO: call visible resource
        } finally {
            attributes.recycle();
        }

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

        // current day paint
        currentDayBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        currentDayBackgroundPaint.setColor(accentColor);

        currentDayTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        currentDayTextPaint.setColor(backgroundColor);
        currentDayTextPaint.setTextSize(dateTextSize);
        currentDayTextPaint.setTypeface(Typeface.DEFAULT);
        currentDayTextPaint.setTextAlign(Paint.Align.CENTER);

        // activeDates
        activeCache = getDisplayDatesForMonth(activeCalendar);

        // Month Text
        activeDate = new Date(activeCalendar.getTimeInMillis());
        monthText = new SimpleDateFormat("MMMM yyyy").format(activeDate);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        safeWidth = (float) getMeasuredWidth() - getPaddingRight() - getPaddingLeft(); // TODO: include all the margin, padding calls
        safeHeight = (float) getMeasuredHeight() - getPaddingBottom();

        // day text cell width
        dayCellDimension = safeWidth / DAYS.length;

        // monthText
        monthTextX = getPaddingLeft() + (dayCellDimension / 2) - (dateTextSize / 2);
        monthTextY = getPaddingTop() + monthTextSize;

        // current day
        currentDayX = currentCalendar.get(Calendar.DAY_OF_WEEK) - 1;

        for (int i = 0; i < dateXPositions.length; i++) {
            // calculate position
            dateXPositions[i] = getPaddingLeft() + (dayCellDimension / 2) + (dayCellDimension * i); //centre of the text position
        } // TODO: fix up to use only variables not get padding calls etc

        monthHeaderHeight = getPaddingTop() + monthTextSize + viewPadding; //TODO: calculate using a given padding
        float textYOffset = (dayCellDimension / 2) + (dateTextSize / 2) - 2; // centre text in cell

        for (int i = 0; i < dateYPositions.length; i++) {
            // calculate y positions
            dateYPositions[i] = (dayCellDimension * i) + monthHeaderHeight + textYOffset;
        }

        float viewHeight = monthHeaderHeight + ((7) * dayCellDimension) + getPaddingBottom();

        setMeasuredDimension(getMeasuredWidth(), (int) viewHeight);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // draw background if needed
        backgroundPaint.setColor(backgroundColor);
        canvas.drawRect(getPaddingLeft(), getPaddingTop(), safeWidth, safeHeight, backgroundPaint);

        // draw current day background indicator


        // draw month header
        canvas.drawText(monthText, monthTextX, monthTextY, monthTextPaint);

        // draw day header
        for (int i = 0; i < DAYS.length; i++) {
            canvas.drawText(DAYS[i], dateXPositions[i], dateYPositions[0], dateTextPaint);
            //canvas.drawText(DAYS[i], 100, 220, dayXPositions[i], monthHeaderHeight, dateTextPaint);
        }

        // draw active day
        if(currentDayY != 0) {
            canvas.drawCircle(dateXPositions[currentDayX], dateYPositions[currentDayY], dayCellDimension / 2, currentDayBackgroundPaint);
        }
        // draw dates

        int dateIndex = 0;
        // rows
        for (int r = 1; r <= numRows; r++) {
            // columns
            for (int c = 0; c < 7; c++) {

                if (Integer.parseInt(activeCache[dateIndex]) > dateIndex + 2) {
                    dateTextPaint.setColor(inactiveColor);
                } else if (dateIndex > 28 && Integer.parseInt(activeCache[dateIndex]) < 8) {
                    dateTextPaint.setColor(inactiveColor);
                } else {
                    dateTextPaint.setColor(foregroundColor);
                }
                if (currentDayY != 0 && r == currentDayY && c == currentDayX) {
                    dateTextPaint.setColor(backgroundColor);
                }
                canvas.drawText(activeCache[dateIndex], dateXPositions[c], dateYPositions[r], dateTextPaint);
                dateIndex++;
            }
        }

        dateTextPaint.setColor(foregroundColor);

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

    public Calendar getActiveCalendar() {
        return activeCalendar;
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

    public void setActiveCalendar(Calendar c) {
        this.activeCalendar = c;
        this.activeCache = getDisplayDatesForMonth(activeCalendar);
        init();
        invalidate();
    }

    // Helper Methods

    public String[] getDisplayDatesForMonth(Calendar givenCal) {

        Calendar workingCal = Calendar.getInstance();
        workingCal.set(Calendar.MONTH, givenCal.get(Calendar.MONTH));
        // Calculate array length
        // empties at start
        workingCal.set(Calendar.DAY_OF_MONTH, workingCal.getActualMinimum(Calendar.DAY_OF_MONTH));
        int numStartEmpties = workingCal.get(Calendar.DAY_OF_WEEK) - 1;
        // Days in month
        int numDaysInMonth = workingCal.getActualMaximum(Calendar.DAY_OF_MONTH);
        // empties at end
        workingCal.set(Calendar.DAY_OF_MONTH, workingCal.getActualMaximum(Calendar.DAY_OF_MONTH) + 1);
        int numEndEmpties = 7 - (workingCal.get(Calendar.DAY_OF_WEEK) - 1);
        if (workingCal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            numEndEmpties = 0;
        }

        int numDaysInDisplay = numStartEmpties + numDaysInMonth + numEndEmpties;
        String[] activeCache = new String[numDaysInDisplay];
        numRows = numDaysInDisplay / 7;

        int index = 0;
        // fill the start
        workingCal.set(Calendar.MONTH, givenCal.get(Calendar.MONTH));
        workingCal.set(Calendar.DAY_OF_MONTH, workingCal.getActualMinimum(Calendar.DAY_OF_MONTH) - numStartEmpties);
        int startDate = workingCal.get(Calendar.DAY_OF_MONTH);
        for (int i = startDate; i < startDate + numStartEmpties; i++) {
            activeCache[index] = String.valueOf(i);
            index++;
        }
        // fill the middle
        currentDayY = 0;
        for (int i = 1; i <= numDaysInMonth; i++) {
            activeCache[index] = String.valueOf(i);
            index++;

            if (i == currentCalendar.get(Calendar.DAY_OF_MONTH) &&
                    givenCal.get(Calendar.MONTH) == currentCalendar.get(Calendar.MONTH) &&
                    workingCal.get(Calendar.YEAR) == currentCalendar.get(Calendar.YEAR)) {
                currentDayY = (int) Math.ceil(index / 7.0);
            }
        }
        // fill the end
        for (int i = 1; i <= numEndEmpties; i++) {
            activeCache[index] = String.valueOf(i);
            index++;
        }
        return activeCache;
    }
}
