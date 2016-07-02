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

	//xml?????????,????��?????
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
	//��ÿ����View����measure():����ÿ��View�Ĵ�С����ʵ�ʿ�͸�
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		//ͨ��init()����������Ϊ��ViewGroup���������������ͼ �� Button�� ImageView��TextView
		int childCount = getChildCount() ;
		Log.i(TAG, "the size of this ViewGroup is ----> " + childCount) ;

		Log.i(TAG, "**** onMeasure start *****");

		//��ȡ��ViewGroup��ʵ�ʳ��Ϳ�  �漰��MeasureSpec���ʹ��
		int specSize_Widht = MeasureSpec.getSize(widthMeasureSpec) ;
		int specSize_Heigth = MeasureSpec.getSize(heightMeasureSpec) ;

		Log.i(TAG, "**** specSize_Widht " + specSize_Widht+ " * specSize_Heigth   *****" + specSize_Heigth) ;

		//���ñ�ViewGroup�Ŀ��
		setMeasuredDimension(specSize_Widht , specSize_Heigth) ;


		for (int i=0 ;i<childCount ; i++){
			View child = getChildAt(i) ;   //���ÿ�����������
			child.measure(50, 50) ;   //�򵥵�����ÿ����View����Ŀ��Ϊ 50px , 50px
			//���߿��Ե���ViewGroup���෽��measureChild()����measureChildWithMargins()����
			this.measureChild(child, widthMeasureSpec, heightMeasureSpec) ;
		}

	}


	@Override
	//��ÿ����View��ͼ���в���
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		//ͨ��init()����������Ϊ��ViewGroup���������������ͼ �� Button�� ImageView��TextView
		int childCount = getChildCount() ;

		int startLeft = 0 ;//����ÿ����View����ʼ������
		int startTop = 10 ; //ÿ����View���븸��ͼ��λ�� �� ������Ϊ10px�� �� �������Ϊ android:margin=10px ;

		Log.i(TAG, "**** onLayout start ****") ;
		for(int i=0 ;i<childCount; i++) {
			View child = getChildAt(i) ;   //���ÿ�����������
			child.layout(startLeft, startTop, startLeft+child.getMeasuredWidth(), startTop+child.getMeasuredHeight()) ;
			startLeft =startLeft+child.getMeasuredWidth() + 10;  //У׼startLeftֵ��View֮��ļ����Ϊ10px ;
			Log.i(TAG, "**** onLayout startLeft ****" +startLeft) ;
		}
	}
	//��ͼ����Android�Ѿ�Ϊ���Ƿ�װ���� ,���ֻΪ�˹۲췽�����ó�
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
