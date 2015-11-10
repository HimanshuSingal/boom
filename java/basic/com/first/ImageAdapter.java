package basic.com.first;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class ImageAdapter extends BaseAdapter {
    private Context mContext;

    public ImageAdapter(Context c) {
        mContext = c;
    }

    public int getCount() {
        return Parameters.col_size*Parameters.row_size;
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(85, 85));
            imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView.setPadding(0, 0, 0, 0);
        } else {
            imageView = (ImageView) convertView;
        }
        Field pos=Parameters.value(position);
        if(Parameters.value(position).player==1)
             imageView.setImageResource(mThumbIds[(Parameters.value(position).val)]);
        else
            imageView.setImageResource(mThumbIds2[Parameters.value(position).val]);
        return imageView;
    }

    // references to our images
    private Integer[] mThumbIds = {
            R.drawable.empty, R.drawable.one,
            R.drawable.two, R.drawable.three,
             };
    private Integer[] mThumbIds2 = {
            R.drawable.empty, R.drawable.onep,
            R.drawable.twop, R.drawable.threep,
    };
}