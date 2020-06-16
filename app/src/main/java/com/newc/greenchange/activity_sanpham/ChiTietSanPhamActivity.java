package com.newc.greenchange.activity_sanpham;

import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.newc.greenchange.CheckConnection;
import com.newc.greenchange.R;
import com.newc.greenchange.Server;
import com.newc.greenchange.adapter_sanpham.DanhGiaSanPhamAdapter;
import com.newc.greenchange.model.DanhGia;
import com.newc.greenchange.model.SanPham;
import com.newc.greenchange.model.SanPham;
import com.newc.greenchange.model.TaiKhoan;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChiTietSanPhamActivity extends AppCompatActivity {

    //Biến toàn cục
    Toolbar toolbarChiTietSanPham;
    ListView lvDanhGiaSanPham;
    ImageView imgChiTietSanPham;
    TextView txtTenSanPham, txtChatLieuSanPham, txtThongTinSanPham;
    Button btnDanhGiaSP, btnMuaSanPham, btnXacNhanDanhGia, btnHuyDanhGia;
    EditText edtDanhGia;
    SanPham sanPham;
    ArrayList<DanhGia> danhGiaArrayList;
    ArrayList<TaiKhoan> taiKhoanArrayList;
    DanhGiaSanPhamAdapter danhGiaSanPhamAdapter;
    int id;

    //Hàm main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        if (CheckConnection.isNetworkConnected(this)){
            Init();
            ActionToolbar();
            GetThongTinSanPham();
            GetDataDanhGia(Server.duongDanHienThiDanhGia);
            ChuyenDenTrangMuaBan();
            DanhGiaSanPham();
        }else {
            CheckConnection.connectionErrorMessage(this);
        }

    }

    //Đổ dữ liệu đánh giá ra listView
    private void GetDataDanhGia(String url) {
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int idtaikhoan=0;
                        int idsanpham=0;
                        String noidung="";
                        int iddanhgia=0;
                        String tentaikhoan="";
                        if (response!=null){
                            try {
                                JSONArray jsonArray=new JSONArray(response);
                                for (int i=0;i<response.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    idtaikhoan=jsonObject.getInt("IDTaiKhoan");
                                    idsanpham=jsonObject.getInt("IDSanPham");
                                    noidung=jsonObject.getString("NoiDung");
                                    iddanhgia=jsonObject.getInt("IDDanhGia");
                                    tentaikhoan=jsonObject.getString("TenTaiKhoan");
                                    danhGiaArrayList.add(new DanhGia(idtaikhoan, idsanpham, noidung, iddanhgia, tentaikhoan));
                                    danhGiaSanPhamAdapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("idsanpham", String.valueOf(id));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    //Hàm xử lý button Đánh giá
    private void DanhGiaSanPham() {
        btnDanhGiaSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DialogDanhGia();
            }
        });
    }

    //Hàm khởi tạo dialog đánh giá sản phẩm
    private void DialogDanhGia(){
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_danhgiasanpham);
        dialog.setCancelable(false);
        //ánh xạ
        edtDanhGia=(EditText) dialog.findViewById(R.id.editTextDanhGia);
        btnXacNhanDanhGia=(Button) dialog.findViewById(R.id.buttonXacNhanDanhGia);
        btnHuyDanhGia=(Button) dialog.findViewById(R.id.buttonHuyDanhGia);

        btnXacNhanDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String danhgia=edtDanhGia.getText().toString().trim();
                if (danhgia.isEmpty()){
                    Toast.makeText(ChiTietSanPhamActivity.this, "Bạn chưa viết đánh giá sản phẩm này! Vui lòng nhập đánh giá!", Toast.LENGTH_SHORT).show();
                }else {
                    InsertDanhGiaSanPham(Server.duongDanDanhGiaSanPham);
                    dialog.dismiss();
                }
                danhGiaSanPhamAdapter.notifyDataSetChanged();
            }
        });
        dialog.show();
        btnHuyDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    //Hàm insert đánh giá của người dùng lên database
    private void InsertDanhGiaSanPham(String url) {
        RequestQueue requestQueue=Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("Success")){
                            Toast.makeText(ChiTietSanPhamActivity.this, "Cảm ơn bạn đã đánh giá sản phẩm này!", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(ChiTietSanPhamActivity.this, "Lỗi đánh giá!", Toast.LENGTH_SHORT).show();
                            Log.d("Loidanhgia", response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChiTietSanPhamActivity.this, "Server hiện đang gặp lỗi! Bạn vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                        Log.d("errordanhgia", error.toString());
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> param=new HashMap<>();
                param.put("idtaikhoan", String.valueOf(TaiKhoan.idtaikhoan));
                param.put("idsanpham", String.valueOf(sanPham.getIDSanPham()));
                param.put("noidung", edtDanhGia.getText().toString().trim());
                return param;
            }
        };
        requestQueue.add(stringRequest);
        danhGiaSanPhamAdapter.notifyDataSetChanged();

    }

    //Hàm chuyển đến trang mua bán
    private void ChuyenDenTrangMuaBan() {
        btnMuaSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentTrangMuaBan=new Intent();
                intentTrangMuaBan.setAction(Intent.ACTION_VIEW);
                intentTrangMuaBan.setData(Uri.parse(sanPham.getDuongLink()));
                startActivity(intentTrangMuaBan);
            }
        });
    }

    //Hàm lấy thông tin sản phẩm
    private void GetThongTinSanPham() {
        id=0;
        String tenchitiet="";
        String hinhchitiet="";
        String chatlieuchitiet="";
        String thongtinchitiet="";
        int maloaivatdung=0;

        sanPham= (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        id=sanPham.getIDSanPham();
        tenchitiet=sanPham.getTenSanPham();
        hinhchitiet=sanPham.getHinhAnhSanPham();
        chatlieuchitiet=sanPham.getChatLieu();
        thongtinchitiet=sanPham.getThongTinSanPham();
        maloaivatdung=sanPham.getMaLoai();

        txtTenSanPham.setText(tenchitiet);
        Picasso.with(this).load(hinhchitiet).fit().into(imgChiTietSanPham);
        txtChatLieuSanPham.setText(chatlieuchitiet);
        txtThongTinSanPham.setText(thongtinchitiet);

    }

    //Hàm tạo toolbar
    private void ActionToolbar() {
        setSupportActionBar(toolbarChiTietSanPham);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTietSanPham.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Hàm ánh xạ
    private void Init() {
        lvDanhGiaSanPham        =(ListView) findViewById(R.id.listViewDanhGiaSanPham);
        toolbarChiTietSanPham   =(Toolbar) findViewById(R.id.toolbarChiTietSanPham);
        imgChiTietSanPham       =(ImageView) findViewById(R.id.imageViewChiTietSanPham);
        txtTenSanPham           =(TextView) findViewById(R.id.textViewTenSanPham);
        txtChatLieuSanPham      =(TextView) findViewById(R.id.textViewChatLieuSanPham);
        txtThongTinSanPham      =(TextView) findViewById(R.id.textViewThongTinSanPham);
        btnDanhGiaSP            =(Button) findViewById(R.id.buttonDanhGiaSanPham);
        btnMuaSanPham           =(Button) findViewById(R.id.buttonTrangMuaBan);
        danhGiaArrayList=new ArrayList<>();
        danhGiaSanPhamAdapter=new DanhGiaSanPhamAdapter(getApplicationContext(),R.layout.dong_danhgiasanpham,danhGiaArrayList,taiKhoanArrayList);
        lvDanhGiaSanPham.setAdapter(danhGiaSanPhamAdapter);
    }
}
