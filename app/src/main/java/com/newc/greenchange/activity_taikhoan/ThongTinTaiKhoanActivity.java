package com.newc.greenchange.activity_taikhoan;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.newc.greenchange.CheckConnection;
import com.newc.greenchange.R;
import com.newc.greenchange.model.TaiKhoan;

public class ThongTinTaiKhoanActivity extends AppCompatActivity {
    TextView txtTenTaiKhoan, txtMatKhau, txtHoTen, txtNgaySinh,
                txtSoDT, txtEmail, txtTinhTP;
    Button btnChinhSuaThongTin, btnQuayLai;
    TaiKhoan taiKhoan;
    Toolbar toolbarThongTinTaiKhoan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_tai_khoan);

        if (CheckConnection.isNetworkConnected(getApplicationContext())){
            Init();
            ActionToolBar();
            NhanThongTinTaiKhoan();
            ThongTinTaiKhoan();
            XuLyNutThoat();
            XuLyNutChinhSua();
        }else {
            CheckConnection.connectionErrorMessage(getApplicationContext());
        }
    }

    private void XuLyNutChinhSua() {
        btnChinhSuaThongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(), ChinhSuaThongTinActivity.class);
                intent.putExtra("thongtinuser", taiKhoan);
                startActivity(intent);
            }
        });

    }

    private void XuLyNutThoat() {
        btnQuayLai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void NhanThongTinTaiKhoan(){
        Intent intentTaiKhoan=getIntent();
        taiKhoan= (TaiKhoan) intentTaiKhoan.getSerializableExtra("thongtinuser");
    }

    private void ThongTinTaiKhoan() {
        txtTenTaiKhoan.setText("Tên tài khoản: "+taiKhoan.getTentaikhoan());
        txtMatKhau.setText("Mật khẩu: "+taiKhoan.getMatkhau());
        txtHoTen.setText("Tên người dùng: "+taiKhoan.getHoten());
        txtNgaySinh.setText("Ngày sinh: "+taiKhoan.getNgaysinh());
        txtSoDT.setText("Số điện thoại: "+String.valueOf(taiKhoan.getSodt()));
        txtEmail.setText("Email: "+taiKhoan.getEmail());
        txtTinhTP.setText("Tỉnh/TP: "+taiKhoan.getTinhtp());
    }

    private void Init() {
        txtTenTaiKhoan=(TextView) findViewById(R.id.textViewTenTaiKhoan);
        txtMatKhau=(TextView) findViewById(R.id.textViewMatKhau);
        txtHoTen=(TextView) findViewById(R.id.textViewHoTen);
        txtNgaySinh=(TextView) findViewById(R.id.textViewNgaySinh);
        txtSoDT=(TextView) findViewById(R.id.textViewSoDT);
        txtEmail=(TextView) findViewById(R.id.textViewEmail);
        txtTinhTP=(TextView) findViewById(R.id.textViewTinhTP);
        btnChinhSuaThongTin=(Button) findViewById(R.id.buttonChinhSuaTaiKhoan);
        btnQuayLai=(Button) findViewById(R.id.buttonThoatChinhSuaTaiKhoan);
        toolbarThongTinTaiKhoan=(Toolbar) findViewById(R.id.toolbarThongTinTaiKhoan) ;
    }

    private void ActionToolBar() {
        setSupportActionBar(toolbarThongTinTaiKhoan);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarThongTinTaiKhoan.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
