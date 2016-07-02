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

	private Button btn;

	private MyView myView;

	public MyView getMyView() {
		return myView;
	}

	public void setMyView(MyView myView) {
		this.myView = myView;
	}

	public Button getBtn() {
		return btn;
	}

	public void setBtn(Button btn) {
		this.btn = btn;
	}

	public MyViewGroup(Context context) {
		super(context);
		mContext = context ;
		init() ;
	}

	//xml?????????,????ù?????
    public MyViewGroup(Context context , AttributeSet attrs){
    	super(context,attrs) ;
    	mContext = context ;
    	init() ;
    }
	
    //?MyViewGroup????????View
    private void init(){
    	//????ViewGroup????addView()?????????View
    	
    	//child ????? ?? Button
    	btn= new Button(mContext) ;
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
    	myView = new MyView(mContext) ;
    	this.addView(myView) ; 
    }
    
    @Override
	//对每个子View进行measure():设置每子View的大小，即实际宽和高
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		//通过init()方法，我们为该ViewGroup对象添加了三个视图 ， Button、 ImageView、TextView
		int childCount = getChildCount() ;
		Log.i(TAG, "the size of this ViewGroup is ----> " + childCount) ;

		Log.i(TAG, "**** onMeasure start *****");

		//获取该ViewGroup的实际长和宽  涉及到MeasureSpec类的使用
		int specSize_Widht = MeasureSpec.getSize(widthMeasureSpec) ;
		int specSize_Heigth = MeasureSpec.getSize(heightMeasureSpec) ;

		Log.i(TAG, "**** specSize_Widht " + specSize_Widht+ " * specSize_Heigth   *****" + specSize_Heigth) ;

		//设置本ViewGroup的宽高
		setMeasuredDimension(specSize_Widht , specSize_Heigth) ;


		for (int i=0 ;i<childCount ; i++){
			View child = getChildAt(i) ;   //获得每个对象的引用
			child.measure(50, 50) ;   //简单的设置每个子View对象的宽高为 50px , 50px
			//或者可以调用ViewGroup父类方法measureChild()或者measureChildWithMargins()方法
			this.measureChild(child, widthMeasureSpec, heightMeasureSpec) ;
		}

	}


	@Override
	//对每个子View视图进行布局
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		//通过init()方法，我们为该ViewGroup对象添加了三个视图 ， Button、 ImageView、TextView
		int childCount = getChildCount() ;

		int startLeft = 0 ;//设置每个子View的起始横坐标
		int startTop = 10 ; //每个子View距离父视图的位置 ， 简单设置为10px吧 。 可以理解为 android:margin=10px ;

		Log.i(TAG, "**** onLayout start ****") ;
		for(int i=0 ;i<childCount; i++) {
			View child = getChildAt(i) ;   //获得每个对象的引用
			child.layout(startLeft, startTop, startLeft+child.getMeasuredWidth(), startTop+child.getMeasuredHeight()) ;
			startLeft =startLeft+child.getMeasuredWidth() + 10;  //校准startLeft值，View之间的间距设为10px ;
			Log.i(TAG, "**** onLayout startLeft ****" +startLeft) ;
		}
	}
	//绘图过程Android已经为我们封装好了 ,这儿只为了观察方法调用程
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
