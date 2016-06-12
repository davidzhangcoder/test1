package com.test1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.test1.databinding.ActivityMain1Binding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity1 extends AppCompatActivity {

    private List<Sample> samples;

    private ActivityMain1Binding binding;

    private Sample sample;

    private Button turnBtn;

    private ImageView imageView;

    @InjectView( R.id.outterView )
    LinearLayout outterView;

    private LinearLayout innerView;

    private String img_path = "";

    private List<Tuple2<String,Bitmap>> bitmapList = new ArrayList<Tuple2<String,Bitmap>>();

    private SelectableImageAdapter selectableImageAdapter;

    private SelectableImage a1;



    public LinearLayout getOutterView() {
        return outterView;
    }

    public void setOutterView(LinearLayout outterView) {
        this.outterView = outterView;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main1);
        sample = new Sample(ContextCompat.getColor(this, R.color.sample_red), "Transitions");
        binding.setSample(sample);
        binding.setClick(this);

        ButterKnife.inject(this);

        binding.button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity1.this, MainActivity.class);
                startActivity(intent);
                // 第一个参数是目标Activity进入时的动画，第二个参数是当前Activity退出时的动画
                overridePendingTransition(R.animator.fade_in,
                        R.animator.fade_out);

            }
        });

        binding.button.setOnTouchListener( new ButtonOnTouchListener() );

//        binding.imageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                launch(binding.imageView);
//            }
//        });
//
//        binding.imageView3.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                launch( binding.imageView );
//            }
//        });
//
//        binding.imageView4.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                launch( binding.imageView );
//            }
//        });


//        setContentView(R.layout.activity_main1);
//        setupWindowAnimations();
        setupSamples();
        setupToolbar();
        setupLayout();


        innerView = new LinearLayout( this );
        a1 = new SelectableImage( this , null , this.getResources().getDimension(R.dimen.x86), this.getResources().getDimension(R.dimen.x86) );
        innerView.addView( a1 );


        outterView.addView( innerView );

    }

    private void launch(View view) {
        ActivityOptionsCompat compat = ActivityOptionsCompat.makeScaleUpAnimation(
                view,
                view.getLeft(),
                view.getTop(),
                view.getWidth(),
                view.getHeight());
        ActivityCompat.startActivity(this, new Intent(MainActivity1.this, MainActivity2.class),
                compat.toBundle());
    }

    public void click(View view) {

        ViewGroup.LayoutParams para = ((ImageView)a1.findViewById( R.id.image_picker_add )).getLayoutParams();
        para.height = 300;
        para.width = 300;
        ((ImageView)a1.findViewById( R.id.image_picker_add )).setLayoutParams(para);


        if ("Transitions".equals(sample.getName()))
            sample.setName("Shared Elements");
        else
            sample.setName("Transitions");
    }


//    private void setupWindowAnimations() {
//        // Re-enter transition is executed when returning to this activity
//        Slide slideTransition = new Slide();
//        slideTransition.setSlideEdge(Gravity.LEFT);
//        slideTransition.setDuration(getResources().getInteger(R.integer.anim_duration_long));
//        getWindow().setReenterTransition(slideTransition);
//        getWindow().setExitTransition(slideTransition);
//    }

    private void setupSamples() {
        samples = Arrays.asList(
                new Sample(ContextCompat.getColor(this, R.color.sample_red), "Transitions"),
                new Sample(ContextCompat.getColor(this, R.color.sample_blue), "Shared Elements"),
                new Sample(ContextCompat.getColor(this, R.color.sample_green), "View animations"),
                new Sample(ContextCompat.getColor(this, R.color.sample_yellow), "Circular Reveal Animation")
        );
    }

    private void setupToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void setupLayout() {
        RecyclerView recyclerView = binding.sampleList;
//        recyclerView.setHasFixedSize(true);

//        recyclerView.setLayoutManager(new LinearLayoutManager(this,OrientationHelper.VERTICAL,false));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));//这里用线性宫格显示 类似于grid view
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流

//        int spacingInPixels = 50;
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3,spacingInPixels,true));

        List<SelectableImage> selectableImageList = new ArrayList<SelectableImage>();
        SelectableImage selectableImage = new SelectableImage( this , null , this.getResources().getDimension(R.dimen.x86) , this.getResources().getDimension(R.dimen.x86) );
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT);
        selectableImage.setLayoutParams( params );
//        selectableImageList.add(selectableImage);
//        selectableImageList.add( selectableImage );
//        selectableImageList.add( selectableImage );
//        selectableImageList.add( selectableImage );
//        selectableImageList.add( selectableImage );
//        selectableImageList.add( selectableImage );
//        selectableImageList.add( selectableImage );

        bitmapList.add(new Tuple2<String, Bitmap>("",null));
        int windowWidth = 320;
        int imageX = windowWidth/3 -20;
        this.getResources().getDimension(R.dimen.x86);
        int x = R.dimen.x86;
        selectableImageAdapter = new SelectableImageAdapter(this,bitmapList,
                this.getResources().getDimension(R.dimen.x86),this.getResources().getDimension(R.dimen.x86));
        recyclerView.setAdapter(selectableImageAdapter);
