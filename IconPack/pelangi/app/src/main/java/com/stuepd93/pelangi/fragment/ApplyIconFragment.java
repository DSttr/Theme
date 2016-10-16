package com.stuepd93.pelangi.fragment;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.Collections;
import com.stuepd93.pelangi.item.*;
import com.stuepd93.pelangi.*;
import com.stuepd93.pelangi.util.*;
import com.stuepd93.pelangi.Adapter.*;
import android.app.*;

/**
 * Created by architjn on 04/01/16.
 */
public class ApplyIconFragment extends Fragment {

    private ArrayList<LauncherListItem> launchers = new ArrayList<>();
    private View mainView;
    private Context context;
	private MainActivity mainActivity;

	
	private Toolbar mToolbar;
	
	public ApplyIconFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity)activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.activity_apply, container, false);
        context = mainView.getContext();
		mToolbar = (Toolbar)mainView.findViewById(R.id.toolbar_apply);
        setupToolbar();
        if (Build.VERSION.SDK_INT >= 21) {
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
            getActivity().getWindow().setNavigationBarColor(ContextCompat.getColor(context,R.color.colorPrimaryDark));
        }
        init();
        return mainView;
    }

    private void init() {
        String[] launcherArray = getResources().getStringArray(R.array.launchers_list);
        int installed = 0;
        for (String launcher : launcherArray) {
            String[] value = launcher.split("\\|");
            boolean isInstalled = isLauncherInstalled(value[1]);
            if (isInstalled)
                installed++;
            launchers.add(new LauncherListItem(value, isInstalled));
        }
        Collections.sort(launchers, new LauncherComparator());
        LauncherAdapter adapter = new LauncherAdapter(context, launchers, installed);
        RecyclerView rv = (RecyclerView) mainView.findViewById(R.id.apply_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);
        rv.setAdapter(adapter);
    }

    private boolean isLauncherInstalled(String packageName) {
        final PackageManager pm = context.getPackageManager();
        try {
            pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mainActivity.setupDikiDrawer(mToolbar);
    }
	
	private void setupToolbar(){
        mainActivity.setSupportActionBar(mToolbar);
		mToolbar.setTitle("Apply Icon");
    }

}

