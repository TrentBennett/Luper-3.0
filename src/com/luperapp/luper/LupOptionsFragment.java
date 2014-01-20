package com.luperapp.luper;

import com.luperapp.objects.Lup;
import com.luperapp.objects.LupManager;

import android.annotation.SuppressLint;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

public class LupOptionsFragment extends Fragment {

	private ViewGroup container;
	private Context context;
	private DrawerLayout mDrawerLayout;
	private Lup mLup;
	private TextView mNameTextView;
	private Button mDoneButton;
	private Spinner mRecurrenceSpinner;
	private ToggleButton mPhoneToggle;
	private ToggleButton mTextToggle;
	private ToggleButton mEmailToggle;

	@SuppressLint("NewApi")
	public View onCreateView(LayoutInflater inflater, ViewGroup contain, Bundle savedInstanceState) {
		
		container = (ViewGroup) inflater.inflate(R.layout.fragment_lup_options, contain, false);
        context = container.getContext();
        
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        
        mNameTextView = (TextView) container.findViewById(R.id.name_text_view);
        mDoneButton = (Button) container.findViewById(R.id.done_button);
        mRecurrenceSpinner = (Spinner) container.findViewById(R.id.recurrence_spinner);
        mPhoneToggle = (ToggleButton) container.findViewById(R.id.phone_switch);
        mTextToggle = (ToggleButton) container.findViewById(R.id.text_switch);
        mEmailToggle = (ToggleButton) container.findViewById(R.id.email_switch);
        
        ArrayAdapter<Object> arrayAdapter = new ArrayAdapter<Object>(getActivity(), android.R.layout.simple_spinner_dropdown_item, getResources().getStringArray(R.array.remind_every_array));
		mRecurrenceSpinner.setAdapter(arrayAdapter);
        mDoneButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				mDrawerLayout.closeDrawer(Gravity.RIGHT);
				
			}
        	
        });
        
        mPhoneToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mLup.setIsPhone(isChecked);
			}
		});
        mTextToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mLup.setIsText(isChecked);
			}
		});
        mEmailToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mLup.setIsEmail(isChecked);
			}
		});
        
        return container;
    }

	public void setLup(Lup clickedLup) {
		mLup = clickedLup;
		
		mNameTextView.setText(mLup.getName());
		
				
	}

}