//        recyclerView.setItemViewCacheSize(5);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (data != null) {
            Uri uri = data.getData();

            String[] proj = { MediaStore.Images.Media.DATA };
            Cursor actualimagecursor = this.managedQuery(uri,proj,null,null,null);
            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            actualimagecursor.moveToFirst();

            img_path = actualimagecursor.getString(actual_image_column_index);

            WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
            int windowHeight = manager.getDefaultDisplay().getHeight();
            int windowWidth = manager.getDefaultDisplay().getWidth();

//            Bitmap bitmap = DZImageUtil.scaleImage( img_path , imageView.getWidth() , imageView.getHeight() );

            bitmapList.add(bitmapList.size() - 1, new Tuple2(img_path, null));

            selectableImageAdapter.notifyItemRangeChanged( bitmapList.size() - 2

                    , bitmapList.size() - 1 );
//            selectableImageAdapter.notifyDataSetChanged();

            //Way 1
//            this.imageView.setImageBitmap(bitmap);
//            this.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);

            //Way 2
//            this.imageView.setImageBitmap( DZImageUtil.getImageThumbnail( img_path , imageView.getWidth() , imageView.getHeight() ) );
        }

//        super.onActivityResult(requestCode, resultCode, data);
//
//        SelectableImage a2 = new SelectableImage( this , null );
//        innerView.addView(a2);

    }



//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main_activity1, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    public boolean onTouch(View v, MotionEvent event)
    {
        event.getX();
        event.getY();
        return true;
    }


    private DisplayMetrics displayMetrics;
    private boolean isFirst=true;
    private float lastX=0;
    private float lastY=0;
    private int screenWidth=0;
    private int screenHeight=0;
    private int left;
    private int top;
    private int right;
    private int bottom;

    private class ButtonOnTouchListener implements View.OnTouchListener
    {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (isFirst) {
                // 得到屏幕的宽
                displayMetrics = getResources().getDisplayMetrics();
                screenWidth = displayMetrics.widthPixels;
                // 得到标题栏和状态栏的高度
                Rect rect = new Rect();
                Window window = getWindow();
//                mImageView.getWindowVisibleDisplayFrame(rect);
                int statusBarHeight = rect.top;
                int contentViewTop = window.findViewById(Window.ID_ANDROID_CONTENT).getTop();
                int titleBarHeight = contentViewTop - statusBarHeight;
                // 得到屏幕的高
                screenHeight = displayMetrics.heightPixels- (statusBarHeight + titleBarHeight);
                isFirst=false;
            }

            int action=event.getAction();
            switch (action) {
                //按下
                case MotionEvent.ACTION_DOWN:
                    //按下处坐标
                    lastX=event.getRawX();
                    lastY=event.getRawY();
                    break;
                //移动
                case MotionEvent.ACTION_MOVE:
                    //移动的距离
                    float distanceX=event.getRawX()-lastX;
                    float distanceY=event.getRawY()-lastY;
                    //移动后控件的坐标
                    left=(int)(view.getLeft()+distanceX);
                    top=(int)(view.getTop()+distanceY);
                    right=(int)(view.getRight()+distanceX);
                    bottom=(int)(view.getBottom()+distanceY);
                    //处理拖出屏幕的情况
                    if (left<0) {
                        left=0;
                        right=view.getWidth();
                    }
                    if (right>screenWidth) {
                        right=screenWidth;
                        left=screenWidth-view.getWidth();
                    }
                    if (top<0) {
                        top=0;
                        bottom=view.getHeight();
                    }
                    if (bottom>screenHeight) {
                        bottom=screenHeight;
                        top=screenHeight-view.getHeight();
                    }
                    //显示图片
                    view.layout(left, top, right, bottom);
                    lastX=event.getRawX();
                    lastY=event.getRawY();
                    break;
                //抬起
                case MotionEvent.ACTION_UP:
                    // 每次移动都要设置其layout，不然由于父布局可能嵌套listview，当父布局发生改变冲毁（如下拉刷新时）则移动的view会回到原来的位置
                    LinearLayout.LayoutParams lpFeedback = (LinearLayout.LayoutParams)view.getLayoutParams();

                    lpFeedback.leftMargin = view.getLeft();
                    lpFeedback.topMargin = view.getTop();
                    lpFeedback.setMargins(view.getLeft(), view.getTop(), 0, 0);
                    view.setLayoutParams(lpFeedback);

                    break;
                default:
                    break;
            }
            return false;

        }
    }
}
