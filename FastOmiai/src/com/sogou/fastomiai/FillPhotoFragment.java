package com.sogou.fastomiai;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.sogou.fastomiai.FillInfoActivity.FileItem;
import com.sogou.fastomiai.FillInfoActivity.UserSupplementInfo;


public class FillPhotoFragment extends Fragment {
    
    private boolean isFilled = false;
    
    private Button mBtnPhoto1;
    private Button mBtnPhoto2;
    private Button mBtnVideo1;
    private Button mBtnVideo2;
    
    private ImageView mImagePhoto1;
    private ImageView mImagePhoto2;
    
    private Button mBtnSelPhoto;
    private ImageView mImageSelPhoto;
    
    private ArrayList<FileItem> mFiles;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_fill_photo, container, false);
		
		mFiles = new ArrayList<FileItem>();
		
		mImagePhoto1 = (ImageView)v.findViewById(R.id.image_photo1);
		mImagePhoto2 = (ImageView)v.findViewById(R.id.image_photo2);
		
		mBtnPhoto1 = (Button)v.findViewById(R.id.btn_photo1);
		mBtnPhoto1.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                mBtnSelPhoto = mBtnPhoto1;
                mImageSelPhoto = mImagePhoto1;
                takePhoto();
            }
        });
		mBtnPhoto2 = (Button)v.findViewById(R.id.btn_photo2);
		mBtnPhoto2.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                mBtnSelPhoto = mBtnPhoto2;
                mImageSelPhoto = mImagePhoto2;
                takePhoto();
            }
        });
		
		mBtnVideo1 = (Button)v.findViewById(R.id.btn_video1);
        mBtnVideo1.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "暂未实现",
                        Toast.LENGTH_SHORT).show();
            }
        });
        mBtnVideo2 = (Button)v.findViewById(R.id.btn_video2);
        mBtnVideo2.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity().getApplicationContext(), "暂未实现",
                        Toast.LENGTH_SHORT).show();
            }
        });
		
		return v;
	}
	
	public void takePhoto() {
        CharSequence[] items = {"从相册选择", "拍照", "取消"};  
        new AlertDialog.Builder(getActivity())
            .setTitle("选择图片来源")
            .setItems(items, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    if ( which == 0 ){
                        try {
                            //选择照片的时候也一样，我们用Action为Intent.ACTION_GET_CONTENT，
                            //有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(intent, 2);
                        } catch (ActivityNotFoundException e) {

                        }
                    } else if (which == 1) {
                        try {
                            //拍照我们用Action为MediaStore.ACTION_IMAGE_CAPTURE，
                            //有些人使用其他的Action但我发现在有些机子中会出问题，所以优先选择这个
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 1);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        
                    }
                }
            })
            .create().show(); 
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
	    super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
        case Activity.RESULT_OK:
            if (data != null) {
                // 从相册选择
                ContentResolver resolver = getActivity().getContentResolver();
                // 照片的原始资源地址
                Uri originalUri = data.getData();
                try {
                    // 使用ContentProvider通过URI获取原始图片
                    Bitmap photo = MediaStore.Images.Media.getBitmap(resolver,
                            originalUri);
                    if (photo != null) {
                        // 为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
                        int SCALE = photo.getWidth() / 640;
                        if (SCALE < 1) {
                            SCALE = 1;
                        }
                        
                        Bitmap smallBitmap = zoomBitmap(photo,
                                photo.getWidth() / SCALE, photo.getHeight() / SCALE);
                        // 释放原始图片占用的内存，防止out of memory异常发生
                        photo.recycle();

                        mBtnSelPhoto.setVisibility(View.GONE);
                        mImageSelPhoto.setVisibility(View.VISIBLE);
                        mImageSelPhoto.setImageBitmap(smallBitmap);
                        isFilled = true;

                        FileItem item = new FileItem();
                        item.name = String.valueOf(System.currentTimeMillis())
                                + ".jpg";
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        smallBitmap.compress(Bitmap.CompressFormat.PNG, 100,
                                baos);
                        item.file = Base64.encodeToString(baos.toByteArray(),
                                Base64.DEFAULT);
                        mFiles.add(item);
                        try {
                            File file = new File(
                                    Environment.getExternalStorageDirectory(),
                                    "fastomiai.jpg");
                            FileOutputStream fos = new FileOutputStream(file);
                            fos.write(baos.toByteArray());
                            fos.close();
                        } catch (Exception e) {

                        }
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                // 拍照后
                Bundle extras = data.getExtras();
                if (extras != null) {
                    // 这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片
                    Bitmap image = extras.getParcelable("data");
                    if (image != null) {
                        mBtnSelPhoto.setVisibility(View.GONE);
                        mImageSelPhoto.setVisibility(View.VISIBLE);
                        mImageSelPhoto.setImageBitmap(image);
                        isFilled = true;

                        FileItem item = new FileItem();
                        item.name = String.valueOf(System.currentTimeMillis())
                                + ".png";
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        image.compress(Bitmap.CompressFormat.PNG, 100, baos);
                        item.file = Base64.encodeToString(baos.toByteArray(),
                                Base64.DEFAULT);
                        mFiles.add(item);
                        try {
                            File file = new File(
                                    Environment.getExternalStorageDirectory(),
                                    "fastomiai.png");
                            FileOutputStream fos = new FileOutputStream(file);
                            fos.write(baos.toByteArray());
                            fos.close();
                        } catch (Exception e) {

                        }
                    }
                }
            }
        }
	}

    /** 缩放Bitmap图片 **/
    public Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);// 利用矩阵进行缩放不会造成内存溢出
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }
    
    public Bitmap getSmallBitmap(Context mContext, String filePath) {
        DisplayMetrics dm;
        dm = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(dm);
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, dm.widthPixels, dm.heightPixels);
        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(filePath, options);
    }
    
    public int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
    
            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    public void fillInfo(UserSupplementInfo info) {
        info.photo = new ArrayList<FileItem>();
        info.vedio = new ArrayList<FileItem>();
        if (mFiles.size() != 0) {
            info.photo = mFiles;
        }
    }
    
    public boolean isFilled() {
        return isFilled;
    }
}
