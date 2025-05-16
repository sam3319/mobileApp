package com.example.chapter11codingchallenge;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ImageModel> imageList;
    private LayoutInflater inflater;

    public ImageAdapter(Context context, ArrayList<ImageModel> imageList) {
        this.context = context;
        this.imageList = imageList;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return imageList.size();
    }

    @Override
    public Object getItem(int position) {
        return imageList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.grid_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = convertView.findViewById(R.id.imageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ImageModel model = imageList.get(position);

        // 비트맵 로딩 최적화 (실제 앱에서는 Glide나 Picasso 라이브러리 사용 권장)
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 4; // 이미지 크기 축소
        Bitmap bitmap = BitmapFactory.decodeFile(model.getPath(), options);
        holder.imageView.setImageBitmap(bitmap);

        return convertView;
    }

    static class ViewHolder {
        ImageView imageView;
    }
}

