package com.newc.greenchange.adapter_vatdung;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newc.greenchange.model.Loai;
import com.newc.greenchange.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LoaiAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<Loai> loaiList;

    public LoaiAdapter(Context context, int layout, List<Loai> loaiList) {
        this.context = context;
        this.layout = layout;
        this.loaiList = loaiList;
    }

    @Override
    public int getCount() {
        return loaiList.size();
    }

    @Override
    public Object getItem(int position) {
        return loaiList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imgHinhLoai;
        TextView txtTenLoai;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            viewHolder.imgHinhLoai=(ImageView) convertView.findViewById(R.id.imageViewHinhLoaiCustom);
            viewHolder.txtTenLoai=(TextView) convertView.findViewById(R.id.textViewTenLoaiCustom);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        Loai loai=loaiList.get(position);
        Picasso.with(context).load(loai.getHinhanhloai()).fit().into(viewHolder.imgHinhLoai);
        viewHolder.txtTenLoai.setText(loai.getTenloai());

        return convertView;
    }
}
