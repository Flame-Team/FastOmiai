package com.sogou.fastomiai;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;


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
    
}
