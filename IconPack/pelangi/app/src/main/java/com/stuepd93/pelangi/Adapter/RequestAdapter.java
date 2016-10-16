package com.stuepd93.pelangi.Adapter;

import android.content.*;
import android.os.*;
import android.support.v7.widget.*;
import android.view.*;
import android.widget.*;
import com.pk.requestmanager.*;
import com.stuepd93.pelangi.*;
import com.stuepd93.pelangi.Adapter.*;
import java.util.*;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.SimpleItemViewHolder>
 {

	private final List<AppInfo> items;
	private Context context;

	public final static class SimpleItemViewHolder extends RecyclerView.ViewHolder {
		public TextView appName;
		public ImageView appIcon;
		public CheckBox checkBox;
		public View mainView;

		public SimpleItemViewHolder(View view) {
			super(view);

			appName = (TextView) view.findViewById(R.id.req_item_name);
			appIcon = (ImageView) view.findViewById(R.id.req_item_icon);
			checkBox = (CheckBox) view.findViewById(R.id.req_item_checkbox);
			mainView = view;
		}
	}

	public RequestAdapter(Context context, List<AppInfo> items) {
		this.context = context;
		this.items = items;
	}

	@Override
	public RequestAdapter.SimpleItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
		View itemView = LayoutInflater.from(parent.getContext()).
			inflate(R.layout.req_list_item, parent, false);


		return new SimpleItemViewHolder(itemView);
	}

	@Override
	public void onBindViewHolder(final SimpleItemViewHolder holder, final int position) {

		holder.appName.setText(items.get(position).getName());
		holder.appIcon.setImageDrawable(items.get(position).getImage());
		holder.checkBox.setChecked(items.get(position).isSelected());
		holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    addToList(position);
                }
            });
		holder.mainView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    addToList(position);
                }
            });
	}

	private void addToList(int position) {
		AppInfo mApp = items.get(position);
		mApp.setSelected(!mApp.isSelected());
		items.set(position, mApp);

		// Let the adapter know you selected something
		new CountDownTimer(400, 1000) {
			public void onTick(long millisUntilFinished) {
			}

			public void onFinish() {
				notifyDataSetChanged();
			}
		}.start();
	}

	@Override
	public int getItemCount() {
		return this.items.size();
	}
}
