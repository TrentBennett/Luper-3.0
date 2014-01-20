package com.luperapp.objects;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.app.Activity;
import android.content.Context;
import android.util.Log;

public class LupManager {

	private final String FILE_NAME = "lup_data.ser";
	private Map<String, ArrayList<Lup>> data;
	private Activity parentActivity;
	
	public LupManager(Activity activity) {
		
		parentActivity = activity;
		load();
	}

	public void addLup(String socialGroup, Lup lup)
	{
		ArrayList<Lup> list = data.get(socialGroup);
		
		if(list == null)
			list = new ArrayList<Lup>();
		
		list.add(lup);
		
		data.put(socialGroup, list);
		save();
		
	}
	
	public ArrayList<Lup> getSocialGroup(String socialGroup)
	{
		
		ArrayList<Lup> list = data.get(socialGroup);
		
		if(list == null)
				list = new ArrayList<Lup>();				
		
		Log.v("get", ""+list);
		return list;
	}
	
	public boolean load(){
		
		
		try{
		
			FileInputStream fis = parentActivity.openFileInput(FILE_NAME);
			ObjectInputStream is = new ObjectInputStream(fis);
			data = (Map<String, ArrayList<Lup>>) is.readObject();
			is.close();
		}
		catch(Exception e)
		{
			data = new HashMap<String, ArrayList<Lup>>();
			Log.v("EXECTION","Load Failed!! new HashMap created");
			e.printStackTrace();
			return false;
		}
		
		return true;
		
	}
	
	public boolean save(){
		
		try{

			FileOutputStream fos = parentActivity.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
			ObjectOutputStream os = new ObjectOutputStream(fos);
			os.writeObject(data);
			os.close();

		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
