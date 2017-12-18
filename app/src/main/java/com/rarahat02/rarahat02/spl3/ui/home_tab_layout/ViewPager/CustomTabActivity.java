package com.rarahat02.rarahat02.spl3.ui.home_tab_layout.ViewPager;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.rarahat02.rarahat02.spl3.ui.activity.MainActivity;
import com.rarahat02.rarahat02.spl3.ui.home_tab_layout.ViewPagerAdapter;
import com.yarolegovich.slidingrootnav.sample.fragment.audioplayer.PlayerOrdinaryFiles;
import com.yarolegovich.slidingrootnav.sample.fragment.audioplayer.PlayerPurchasedFiles;
import com.yarolegovich.slidingrootnav.sample.fragment.audioplayer.PlayerUnPurchasedFiles;

import droidmentor.tabwithviewpager.R;

public class CustomTabActivity extends Fragment
{

    private TabLayout tabLayout;

    private ViewPager viewPager;



    //ChatFragment chatFragment;
    //CallsFragment callsFragment;
    Fragment others = null;
    Fragment purchased = null;
    Fragment unPurchased = null;
    //ContactsFragment contactsFragment;

    String[] tabTitle={"All Songs", "Purchased Songs", " Un-Purchased Songs"}; //, "Not Purchased"};
    int[] unreadCount={0,0,0};   //,0};




    public static CustomTabActivity newInstance()
    {
        CustomTabActivity fragment = new CustomTabActivity();
        return fragment;
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.activity_tab_without_icon, container, false);


        //Initializing viewPager
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);

        try
        {
            setupTabIcons();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(position,false);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });




        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        ((MainActivity)getActivity()).getSupportActionBar().hide();
    }
    @Override
    public void onStop() {
        super.onStop();
        ((MainActivity)getActivity()).getSupportActionBar().show();
    }





    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager)
    {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getChildFragmentManager());
        //callsFragment = new CallsFragment();
        //chatFragment=new ChatFragment();
        others = new PlayerOrdinaryFiles();
        purchased = new PlayerPurchasedFiles();
        unPurchased = new PlayerUnPurchasedFiles();

        adapter.addFragment(others,"Others");
        adapter.addFragment(purchased,"Purchased");
        adapter.addFragment(unPurchased,"Not Purchased");
        ///adapter.addFragment(callsFragment,"LATEST");
        //adapter.addFragment(chatFragment,"CHAT");

        viewPager.setAdapter(adapter);
    }

    private View prepareTabView(int pos) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.custom_tab,null);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        TextView tv_count = (TextView) view.findViewById(R.id.tv_count);
        tv_title.setText(tabTitle[pos]);
        if(unreadCount[pos]>0)
        {
            tv_count.setVisibility(View.VISIBLE);
            tv_count.setText("" + unreadCount[pos]);
        }
        else
            tv_count.setVisibility(View.GONE);


        return view;
    }

    private void setupTabIcons()
    {

        for(int i=0;i<tabTitle.length;i++)
        {
            /*TabLayout.Tab tabitem = tabLayout.newTab();
            tabitem.setCustomView(prepareTabView(i));
            tabLayout.addTab(tabitem);*/

            tabLayout.getTabAt(i).setCustomView(prepareTabView(i));
        }


    }
}
