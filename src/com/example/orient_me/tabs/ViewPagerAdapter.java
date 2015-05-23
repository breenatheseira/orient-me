// Bangad, A. (2015) How to Make Material Design Sliding Tabs. [Online]. Available from: http://www.android4devs.com/2015/01/how-to-make-material-design-sliding-tabs.html [Accessed: 23 May 2015].

package com.example.orient_me.tabs;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.orient_me.DocumentFragment;
import com.example.orient_me.badges.ViewBadgesListFragment;
import com.example.orient_me.contacts.ViewContactsListFragment;
import com.example.orient_me.maps.MapPlaceFragment;
import com.example.orient_me.schedules.ViewScheduleListFragment;
import com.example.orient_me.social.FacebookFragment;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
	 
    CharSequence Titles[];
    int tabAmount;
 
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);
 
        this.Titles = mTitles;
        this.tabAmount = mNumbOfTabsumb;
    }
 
    @Override
    public Fragment getItem(int position) {
    	Fragment item = new Fragment();
    	
    	switch(position){
    	case 0:
    		item = new DocumentFragment();
    		break;
    	case 1:
    		item = new ViewScheduleListFragment();
    		break;
    	case 2:
    		item = new ViewContactsListFragment();
    		break;
    	case 3:
    		item = new MapPlaceFragment();
    		break;
    	case 4:
    		item = new FacebookFragment();
    		break;
    	case 5:
    		item = new ViewBadgesListFragment();
    		break;    		
    	}
        return item;
    }
 
    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }
 
    @Override
    public int getCount() {
        return tabAmount;
    }
}
