package com.test1.customviewgroup;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.test1.R;

/**
 * @author http://http://blog.csdn.net/qinjuning
 */
//?????ViewGroup ????
public class MyViewGroup  extends ViewGroup{


	private static String TAG = "MyViewGroup" ;
	private Context mContext ;
	
	public MyViewGroup(Context context) {
		super(context);
		mContext = context ;
		init() ;
	}

	//xml?????????,????¨´?????
    public MyViewGroup(Context context , AttributeSet attrs){
    	super(context,attrs) ;
    	mContext = context ;
    	init() ;
    }
	
    //?MyViewGroup????????View
    private void init(){
    	//????ViewGroup????addView()?????????View
    	
    	//child ????? ?? Button
    	Button btn= new Button(mContext) ;
    	btn.setText("I am Button") ;
    	this.addView(btn) ;
    	
    	//child ????? : ImageView 
    	ImageView img = new ImageView(mContext) ;
    	img.setBackgroundResource(R.drawable.icon) ;
    	this.addView(img) ;
    	
    	//child ?????? : TextView
    	TextView txt = new TextView(mContext) ;
    	txt.setText("Only Text") ;
    	this.addView(txt) ; 
    	
    	//child ?????? ?? ?????View
    	MyView myView = new MyView(mContext) ;
    	this.addView(myView) ; 
    }
    
    @Override
    //???????View????measure():???????View???§³??????????
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
    	//???init()?????????????ViewGroup??????????????? ?? Button?? ImageView??TextView
    	int childCount = getChildCount() ;
    	Log.i(TAG, "the size of this ViewGroup is ----> " + childCount) ;
    	    	    	
    	Log.i(TAG, "**** onMeasure start *****") ;
    	
    	//?????ViewGroup?????????  ????MeasureSpec??????
    	int specSize_Widht = MeasureSpec.getSize(widthMeasureSpec) ;
    	int specSize_Heigth = MeasureSpec.getSize(heightMeasureSpec) ;
    	
    	Log.i(TAG, "**** specSize_Widht " + specSize_Widht + " * specSize_Heigth   *****" + specSize_Heigth) ;
    	
    	//?????ViewGroup????
    	setMeasuredDimension(specSize_Widht, specSize_Heigth) ;
    	
    	
    	
    	
    	for(int i=0 ;i<childCount ; i++){
    		View child = getChildAt(i) ;   //???????????????
    		child.measure(50, 50) ;   //????????????View???????? 50px , 50px  
    		//??????????ViewGroup??????measureChild()????measureChildWithMargins()????
    	    this.measureChild(child, widthMeasureSpec, heightMeasureSpec) ;
    	}
    	
    }
    
	@Override
	//???????View??????§Ó???
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
    	//???init()?????????????ViewGroup??????????????? ?? Button?? ImageView??TextView
    	int childCount = getChildCount() ;
    	
    	int startLeft = 0 ;//?????????View?????????? 
    	int startTop = 10 ; //?????View?????????¦Ë?? ?? ???????10px?? ?? ???????? android:margin=10px ;
    	
    	Log.i(TAG, "**** onLayout start ****") ;
    	for(int i=0 ;i<childCount ; i++){
    		View child = getChildAt(i) ;   //???????????????
    		child.layout(startLeft, startTop, startLeft+child.getMeasuredWidth(), startTop+child.getMeasuredHeight()) ;
    		startLeft =startLeft+child.getMeasuredWidth() + 10;  //§µ?startLeft???View?????????10px ;
    		Log.i(TAG, "**** onLayout startLeft ****" + startLeft) ;
    	}   	   	
	}
	//??????Android?????????????? ,??????????????¨®?
	protected void dispatchDraw(Canvas canvas){
		Log.i(TAG, "**** dispatchDraw start ****") ;
		
		super.dispatchDraw(canvas) ;
	}
    
	protected boolean drawChild(Canvas canvas , View child, long drawingTime){
		Log.i(TAG, "**** drawChild start ****") ;
		
		return super.drawChild(canvas, child, drawingTime) ;
	}








	@Override
	public void invalidate() {
		Log.i(TAG, "**** invalidate ****") ;

		super.invalidate();
	}

	@Override
	public void requestLayout() {
		Log.i(TAG, "**** requestLayout ****") ;

		super.requestLayout();
	}

	@Override
	public boolean requestFocus(int direction, Rect previouslyFocusedRect)
	{
		Log.i(TAG, "**** requestFocus ****") ;

		return super.requestFocus(direction,previouslyFocusedRect);
	}
}
