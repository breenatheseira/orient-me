//Bangad, A. (2015) How to Make Material Design Sliding Tabs. [Online]. Available from: http://www.android4devs.com/2015/01/how-to-make-material-design-sliding-tabs.html [Accessed: 23 May 2015]. 

package com.example.orient_me;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.orient_me.tabs.SlidingTabLayout;
import com.example.orient_me.tabs.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
		 
	    Toolbar toolbar;
	    ViewPager pager;
	    ViewPagerAdapter adapter;
	    SlidingTabLayout tabs;
	    CharSequence Titles[]={"Home","Events"};
	    int Numboftabs =2;
	 
	    @Override
	    protected void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.activity_main);
	 
	        toolbar = (Toolbar) findViewById(R.id.tool_bar);
	        setSupportActionBar(toolbar);
	 
	        adapter =  new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs);
	 
	        pager = (ViewPager) findViewById(R.id.pager);
	        pager.setAdapter(adapter);
	 
	        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
	        tabs.setDistributeEvenly(true); 
	 
	        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
	            @Override
	            public int getIndicatorColor(int position) {
	                return getResources().getColor(R.color.lightblue);
	            }
	        });
	 
	        tabs.setViewPager(pager);
	    }
}
