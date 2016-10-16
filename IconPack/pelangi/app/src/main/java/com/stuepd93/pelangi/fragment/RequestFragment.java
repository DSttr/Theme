package com.stuepd93.pelangi.fragment;

import android.support.v4.app.*;
import com.stuepd93.pelangi.util.*;
import java.util.*;
import com.pk.requestmanager.*;
import android.support.v7.widget.*;
import android.view.*;
import android.content.*;
import com.stuepd93.pelangi.Adapter.*;
import android.support.annotation.*;
import android.os.*;
import com.stuepd93.pelangi.*;
import android.support.v4.content.*;
import android.support.design.widget.*;
import android.app.Activity;

public class RequestFragment extends Fragment implements LoadAppsList.Callback
{
	
	private RecyclerView rv;
    private PkRequestManager requestManager;
    private List<AppInfo> apps;
    private View mainView;
    private Context context;
    private RequestAdapter adapter;
	private Toolbar toolbar;
	private MainActivity appCompatActivity;
	
	public RequestFragment(){
		
	}
	

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        appCompatActivity = (MainActivity)activity;
    }
	
	@Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.request_fragment, container, false);
        context = mainView.getContext();
        rv = (RecyclerView) mainView.findViewById(R.id.req_rv);
        toolbar = (Toolbar)mainView.findViewById(R.id.toolbar_req);
		setActionBar();
        if (Build.VERSION.SDK_INT >= 21)
            getActivity().getWindow().setStatusBarColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));

        new LoadAppsList(context, this, requestManager).execute();

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        layoutManager.scrollToPosition(0);
        rv.setLayoutManager(layoutManager);
        rv.setHasFixedSize(true);

        Snackbar.make(mainView.findViewById(R.id.coordinating_req), "Loading apps may take some time..", Snackbar.LENGTH_LONG).show();
        CoordinatorLayout.LayoutParams p = (CoordinatorLayout.LayoutParams) mainView.findViewById(R.id.fab_rev).getLayoutParams();
        p.setBehavior(new ScrollAwareFABBehavior(context));
        mainView.findViewById(R.id.fab_rev).setLayoutParams(p);
        (mainView.findViewById(R.id.fab_rev)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					try {
						// Small workaround
						requestManager.setActivity(getActivity());
						// Build and send the request in the background.
						requestManager.sendRequestAsync();
						if (requestManager.getNumSelected() == 0) {
							Snackbar.make(mainView.findViewById(R.id.coordinating_req), "No apps selected!", Snackbar.LENGTH_LONG)
                                .setAction("Select All", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        List<AppInfo> mAppList = apps;
                                        for (AppInfo mApp : mAppList) {
                                            mApp.setSelected(true);
                                        }
                                        adapter.notifyDataSetChanged();
                                    }
                                }).show();
						} else {
							Snackbar.make(mainView.findViewById(R.id.coordinating_req), "Generating Mail..", Snackbar.LENGTH_LONG).show();
						}
					} catch (NullPointerException e) {
						e.printStackTrace();
						Snackbar.make(mainView.findViewById(R.id.coordinating_req), "Apps are loading wait for them..", Snackbar.LENGTH_LONG).show();
					}
				}
			});
        return mainView;
    }
	
	@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        appCompatActivity.setupDikiDrawer(toolbar);
    }

    private void setActionBar() {
		toolbar.setTitle("Request Icon");
        appCompatActivity.setSupportActionBar(toolbar);
    }

    @Override
    public void onListLoaded(List<AppInfo> apps, PkRequestManager requestManager) {
        this.requestManager = requestManager;
        this.apps = apps;
        (mainView.findViewById(R.id.progressBar_req)).setVisibility(View.GONE);
        adapter = new RequestAdapter(context, apps);
        rv.setAdapter(adapter);
    }

	
}
