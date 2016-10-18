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
import kr.co.namee.permissiongen.*;
import android.*;
import android.content.*;



public class MainActivity extends AppCompatActivity implements
NavigationView.OnNavigationItemSelectedListener,View.OnClickListener
{

	@Override
	public void onClick(View p1)
	{
		// TODO: Implement this method
	}

	
	private final static String APPLY_FRAGMENT= "apply_fragment";
    private final static String ICON_FRAGMENT = "icon_fragment";
    private final static String HOME_FRAGMENT = "home_fragment";
	private final static String REQ_FRAGMENT = "home_fragment";
    private final static String ABOUT_FRAGMENT = "about";
    private final static String SELECTED_TAG = "selected_index";
	private final static int ICON = 0;
    private final static int BARANG = 1;
    private final static int HOME = 2;
    private final static int ABOUT = 3;
	private final static int REQ = 4;

	private static int selectedIndex;

	private NavigationView mNavigationView;
    private ActionBarDrawerToggle mDrawerToggle;
	private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

		reqPerms();

		mNavigationView = (NavigationView)findViewById(R.id.navigation_view);
		mNavigationView.setNavigationItemSelectedListener(this);
		mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);

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
						case R.id.req_nav:
							if(!menuItem.isChecked()){
								selectedIndex = REQ;
								menuItem.setChecked(true);
								getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
																					   new RequestFragment(), REQ_FRAGMENT).commit();
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
	
	public void switchFragment(Fragment fragment, boolean b) {
        if (!b)
            getSupportFragmentManager().beginTransaction()
				.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
				.replace(R.id.fragment_container, fragment).commit();
        else
            getSupportFragmentManager().beginTransaction().addToBackStack(null)
				.setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
				.replace(R.id.fragment_container, fragment).commit();
    }
	
	private void reqPerms(){
		PermissionGen.with(MainActivity.this)
			.addRequestCode(100)
			.permissions(
			Manifest.permission.CAMERA,
			Manifest.permission.INTERNET,
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_EXTERNAL_STORAGE)
			.request();
	}
	
	@PermissionSuccess(requestCode = 100)
	public void doSomething(){
		// Lakukan sesuatu disini
	}
	@PermissionFail(requestCode = 100)
	public void doFailSomething(){
		AlertDialog.Builder dlg=new AlertDialog.Builder(this);
		dlg.setTitle("Perijinan ditolak");
		dlg.setCancelable(false);
		dlg.setMessage("Untuk menggunakan Aplikasi ini kamu perlu membolehkan beberapa perijinan yang diajukan. Atau Aplikasi ini tidak bisa digunakan");
		dlg.setNegativeButton("Keluar", new DialogInterface.OnClickListener(){
				@Override
				public void onClick(DialogInterface p1, int p2) {
					MainActivity.this.finish();
				}
			});
		dlg.show();
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions,
										   int[] grantResults) {
		PermissionGen.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
	
}
