package com.test1.customviewgroup;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.Bitmap.Config;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

//�Զ���View����
public class MyView extends View{

	private Paint paint  = new Paint() ;
	
	public MyView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	public MyView(Context context , AttributeSet attrs){
		super(context,attrs);
	}
	
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec){
		//���ø�View��СΪ 80 80
		setMeasuredDimension(50 , 50) ;
	}
	
	
	
	//����canvas���󣬼�����Ĭ�ϵ���ʾ����
	@Override
	public void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		Log.i("MyViewGroup", "MyView is onDraw ") ;
		//�Ӵ�
		paint.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
		paint.setColor(Color.RED);
		canvas.drawColor(Color.BLUE) ;
		canvas.drawRect(0, 0, 30, 30, paint);
		canvas.drawText("MyView", 10, 40, paint);
	}
}
