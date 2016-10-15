package com.stuepd93.pelangi;


import android.support.v7.app.*;
import android.support.design.widget.*;
import android.view.*;
import android.support.v4.widget.*;
import android.support.v7.widget.*;
import android.os.*;
import com.stuepd93.pelangi.fragment.*;
import android.support.v4.view.*;
import android.support.annotation.*;
import android.widget.Toast;
import android.support.v4.app.Fragment;



public class MainActivity extends AppCompatActivity
{
	
	private final static String APPLY_FRAGMENT= "status_bar";
    private final static String ICON_FRAGMENT = "drawer_settings";
    private final static String HOME_FRAGMENT = "button_settings";
    private final static String ABOUT_FRAGMENT = "about";
    private final static String SELECTED_TAG = "selected_index";
	private final static int ICON = 0;
    private final static int BARANG = 1;
    private final static int HOME = 2;
    private final static int ABOUT = 3;

	private static int selectedIndex;

	private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;
	private Toolbar mToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
    
		setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		mToolbar = (Toolbar)findViewById(R.id.toolbar);
		setupDikiToolbar(mToolbar);
		
		
		mNavigationView = (NavigationView)findViewById(R.id.navigation_view);
		mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
		initNavigationDrawer(mToolbar);

		if(savedInstanceState!=null){
            mNavigationView.getMenu().getItem(savedInstanceState.getInt(SELECTED_TAG)).setChecked(true);
            return;
        }
		selectedIndex = ICON;

        getSupportFragmentManager().beginTransaction().add(R.id.fragment_container,
														   new IconsFragment(),ICON_FRAGMENT).commit();

		
    }
	

	public void tampilkanSnackbar(String message) {
		Snackbar.make(mNavigationView, message, Snackbar.LENGTH_LONG).show();
	}

	@Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SELECTED_TAG, selectedIndex);
    }

    public void initNavigationDrawer(Toolbar toolbar) {

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

				@Override
				public boolean onNavigationItemSelected(MenuItem menuItem) {

					switch(menuItem.getItemId()){
						case R.id.home_nav:
							if(!menuItem.isChecked()){
								selectedIndex = HOME;
								menuItem.setChecked(true);
								getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
																							   new HomeFragment(), HOME_FRAGMENT).commit();
							}
							mDrawerLayout.closeDrawer(GravityCompat.START);
								return true;
						case R.id.apply_nav:
							if(!menuItem.isChecked()){
								selectedIndex = ICON;
								menuItem.setChecked(true);
								getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
																					   new ApplyIconFragment(), APPLY_FRAGMENT).commit();
							}
							mDrawerLayout.closeDrawer(GravityCompat.START);
							return true;
						case R.id.icon_nav:
							if(!menuItem.isChecked()){
								selectedIndex = BARANG;
								menuItem.setChecked(true);
								getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
																					   new IconsFragment(),ICON_FRAGMENT).commit();
							}
							mDrawerLayout.closeDrawer(GravityCompat.START);
							return true;
						case R.id.about_nav:
							if(!menuItem.isChecked()){
								selectedIndex = ABOUT;
								menuItem.setChecked(true);
								getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
																					   new About(),ABOUT_FRAGMENT).commit();
							}
							mDrawerLayout.closeDrawer(GravityCompat.START);
							return true;
					}
					return true;
				}
			});

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,mDrawerLayout,mToolbar,R.string.drawer_open,R.string.drawer_close){

            @Override
            public void onDrawerClosed(View v){
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        mDrawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

	private void setupDikiToolbar(Toolbar mToolbar)
	{
		// TODO: Implement this method
		setSupportActionBar(mToolbar);
		mToolbar.setTitle("");

	}
	public void setupDikiDrawer(Toolbar mToolbar){
        mDrawerToggle = new ActionBarDrawerToggle(
			this,
			mDrawerLayout,
			mToolbar,
			R.string.drawer_open,
			R.string.drawer_close
        ) {

            public void onDrawerClosed(View view) {
                invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();

    }
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
}
