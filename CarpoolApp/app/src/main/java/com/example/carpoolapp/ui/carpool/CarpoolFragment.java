package com.example.carpoolapp.ui.carpool;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.example.carpoolapp.R;
import com.example.carpoolapp.databinding.FragmentCarpoolBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

// 카풀 리스트 프래그먼트
public class CarpoolFragment extends Fragment {

	private FragmentCarpoolBinding binding;
//	CarpoolViewModel carpoolViewModel;
//	CarpoolAdapter carpoolAdapter;

	private final int numPage = 2;
	private ViewPager2 viewPager2;
	private FragmentStateAdapter fragmentStateAdapter;
	private View indicator;
	TabLayout tabLayout;
	int indicatorWidth;

	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		binding = FragmentCarpoolBinding.inflate(inflater, container, false);
		View root = binding.getRoot();

		binding.flbCarpoolRegister.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(getActivity(), CarpoolRegisterActivity.class);
				startActivity(intent);
			}
		});


		//adapter
		fragmentStateAdapter = new TabFragmentAdapter(getActivity(), numPage);
		viewPager2 = root.findViewById(R.id.viewpager2);
		viewPager2.setAdapter(fragmentStateAdapter);

		// Integrating TabLayout with ViewPager2
		tabLayout = root.findViewById(R.id.tab);
		new TabLayoutMediator(tabLayout, viewPager2,
				(tab, position) -> {
				if( position == 0){
					tab.setText(" 출근 ");
				}
				else{
					tab.setText(" 퇴근 ");

				}
				}
		).attach();


		return root;
	}

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		carpoolViewModel = new ViewModelProvider(this).get(CarpoolViewModel.class);
//		carpoolViewModel.getCarpools().observe(this, carpoolList -> {
//			carpoolAdapter.submitList(carpoolList);
//		});
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.d(">>", "resume");
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.d(">>", "onstart");
//		carpoolViewModel.loadCarpools();
	}

	@Override
	public void onDestroyView() {
		super.onDestroyView();
		binding = null;
	}
}