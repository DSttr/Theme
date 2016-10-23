package com.stuepd93.pelangi.fragment;

import com.stuepd93.pelangi.*;
import android.support.v7.widget.*;
import android.app.*;
import android.view.*;
import android.os.*;
import android.content.*;
import android.net.*;
import android.support.v4.app.Fragment;

public class WallpaperFragment extends Fragment
{
	private MainActivity mainActivity;
    private Toolbar toolbar;


    public WallpaperFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.wallpaper_fragment, container, false);

        toolbar = (Toolbar)view.findViewById(R.id.about_toolbar);

        setupToolbar();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity.setupDikiDrawer(toolbar);
    }

    private void setupToolbar(){
        toolbar.setTitle("Wallpaper");
        mainActivity.setSupportActionBar(toolbar);
    }

}
