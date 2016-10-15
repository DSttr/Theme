package com.stuepd93.pelangi.fragment;

import com.stuepd93.pelangi.*;
import android.support.v7.widget.Toolbar;
import android.support.v4.app.Fragment;
import android.app.*;
import android.view.*;
import android.os.*;
import android.support.design.widget.*;
import android.widget.*;
import android.support.v7.widget.RecyclerView;
import android.net.*;
import android.content.*;
import android.support.annotation.*;

public class HomeFragment extends Fragment 
{
	private MainActivity appCompatActivity;
    private Toolbar toolbar;
	private View mainView;
	private Context context;
	
	public HomeFragment() {
        // Required empty public constructor
    }
	
	@Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
		
        View mainView = inflater.inflate(R.layout.home_fragment, container, false);

		context = mainView.getContext();
		init();

        setupToolbar();

        return mainView;
    }

	private void init()
	{
		// TODO: Implement this method
		toolbar = (Toolbar)mainView.findViewById(R.id.toolbar);
		((CollapsingToolbarLayout)mainView.findViewById(R.id.collapsing_toolbar)).setTitle(
			getString(R.string.collapsing_toolbar_fragment_title));
        setCards();
		/*(mainView.findViewById(R.id.fab_main)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					((MainActivity) getActivity()).Fragment(new ApplyIconFragment(), true);
				}
			});*/
        getHomeActivity().initNavigationDrawer(toolbar);
		
	}
	
	
	private MainActivity getHomeActivity() {
        return ((MainActivity) getActivity());
    }
	
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        appCompatActivity.initNavigationDrawer(toolbar);
    }

    private void setupToolbar(){
        toolbar.setTitle("");
        appCompatActivity.setSupportActionBar(toolbar);
    }
	
	private void setCards() {
        Button button1, button2, button3;
        button1 = (Button) mainView.findViewById(R.id.home_card_one_button);
        button2 = (Button) mainView.findViewById(R.id.home_card_two_button);
        button3 = (Button) mainView.findViewById(R.id.home_card_three_button);
        button1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Uri uri = Uri.parse(getResources().getString(R.string.card1_link));
					Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(goToMarket);
				}
			});
        if (!getResources().getBoolean(R.bool.card1_visible))
            (mainView.findViewById(R.id.main_card2)).setVisibility(View.GONE);
        button2.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Uri uri = Uri.parse(getResources().getString(R.string.card2_link));
					Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(goToMarket);
				}
			});
        if (!getResources().getBoolean(R.bool.card2_visible))
            (mainView.findViewById(R.id.main_card2)).setVisibility(View.GONE);
        button3.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					Uri uri = Uri.parse(getResources().getString(R.string.card3_link));
					Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
					startActivity(goToMarket);
				}
			});
        if (!getResources().getBoolean(R.bool.card3_visible))
            (mainView.findViewById(R.id.main_card3)).setVisibility(View.GONE);
    }
}
