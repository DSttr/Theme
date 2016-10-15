package com.stuepd93.pelangi.fragment;

import android.app.*;
import android.content.*;
import android.net.*;
import android.os.*;
import android.support.v4.app.*;
import android.support.v7.widget.*;
import android.view.*;

import android.support.v4.app.Fragment;
import com.stuepd93.pelangi.*;
import android.support.design.widget.*;

public class About extends Fragment
{

	private MainActivity mainActivity;
    private Toolbar toolbar;


    public About() {
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
        View view =  inflater.inflate(R.layout.about_fragment, container, false);

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
        toolbar.setTitle("About");
        mainActivity.setSupportActionBar(toolbar);
    }
}

