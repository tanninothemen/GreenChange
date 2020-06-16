package com.newc.greenchange.activity_vatdung;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import com.newc.greenchange.activity_sanpham.Main_SanPhamActivity;
import com.newc.greenchange.activity_taikhoan.LoginActivity;
import com.newc.greenchange.activity_taikhoan.ThongTinTaiKhoanActivity;
import com.newc.greenchange.adapter_vatdung.HienThiVatDungAdapter;
import com.newc.greenchange.model.Loai;
import com.newc.greenchange.adapter_vatdung.LoaiAdapter;
import com.newc.greenchange.R;
import com.newc.greenchange.Server;
import com.newc.greenchange.model.TaiKhoan;
import com.newc.greenchange.model.VatDung;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Main_VatDungActivity extends AppCompatActivity {

    //Biến toàn cục
    DrawerLayout drawerLayout;
    Toolbar toolbarTrangChu;
    NavigationView navigationView;
    ListView lvManHinhChinh;
    GridView grvVatDung;
    ImageButton ibtnXemSanPham;
    ArrayList<VatDung> vatDungArrayList;
    HienThiVatDungAdapter vatDungAdapter;
    ArrayList<Loai> loaiArrayList;
    LoaiAdapter loaiAdapter;
    TaiKhoan taiKhoan;

    //Hàm chính
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Init();
        if(CheckConnection.isNetworkConnected(getApplicationContext())){
            NhanThongTinLogin();
            ActionBar();
            GetDataVatDung(Server.duongDanVatDung);
            GetDataLoai(Server.duongDanLoaiVatDung);
            ChangeSanPhamActivity();
            XemChiTietVatDung();
            CatchOnItemMenu();
        }else {
            CheckConnection.connectionErrorMessage(getApplicationContext());
            finish();
        }

    }

    //Ánh Xạ
    private void Init() {
        navigationView=(NavigationView) findViewById(R.id.navigationView);
        lvManHinhChinh=(ListView) findViewById(R.id.listViewManHinhChinh);
        drawerLayout=(DrawerLayout) findViewById(R.id.drawerLayout);
        grvVatDung=(GridView) findViewById(R.id.gridViewVatDung);
        ibtnXemSanPham=(ImageButton) findViewById(R.id.imageButtonHienThiSanPham);
        toolbarTrangChu=(Toolbar) findViewById(R.id.toolbarTrangChu);
        //ánh xạ cho phần vật dụng
        vatDungArrayList=new ArrayList<>();
        vatDungAdapter=new HienThiVatDungAdapter(this, R.layout.dong_vatdung,vatDungArrayList);
        grvVatDung.setAdapter(vatDungAdapter);
        //ánh xạ cho phần loại
        loaiArrayList=new ArrayList<>();
        loaiArrayList.add(new Loai(0, "Trang chủ", "https://img.icons8.com/dusk/64/000000/home.png"));
        loaiAdapter=new LoaiAdapter(this,R.layout.dong_loaivatdung,loaiArrayList);
        lvManHinhChinh.setAdapter(loaiAdapter);
    }

    private void NhanThongTinLogin(){
        Intent intentTaiKhoan=getIntent();
        taiKhoan= (TaiKhoan) intentTaiKhoan.getSerializableExtra("thongtinuser");
    }

    //Khởi tạo menu
    private void ActionBar() {
        setSupportActionBar(toolbarTrangChu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarTrangChu.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbarTrangChu.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
    }

    //Chuyển sang màn hình sản phẩm
    private void ChangeSanPhamActivity() {
        ibtnXemSanPham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NhanThongTinLogin();
                Intent intent=new Intent(Main_VatDungActivity.this, Main_SanPhamActivity.class);
                intent.putExtra("thongtinuser",taiKhoan);
                startActivity(intent);
            }
        });
    }

    //Lấy Vật Dụng để đổ về danh sách
    private void GetDataVatDung(String url){
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest=new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        for (int i=0;i<response.length();i++){
                            try {
                                JSONObject object=response.getJSONObject(i);
                                vatDungArrayList.add(new VatDung(
                                        object.getInt("IDVatDung"),
                                        object.getString("TenVatDung"),
                                        object.getString("HinhAnhVatDung"),
                                        object.getString("ChatLieu"),
                                        object.getString("ThongTinGayHai"),
                                        object.getInt("MaLoai")
                                ));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        vatDungAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(Main_VatDungActivity.this, "Có lỗi xảy ra! Vui lòng thử lại.", Toast.LENGTH_SHORT).show();
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }

    //Chuyển sang màn hình chi tiết vật dụng
    private void XemChiTietVatDung() {
        grvVatDung.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent(getApplicationContext(), ChiTietVatDungActivity.class);
                intent.putExtra("thongtinvatdung", vatDungArrayList.get(i));
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

    //Chọn vật dụng theo loại
    private void CatchOnItemMenu() {
        lvManHinhChinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if (CheckConnection.isNetworkConnected(getApplicationContext())){
                            Intent intent=new Intent(getApplicationContext(), Main_VatDungActivity.class);
                            startActivity(intent);
                        }else {
                            CheckConnection.connectionErrorMessage(getApplicationContext());
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckConnection.isNetworkConnected(getApplicationContext())){
                            Intent intent=new Intent(getApplicationContext(),DoGD_VatDungActivity.class);
                            intent.putExtra("maloaivd",loaiArrayList.get(position).getMaloai());
                            startActivity(intent);
                        }else {
                            CheckConnection.connectionErrorMessage(getApplicationContext());
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckConnection.isNetworkConnected(getApplicationContext())){
                            Intent intent=new Intent(getApplicationContext(),NhaBep_VatDungActivity.class);
                            intent.putExtra("maloaivd",loaiArrayList.get(position).getMaloai());
                            startActivity(intent);
                        }else {
                            CheckConnection.connectionErrorMessage(getApplicationContext());
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckConnection.isNetworkConnected(getApplicationContext())){
                            Intent intent=new Intent(getApplicationContext(),PhongTam_VatDungActivity.class);
                            intent.putExtra("maloaivd",loaiArrayList.get(position).getMaloai());
                            startActivity(intent);
                        }else {
                            CheckConnection.connectionErrorMessage(getApplicationContext());
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckConnection.isNetworkConnected(getApplicationContext())){
                            Intent intent=new Intent(getApplicationContext(),DuLich_VatDungActivity.class);
                            intent.putExtra("maloaivd",loaiArrayList.get(position).getMaloai());
                            startActivity(intent);
                        }else {
                            CheckConnection.connectionErrorMessage(getApplicationContext());
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 5:
                        if (CheckConnection.isNetworkConnected(getApplicationContext())){
                            Intent intent=new Intent(getApplicationContext(),SucKhoe_VatDungActivity.class);
                            intent.putExtra("maloaivd",loaiArrayList.get(position).getMaloai());
                            startActivity(intent);
                        }else {
                            CheckConnection.connectionErrorMessage(getApplicationContext());
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 6:
                        if (CheckConnection.isNetworkConnected(getApplicationContext())){
                            Intent intent=new Intent(getApplicationContext(),TreEm_VatDungActivity.class);
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
