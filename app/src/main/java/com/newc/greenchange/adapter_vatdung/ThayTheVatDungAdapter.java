package com.newc.greenchange.adapter_vatdung;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.newc.greenchange.R;
import com.newc.greenchange.activity_sanpham.ChiTietSanPhamActivity;
import com.newc.greenchange.activity_vatdung.ChiTietVatDungActivity;
import com.newc.greenchange.model.SanPham;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ThayTheVatDungAdapter extends RecyclerView.Adapter<ThayTheVatDungAdapter.ItemHolder> {

    private Context context;
    private int layout;
    private List<SanPham> sanPhamList;

    public ThayTheVatDungAdapter(Context context, int layout, List<SanPham> sanPhamList) {
        this.context = context;
        this.layout = layout;
        this.sanPhamList = sanPhamList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext()).inflate(layout,null);
        ItemHolder itemHolder=new ItemHolder(v);

        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder itemHolder, int i) {
        final SanPham sanPham=sanPhamList.get(i);
        Picasso.with(context).load(sanPham.getHinhAnhSanPham()).fit().into(itemHolder.imgSanPhamThayThe);
        itemHolder.txtSanPhamThayThe.setText(sanPham.getTenSanPham());
        itemHolder.setItemClickListener(new ItemClickListener() {
            @Override
            public void onClick(View view, int position) {
                SanPham sanPham=sanPhamList.get(position);
                Intent intent=new Intent(context, ChiTietSanPhamActivity.class);
                intent.putExtra("thongtinsanpham", sanPham);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return sanPhamList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imgSanPhamThayThe;
        TextView txtSanPhamThayThe;
        ItemClickListener itemClickListener;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imgSanPhamThayThe=(ImageView) itemView.findViewById(R.id.imageViewSanPhamThayTheCustom);
            txtSanPhamThayThe=(TextView) itemView.findViewById(R.id.textViewTenSanPhamThayTheCustom);
            itemView.setOnClickListener(this);
        }

        //Tạo setter cho biến itemClickListenenr
        public void setItemClickListener(ItemClickListener itemClickListener)
        {
            this.itemClickListener = itemClickListener;
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onClick(view,getAdapterPosition());
        }
    }
}
