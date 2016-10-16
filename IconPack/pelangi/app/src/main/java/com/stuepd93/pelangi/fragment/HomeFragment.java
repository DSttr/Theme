package com.stuepd93.pelangi.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;
import com.stuepd93.pelangi.*;
import com.stuepd93.pelangi.item.*;
import com.stuepd93.pelangi.Adapter.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {



    private List<CardItemModel> cardItems = new ArrayList<>(30);
    private MainActivity appCompatActivity;
    private Toolbar toolbar;
    private RecyclerView recyclerView;


    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        appCompatActivity = (MainActivity)activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.home_fragment, container, false);

        toolbar = (Toolbar)view.findViewById(R.id.toolbar);

        setupToolbar();
		
		((CollapsingToolbarLayout)view.findViewById(R.id.collapsing_toolbar)).setTitle(
			getString(R.string.collapsing_toolbar_fragment_title));

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler_view);

        setupRecyclerView();

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        appCompatActivity.setupDikiDrawer(toolbar);
    }

    private void setupToolbar(){
        toolbar.setTitle("");
        appCompatActivity.setSupportActionBar(toolbar);
    }

    private void setupRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(appCompatActivity));
        recyclerView.setHasFixedSize(true);
        initializeCardItemList();
        recyclerView.setAdapter(new RecyclerAdapter(cardItems));
    }

    private void initializeCardItemList(){
        CardItemModel cardItemModel;
        String[] cardTitles = getResources().getStringArray(R.array.card_titles);
        String[] cardContents = getResources().getStringArray(R.array.card_contents);
        final int length = cardTitles.length;
        for(int i=0;i<length;i++){
            cardItemModel = new CardItemModel(cardTitles[i],cardContents[i]);
            cardItems.add(cardItemModel);
        }
    }
}
