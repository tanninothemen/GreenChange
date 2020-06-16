package com.newc.greenchange.adapter_sanpham;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newc.greenchange.R;
import com.newc.greenchange.model.SanPham;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HienThiSanPhamAdapter extends BaseAdapter {
//Nguyễn Thành Quang
    private Context context;
    private int layout;
    private List<SanPham> sanPhamList;

    public HienThiSanPhamAdapter(Context context, int layout, List<SanPham> sanPhamList) {
        this.context = context;
        this.layout = layout;
        this.sanPhamList = sanPhamList;
    }

    @Override
    public int getCount() {
        return sanPhamList.size();
    }

    @Override
    public Object getItem(int i) {
        return sanPhamList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{
        ImageView imgHinhAnhSanPham;
        TextView txtTenSanPham;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(layout,null);
            viewHolder.imgHinhAnhSanPham=(ImageView) view.findViewById(R.id.imageViewHinhSanPhamCustom);
            viewHolder.txtTenSanPham=(TextView) view.findViewById(R.id.textViewTenSanPhamCustom);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }

        SanPham sanPham= (SanPham) getItem(i);
        Picasso.with(context).
                load(sanPham.getHinhAnhSanPham()).
                fit().into(viewHolder.imgHinhAnhSanPham);
        viewHolder.txtTenSanPham.setText(sanPham.getTenSanPham());

        return view;
    }
}
