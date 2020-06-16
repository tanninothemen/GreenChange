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

public class VatDung_TreEm_Adapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<VatDung> vatDungList;

    public VatDung_TreEm_Adapter(Context context, int layout, List<VatDung> vatDungList) {
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
        ImageView imgTreEm;
        TextView txtTenTreEm, txtChatLieuTreEm;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if (convertView==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(layout,null);
            viewHolder.imgTreEm=(ImageView) convertView.findViewById(R.id.imageViewTreEm);
            viewHolder.txtTenTreEm=(TextView) convertView.findViewById(R.id.textViewTenTreEm);
            viewHolder.txtChatLieuTreEm=(TextView) convertView.findViewById(R.id.textViewChatLieuTreEm);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }

        VatDung vatDung= (VatDung) getItem(position);
        Picasso.with(context).load(vatDung.getHinhAnhVatDung()).fit().into(viewHolder.imgTreEm);
        viewHolder.txtTenTreEm.setText(vatDung.getTenVatDung());
        viewHolder.txtChatLieuTreEm.setText(vatDung.getChatLieu());
        return convertView;
    }
}
