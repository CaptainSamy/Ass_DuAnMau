package com.example.ass_duanmau;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ass_duanmau.dao.NguoiDungDao;
import com.example.ass_duanmau.database.DatabaseHelper;
import com.example.ass_duanmau.model.NguoiDung;

public class LoginActivity extends AppCompatActivity {
    private EditText edtUsername;
    private EditText edtPassword;
    private CheckBox cbRemember;
    private Button btnLogin;
    NguoiDungDao nguoiDungDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTitle("ĐĂNG NHẬP");
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        cbRemember = (CheckBox) findViewById(R.id.cbRemember);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        nguoiDungDao = new NguoiDungDao(getApplicationContext());

        NguoiDung user2;
        user2 = nguoiDungDao.getUser("admin");
        if (user2 == null) {
            NguoiDung user3 = new NguoiDung("admin", "admin", "000000001", "Captain Gấu");
            nguoiDungDao.insertNguoiDung(user3);
        }
        checkLogin();

        edtUsername.setText("admin");
        edtPassword.setText("admin");


    }


    public void checkLogin() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if (password.length() < 3 || userName.isEmpty() || password.isEmpty()) {

                    if (userName.isEmpty())
                        edtUsername.setError(getString(R.string.notify_empty_user));

                    if (password.isEmpty())
                        edtPassword.setError(getString(R.string.notify_empty_pass));


                } else {

                    NguoiDung user = nguoiDungDao.getUser(userName);
                    if (user != null && user.getUserName() != null) {
                        if (password.matches(user.getPassword())) {
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            startActivity(intent);
                            finish();
                            Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        } else {

                            Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu chưa chính xác", Toast.LENGTH_SHORT).show();

                        }
                    } else {
                        Toast.makeText(LoginActivity.this, "Bạn chưa có tài khoản", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }



    public void rememberUser(String u, String p, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor edit = pref.edit();
        if (!status) {
            edit.clear();
        } else {
            edit.putString("USERNAME", u);
            edit.putString("PASSWORD", p);
            edit.putBoolean("REMEMBER", status);
        }
        edit.commit();
    }
}
