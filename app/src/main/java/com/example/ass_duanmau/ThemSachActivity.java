package com.example.ass_duanmau;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.ass_duanmau.dao.SachDao;
import com.example.ass_duanmau.dao.TheLoaiDao;
import com.example.ass_duanmau.model.Sach;
import com.example.ass_duanmau.model.Theloai;

import java.util.ArrayList;
import java.util.List;

public class ThemSachActivity extends AppCompatActivity {
    SachDao sachDAO;
    TheLoaiDao theLoaiDAO;
    Spinner spnTheLoai;
    EditText edMaSach, edTenSach, edNXB, edTacGia, edGiaBia, edSoLuong;
    String maTheLoai = "";
    List<Theloai> listTheLoai = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_sach);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setTitle("THÊM SÁCH");
        spnTheLoai = (Spinner) findViewById(R.id.spTheLoai_ThemSach);
        getTheLoai();
        edMaSach = (EditText) findViewById(R.id.edMaSach_ThemSach);
        edTenSach = (EditText) findViewById(R.id.edTenSach_ThemSach);
        edNXB = (EditText) findViewById(R.id.edNXB_ThemSach);
        edTacGia = (EditText) findViewById(R.id.edTacGia_ThemSach);
        edGiaBia = (EditText) findViewById(R.id.edGia_ThemSach);
        edSoLuong = (EditText)findViewById(R.id.edSoluong_ThemSach);

        spnTheLoai.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                maTheLoai = listTheLoai.get(spnTheLoai.getSelectedItemPosition()).getMaTheLoai();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void showSpinner(View view){
        sachDAO = new SachDao(ThemSachActivity.this);
        sachDAO.getAllSach();
    }
    public void getTheLoai(){
        theLoaiDAO = new TheLoaiDao(ThemSachActivity.this);
        listTheLoai = theLoaiDAO.getAllTheLoai();
        ArrayAdapter<Theloai> dataAdapter = new ArrayAdapter<Theloai>(this,
                android.R.layout.simple_spinner_item, listTheLoai);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnTheLoai.setAdapter(dataAdapter);
    }

    public void addBook(View view){
        sachDAO = new SachDao(ThemSachActivity.this);
        Sach sach = new
                Sach(edMaSach.getText().toString(),maTheLoai,edTenSach.getText().toString(),
                edTacGia.getText().toString(),edNXB.getText().toString(),
                Double.parseDouble(edGiaBia.getText().toString()),Integer.parseInt(edSoLuong.getText
                ().toString()));
        try {
            if (sachDAO.inserSach(sach) > 0) {
                Toast.makeText(getApplicationContext(), "Thêm thành công",
                        Toast.LENGTH_SHORT).show();
                Intent a = new Intent(ThemSachActivity.this,SachActivity.class);
                startActivity(a);
            } else {
                Toast.makeText(getApplicationContext(), "Thêm thất bại",
                        Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Log.e("Error", ex.toString());
        }
    }
    public void CancelSach(View view){
        finish();
    }

    public int checkPositionTheLoai(String strTheLoai) {
        for (int i = 0; i < listTheLoai.size(); i++) {
            if (strTheLoai.equals(listTheLoai.get(i).getMaTheLoai())) {
                return 1;
            }
        }
        return 0;
    }
}
