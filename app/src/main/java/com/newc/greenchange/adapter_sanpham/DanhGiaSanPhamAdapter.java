package com.newc.greenchange.adapter_sanpham;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.TextView;

import com.newc.greenchange.R;
import com.newc.greenchange.model.DanhGia;
import com.newc.greenchange.model.TaiKhoan;

import org.w3c.dom.Text;

import java.util.List;

public class DanhGiaSanPhamAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<DanhGia> danhGiaList;
    private List<TaiKhoan> taiKhoanList;

    public DanhGiaSanPhamAdapter(Context context, int layout, List<DanhGia> danhGiaList,List<TaiKhoan> taiKhoanList) {
        this.context = context;
        this.layout = layout;
        this.danhGiaList = danhGiaList;
        this.taiKhoanList=taiKhoanList;
    }

    @Override
    public int getCount() {
        return danhGiaList.size();
    }

    @Override
    public Object getItem(int i) {
        return danhGiaList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    private class ViewHolder{
        TextView txtTenDanhGia, txtNoiDungDanhGia;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ViewHolder viewHolder;
        if (view==null){
            viewHolder=new ViewHolder();
            LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=inflater.inflate(layout,null);
            viewHolder.txtTenDanhGia=(TextView) view.findViewById(R.id.textViewTenDanhGia);
            viewHolder.txtNoiDungDanhGia=(TextView) view.findViewById(R.id.textViewNoiDungDanhGia);
            view.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) view.getTag();
        }

        DanhGia danhGia= (DanhGia) getItem(i);

        viewHolder.txtTenDanhGia.setText(danhGia.getTentaikhoan()+":");
        viewHolder.txtNoiDungDanhGia.setText(danhGia.getNoidungdanhgia());
        return view;
    }
}
