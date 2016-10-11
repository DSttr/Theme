package com.stuepd93.pelangi;

import android.app.*;
import android.os.*;
import android.support.v7.app.*;
import android.widget.AdapterView.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.graphics.*;
import java.util.*;
import android.content.res.*;
import android.graphics.BitmapFactory.*;

public class MainActivity extends AppCompatActivity implements OnItemClickListener
{

	
	private static final String ACTION_ADW_PICK_ICON="org.adw.launcher.icons.ACTION_PICK_ICON";
	private boolean mPickerMode=false;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		
		int iconSize=getResources().getDimensionPixelSize(android.R.dimen.app_icon_size);
		GridView g=(GridView) findViewById(R.id.icon_grid);
		g.setNumColumns(GridView.AUTO_FIT);
        g.setColumnWidth(iconSize);
        g.setStretchMode(GridView.STRETCH_SPACING_UNIFORM);
        g.setVerticalSpacing(iconSize/3);
        g.setOnItemClickListener(this);
        IconsAdapter adapter=new IconsAdapter(this,iconSize);
        g.setAdapter(adapter);
        if(getIntent().getAction().equals(ACTION_ADW_PICK_ICON)){
        	mPickerMode=true;
        }
    }
	
	public void onItemClick(AdapterView<?> adapter, View view, int position, long id) {
		if(mPickerMode){
			Intent intent=new Intent();
			Bitmap bitmap=null;
			try{
				bitmap=(Bitmap) adapter.getAdapter().getItem(position);
			}catch (Exception e) {
			}
			if(bitmap!=null){
				intent.putExtra("icon",bitmap);
				setResult(RESULT_OK, intent);
			}else{
				setResult(RESULT_CANCELED, intent);
			}
			finish();
		}
	}
	
	private class IconsAdapter extends BaseAdapter{
		private Context mContext;
		private int mIconSize;
		public IconsAdapter(Context mContext, int iconsize) {
			super();
			this.mContext = mContext;
			this.mIconSize = iconsize;
			loadIcons();
		}

		@Override
		public int getCount() {
			return mThumbs.size();
		}

		@Override
		public Object getItem(int position) {
			BitmapFactory.Options opts = new BitmapFactory.Options();
		   // Options opts=new BitmapFactory.Options();
		    opts.inPreferredConfig=Bitmap.Config.ARGB_8888;
		    
			return BitmapFactory.decodeResource(mContext.getResources(), mThumbs.get(position), opts);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
            ImageView imageView;
            if (convertView == null) {
                imageView = new ImageView(mContext);
                imageView.setLayoutParams(new GridView.LayoutParams(mIconSize, mIconSize));
            } else {
                imageView = (ImageView) convertView;
            }
            imageView.setImageResource(mThumbs.get(position));
            return imageView;
		}

		private ArrayList<Integer> mThumbs;

	    private void loadIcons() {
	        mThumbs = new ArrayList<Integer>();

	        final Resources resources = getResources();
	        final String packageName = getApplication().getPackageName();

	        addIcons(resources, packageName, R.array.icon_pack);
	    }
	    private void addIcons(Resources resources, String packageName, int list) {
	        final String[] extras = resources.getStringArray(list);
	        for (String extra : extras) {
	            int res = resources.getIdentifier(extra, "drawable", packageName);
	            if (res != 0) {
	                final int thumbRes = resources.getIdentifier(extra,"drawable", packageName);
	                if (thumbRes != 0) {
	                    mThumbs.add(thumbRes);
	                }
	            }
	        }
	    }

	}
}
