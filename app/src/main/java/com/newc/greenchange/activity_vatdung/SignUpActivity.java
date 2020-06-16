package com.newc.greenchange.activity_vatdung;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener {
    //Biến toàn cục
    EditText edtNhapTaiKhoan, edtNhapMatKhau, edtNhapLaiMatKhau;
    Button btnTaoTaiKhoan, btnQuayLaiDangNhap;
    String urlInsertTaiKhoan="http://newcmobile.xyz/greenchange/insert_taikhoan.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Init();
        XuLyDangKy();

    }

    private void Init() {
        edtNhapTaiKhoan=(EditText) findViewById(R.id.editTextNhapTaiKhoan);
        edtNhapMatKhau=(EditText) findViewById(R.id.editTextNhapMatKhau);
        edtNhapLaiMatKhau=(EditText) findViewById(R.id.editTextNhapLaiMatKhau);
        btnTaoTaiKhoan=(Button) findViewById(R.id.buttonTaoTaiKhoan);
        btnQuayLaiDangNhap=(Button) findViewById(R.id.buttonQuayLaiDangNhap);
    }

    private void XuLyDangKy() {
        btnTaoTaiKhoan.setOnClickListener(this);
        btnQuayLaiDangNhap.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonTaoTaiKhoan:
                String taikhoan=edtNhapTaiKhoan.getText().toString().trim();
                String matkhau=edtNhapMatKhau.getText().toString().trim();
                String nhaplaimatkhau=edtNhapLaiMatKhau.getText().toString().trim();
                if (taikhoan.isEmpty() || matkhau.isEmpty() || nhaplaimatkhau.isEmpty()){
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin đăng ký", Toast.LENGTH_SHORT).show();
                }
                else if(!edtNhapLaiMatKhau.getText().toString().equals(edtNhapMatKhau.getText().toString())){
                    Toast.makeText(this, "Mật khẩu không giống nhau! Vui lòng nhập lại", Toast.LENGTH_SHORT).show();
                }else {
                    TaoTaiKhoan(urlInsertTaiKhoan);
                }
                break;
            case R.id.buttonQuayLaiDangNhap:
                finish();
                break;
        }
    }

    private void TaoTaiKhoan(String url) {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.trim().equals("Success")){
                            Toast.makeText(SignUpActivity.this, "Tạo tài khoản thành công!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
                        }else {
                            Toast.makeText(SignUpActivity.this, "Lỗi tạo tài khoản", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(SignUpActivity.this, "Quá trình tạo tài khoản xảy ra lỗi! Vui lòng thử lại!", Toast.LENGTH_SHORT).show();
                        Log.d("onErrorResponse", "Lỗi: "+error.toString());
                    }
                }
                ){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("tentaikhoan",edtNhapTaiKhoan.getText().toString().trim());
                params.put("matkhau",edtNhapMatKhau.getText().toString().trim());
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }
}
