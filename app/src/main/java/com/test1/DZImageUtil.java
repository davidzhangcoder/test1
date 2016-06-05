package com.test1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.media.ThumbnailUtils;

public class DZImageUtil {
//	private static int windowWidth;
//	private static int windowHeight;
//	
//	static 
//	{
//        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);  
//        // ��һ�ֻ�ȡ�ֻ���Ļ��ߵķ���  
//        windowHeight = manager.getDefaultDisplay().getHeight();  
//        windowWidth = manager.getDefaultDisplay().getWidth();  
//
//	}

    public static Bitmap scaleImage(String src, int windowWidth, int windowHeight) {

        // ͼƬ����������
        Options options = new Options();
        // ��ȥ�������ͼƬ��ֻ�ǻ�ȡͼƬ�Ŀ��
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(src, options);
        int imageWidth = options.outWidth;
        int imageHeight = options.outHeight;
//        System.out.println("ͼƬ�� :" + imageWidth);
//        System.out.println("ͼƬ�� :" + imageHeight);

        int scaleX = imageWidth / windowWidth;
        int scaleY = imageHeight / windowHeight;
        int scale = 1;
        if (scaleX >= scaleY && scaleX >= 1) {
            // ˮƽ��������ű������ֱ��������ű����ͬʱͼƬ�Ŀ�Ҫ���ֻ���ĻҪ��,�Ͱ�ˮƽ�����������
//            System.out.println("�����������");
            scale = scaleX;
        } else if (scaleY >= scaleX && scaleY >= 1) {
            // ��ֱ��������ű����ˮƽ��������ű����ͬʱͼƬ�ĸ�Ҫ���ֻ���ĻҪ�󣬾Ͱ���ֱ�����������
//            System.out.println("���߱�������");
            scale = scaleY;
        }
//        System.out.println("���ű���" + scale);
        // �������ͼƬ
        options.inJustDecodeBounds = false;
        // ���ò�����
        options.inSampleSize = scale;
        Bitmap bitmap = BitmapFactory.decodeFile(src, options);

        return bitmap;
    }

    /**
     * 根据指定的图像路径和大小来获取缩略图
     * 此方法有两点好处：
     * 1. 使用较小的内存空间，第一次获取的bitmap实际上为null，只是为了读取宽度和高度，
     * 第二次读取的bitmap是根据比例压缩过的图像，第三次读取的bitmap是所要的缩略图。
     * 2. 缩略图对于原图像来讲没有拉伸，这里使用了2.2版本的新工具ThumbnailUtils，使
     * 用这个工具生成的图像不会被拉伸。
     *
     * @param imagePath 图像的路径
     * @param width     指定输出图像的宽度
     * @param height    指定输出图像的高度
     * @return 生成的缩略图
     */
    public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
        Bitmap bitmap = null;
        Options options = new Options();
        options.inJustDecodeBounds = true;
        // 获取这个图片的宽和高，注意此处的bitmap为null
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false; // 设为 false
        // 计算缩放比
        int h = options.outHeight;
        int w = options.outWidth;
        int beWidth = w / width;
        int beHeight = h / height;
        int be = 1;
        if (beWidth < beHeight) {
            be = beWidth;
        } else {
            be = beHeight;
        }
        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        // 重新读入图片，读取缩放后的bitmap，注意这次要把options.inJustDecodeBounds 设为 false
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        // 利用ThumbnailUtils来创建缩略图，这里要指定要缩放哪个Bitmap对象
        bitmap = ThumbnailUtils.extractThumbnail(bitmap, width, height,
                ThumbnailUtils.OPTIONS_RECYCLE_INPUT);
        return bitmap;
    }

}
