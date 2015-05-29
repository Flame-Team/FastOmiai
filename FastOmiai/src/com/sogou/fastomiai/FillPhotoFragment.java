package com.sogou.fastomiai;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sogou.fastomiai.FillInfoActivity.FileItem;
import com.sogou.fastomiai.FillInfoActivity.UserSupplementInfo;


public class FillPhotoFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_fill_photo, container, false);  
	}
    
    public void fillInfo(UserSupplementInfo info) {
        info.photo = new ArrayList<FileItem>();
        info.vedio = new ArrayList<FileItem>();
        FileItem item = new FileItem();
        item.name = "1.png";
        item.file = "abc";
        info.photo.add(item);
        info.vedio.add(item);
    }
}
