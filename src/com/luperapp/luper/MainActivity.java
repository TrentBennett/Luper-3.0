package com.luperapp.luper;


import android.os.Build;
import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.res.Configuration;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {

	public static final int CONTACT_PICKER_RESULT = 1001; 
	private CharSequence mTitle;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private String[] mMenuItems;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		setTitle("Luper");
		
		setNavigationDrawer();
		
		//Set Fragment to TodaysRemindersFragment
		getFragmentManager().beginTransaction()
        .replace(R.id.content_frame, new TodaysRemindersFragment())
        .commit();


	}
	
	@SuppressLint("NewApi")
	private void setNavigationDrawer() {
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mMenuItems = getResources().getStringArray(R.array.menu_items);
		
		mDrawerList.setAdapter(new ArrayAdapter<Object>(this, R.layout.drawer_item, mMenuItems));
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		
		
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.drawable.ic_drawer, 0,0){
			public void onDrawerOpened(View view)
			{
				
			}
			public void onDrawerClosed(View view)
			{
				
			}
		};
		
		mDrawerLayout.setDrawerListener(mDrawerToggle);
		mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.RIGHT);
		
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		if(Build.VERSION.SDK_INT >= 14)
			getActionBar().setHomeButtonEnabled(true);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
    protected void onPostCreate(Bundle savedInstanceState) {
	        super.onPostCreate(savedInstanceState);
	        // Sync the toggle state after onRestoreInstanceState has occurred.
	        mDrawerToggle.syncState();
	    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
        }
        // Handle your other action bar items...
        return super.onOptionsItemSelected(item);
    }
	
	
	@Override
	public void setTitle(CharSequence title)
	{
		mTitle = title;
		getActionBar().setTitle(mTitle);
	}
	
	private void selectPosition(int position)
	{
		setTitle(mMenuItems[position]);
		
		// Create a new fragment and specify the planet to show based on position
	    Fragment fragment;
	    
	    if(position>0) //IF the selected menu choice is not "Todays Reminders"
	    {
	    	fragment  = new SocialGroupFragment();
		    Bundle args = new Bundle();
		    args.putString(SocialGroupFragment.ARG_SG_KEY, mMenuItems[position]);
		    fragment.setArguments(args);
	    }
	    else
	    {
	    	fragment  = new TodaysRemindersFragment();
	    }

	    // Insert the fragment by replacing any existing fragment
	    FragmentManager fragmentManager = getFragmentManager();
	    fragmentManager.beginTransaction()
	                   .replace(R.id.content_frame, fragment)
	                   .commit();

	    // Highlight the selected item, update the title, and close the drawer
	    mDrawerList.setItemChecked(position, true);
	    mDrawerLayout.closeDrawer(mDrawerList);
	}
	
	
	
	private class DrawerItemClickListener implements ListView.OnItemClickListener  {
		
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
			selectPosition(position);
			
		}
	}
}
