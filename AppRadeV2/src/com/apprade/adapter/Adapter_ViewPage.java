package com.apprade.adapter;

import com.apprade.activity.Fragment_Intro_1;
import com.apprade.activity.Fragment_Intro_2;
import com.apprade.activity.Fragment_Intro_3;
import com.apprade.activity.Fragment_Intro_4;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


public class Adapter_ViewPage extends FragmentPagerAdapter {
	private static final int TAG_NUM_PAGINAS = 4;

	public Adapter_ViewPage(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {

		switch (arg0) {

		case 0:
			return new Fragment_Intro_1();
		case 1:
			return new Fragment_Intro_2();
		case 2:
			return new Fragment_Intro_4();
		case 3:
			return new Fragment_Intro_3();
		default:
			break;
		}
		return null;
	}


	@Override
	public int getCount() {
		return TAG_NUM_PAGINAS;
	}

}
