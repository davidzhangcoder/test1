package com.test1.progressbar;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.util.StateSet;
import android.widget.Button;

import com.test1.R;

/**
 * Created by David on 01/07/2016.
 */
public class DZCircularProgressButton extends Button
{
    private StrokeGradientDrawable background;

    private ColorStateList idleColorStateList;
    private ColorStateList completeColorStateList;
    private ColorStateList errorColorStateList;

    private StateListDrawable idleStateListDrawable;
    private StateListDrawable completeStateListDrawable;
    private StateListDrawable errorStateListDrawable;

    private float cornerRadius;

    private int mStrokeWidth;

    public DZCircularProgressButton(Context context) {
        super(context);
    }

    public DZCircularProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);

        initAttr(context, attrs);

        createColorStateList();

        setText("mIdleText");

        setBackgroundCompat(idleStateListDrawable);
    }

    public DZCircularProgressButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initAttr(context, attrs);

        createColorStateList();

        setText("mIdleText");

        setBackgroundCompat(idleStateListDrawable);
    }

    private void initAttr(Context context, AttributeSet attrs)
    {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.DZCircularProgressButton);

        int idleColorSelector = typedArray.getResourceId(R.styleable.DZCircularProgressButton_idleColorSelector, R.color.cpb_idle_state_selector);
        idleColorStateList = this.getResources().getColorStateList( idleColorSelector );

        int completeColorSelector = typedArray.getResourceId(R.styleable.DZCircularProgressButton_completeColorSelector, R.color.cpb_complete_state_selector );
        completeColorStateList = this.getResources().getColorStateList( completeColorSelector );

        int errorColorSelector = typedArray.getResourceId(R.styleable.DZCircularProgressButton_errorColorSelector, R.color.cpb_error_state_selector );
        errorColorStateList = this.getResources().getColorStateList( errorColorSelector );

        cornerRadius = typedArray.getDimension(R.styleable.DZCircularProgressButton_cornerRadius, 0);

        typedArray.recycle();

        mStrokeWidth = (int) getContext().getResources().getDimension(R.dimen.cpb_stroke_width);
    }

    private void createColorStateList()
    {
        int enablecolor = idleColorStateList.getColorForState(new int[]{android.R.attr.state_enabled}, 0);
        int presscolor = idleColorStateList.getColorForState(new int[]{android.R.attr.state_pressed}, 0);
        int focuscolor = idleColorStateList.getColorForState(new int[]{android.R.attr.state_focused}, 0);
        int disablecolor = idleColorStateList.getColorForState(new int[]{-android.R.attr.state_enabled}, 0);

        if( background == null )
            background = createStrokeGradientDrawable(enablecolor);

        idleStateListDrawable = new StateListDrawable();

//        idleStateListDrawable.addState(new int[]{android.R.attr.state_enabled}, createStrokeGradientDrawable(enablecolor).getGradientDrawable());
        idleStateListDrawable.addState( new int[]{ android.R.attr.state_pressed } ,  createStrokeGradientDrawable( presscolor ).getGradientDrawable() );
        idleStateListDrawable.addState( new int[]{ android.R.attr.state_focused } ,  createStrokeGradientDrawable( focuscolor ).getGradientDrawable() );
        idleStateListDrawable.addState(new int[]{-android.R.attr.state_enabled}, createStrokeGradientDrawable(disablecolor).getGradientDrawable());
        idleStateListDrawable.addState(StateSet.WILD_CARD, background.getGradientDrawable());
    }

    private StrokeGradientDrawable createStrokeGradientDrawable( int color )
    {
        GradientDrawable drawable = (GradientDrawable)this.getResources().getDrawable( R.drawable.cpb_background ).mutate();
        drawable.setCornerRadius(cornerRadius);
        drawable.setColor(color);

        StrokeGradientDrawable strokeGradientDrawable = new StrokeGradientDrawable(drawable);
        strokeGradientDrawable.setStrokeColor(color);
        strokeGradientDrawable.setStrokeWidth( mStrokeWidth );

        return strokeGradientDrawable;
    }

    @SuppressWarnings("deprecation")
    public void setBackgroundCompat(Drawable drawable) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(drawable);
        } else {
            setBackgroundDrawable(drawable);
        }
    }

    @Override
    protected void drawableStateChanged()
    {

//        createColorStateList();
//        setBackgroundCompat( idleStateListDrawable );
//
//        this.getDrawableState();

        super.drawableStateChanged();

    }

    public void doProgressMorphing()
    {
        final int mFromWidth = this.getWidth();
        final int mToWidth = this.getHeight();
        final int mPadding = 0;

        final int fromColor = idleColorStateList.getColorForState(new int[]{android.R.attr.state_enabled}, 0);
        final int toColor = getResources().getColor(R.color.cpb_white);

        ValueAnimator valueAnimator = ValueAnimator.ofInt(this.getWidth(), this.getHeight());

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {

                Integer value = (Integer) animation.getAnimatedValue();
                int leftOffset;
                int rightOffset;
                int padding;

                if (mFromWidth > mToWidth) {
                    leftOffset = (mFromWidth - value) / 2;
                    rightOffset = mFromWidth - leftOffset;
                    padding = (int) (mPadding * animation.getAnimatedFraction());
                } else {
                    leftOffset = (mToWidth - value) / 2;
                    rightOffset = mToWidth - leftOffset;
                    padding = (int) (mPadding - mPadding * animation.getAnimatedFraction());
                }

                background.getGradientDrawable()
                        .setBounds(leftOffset + padding, padding, rightOffset - padding, getHeight() - padding);


            }
        });

        ObjectAnimator bgColorAnimation = ObjectAnimator.ofInt(background.getGradientDrawable(), "color", fromColor, toColor);
        bgColorAnimation.setEvaluator(new ArgbEvaluator());

//        valueAnimator.start();

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.setDuration( 400 );
        animatorSet.playTogether(valueAnimator, bgColorAnimation);

        animatorSet.start();
    }


}
