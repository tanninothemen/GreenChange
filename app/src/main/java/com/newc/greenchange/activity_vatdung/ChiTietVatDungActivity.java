package com.newc.greenchange.activity_vatdung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.newc.greenchange.CheckConnection;
import com.newc.greenchange.R;
import com.newc.greenchange.Server;
import com.newc.greenchange.activity_sanpham.ChiTietSanPhamActivity;
import com.newc.greenchange.adapter_vatdung.ThayTheVatDungAdapter;
import com.newc.greenchange.model.SanPham;
import com.newc.greenchange.model.VatDung;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChiTietVatDungActivity extends AppCompatActivity{
// Nguyễn Thành Tân

    Toolbar toolbarChiTietVatDung;
    ImageView imgChiTietVatDung;
    TextView txtTenVatDung, txtChatLieuVatDung, txtThongTinGayHaiVatDung;
    RecyclerView recyclerViewSanPhamThayThe;
    ArrayList<SanPham> sanPhamArrayList;
    ThayTheVatDungAdapter adapter;

    int idvd=0;

    //Hàm main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_vat_dung);

        if (CheckConnection.isNetworkConnected(getApplicationContext())){
            Init();
            ActionToolbar();
            GetThongTinVatDung();
            GetIDVatDung();
            GetSanPhamThayThe(Server.duongDanSanPhamThayThe);
        }else {
            CheckConnection.connectionErrorMessage(getApplicationContext());
        }

    }

    //Hàm lấy idvatdung cho san pham thay the
    private void GetIDVatDung() {
        VatDung vatDung= (VatDung) getIntent().getSerializableExtra("thongtinvatdung");
        idvd=vatDung.getIDVatDung();
        Log.d("kiemtraidvatdung", String.valueOf(idvd));
    }

    private void GetSanPhamThayThe(String url) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int idsanpham=0;
                        String tensanpham="";
                        String hinhanhsanpham="";
                        String chatlieu="";
                        String duonglink="";
                        String thongtinsanpham="";
                        int maloai=0;
                        if (response!=null && response.length()!=2) {
                            try {
                                JSONArray jsonArray = new JSONArray(response);
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    idsanpham = jsonObject.getInt("IDSanPham");
                                    tensanpham = jsonObject.getString("TenSanPham");
                                    hinhanhsanpham = jsonObject.getString("HinhAnhSanPham");
                                    chatlieu = jsonObject.getString("ChatLieu");
                                    thongtinsanpham = jsonObject.getString("ThongTinSanPham");
                                    duonglink = jsonObject.getString("DuongLink");
                                    maloai = jsonObject.getInt("MaLoai");
                                    sanPhamArrayList.add(new SanPham(idsanpham, tensanpham, hinhanhsanpham, chatlieu, thongtinsanpham, duonglink, maloai));
                                    adapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<>();
                param.put("idvatdung",String.valueOf(idvd));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void GetThongTinVatDung() {
        int id=0;
        String tenchitiet="";
        String hinhchitiet="";
        String chatlieuchitiet="";
        String thongtingayhaichitiet="";
        int maloaivatdung=0;

        VatDung vatDung= (VatDung) getIntent().getSerializableExtra("thongtinvatdung");
        id=vatDung.getIDVatDung();
        tenchitiet=vatDung.getTenVatDung();
        hinhchitiet=vatDung.getHinhAnhVatDung();
        chatlieuchitiet=vatDung.getChatLieu();
        thongtingayhaichitiet=vatDung.getThongTinGayHai();
        maloaivatdung=vatDung.getMaLoai();

        txtTenVatDung.setText(tenchitiet);
        Picasso.with(this).load(hinhchitiet).fit().into(imgChiTietVatDung);
        txtChatLieuVatDung.setText(chatlieuchitiet);
        txtThongTinGayHaiVatDung.setText(thongtingayhaichitiet);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarChiTietVatDung);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTietVatDung.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Init() {
        toolbarChiTietVatDung=(Toolbar) findViewById(R.id.toolbarChiTietVatDung);
        imgChiTietVatDung=(ImageView) findViewById(R.id.imageViewChiTietVatDung);
        txtTenVatDung=(TextView) findViewById(R.id.textViewTenVatDung);
        txtChatLieuVatDung=(TextView) findViewById(R.id.textViewChatLieuVatDung);
        txtThongTinGayHaiVatDung=(TextView) findViewById(R.id.textViewThongTinGayHaiVatDung);
        recyclerViewSanPhamThayThe=(RecyclerView) findViewById(R.id.recyclerViewSanPhamThayThe);
        sanPhamArrayList=new ArrayList<>();
        adapter=new ThayTheVatDungAdapter(getApplicationContext(),R.layout.dong_sanpham_thaythe,sanPhamArrayList);
        recyclerViewSanPhamThayThe.setHasFixedSize(true);
        recyclerViewSanPhamThayThe.setLayoutManager(new GridLayoutManager(getApplicationContext(),1,LinearLayoutManager.HORIZONTAL,false));
        recyclerViewSanPhamThayThe.setAdapter(adapter);
    }
}
