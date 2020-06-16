package com.newc.greenchange.adapter_vatdung;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.newc.greenchange.R;
import com.newc.greenchange.model.VatDung;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HienThiVatDungAdapter extends BaseAdapter {

    private Context context;
    private int layout;
    private List<VatDung> vatDungList;

    public HienThiVatDungAdapter(Context context, int layout, List<VatDung> vatDungList) {
        this.context = context;
        this.layout = layout;
        this.vatDungList = vatDungList;
    }

    @Override
    public int getCount() {
        return vatDungList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    private class ViewHolder{
        ImageView imgHinhAnhVatDung;
        TextView txtTenVatDung;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            viewHolder.imgHinhAnhVatDung=(ImageView) convertView.findViewById(R.id.imageViewHinhVatDungCustom);
            viewHolder.txtTenVatDung=(TextView) convertView.findViewById(R.id.textViewTenVatDungCustom);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        VatDung vatdung=vatDungList.get(position);
        viewHolder.txtTenVatDung.setText(vatdung.getTenVatDung());
        Picasso.with(context).load(vatdung.getHinhAnhVatDung()).
                fit().into(viewHolder.imgHinhAnhVatDung);
        return convertView;
    }

}
