package com.luperapp.luper;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class TodaysRemindersFragment extends Fragment {
	
	public static final String ARG_NUMBER = "Social group number";
	private ViewGroup container;
	private Context rootContext;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment_todays_reminders, container, false);
        
        this.container = container;
        rootContext = rootView.getContext();


     
        return rootView;
    }
}
