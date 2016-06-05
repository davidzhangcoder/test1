package com.test1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by David on 23/05/2016.
 */
public class SelectableImageAdapter extends RecyclerView.Adapter<SelectableImageAdapter.SelectableImageHolder>
{
    private Context context;
    private LayoutInflater layoutInflater;
    private List<Tuple2<String,Bitmap>> bitmapList = new ArrayList<Tuple2<String,Bitmap>>();



    public SelectableImageAdapter(Context context , List<Tuple2<String,Bitmap>> bitmapList)
    {
        layoutInflater = LayoutInflater.from( context );
        this.context = context;
        this.bitmapList = bitmapList;
//        this.setHasStableIds(true);
    }

    @Override
    public SelectableImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectableImageHolder( new SelectableImage( context , null ) );
    }

    @Override
    public void onBindViewHolder(final SelectableImageHolder holder, final int position)
    {

        if( !bitmapList.get(position).getItem1().equals("") ) {

            Log.d("test1", "onBindViewHolder执行..NOT EMPTY " +
                            "getMeasuredHeight:" + holder.selectableImageAdd.getMeasuredHeight() + "  ,getMeasuredWidth:" + holder.selectableImageAdd.getMeasuredWidth() +
                            "getHeight:" + holder.selectableImageAdd.getHeight() + "  ,getWidth:" + holder.selectableImageAdd.getWidth()
            );

            if( holder.selectableImageAdd.getWidth() == 0 || holder.selectableImageAdd.getHeight() == 0 ) {
                ViewTreeObserver vto = holder.selectableImageAdd.getViewTreeObserver();
                vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        holder.selectableImageAdd.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                        Log.d("===OnGlobalLayout", "OnGlobalLayoutListener..myImageView " +
                                "height:" + holder.selectableImageAdd.getHeight() + "  ,width:" + holder.selectableImageAdd.getWidth());

                        holder.selectableImageAdd.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.image_picker_add));
                        Bitmap bitmap = bitmapList.get(position).getItem2();
                        if( bitmap == null ) {
                            bitmap = DZImageUtil.scaleImage(bitmapList.get(position).getItem1(), holder.selectableImageAdd.getWidth(), holder.selectableImageAdd.getHeight());
                            holder.selectableImageAdd.setImageDrawable(new BitmapDrawable(bitmap));
                            bitmapList.get(position).setItem2(bitmap);
                        }
                        else
                            holder.selectableImageAdd.setImageDrawable(new BitmapDrawable(bitmap));

                        holder.selectableImageAdd.setScaleType(ImageView.ScaleType.CENTER_CROP);

                    }
                });
                Bitmap bitmap = bitmapList.get(position).getItem2();
                if( bitmap != null ) {
                    holder.selectableImageAdd.setImageDrawable(new BitmapDrawable(bitmap));
                    holder.selectableImageAdd.setScaleType(ImageView.ScaleType.CENTER_CROP);
                }
            }
            else
        {
                holder.selectableImageAdd.setImageDrawable(ContextCompat.getDrawable(context, R.mipmap.image_picker_add));
                Bitmap bitmap = bitmapList.get(position).getItem2();
                if( bitmap == null ) {
                    bitmap = DZImageUtil.scaleImage(bitmapList.get(position).getItem1(), holder.selectableImageAdd.getWidth(), holder.selectableImageAdd.getHeight());
                    holder.selectableImageAdd.setImageDrawable(new BitmapDrawable(bitmap));
                    bitmapList.get(position).setItem2(bitmap);
                }
                else
                {
                    holder.selectableImageAdd.setImageDrawable(new BitmapDrawable(bitmap));
                }
                holder.selectableImageAdd.setScaleType(ImageView.ScaleType.CENTER_CROP);
            }

            holder.selectableImageDelete.setVisibility(View.VISIBLE);
            holder.setImagePath(bitmapList.get(position).getItem1());
//            holder.selectableImage.setAddAction( null );
        }
        else
        {

//            int w = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
//            int h = View.MeasureSpec.makeMeasureSpec(0,View.MeasureSpec.UNSPECIFIED);
//
//            Log.d("=SelectableImageAdapter", "onBindViewHolder执行..EMPTY " +
//                    "h:" + h + "  ,w:" + w);
//
//            holder.selectableImageAdd.measure(w, h);

//            ViewTreeObserver vto =  holder.selectableImageAdd.getViewTreeObserver();
//            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//                @Override
//                public void onGlobalLayout() {
//                    holder.selectableImageAdd.getViewTreeObserver().removeGlobalOnLayoutListener(this);
//                    Log.d("===OnGlobalLayout", "OnGlobalLayoutListener..myImageView " +
//                            "height:" + holder.selectableImageAdd.getHeight() + "  ,width:" + holder.selectableImageAdd.getWidth());
//                }
//            });
//            Log.d("===MainActivity", "onCreate执行完毕..myImageView " +
//                    "height:" + holder.selectableImageAdd.getHeight() + "  ,width:" + holder.selectableImageAdd.getWidth());
//
//
//
//
//            Log.d("=SelectableImageAdapter", "onBindViewHolder执行..EMPTY " +
//                    "height:" + holder.selectableImageAdd.getMeasuredHeight() + "  ,width:" + holder.selectableImageAdd.getMeasuredWidth());


            holder.selectableImageAdd.setImageDrawable( ContextCompat.getDrawable( context , R.mipmap.image_picker_add ) );
            holder.selectableImageDelete.setVisibility( View.INVISIBLE );
            holder.setImagePath( "" );
        }

    }

    @Override
    public int getItemCount() {
        return bitmapList.size();
    }

//    @Override
//    public long getItemId(int position) {
//        return position;
//    }

    public class SelectableImageHolder extends RecyclerView.ViewHolder {

        private ImageView selectableImageAdd;
        private ImageView selectableImageDelete;
        private String imagePath;
        private SelectableImage selectableImage;

        public SelectableImageHolder(View view)
        {
            super(view);

            selectableImage = (SelectableImage)view;
            selectableImageAdd = (ImageView)view.findViewById( R.id.image_picker_add );
            selectableImageDelete = (ImageView)view.findViewById( R.id.image_picker_delete );

            ((SelectableImage)view).setDeleteAction(new SelectableImage.ButtonAction() {
                @Override
                public void doButtonAction()
                {
                    bitmapList.remove( imagePath );
                    SelectableImageAdapter.this.notifyDataSetChanged();
                }
            });

            ((SelectableImage)view).setAddAction(new SelectableImage.ButtonAction() {
                @Override
                public void doButtonAction()
                {
                    doOnClickNotifiableImageView();
                }
            });




//            selectableImageAdd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v)
//                {
//
//                }
//            });
//
//            selectableImageDelete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v)
//                {
//
//                }
//            });

        }

        private void doOnClickNotifiableImageView()
        {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            ((Activity)SelectableImageAdapter.this.context).startActivityForResult(intent, 0);
        }


        public String getImagePath() {
            return imagePath;
        }

        public void setImagePath(String imagePath) {
            this.imagePath = imagePath;
        }
    }





}
