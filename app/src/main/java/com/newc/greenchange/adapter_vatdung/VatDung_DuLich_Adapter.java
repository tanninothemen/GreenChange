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

public class VatDung_DuLich_Adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<VatDung> vatDungList;

    public VatDung_DuLich_Adapter(Context context, int layout, List<VatDung> vatDungList) {
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
        return vatDungList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    private class ViewHolder{
        ImageView imgDuLich;
        TextView txtTenDuLich, txtChatLieuDuLich;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            viewHolder.imgDuLich=(ImageView) convertView.findViewById(R.id.imageViewDuLich);
            viewHolder.txtTenDuLich=(TextView) convertView.findViewById(R.id.textViewTenDuLich);
            viewHolder.txtChatLieuDuLich=(TextView) convertView.findViewById(R.id.textViewChatLieuDuLich);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        VatDung vatDung= (VatDung) getItem(position);
        Picasso.with(context).load(vatDung.getHinhAnhVatDung()).fit().into(viewHolder.imgDuLich);
        viewHolder.txtTenDuLich.setText(vatDung.getTenVatDung());
        viewHolder.txtChatLieuDuLich.setText(vatDung.getChatLieu());
        return convertView;
    }
}
