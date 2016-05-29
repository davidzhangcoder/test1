package com.test1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * TODO: document your custom view class.
 */
public class SelectableImage extends FrameLayout
{

    private ImageView imageView;

    private ImageView deleteImageView;


    private ButtonAction deleteAction;

    private ButtonAction addAction;

    public interface ButtonAction
    {
        public void doButtonAction();
    }

    public ButtonAction getAddAction() {
        return addAction;
    }

    public void setAddAction(ButtonAction addAction) {
        this.addAction = addAction;
    }

    public ButtonAction getDeleteAction() {
        return deleteAction;
    }

    public void setDeleteAction(ButtonAction deleteAction) {
        this.deleteAction = deleteAction;
    }

    public SelectableImage(Context context , AttributeSet attrs )
    {
        super(context , attrs);
        LayoutInflater.from(context).inflate(R.layout.selectable_image, this);

        imageView = (ImageView)this.findViewById( R.id.image_picker_add );
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( addAction != null )
                    addAction.doButtonAction();
            }
        });

        deleteImageView = (ImageView)this.findViewById( R.id.image_picker_delete );
        deleteImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if( deleteAction != null )
                    deleteAction.doButtonAction();
            }
        });


    }

    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d("===MyImageView","onMeasure 我被调用了" +System.currentTimeMillis());
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d("===MyImageView", "onDraw 我被调用了" + System.currentTimeMillis());
    }


//    private void doOnClickNotifiableImageView()
//    {
//        Intent intent = new Intent();
//        intent.setAction(Intent.ACTION_PICK);
//        intent.setType("image/*");
//        ((Activity)this.getContext()).startActivityForResult(intent, 0);
//    }


//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (data != null) {
//            Uri uri = data.getData();
//
//            String[] proj = {MediaStore.Images.Media.DATA};
//            Cursor actualimagecursor = ((Activity) this.getContext()).managedQuery(uri, proj, null, null, null);
//            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
//            actualimagecursor.moveToFirst();
//
//            String img_path = actualimagecursor.getString(actual_image_column_index);
//
//            WindowManager manager = (WindowManager) ((Activity) this.getContext()).getSystemService(Context.WINDOW_SERVICE);
//            int windowHeight = manager.getDefaultDisplay().getHeight();
//            int windowWidth = manager.getDefaultDisplay().getWidth();
//
//            Bitmap bitmap = DZImageUtil.scaleImage(img_path, imageView.getWidth(), imageView.getHeight());
//
//            //Way 1
////            this.imageView.setImageBitmap(bitmap);
////            this.imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
//
//            //Way 2
//            this.imageView.setImageBitmap(DZImageUtil.getImageThumbnail(img_path, imageView.getWidth(), imageView.getHeight()));
//        }
//    }




}
