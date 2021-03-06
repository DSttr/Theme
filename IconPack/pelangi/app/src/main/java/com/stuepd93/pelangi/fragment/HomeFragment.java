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
import com.stuepd93.pelangi.R;
import com.stuepd93.pelangi.item.*;
import com.stuepd93.pelangi.Adapter.*;
import android.content.*;
import android.support.annotation.*;
import android.widget.*;
import android.os.*;
import android.app.*;
import android.graphics.*;
import android.support.v4.widget.*;
import com.afollestad.materialdialogs.*;
import android.content.pm.*;
import android.view.*;
import android.net.*;
import com.stuepd93.pelangi.*;
import com.stuepd93.pelangi.BuildConfig;
import com.stuepd93.pelangi.util.*;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View mainView;
    private Context context;
    private static final String MARKET_URL = "https://play.google.com/store/apps/details?id=";

	private DrawerLayout drawer;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mainView = inflater.inflate(R.layout.home_fragment, container, false);
        context = mainView.getContext();
        checkForLicense();
        init();
        return mainView;
    }
	
    private void init() {
        setHasOptionsMenu(true);
        Toolbar toolbar = (Toolbar) mainView.findViewById(R.id.toolbar_main);
        setActionBar(toolbar);
        ((CollapsingToolbarLayout) mainView.findViewById(R.id.collapsingtoolbarlayout_main))
			.setTitle(getResources().getString(R.string.theme_title));
        if (Build.VERSION.SDK_INT >= 21) {
            ActivityManager.TaskDescription taskDescription = new
				ActivityManager.TaskDescription(getResources().getString(R.string.theme_title),
												BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher),
												getResources().getColor(R.color.primaryColor));
            getActivity().setTaskDescription(taskDescription);
        }
        (mainView.findViewById(R.id.fab_main)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					((MainActivity) getActivity()).switchFragment(new ApplyIconFragment(), true);
				}
			});
        getHomeActivity().setupDikiDrawer(toolbar);
        setCards();
    }

    private MainActivity getHomeActivity() {
        return ((MainActivity) getActivity());
    }

    private void setActionBar(Toolbar toolbar) {
        ((MainActivity) getActivity()).setSupportActionBar(toolbar);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            drawer.openDrawer(Gravity.LEFT);
        }
        if (id == R.id.action_sendemail) {
            StringBuilder emailBuilder = new StringBuilder();

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("mailto:" + getResources().getString(R.string.email_id)));
            intent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.email_subject));

            emailBuilder.append("\n \n \nOS Version: ").append(System.getProperty("os.version")).append("(").append(Build.VERSION.INCREMENTAL).append(")");
            emailBuilder.append("\nOS API Level: ").append(Build.VERSION.SDK_INT);
            emailBuilder.append("\nDevice: ").append(Build.DEVICE);
            emailBuilder.append("\nManufacturer: ").append(Build.MANUFACTURER);
            emailBuilder.append("\nModel (and Product): ").append(Build.MODEL).append(" (").append(Build.PRODUCT).append(")");
            PackageInfo appInfo = null;
            try {
                appInfo = getActivity().getPackageManager().getPackageInfo(getActivity().getPackageName(), 0);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
            assert appInfo != null;
            emailBuilder.append("\nApp Version Name: ").append(appInfo.versionName);
            emailBuilder.append("\nApp Version Code: ").append(appInfo.versionCode);

            intent.putExtra(Intent.EXTRA_TEXT, emailBuilder.toString());
            startActivity(Intent.createChooser(intent, (getResources().getString(R.string.send_title))));
            return true;
        } else if (id == R.id.action_share) {
            Intent sharingIntent = new Intent(Intent.ACTION_SEND);
            sharingIntent.setType("text/plain");
            String shareBody =
				getResources().getString(R.string.share_one) +
				getResources().getString(R.string.developer_name) +
				getResources().getString(R.string.share_two) +
				MARKET_URL + getActivity().getPackageName();
            sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
            startActivity(Intent.createChooser(sharingIntent, (getResources().getString(R.string.share_title))));
        } else if (id == R.id.action_changelog) {
            showChangelog();
        }

        return super.onOptionsItemSelected(item);
    }

    private void checkForLicense() {
        if (getResources().getBoolean(R.bool.license_check) && !BuildConfig.DEBUG) {
            String installer = getActivity().getPackageManager()
				.getInstallerPackageName(getActivity().getPackageName());
            try {
                if (installer.matches("com.android.vending") ||
					installer.matches("com.google.android.feedback") ||
					installer.matches("com.amazon.venezia")) {
                } else {
                    showNotLicensedDialog();
                }
            } catch (Exception e) {
                e.printStackTrace();
                showNotLicensedDialog();
            }
        }
    }

    private void showNotLicensedDialog() {
        new MaterialDialog.Builder(context)
			.title(R.string.license_failed_title)
			.content(R.string.license_failed)
			.positiveText(R.string.download)
			.negativeText(R.string.exit)
			.callback(new MaterialDialog.ButtonCallback() {
				@Override
				public void onPositive(MaterialDialog dialog) {
					Intent browserIntent = new Intent(Intent.ACTION_VIEW,
													  Uri.parse(MARKET_URL + getActivity().getPackageName()));
					startActivity(browserIntent);
					getActivity().finish();
				}

				@Override
				public void onNegative(MaterialDialog dialog) {
					getActivity().finish();
				}
			})
			.cancelListener(new DialogInterface.OnCancelListener() {
				@Override
				public void onCancel(DialogInterface dialog) {
					getActivity().finish();
				}
			})
			.dismissListener(new DialogInterface.OnDismissListener() {
				@Override
				public void onDismiss(DialogInterface dialog) {
					getActivity().finish();
				}
			}).show();
    }

    private void showChangelog() {
        new MaterialDialog.Builder(context)
			.title(R.string.changelog_dialog_title)
			.adapter(new ChangelogAdapter(context, R.array.fullchangelog), null)
			.positiveText(R.string.nice)
			.onPositive(new MaterialDialog.SingleButtonCallback() {
				@Override
				public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
					dialog.dismiss();
				}
			}).show();
    }

    public void setDrawer(DrawerLayout drawer) {
        this.drawer = drawer;
    }
}

