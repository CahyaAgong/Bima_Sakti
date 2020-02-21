package com.bimasakti.diskominfosan.tablayout2;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.bimasakti.diskominfosan.tablayout2.tab.Tab1diskusi;
import com.bimasakti.diskominfosan.tablayout2.tab.Tab2diskusi;
import com.bimasakti.diskominfosan.tablayout2.tab.Tab3diskusi;
import com.bimasakti.diskominfosan.tablayout2.tab.Tab4diskusi;
import com.bimasakti.diskominfosan.tablayout2.tab.Tab5diskusi;
import com.bimasakti.diskominfosan.tablayout2.tab.Tab6diskusi;
import com.bimasakti.diskominfosan.tablayout2.tab.Tab7diskusi;
import com.bimasakti.diskominfosan.tablayout2.tab.Tab8diskusi;

public class ViewPagerAdapter2 extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter2 is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter2 is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter2(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            Tab1diskusi tab1 = new Tab1diskusi();
            return tab1;
        }
        if(position == 1) // if the position is 0 we are returning the First tab
        {
            Tab2diskusi tab2 = new Tab2diskusi();
            return tab2;
        }
        if(position == 2) // if the position is 0 we are returning the First tab
        {
            Tab3diskusi tab3 = new Tab3diskusi();
            return tab3;
        }
        if(position == 3) // if the position is 0 we are returning the First tab
        {
            Tab4diskusi tab4 = new Tab4diskusi();
            return tab4;
        }
        if(position == 4) // if the position is 0 we are returning the First tab
        {
            Tab5diskusi tab5 = new Tab5diskusi();
            return tab5;
        }
        if(position == 5) // if the position is 0 we are returning the First tab
        {
            Tab6diskusi tab6 = new Tab6diskusi();
            return tab6;
        }
        if(position == 6) // if the position is 0 we are returning the First tab
        {
            Tab7diskusi tab7 = new Tab7diskusi();
            return tab7;
        }
        else             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            Tab8diskusi tab8 = new Tab8diskusi();
            return tab8;
        }


    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}