package com.example.carpoolapp.ui.carpool;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class TabFragmentAdapter  extends FragmentStateAdapter {

	private final int pageCount;

	public TabFragmentAdapter(@NonNull FragmentActivity fragmentActivity, int pageCount) {
		super(fragmentActivity);
		this.pageCount = pageCount;
	}

	@NonNull
	@Override
	public Fragment createFragment(int position) {
		switch (position) {
			case 0:
				GoWorkFragment goWorkFragment = new GoWorkFragment();
				return goWorkFragment;
			case 1:
				BackHomeFragment backHomeFragment = new BackHomeFragment();
				return backHomeFragment;
			default:
				return null;
		}
	}

	@Override
	public int getItemCount() {
		return pageCount;
	}
}
