package com.newc.greenchange.activity_taikhoan;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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
import com.newc.greenchange.Server;
import com.newc.greenchange.activity_taikhoan.ThongTinTaiKhoanActivity;
import com.newc.greenchange.activity_vatdung.Main_VatDungActivity;
import com.newc.greenchange.model.TaiKhoan;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class ChinhSuaThongTinActivity extends AppCompatActivity {

    EditText edtTaiKhoanUpdate, edtMatKhauUpdate, edtHoTenUpdate,
                edtNgaySinhUpdate, edtSoDTUpdate, edtEmailUpdate,
                    edtTinhTPUpdate;
    Button btnXacNhanChinhSua, btnHuyChinhSua;
    Toolbar toolbarChinhSuaThongTin;
    TaiKhoan taiKhoan;
    int idtaikhoan=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chinh_sua_thong_tin);
        if (CheckConnection.isNetworkConnected(this)){
            Init();
            ActionToolBar();
            NhanThongTinTaiKhoan();
            GanThongTinTaiKhoanMacDinh();
            SelectDate();
            XuLyXacNhanChinhSua();
        }else {
            CheckConnection.connectionErrorMessage(this);
        }
    }

    private void NhanThongTinTaiKhoan(){
        Intent intentTaiKhoan=getIntent();
        taiKhoan= (TaiKhoan) intentTaiKhoan.getSerializableExtra("thongtinuser");
    }

    private void GanThongTinTaiKhoanMacDinh(){
        idtaikhoan=taiKhoan.getIdtaikhoan();
        edtTaiKhoanUpdate.setText(taiKhoan.getTentaikhoan());
        edtMatKhauUpdate.setText(taiKhoan.getMatkhau());
        edtHoTenUpdate.setText(taiKhoan.getHoten());
        edtNgaySinhUpdate.setText(taiKhoan.getNgaysinh());
        edtSoDTUpdate.setText(String.valueOf(taiKhoan.getSodt()));
        edtEmailUpdate.setText(taiKhoan.getEmail());
        edtTinhTPUpdate.setText(taiKhoan.getTinhtp());
    }

    private void XuLyXacNhanChinhSua() {
        btnXacNhanChinhSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tentaikhoanupdate=edtTaiKhoanUpdate.getText().toString().trim();
                String matkhauupdate=edtMatKhauUpdate.getText().toString().trim();
                if (tentaikhoanupdate.equals("") || matkhauupdate.equals("")){
                    Toast.makeText(ChinhSuaThongTinActivity.this, "Bạn không được để trống tài khoản và mật khẩu!", Toast.LENGTH_SHORT).show();
                }
                XuLyChinhSua(Server.duongDanCapNhatThongTinTaiKhoan);
            }
        });
    }

    private void XuLyChinhSua(String url) {
        RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.trim().equals("Success")){
                            Toast.makeText(ChinhSuaThongTinActivity.this, "Cập nhật thông tin tài khoản thành công", Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getApplicationContext(), ThongTinTaiKhoanActivity.class);
                            intent.putExtra("thongtinuser",taiKhoan);
                            startActivity(intent);
                        }else {
                            Toast.makeText(ChinhSuaThongTinActivity.this, "Cập nhật thông tin bị lỗi! Vui lòng thử lại sau!", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(ChinhSuaThongTinActivity.this, "Lỗi hệ thống.", Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<>();
                param.put("idtaikhoan", String.valueOf(idtaikhoan));
                param.put("tentaikhoan", edtTaiKhoanUpdate.getText().toString().trim());
                param.put("matkhau", edtMatKhauUpdate.getText().toString().trim());
                param.put("hoten", edtHoTenUpdate.getText().toString().trim());
                param.put("ngaysinh", edtNgaySinhUpdate.getText().toString().trim());
                param.put("sodt", edtSoDTUpdate.getText().toString().trim());
                param.put("email", edtEmailUpdate.getText().toString().trim());
                param.put("tinh_tp", edtTinhTPUpdate.getText().toString().trim());
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void SetDatePickerIntoEditText(){
        final Calendar calendar=Calendar.getInstance();
        int ngay=calendar.get(Calendar.DATE);
        int thang=calendar.get(Calendar.MONTH);
        int nam=calendar.get(Calendar.YEAR);
        DatePickerDialog pickerDialog=new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        calendar.set(i,i1,i2);
                        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy/MM/dd");
                        edtNgaySinhUpdate.setText(simpleDateFormat.format(calendar.getTime()));
                    }
                },nam,thang,ngay);
        pickerDialog.show();
    }

    private void SelectDate(){
        edtNgaySinhUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SetDatePickerIntoEditText();
            }
        });
    }

    private void Init() {
        toolbarChinhSuaThongTin=(Toolbar) findViewById(R.id.toolbarChinhSuaTaiKhoan);
        btnXacNhanChinhSua=(Button) findViewById(R.id.buttonXacNhanChinhSua);
        btnHuyChinhSua=(Button) findViewById(R.id.buttonHuyChinhSua);
        edtTaiKhoanUpdate=(EditText) findViewById(R.id.editTextTaiKhoanUpdate);
        edtMatKhauUpdate=(EditText) findViewById(R.id.editTextMatKhauUpdate);
        edtHoTenUpdate=(EditText) findViewById(R.id.editTextHoTenUpdate);
        edtNgaySinhUpdate=(EditText) findViewById(R.id.editTextNgaySinhUpdate);
        edtSoDTUpdate=(EditText) findViewById(R.id.editTextSoDTUpdate);
        edtEmailUpdate=(EditText) findViewById(R.id.editTextEmailUpdate);
        edtTinhTPUpdate=(EditText) findViewById(R.id.editTextTinhTPUpdate);
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarChinhSuaThongTin);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChinhSuaThongTin.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
