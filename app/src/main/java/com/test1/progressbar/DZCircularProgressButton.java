package com.test1.progressbar;

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


}
