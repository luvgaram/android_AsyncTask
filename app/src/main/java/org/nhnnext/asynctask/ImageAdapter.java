package org.nhnnext.asynctask;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by eunjooim on 15. 9. 22.
 */
public class ImageAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> URLs;

    public ImageAdapter(Context context) {
        this.context = context;
        ImageURL imageURL = new ImageURL();
        URLs = imageURL.getURLS();
    }

    public int getCount() {
        return URLs.size();
    }

    public String getItem(int position) {
        return URLs.get(position);
    }

    public long getItemId(int position) {
        return getItem(position).hashCode();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;

        if (convertView == null) {
            imageView = new ImageView(context);
            imageView.setLayoutParams(new GridView.LayoutParams(320, 240));
            imageView.setAdjustViewBounds(false);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(8, 8, 8, 8);
        } else {
            imageView = (ImageView) convertView;
        }

        FileDownloaderWithHandler fileDownloader = new FileDownloaderWithHandler();
        fileDownloader.downFile(getItem(position), imageView);

        return imageView;
    }
}
