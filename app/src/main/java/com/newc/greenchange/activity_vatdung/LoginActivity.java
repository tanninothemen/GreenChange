package com.newc.greenchange.activity_vatdung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.newc.greenchange.R;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //Biến toàn cục
    ImageButton ibtnLogin, ibtnSignup;
    EditText edtTaiKhoan, edtMatKhau;
    String urlLoginNguoiDung="http://newcmobile.xyz/greenchange/login_taikhoan.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Init();
        ThaoTacDangNhap();
    }

    private void Init() {
        ibtnLogin=(ImageButton) findViewById(R.id.imageButtonDangNhap);
        ibtnSignup=(ImageButton) findViewById(R.id.imageButtonDangKy);
        edtTaiKhoan=(EditText) findViewById(R.id.editTextTaiKhoan);
        edtMatKhau=(EditText) findViewById(R.id.editTextMatKhau);
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
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("Success")){
                            startActivity(new Intent(LoginActivity.this, Main_VatDungActivity.class));
                            finish();
                        }else {
                            Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu của bạn không hợp lệ", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "Lỗi đăng nhập! Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                Log.d("onErrorResponse", "Lỗi: " + error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("tentaikhoan", edtTaiKhoan.getText().toString().trim());
                params.put("matkhau", edtMatKhau.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
