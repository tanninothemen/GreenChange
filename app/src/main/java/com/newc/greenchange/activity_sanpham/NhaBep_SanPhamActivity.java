package com.newc.greenchange.activity_sanpham;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
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
import com.newc.greenchange.adapter_sanpham.SanPham_NhaBep_Adapter;
import com.newc.greenchange.model.SanPham;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NhaBep_SanPhamActivity extends AppCompatActivity {

    Toolbar toolbarNhaBep;
    ListView lvSanPhamNhaBep;
    SanPham_NhaBep_Adapter nhaBep_adapter;
    ArrayList<SanPham> nhaBepArrayList;
    int iddogiadung=0;
    int page=1;
    View footerView;
    boolean isLoading=false;
    myHandler handler;
    boolean limitData=false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nha_bep__san_pham);

        Init();
        if (CheckConnection.isNetworkConnected(getApplicationContext())){
            ActionToolBar();
            GetMaLoaiVD();
            GetData(page);
            LoadMoreData();
        }else {
            CheckConnection.connectionErrorMessage(getApplicationContext());
        }
    }

    //Tải thêm dữ liệu cho listview
    private void LoadMoreData() {
        lvSanPhamNhaBep.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(), ChiTietSanPhamActivity.class);
                intent.putExtra("thongtinsanpham", nhaBepArrayList.get(position));
                startActivity(intent);
            }
        });

        lvSanPhamNhaBep.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem+visibleItemCount==totalItemCount && totalItemCount!=0 && isLoading==false && limitData==false){
                    isLoading=true;
                    ThreadData threadData=new ThreadData();
                    threadData.start();
                }
            }
        });
    }

    //Lấy dữ liệu đổ vào danh sách
    private void GetData(int Page) {
        RequestQueue requestQueue= Volley.newRequestQueue(this);
        String url= Server.duongDanSanPham_TheoLoai+String.valueOf(Page);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int idsanpham=0;
                        String tensanpham="";
                        String hinhanhsanpham="";
                        String chatlieu="";
                        String thongtinsanpham="";
                        String duonglink="";
                        int maloai=0;
                        if (response!=null && response.length()!=2){
                            lvSanPhamNhaBep.removeFooterView(footerView);
                            try {
                                JSONArray jsonArray=new JSONArray(response);
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    idsanpham=jsonObject.getInt("IDSanPham");
                                    tensanpham=jsonObject.getString("TenSanPham");
                                    hinhanhsanpham=jsonObject.getString("HinhAnhSanPham");
                                    chatlieu=jsonObject.getString("ChatLieu");
                                    thongtinsanpham=jsonObject.getString("ThongTinSanPham");
                                    duonglink=jsonObject.getString("DuongLink");
                                    maloai=jsonObject.getInt("MaLoai");
                                    nhaBepArrayList.add(new SanPham(idsanpham,tensanpham,hinhanhsanpham,chatlieu,thongtinsanpham,duonglink,maloai));
                                    nhaBep_adapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else {
                            limitData=true;
                            lvSanPhamNhaBep.removeFooterView(footerView);
                            Toast.makeText(getApplicationContext(), "Bạn đã xem hết vật dụng trong loại này", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param=new HashMap<>();
                param.put("maloai",String.valueOf(iddogiadung));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    //Tương tác với toolbar
    private void ActionToolBar() {
        setSupportActionBar(toolbarNhaBep);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarNhaBep.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Lấy mã loại của vật dụng
    private void GetMaLoaiVD() {
        iddogiadung=getIntent().getIntExtra("maloaivd",-1);
        Log.d("giatrimaloaiSanPham",iddogiadung+"");
    }

    private void Init() {
        toolbarNhaBep=(Toolbar) findViewById(R.id.toolbarSPNhaBep);
        lvSanPhamNhaBep=(ListView) findViewById(R.id.listViewSPNhaBep);
        nhaBepArrayList=new ArrayList<>();
        nhaBep_adapter=new SanPham_NhaBep_Adapter(this,R.layout.dong_nhabep,nhaBepArrayList);
        lvSanPhamNhaBep.setAdapter(nhaBep_adapter);
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView=inflater.inflate(R.layout.progressbar_load,null);
        handler=new NhaBep_SanPhamActivity.myHandler();
    }

    public class myHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    lvSanPhamNhaBep.addFooterView(footerView);
                    break;
                case 1:
                    GetData(++page);
                    isLoading=false;
                    break;
            }
        }
    }

    public class ThreadData extends Thread{
        @Override
        public void run() {
            handler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message=handler.obtainMessage(1);
            handler.sendMessage(message);
            super.run();
        }
    }

}
