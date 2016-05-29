package com.test1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    private List<String> bitmapList = new ArrayList<String>();



    public SelectableImageAdapter(Context context , List<String> bitmapList)
    {
        layoutInflater = LayoutInflater.from( context );
        this.context = context;
        this.bitmapList = bitmapList;
    }

    @Override
    public SelectableImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new SelectableImageHolder( new SelectableImage( context , null ) );
    }

    @Override
    public void onBindViewHolder(SelectableImageHolder holder, int position)
    {
        if( !bitmapList.get(position).equals("") ) {
            holder.selectableImageAdd.setImageDrawable( ContextCompat.getDrawable( context , R.mipmap.image_picker_add ) );
            Bitmap bitmap = DZImageUtil.scaleImage(bitmapList.get(position), holder.selectableImageAdd.getWidth(), holder.selectableImageAdd.getHeight());
            holder.selectableImageAdd.setImageBitmap(bitmap);
            holder.selectableImageAdd.setScaleType(ImageView.ScaleType.CENTER_CROP);
            holder.selectableImageDelete.setVisibility(View.VISIBLE);
            holder.setImagePath(bitmapList.get(position));
//            holder.selectableImage.setAddAction( null );
        }
        else
        {
            holder.selectableImageAdd.setImageDrawable( ContextCompat.getDrawable( context , R.mipmap.image_picker_add ) );
            holder.selectableImageDelete.setVisibility( View.INVISIBLE );
            holder.setImagePath( "" );
        }

    }

    @Override
    public int getItemCount() {
        return bitmapList.size();
    }

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
