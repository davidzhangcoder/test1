package com.test1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.test1.customviewgroup.MyViewGroup;
import com.test1.progressbar.DZCircularProgressButton;
import com.test1.progressbar.DZProgressBar;

public class MainActivity3 extends AppCompatActivity
{

    private Button btn  ;
    private TextView txt ;
    private MyViewGroup myViewGroup  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        //获得main.xml的控件引用
        btn = (Button) findViewById(R.id.btn) ;
        txt = (TextView)findViewById(R.id.txt) ;
        myViewGroup = (MyViewGroup)findViewById(R.id.custemViewGroup) ;
        final DZProgressBar progressOne = (DZProgressBar) findViewById(R.id.dzprogressbar);


        //点击我查看绘制流程
        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
				// TODO Auto-generated method stub
				if(txt.getVisibility() == View.VISIBLE)
					txt.setVisibility(View.INVISIBLE) ;
				else
					txt.setVisibility(View.INVISIBLE) ;

//                myViewGroup.invalidate() ;
				if(myViewGroup.getVisibility() == View.VISIBLE)
					myViewGroup.setVisibility(View.GONE) ;
				else
					myViewGroup.setVisibility(View.VISIBLE) ;

//                myViewGroup.invalidate() ;
//                myViewGroup.requestFocus() ;

                myViewGroup.getMyView().invalidate();;
            }
        }) ;

        Button progressbarbtn = (Button) findViewById(R.id.progressbarbtn) ;
        progressbarbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressOne.setProgress( progressOne.getProgress()+1 );
            }
        }) ;

        final DZCircularProgressButton dZCircularProgressButton1 = (DZCircularProgressButton) findViewById(R.id.dZCircularProgressButton1) ;
        dZCircularProgressButton1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dZCircularProgressButton1.doProgressMorphing();
            }
        }) ;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_activity3, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
