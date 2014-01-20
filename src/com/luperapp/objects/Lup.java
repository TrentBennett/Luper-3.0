package com.luperapp.objects;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;

public class Lup implements Serializable {

	private static final long serialVersionUID = -5869508975763832144L;
	private int lupId;
	private String name;
	private String email, phone;
	private boolean isEnabled;
	private boolean isEmail, isPhone, isText;
	

	public Lup(String name, String email, String phone) 
	{
		SimpleDateFormat s = new SimpleDateFormat("ddMMhhmmss");
		String format = s.format(new Date());
		setId(Integer.parseInt(format));
		
		this.name = name;
		this.email = email;
		this.phone = phone;
		
		isEnabled = true;
		
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public int getId() {
		return lupId;
	}


	private void setId(int lupId) {
		this.lupId = lupId;
	}
	
	public boolean isEnabled()
	{
		return isEnabled;
	}
	public void setIsEnabled(boolean value)
	{
		isEnabled = value;
	}
	
	public boolean isPhone()
	{
		return isPhone;
	}
	public void setIsPhone(boolean value)
	{
		isPhone = value;
	}
	
	public boolean isText()
	{
		return isText;
	}
	public void setIsText(boolean value)
	{
		isEnabled = value;
	}
	
	public boolean isEmail()
	{
		return isEmail;
	}
	public void setIsEmail(boolean value)
	{
		isEnabled = value;
	}
	
	@Override
	public String toString()
	{
		return name + " - " + getId();
	}

}
