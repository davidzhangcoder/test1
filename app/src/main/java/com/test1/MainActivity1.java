package com.test1;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
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
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

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
        a1 = new SelectableImage( this , null );
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
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));//这里用线性宫格显示 类似于grid view
//        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));//这里用线性宫格显示 类似于瀑布流

//        int spacingInPixels = 50;
//        recyclerView.addItemDecoration(new GridSpacingItemDecoration(3,spacingInPixels,true));

        List<SelectableImage> selectableImageList = new ArrayList<SelectableImage>();
        SelectableImage selectableImage = new SelectableImage( this , null );
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
        selectableImageAdapter = new SelectableImageAdapter(this,bitmapList);
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

            bitmapList.add(bitmapList.size() - 1, new Tuple2(img_path,null) );
            selectableImageAdapter.notifyDataSetChanged();

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
}
