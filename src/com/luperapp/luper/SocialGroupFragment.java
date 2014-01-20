package com.luperapp.luper;

import java.util.List;

import com.luperapp.objects.Lup;
import com.luperapp.objects.LupManager;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.ContactsContract.Contacts;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;


public class SocialGroupFragment extends Fragment implements OnItemClickListener{
	
	public static final String ARG_SG_KEY = "Social group key";
	private static final int CONTACT_PICKER_RESULT = 1001;
	private ViewGroup container;
	private Context context;
	private DrawerLayout mDrawerLayout;
	private Button mAddLupButton;
	private ListView mLupList;
	private LupManager mLupManager;
	private String mSocialGroup;
	private LupOptionsFragment mLupOptions;

	public View onCreateView(LayoutInflater inflater, ViewGroup contain, Bundle savedInstanceState) {
		
		container = (ViewGroup) inflater.inflate(R.layout.fragment_social_group, container, false);
        context = container.getContext();
        mSocialGroup = getArguments().getString(ARG_SG_KEY);
        
        mDrawerLayout = (DrawerLayout) getActivity().findViewById(R.id.drawer_layout);
        
        mLupOptions = new LupOptionsFragment();
		getFragmentManager().beginTransaction()
        .replace(R.id.right_drawer, mLupOptions)
        .commit();
        
        mLupManager = new LupManager(getActivity());
          
        mAddLupButton = (Button) container.findViewById(R.id.new_lup_button);
        mLupList = (ListView) container.findViewById(R.id.lup_list);
        mLupList.setOnItemClickListener(this);
        
        mAddLupButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View view) {
				launchContactPicker();
				
			}
        	
        });
     
        updateLupList();
        
        return container;
    }
	

	protected void launchContactPicker() {
		Intent contactPickerIntent = new Intent(Intent.ACTION_PICK, Contacts.CONTENT_URI);
	    startActivityForResult(contactPickerIntent, CONTACT_PICKER_RESULT); 
		
	}
	
	private void updateLupList()
	{
		List<Lup> list = mLupManager.getSocialGroup(mSocialGroup);
		String[] titles = new String[list.size()];
		
		int i=0;
		for(Lup x : list)
		{
			titles[i] = x.getName();
			i++;
		}
		
		mLupList.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.list_layout, titles));
		mLupList.setChoiceMode(mLupList.CHOICE_MODE_SINGLE);
		mLupList.setTextFilterEnabled(true);
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) { 
	        if (resultCode == -1) { 
	        	
	            switch (requestCode) 
	            { 
		            case CONTACT_PICKER_RESULT: 
		            	
		            	String id, name, phone, hasPhone, email;
		            	int idx, colIdx;
		            	
		                Uri contactUri = data.getData();                   
		                Cursor cursor = context.getContentResolver().query(contactUri, null, null, null, null);
			            if (cursor.moveToFirst()) 
			            {			                	
			                idx = cursor.getColumnIndex(ContactsContract.Contacts._ID);
			                id = cursor.getString(idx);

			                idx = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
			                name = cursor.getString(idx);

			                idx = cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER);
			                hasPhone = cursor.getString(idx);
			                
			           
			                email=null;
			                phone=null;
			                
							// Get phone number - if they have one
			                if ("1".equalsIgnoreCase(hasPhone)) {
			                    cursor = context.getContentResolver().query(
			                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
			                            null,
			                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = "+ id, 
			                            null, null);
			                    if (cursor.moveToFirst()) {
			                        colIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
			                        phone = cursor.getString(colIdx);
			                    }
			                    cursor.close();
			                }

			                // Get email address
			                cursor = context.getContentResolver().query(
			                        ContactsContract.CommonDataKinds.Email.CONTENT_URI,
			                        null,
			                        ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + id,
			                        null, null);
			                if (cursor.moveToFirst()) {
			                    colIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.ADDRESS);
			                    email = cursor.getString(colIdx);
			                }
			                cursor.close();
			                
			                Lup lup = new Lup(name, email, phone);
			                mLupManager.addLup(mSocialGroup, lup);
			                			                
			                updateLupList();
			                
			                mLupOptions.setLup(lup);
			                mDrawerLayout.openDrawer(Gravity.RIGHT);
			                
			                break;
			               
			                
			            }                 
		               
	            } 
	        } 
	}


	@Override
	public void onItemClick(AdapterView<?> adpt, View view, int index, long id) {
		Lup clickedLup = mLupManager.getSocialGroup(mSocialGroup).get(index);
		mLupOptions.setLup(clickedLup);
		
		
		mDrawerLayout.openDrawer(Gravity.RIGHT);
		
	} 
	
}
