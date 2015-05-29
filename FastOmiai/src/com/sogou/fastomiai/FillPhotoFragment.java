package com.sogou.fastomiai;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
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
                //取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
                Uri mImageCaptureUri = data.getData();
                //返回的Uri不为空时，那么图片信息数据都会在Uri中获得。如果为空，那么我们就进行下面的方式获取
                if (mImageCaptureUri != null) {
                    Bitmap image;
                    try {
                        //这个方法是根据Uri获取Bitmap图片的静态方法
                        image = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), mImageCaptureUri);
                        if (image != null) {
                            mBtnSelPhoto.setVisibility(View.GONE);
                            mImageSelPhoto.setVisibility(View.VISIBLE);
                            mImageSelPhoto.setImageBitmap(image);
                            isFilled = true;
                            
                            FileItem item = new FileItem();
                            item.name = String.valueOf(System.currentTimeMillis()) + ".png";
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            image.compress(Bitmap.CompressFormat.PNG, 100, baos);
                            item.file = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
                            mFiles.add(item);
                            try {
                                File file = new File(
                                        Environment
                                                .getExternalStorageDirectory(),
                                        "fastomiai.png");
                                FileOutputStream fos = new FileOutputStream(
                                        file);
                                fos.write(baos.toByteArray());
                                fos.close();
                            } catch (Exception e) {

                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    Bundle extras = data.getExtras();
                    if (extras != null) {
                        //这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片
                        Bitmap image = extras.getParcelable("data");
                        if (image != null) {
                            mBtnSelPhoto.setVisibility(View.GONE);
                            mImageSelPhoto.setVisibility(View.VISIBLE);
                            mImageSelPhoto.setImageBitmap(image);
                            isFilled = true;
                            
                            FileItem item = new FileItem();
                            item.name = String.valueOf(System.currentTimeMillis()) + ".png";
                            ByteArrayOutputStream baos = new ByteArrayOutputStream();
                            image.compress(Bitmap.CompressFormat.PNG, 100, baos);
                            item.file = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
                            mFiles.add(item);
                            try {
                                File file = new File(
                                        Environment
                                                .getExternalStorageDirectory(),
                                        "fastomiai.png");
                                FileOutputStream fos = new FileOutputStream(
                                        file);
                                fos.write(baos.toByteArray());
                                fos.close();
                            } catch (Exception e) {

                            }
                        }
                    }
                }
            }
            break;
        default:
            break;

        }
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
