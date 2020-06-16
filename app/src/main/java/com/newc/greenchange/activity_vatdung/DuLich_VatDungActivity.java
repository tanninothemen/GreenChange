package com.newc.greenchange.activity_vatdung;

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
import com.newc.greenchange.adapter_vatdung.VatDung_DuLich_Adapter;
import com.newc.greenchange.model.VatDung;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DuLich_VatDungActivity extends AppCompatActivity {
    Toolbar toolbarDuLich;
    ListView lvVatDungDuLich;
    VatDung_DuLich_Adapter duLich_adapter;
    ArrayList<VatDung> duLichArrayList;
    int iddulich=0;
    int page=1;
    View footerView;
    boolean isLoading=false;
    myHandler handler;
    boolean limitData=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_du_lich__vat_dung);
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

    private void LoadMoreData() {
        lvVatDungDuLich.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getApplicationContext(), ChiTietVatDungActivity.class);
                intent.putExtra("thongtinvatdung", duLichArrayList.get(position));
                startActivity(intent);
            }
        });

        lvVatDungDuLich.setOnScrollListener(new AbsListView.OnScrollListener() {
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
        String url= Server.duongDanVatDung_TheoLoai+String.valueOf(Page);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        int idvatdung=0;
                        String tenvatdung="";
                        String hinhanhvatdung="";
                        String chatlieu="";
                        String thongtingayhai="";
                        int maloai=0;
                        if (response!=null && response.length()!=2){
                            lvVatDungDuLich.removeFooterView(footerView);
                            try {
                                JSONArray jsonArray=new JSONArray(response);
                                for (int i=0;i<jsonArray.length();i++){
                                    JSONObject jsonObject=jsonArray.getJSONObject(i);
                                    idvatdung=jsonObject.getInt("IDVatDung");
                                    tenvatdung=jsonObject.getString("TenVatDung");
                                    hinhanhvatdung=jsonObject.getString("HinhAnhVatDung");
                                    chatlieu=jsonObject.getString("ChatLieu");
                                    thongtingayhai=jsonObject.getString("ThongTinGayHai");
                                    maloai=jsonObject.getInt("MaLoai");
                                    duLichArrayList.add(new VatDung(idvatdung,tenvatdung,hinhanhvatdung,chatlieu,thongtingayhai,maloai));
                                    duLich_adapter.notifyDataSetChanged();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else {
                            limitData=true;
                            lvVatDungDuLich.removeFooterView(footerView);
                            Toast.makeText(DuLich_VatDungActivity.this, "Bạn đã xem hết vật dụng trong loại này", Toast.LENGTH_SHORT).show();
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
                param.put("maloai",String.valueOf(iddulich));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    //Tương tác với toolbar
    private void ActionToolBar() {
        setSupportActionBar(toolbarDuLich);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarDuLich.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //Lấy mã loại của vật dụng
    private void GetMaLoaiVD() {
        iddulich=getIntent().getIntExtra("maloaivd",-1);
        Log.d("giatrimaloaivatdung",iddulich+"");
    }

    private void Init() {
        toolbarDuLich=(Toolbar) findViewById(R.id.toolbarVDDuLich);
        lvVatDungDuLich=(ListView) findViewById(R.id.listViewVDDuLich);
        duLichArrayList=new ArrayList<>();
        duLich_adapter=new VatDung_DuLich_Adapter(this,R.layout.dong_dulich,duLichArrayList);
        lvVatDungDuLich.setAdapter(duLich_adapter);
        LayoutInflater inflater= (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerView=inflater.inflate(R.layout.progressbar_load,null);
        handler=new myHandler();
    }

    public class myHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 0:
                    lvVatDungDuLich.addFooterView(footerView);
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
