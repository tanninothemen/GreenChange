package com.newc.greenchange.activity_taikhoan;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.newc.greenchange.CheckConnection;
import com.newc.greenchange.R;
import com.newc.greenchange.activity_vatdung.Main_VatDungActivity;
import com.newc.greenchange.model.TaiKhoan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //Biến toàn cục
    ImageButton ibtnLogin, ibtnSignup;
    EditText edtTaiKhoan, edtMatKhau;
    CheckBox ckbRemember;
    SharedPreferences sharedPreferences;
    TaiKhoan taiKhoan;
    String urlLoginNguoiDung="http://newcmobile.xyz/greenchange/getthongtin_user.php";

    //Hàm main
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (CheckConnection.isNetworkConnected(getApplicationContext())){
            Init();
            LuuTaiKhoan();
            ThaoTacDangNhap();
        }else {
            CheckConnection.connectionErrorMessage(getApplicationContext());
        }
    }

    //
    private void LuuTaiKhoan() {
        edtTaiKhoan.setText(sharedPreferences.getString("tentaikhoan",""));
        edtMatKhau.setText(sharedPreferences.getString("matkhau",""));
        ckbRemember.setChecked(sharedPreferences.getBoolean("luu", false));
    }

    private void Init() {
        ibtnLogin=(ImageButton) findViewById(R.id.imageButtonDangNhap);
        ibtnSignup=(ImageButton) findViewById(R.id.imageButtonDangKy);
        edtTaiKhoan=(EditText) findViewById(R.id.editTextTaiKhoan);
        edtMatKhau=(EditText) findViewById(R.id.editTextMatKhau);
        ckbRemember=(CheckBox) findViewById(R.id.checkBoxRemember);
        sharedPreferences=getSharedPreferences("taikhoanlogin", MODE_PRIVATE);
    }

    private void ThaoTacDangNhap(){
        ibtnSignup.setOnClickListener(this);
        ibtnLogin.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.imageButtonDangKy:
                Intent intentSignUpActivity=new Intent(this, SignUpActivity.class);
                startActivity(intentSignUpActivity);
                break;
            case R.id.imageButtonDangNhap:
                String taikhoan=edtTaiKhoan.getText().toString().trim();
                String matkhau=edtMatKhau.getText().toString().trim();
                if(taikhoan.isEmpty()||matkhau.isEmpty()){
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                }else {
                    XuLyDangNhap(urlLoginNguoiDung);
                }
                break;
        }
    }

    private void XuLyDangNhap(String url) {
        final RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int idtaikhoan=0;
                        String tentaikhoan="";
                        String matkhau="";
                        String hoten="";
                        String ngaysinh="";
                        int sodt=0;
                        String email="";
                        String tinh_tp="";
                        if (response!=null && response.length()!=2){
                            try {
                                JSONArray jsonArray=new JSONArray(response);
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    idtaikhoan=jsonObject.getInt("IDTaiKhoan");
                                    tentaikhoan=jsonObject.getString("TenTaiKhoan");
                                    matkhau=jsonObject.getString("MatKhau");
                                    hoten=jsonObject.getString("HoTen");
                                    ngaysinh=jsonObject.getString("NgaySinh");
                                    sodt=jsonObject.getInt("SoDT");
                                    email=jsonObject.getString("Email");
                                    tinh_tp=jsonObject.getString("TinhTP");

                                    taiKhoan=new TaiKhoan(idtaikhoan, tentaikhoan, matkhau, hoten, ngaysinh, sodt, email, tinh_tp);
                                    Intent intent=new Intent(LoginActivity.this, Main_VatDungActivity.class);
                                    intent.putExtra("thongtinuser",taiKhoan);
                                    startActivity(intent);
                                    finish();

                                    if (ckbRemember.isChecked()){
                                        String luutaikhoan=edtTaiKhoan.getText().toString().trim();
                                        String luumatkhau=edtMatKhau.getText().toString().trim();
                                        SharedPreferences.Editor editor=sharedPreferences.edit();
                                        editor.putString("tentaikhoan", luutaikhoan);
                                        editor.putString("matkhau", luumatkhau);
                                        editor.putBoolean("luu", true);
                                        editor.commit();
                                    }else {
                                        SharedPreferences.Editor editor=sharedPreferences.edit();
                                        editor.remove("tentaikhoan");
                                        editor.remove("matkhau");
                                        editor.remove("luu");
                                        editor.commit();
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else {
                            Toast.makeText(LoginActivity.this, "Tên tài khoản hoặc mật khẩu của bạn không chính xác! Vui lòng nhập lại!", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Lỗi đăng nhập", Toast.LENGTH_SHORT).show();
                Log.d("error", error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<>();
                param.put("taikhoanuser", edtTaiKhoan.getText().toString().trim());
                param.put("matkhauuser", edtMatKhau.getText().toString().trim());
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
}
