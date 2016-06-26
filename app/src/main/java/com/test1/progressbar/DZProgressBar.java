package com.test1.progressbar;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.test1.R;

/**
 * Created by David on 17/06/2016.
 */
public class DZProgressBar extends LinearLayout
{
    protected final static int DEFAULT_MAX_PROGRESS = 100;
    protected final static int DEFAULT_PROGRESS = 0;
    protected final static int DEFAULT_SECONDARY_PROGRESS = 0;
    protected final static int DEFAULT_PROGRESS_RADIUS = 30;
    protected final static int DEFAULT_BACKGROUND_PADDING = 0;

    private LinearLayout layoutBackground;
    private LinearLayout layoutProgress;
    private LinearLayout layoutSecondaryProgress;

    private int radius;
    private int padding;
    private int totalWidth;

    private float max;
    private float progress;
    private float secondaryProgress;

    private int colorBackground;
    private int colorProgress;
    private int colorSecondaryProgress;

    private boolean isReverse;


    public DZProgressBar(Context context) {
        super(context);
    }

    public DZProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        previewLayout(context);
        setup(context, attrs);
    }

    public DZProgressBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void previewLayout(Context context) {
        setGravity(Gravity.CENTER);
        TextView tv = new TextView(context);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        tv.setLayoutParams(params);
        tv.setGravity(Gravity.CENTER);
        tv.setText(getClass().getSimpleName());
        tv.setTextColor(Color.WHITE);
        tv.setBackgroundColor(Color.GRAY);
        tv.setText("tvtvtvtvtvtvtv");
        addView(tv);
    }

    public void setup(Context context, AttributeSet attrs) {
        setupStyleable(context, attrs);

        removeAllViews();
        // Setup layout for sub class
        LayoutInflater.from(context).inflate(initLayout(), this);
        // Initial default view
        layoutBackground = (LinearLayout) findViewById(R.id.layout_background);
        layoutProgress = (LinearLayout) findViewById(R.id.layout_progress);
        layoutSecondaryProgress = (LinearLayout) findViewById(R.id.layout_secondary_progress);

        initView();
    }

    public void setupStyleable(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.RoundCornerProgress);

        radius = (int) typedArray.getDimension(R.styleable.RoundCornerProgress_rcRadius, dp2px(DEFAULT_PROGRESS_RADIUS));
        padding = (int) typedArray.getDimension(R.styleable.RoundCornerProgress_rcBackgroundPadding, dp2px(DEFAULT_BACKGROUND_PADDING));

//        isReverse = typedArray.getBoolean(R.styleable.RoundCornerProgress_rcReverse, false);

        max = typedArray.getFloat(R.styleable.RoundCornerProgress_rcMax, DEFAULT_MAX_PROGRESS);
        progress = typedArray.getFloat(R.styleable.RoundCornerProgress_rcProgress, DEFAULT_PROGRESS);
//        secondaryProgress = typedArray.getFloat(R.styleable.RoundCornerProgress_rcSecondaryProgress, DEFAULT_SECONDARY_PROGRESS);

        int colorBackgroundDefault = context.getResources().getColor(R.color.round_corner_progress_bar_background_default);
        colorBackground = typedArray.getColor(R.styleable.RoundCornerProgress_rcBackgroundColor, colorBackgroundDefault);

        int colorProgressDefault = context.getResources().getColor(R.color.round_corner_progress_bar_progress_default);
        colorProgress = typedArray.getColor(R.styleable.RoundCornerProgress_rcProgressColor, colorProgressDefault);

//        int colorSecondaryProgressDefault = context.getResources().getColor(R.color.round_corner_progress_bar_secondary_progress_default);
//        colorSecondaryProgress = typedArray.getColor(R.styleable.RoundCornerProgress_rcSecondaryProgressColor, colorSecondaryProgressDefault);

        typedArray.recycle();

        initStyleable(context, attrs);
    }

    @Override
    public void invalidate() {
        super.invalidate();
        drawAll();
    }

    public int initLayout() {
        return R.layout.layout_round_corner_progress_bar;
    }

    protected float dp2px(float dp) {
        DisplayMetrics displayMetrics = getContext().getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    protected void initStyleable(Context context, AttributeSet attrs) {

    }

    protected void initView() {

        ViewTreeObserver vto =this.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                DZProgressBar.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                totalWidth = DZProgressBar.this.getWidth();

                drawAll();

            }
        });


    }

    protected void drawAll() {
        drawBackgroundProgress();
//        drawPadding();
//        drawProgressReverse();
        drawPrimaryProgress();
//        drawSecondaryProgress();
//        onViewDraw();
    }

    // Draw progress background
    @SuppressWarnings("deprecation")
    private void drawBackgroundProgress() {
        GradientDrawable backgroundDrawable = createGradientDrawable(colorBackground);
        int newRadius = radius - (padding / 2);
        backgroundDrawable.setCornerRadii(new float[]{newRadius, newRadius, newRadius, newRadius, newRadius, newRadius, newRadius, newRadius});
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            layoutBackground.setBackground(backgroundDrawable);
        } else {
            layoutBackground.setBackgroundDrawable(backgroundDrawable);
        }
    }

    // Create an empty color rectangle gradient drawable
    protected GradientDrawable createGradientDrawable(int color) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        gradientDrawable.setColor(color);
        return gradientDrawable;
    }

    protected void onViewDraw() {

    }

    private void drawPrimaryProgress() {
        drawProgress(layoutProgress, max, progress, totalWidth, radius, padding, colorProgress, isReverse);
    }

//    private void drawSecondaryProgress() {
//        drawProgress(layoutSecondaryProgress, max, secondaryProgress, totalWidth, radius, padding, colorSecondaryProgress, isReverse);
//    }

    protected void drawProgress(LinearLayout layoutProgress, float max, float progress, float totalWidth,
                                int radius, int padding, int colorProgress, boolean isReverse) {
        GradientDrawable backgroundDrawable = createGradientDrawable(colorProgress);
        int newRadius = radius - (padding / 2);
        backgroundDrawable.setCornerRadii(new float[]{newRadius, newRadius, newRadius, newRadius, newRadius, newRadius, newRadius, newRadius});
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            layoutProgress.setBackground(backgroundDrawable);
        } else {
            layoutProgress.setBackgroundDrawable(backgroundDrawable);
        }

        float ratio = max / progress;
        int progressWidth = (int) ((totalWidth - (padding * 2)) / ratio);
        ViewGroup.LayoutParams progressParams = layoutProgress.getLayoutParams();
        progressParams.width = progressWidth;
        layoutProgress.setLayoutParams(progressParams);
    }

    protected void onSizeChanged(int newWidth, int newHeight, int oldWidth, int oldHeight) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight);
//        if(!isInEditMode()) {
//            totalWidth = newWidth;
//            drawAll();
//            postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    drawPrimaryProgress();
////                    drawSecondaryProgress();
//                }
//            }, 5);
//        }
    }








}
