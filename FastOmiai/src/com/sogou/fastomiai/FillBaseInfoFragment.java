package com.sogou.fastomiai;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import com.sogou.fastomiai.FillInfoActivity.EducationEnum;
import com.sogou.fastomiai.FillInfoActivity.IncomeEnum;
import com.sogou.fastomiai.FillInfoActivity.MarriageEnum;
import com.sogou.fastomiai.FillInfoActivity.UserSupplementInfo;
import com.sogou.fastomiai.FillInfoActivity.ZeroOrOneEnum;
import com.sogou.fastomiai.model.InviteItemInfo.SexEnum;


public class FillBaseInfoFragment extends Fragment {
    
    private EditText mEditName;
    private Spinner mSpinnerSex;
    private Spinner mSpinnerAge;
    private Spinner mSpinnerIncome;
    private Spinner mSpinnerCar;
    private Spinner mSpinnerHouse;
    private Spinner mSpinnerEducation;
    private Spinner mSpinnerMarriage;
    private EditText mEditDeclaration;

	@Override
	public View onCreateView(LayoutInflater inflater,
			@Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fragment_fill_baseinfo, container, false);
		
		mEditName = (EditText)v.findViewById(R.id.edit_name);
		mSpinnerSex = (Spinner)v.findViewById(R.id.spinner_sex);
		mSpinnerAge = (Spinner)v.findViewById(R.id.spinner_age);
		mSpinnerIncome = (Spinner)v.findViewById(R.id.spinner_income);
		mSpinnerCar = (Spinner)v.findViewById(R.id.spinner_car);
		mSpinnerHouse = (Spinner)v.findViewById(R.id.spinner_house);
		mSpinnerEducation = (Spinner)v.findViewById(R.id.spinner_education);
		mSpinnerMarriage = (Spinner)v.findViewById(R.id.spinner_marriage);
		mEditDeclaration = (EditText)v.findViewById(R.id.edit_declaration);
		
		return v;
	}
    
	public void fillInfo(UserSupplementInfo info) {
	    info.name = mEditName.getText().toString();
	    
	    int position = mSpinnerSex.getSelectedItemPosition();
	    info.sex = SexEnum.values()[position];
	    
	    position = mSpinnerAge.getSelectedItemPosition();
	    info.age = Integer.parseInt(getResources().getStringArray(R.array.age)[position]);
	    
	    position = mSpinnerIncome.getSelectedItemPosition();
	    info.income = IncomeEnum.values()[position];
	    
	    position = mSpinnerCar.getSelectedItemPosition();
	    info.hasCar = ZeroOrOneEnum.values()[position];
	    
	    position = mSpinnerHouse.getSelectedItemPosition();
	    info.hasHouse = ZeroOrOneEnum.values()[position];
	    
	    position = mSpinnerEducation.getSelectedItemPosition();
	    info.edu = EducationEnum.values()[position];
	    
	    position = mSpinnerMarriage.getSelectedItemPosition();
	    info.marriage = MarriageEnum.values()[position];
	    
	    // 个人宣言？
	}
	
	public boolean isFilled() {
	    String name = mEditName.getText().toString();
	    if (name == null || name.isEmpty()) {
	        return false;
	    }
	    
	    return true;
	}
}
