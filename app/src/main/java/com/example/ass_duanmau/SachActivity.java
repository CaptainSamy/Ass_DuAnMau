package com.example.ass_duanmau;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.ass_duanmau.adapter.SachAdapter;
import com.example.ass_duanmau.dao.SachDao;
import com.example.ass_duanmau.model.Sach;

import java.util.ArrayList;
import java.util.List;

public class SachActivity extends AppCompatActivity {
    public  static List<Sach> dsSach = new ArrayList<>();
    ListView lvBooks;
    SachAdapter adapter= null;
    SachDao sachDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sach);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        setTitle("Quản Lý Sách");
        lvBooks=(ListView)findViewById(R.id.customlvsach);
        sachDao= new SachDao(SachActivity.this);
        dsSach = sachDao.getAllSach();

        adapter = new SachAdapter(dsSach,this);
        lvBooks.setAdapter(adapter);

        lvBooks.setTextFilterEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menusach, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemaddsach:
                Intent a = new Intent(SachActivity.this,ThemSachActivity.class);
                startActivity(a);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
