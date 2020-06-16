package com.newc.greenchange.activity_sanpham;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.newc.greenchange.CheckConnection;
import com.newc.greenchange.R;
import com.newc.greenchange.Server;
import com.newc.greenchange.activity_taikhoan.LoginActivity;
import com.newc.greenchange.activity_taikhoan.ThongTinTaiKhoanActivity;
import com.newc.greenchange.activity_vatdung.ChiTietVatDungActivity;
import com.newc.greenchange.activity_vatdung.DoGD_VatDungActivity;
import com.newc.greenchange.activity_vatdung.DuLich_VatDungActivity;
import com.newc.greenchange.activity_vatdung.Main_VatDungActivity;
import com.newc.greenchange.activity_vatdung.NhaBep_VatDungActivity;
import com.newc.greenchange.activity_vatdung.PhongTam_VatDungActivity;
import com.newc.greenchange.activity_vatdung.SucKhoe_VatDungActivity;
import com.newc.greenchange.activity_vatdung.TreEm_VatDungActivity;
import com.newc.greenchange.adapter_sanpham.HienThiSanPhamAdapter;
import com.newc.greenchange.adapter_vatdung.LoaiAdapter;
import com.newc.greenchange.model.Loai;
import com.newc.greenchange.model.SanPham;
import com.newc.greenchange.model.TaiKhoan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main_SanPhamActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    Toolbar toolbarSanPham;
    NavigationView navigationViewSP;
    ListView lvManHinhSanPham;
    GridView grvSanPham;
    ImageButton ibtnXemVatDung;
    ArrayList<Loai> loaiArrayList;
    LoaiAdapter loaiAdapter;
    TaiKhoan taiKhoan;

    ArrayList<SanPham> sanPhamArrayList;
    HienThiSanPhamAdapter sanPhamAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_san_pham);

        Init();
        if (CheckConnection.isNetworkConnected(getApplicationContext())){
            NhanThongTinLogin();
            ActionBar();
            GetDataSanPham(Server.duongDanSanPham);
            GetDataLoai(Server.duongDanLoaiVatDung);
            ChangeVatDungActivity();
            XemChiTietVatDung();
            CatchOnItemMenu();
        }else {
            CheckConnection.connectionErrorMessage(getApplicationContext());
        }

    }

    private void NhanThongTinLogin() {
        Intent intentTaiKhoan=getIntent();
        taiKhoan= (TaiKhoan) intentTaiKhoan.getSerializableExtra("thongtinuser");
    }

    //Chuyển sang màn hình chi tiết sản phẩm
    private void XemChiTietVatDung() {
        grvSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(), ChiTietSanPhamActivity.class);
                intent.putExtra("thongtinsanpham", sanPhamArrayList.get(i));
                startActivity(intent);
            }
        });
    }

    //Chuyển sang màn hình sản phẩm
    private void ChangeVatDungActivity() {
        ibtnXemVatDung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), Main_VatDungActivity.class);
                intent.putExtra("thongtinuser",taiKhoan);
                startActivity(intent);
            }
        });
    }

    //Lấy Loại vật dụng đổ vào thanh menu
    private void GetDataLoai(String url){
        RequestQueue requestQueue=Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i=0;i<response.length();i++){
                            try {
                                JSONObject object=response.getJSONObject(i);
                                loaiArrayList.add(new Loai(
                                        object.getInt("MaLoai"),
                                        object.getString("TenLoai"),
                                        object.getString("HinhAnhLoai")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        loaiArrayList.add(new Loai(7, "Tài khoản", "https://img.icons8.com/dusk/64/000000/guest-male.png"));
                        loaiArrayList.add(new Loai(8, "Đăng xuất", "https://img.icons8.com/cute-clipart/64/000000/exit.png"));
                        loaiAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Có lỗi xảy ra! Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                        Log.d("loixayra", ""+error.getMessage());
                    }
                });
        requestQueue.add(jsonArrayRequest);
    }

    //Lấy vật dụng đổ vào gridview
    private void GetDataSanPham(String url) {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i=0;i<response.length();i++){
                            try {
                                JSONObject object=response.getJSONObject(i);
                                sanPhamArrayList.add(new SanPham(
                                        object.getInt("IDSanPham"),
                                        object.getString("TenSanPham"),
                                        object.getString("HinhAnhSanPham"),
                                        object.getString("ChatLieu"),
                                        object.getString("ThongTinSanPham"),
                                        object.getString("DuongLink"),
                                        object.getInt("MaLoai")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        sanPhamAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Có lỗi xảy ra! Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

    //Hàm xử lý ActionBar
    private void ActionBar() {
        setSupportActionBar(toolbarSanPham);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarSanPham.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbarSanPham.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    //Hàm xử lý sự kiện chọn loại trong menu
    private void CatchOnItemMenu() {
        lvManHinhSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if (CheckConnection.isNetworkConnected(getApplicationContext())){
                            Intent intent=new Intent(getApplicationContext(), Main_SanPhamActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.connectionErrorMessage(getApplicationContext());
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.isNetworkConnected(getApplicationContext())){
                            Intent intent=new Intent(getApplicationContext(), DoGD_SanPhamActivity.class);
                            intent.putExtra("maloaivd",loaiArrayList.get(position).getMaloai());
                            startActivity(intent);
                        }else {
                            CheckConnection.connectionErrorMessage(getApplicationContext());
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.isNetworkConnected(getApplicationContext())){
                            Intent intent=new Intent(getApplicationContext(), NhaBep_SanPhamActivity.class);
                            intent.putExtra("maloaivd",loaiArrayList.get(position).getMaloai());
                            startActivity(intent);
                        }else {
                            CheckConnection.connectionErrorMessage(getApplicationContext());
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.isNetworkConnected(getApplicationContext())){
                            Intent intent=new Intent(getApplicationContext(), PhongTam_SanPhamActivity.class);
                            intent.putExtra("maloaivd",loaiArrayList.get(position).getMaloai());
                            startActivity(intent);
                        }else {
                            CheckConnection.connectionErrorMessage(getApplicationContext());
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConnection.isNetworkConnected(getApplicationContext())){
                            Intent intent=new Intent(getApplicationContext(), DuLich_SanPhamActivity.class);
                            intent.putExtra("maloaivd",loaiArrayList.get(position).getMaloai());
                            startActivity(intent);
                        }else {
                            CheckConnection.connectionErrorMessage(getApplicationContext());
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if (CheckConnection.isNetworkConnected(getApplicationContext())){
                            Intent intent=new Intent(getApplicationContext(), SucKhoe_SanPhamActivity.class);
                            intent.putExtra("maloaivd",loaiArrayList.get(position).getMaloai());
                            startActivity(intent);
                        }else {
                            CheckConnection.connectionErrorMessage(getApplicationContext());
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 6:
                        if (CheckConnection.isNetworkConnected(getApplicationContext())){
                            Intent intent=new Intent(getApplicationContext(), TreEm_SanPhamActivity.class);
                            intent.putExtra("maloaivd",loaiArrayList.get(position).getMaloai());
                            startActivity(intent);
                        }else {
                            CheckConnection.connectionErrorMessage(getApplicationContext());
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 7:
                        if (CheckConnection.isNetworkConnected(getApplicationContext())){
                            NhanThongTinLogin();
                            Intent intent=new Intent(getApplicationContext(),ThongTinTaiKhoanActivity.class);
                            intent.putExtra("thongtinuser",taiKhoan);
                            startActivity(intent);
                        }else {
                            CheckConnection.connectionErrorMessage(getApplicationContext());
                        }
                        break;
                    case 8:
                        Intent intentThoat=new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intentThoat);
                        finish();
                        break;
                }
            }
        });
    }

    //Ánh xạ
    private void Init() {
        navigationViewSP=(NavigationView) findViewById(R.id.navigationViewSanPham);
        lvManHinhSanPham=(ListView) findViewById(R.id.listViewManHinhSanPham);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawerLayoutSanPham);
        grvSanPham=(GridView) findViewById(R.id.gridViewSanPham);
        ibtnXemVatDung=(ImageButton) findViewById(R.id.imageButtonHienThiVatDung);
        toolbarSanPham=(Toolbar) findViewById(R.id.toolbarSanPham);
        //ánh xạ cho phần vật dụng
        sanPhamArrayList=new ArrayList<>();
        sanPhamAdapter=new HienThiSanPhamAdapter(this, R.layout.dong_sanpham,sanPhamArrayList);
        grvSanPham.setAdapter(sanPhamAdapter);
        //ánh xạ cho phần menu loại
        loaiArrayList=new ArrayList<>();
        loaiArrayList.add(new Loai(0, "Trang chủ", "https://img.icons8.com/dusk/64/000000/home.png"));
        loaiAdapter=new LoaiAdapter(this,R.layout.dong_loaivatdung,loaiArrayList);
        lvManHinhSanPham.setAdapter(loaiAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_account,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.menuAccount){
            Intent intent=new Intent(getApplicationContext(), ThongTinTaiKhoanActivity.class);
            intent.putExtra("thongtinuser", taiKhoan);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }


}
