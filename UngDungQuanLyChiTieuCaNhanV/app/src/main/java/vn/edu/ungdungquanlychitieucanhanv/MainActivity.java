package vn.edu.ungdungquanlychitieucanhanv;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {
    private SQLiteDatabase database;
    private SharedPreferences share;
    private LinearLayout layout;
    private EditText user , pass , maso , matkhau1 , matkhau2;
    private ImageView imglogo;
    private Animation animation;
    private CheckBox ghinho;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = openOrCreateDatabase("data.db", MODE_PRIVATE, null);
        share = getSharedPreferences("taikhoan", MODE_PRIVATE);
        createtable();
        setWidget();

    }
    public void setWidget() {
        imglogo = (ImageView) findViewById(R.id.imgLogo);
        animation = AnimationUtils.loadAnimation(this, R.anim.animation_logo);
        imglogo.startAnimation(animation);
        layout = (LinearLayout) findViewById(R.id.layoutDangnhap);
        layout.setVisibility(View.GONE);
        user = (EditText) findViewById(R.id.txtNhaptendangnhapmain);
        pass = (EditText) findViewById(R.id.txtNhapmatkhaumain);
        ghinho = (CheckBox) findViewById(R.id.chkGhinhomain);
        Handler h = new Handler();
        h.postDelayed(new Runnable() {
            @Override
            public void run() {
                imglogo.setVisibility(View.GONE);
                layout.setVisibility(View.VISIBLE);
            }
        }, 2000);
    }
    public void createtable() {
        try {
            database.execSQL("create table if not exists tbltaikhoan(mataikhoan text primary key, tentaikhoan text, matkhau text);");
            database.execSQL("create table if not exists tblphannhom(manhom int primary key, tennhom text, tenkhoan text, " +
                    "mataikhoan text constraint mataikhoan references tbltaikhoan(mataikhoan) on delete cascade);");
            database.execSQL("create table if not exists tblthuchi(mathuchi int primary key, loaitk text, sotien int, ngay date, " +
                    "tuan int, manhom int constraint manhom references tblphannhom(manhom) on delete cascade);");
            database.execSQL("create table if not exists tblgiaodich(magiaodich int primary key, lydo text, trangthai text, gio time, " +
                    "mathuchi int constraint mathuchi references tblthuchi(mathuchi) on delete cascade);");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void dangnhap(View v) {
        animation = AnimationUtils.loadAnimation(this, R.anim.animation_edittext);
        boolean tk = false, mk = true;
        Cursor c = database.rawQuery("select * from tbltaikhoan", null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            if (c.getString(c.getColumnIndex("tentaikhoan")).equals(user.getText().toString())) {
                tk = true;
                if (c.getString(c.getColumnIndex("matkhau")).equals(pass.getText().toString())) {
                    Intent intent = new Intent(this, GiaoDienActivity.class);
                    intent.putExtra("matk", c.getString(c.getColumnIndex("mataikhoan")));
                    startActivityForResult(intent, 2);
                } else {
                    mk = false;
                }
            }
            c.moveToNext();
        }
        if (user.getText().toString().equals("")) {
            user.startAnimation(animation);
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập username", Toast.LENGTH_SHORT).show();
        } else if (pass.getText().toString().equals("")) {
            pass.startAnimation(animation);
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập password", Toast.LENGTH_SHORT).show();
        } else if (tk == false) {
            Toast.makeText(getApplicationContext(), "Tài khoản không tồn tại", Toast.LENGTH_SHORT).show();
            user.startAnimation(animation);
        } else if (mk == false) {
            Toast.makeText(getApplicationContext(), "Không đúng mật khẩu", Toast.LENGTH_SHORT).show();
            pass.startAnimation(animation);
        }
    }
    // xóa trắng
    public void xoaTrang() {
        user.setText("");
        pass.setText("");
    }

    public void quenmatkhau(View v) {
        animation = AnimationUtils.loadAnimation(this, R.anim.animation_edittext);
        xoaTrang();
        final Dialog d = new Dialog(MainActivity.this);
        d.requestWindowFeature(Window.FEATURE_NO_TITLE);
        d.setContentView(R.layout.activity_quenmatkhau);
        d.getWindow().setLayout(ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
        d.show();
//        maso = (EditText) d.findViewById(R.id.txtNhapmasoquen);
//        matkhau1 = (EditText) d.findViewById(R.id.txtNhapmatkhauquen);
//        matkhau2 = (EditText) d.findViewById(R.id.txtNhapmatkhau2quen);
//        Button thaydoi = (Button) d.findViewById(R.id.btnThaydoiquen);
//        thaydoi.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (doimatkhau()) {
//                    d.dismiss();
//                }
//            }
//        });
    }
    public void dangky(View v) {
        xoaTrang();
        Intent intent = new Intent(this, DangkyActivity.class);
        startActivity(intent);
    }

    // ghi nhớ vào local
    public void luutaikhoan() {
        SharedPreferences.Editor edit = share.edit();
        if(ghinho.isChecked()) {
            edit.putString("user", user.getText().toString());
            edit.putString("pass" , pass.getText().toString());
            edit.putBoolean("ghinho", ghinho.isChecked());
        } else {
            edit.clear();
        }
        edit.commit();
    }

    @Override
    protected void onResume() {
        if(share.getBoolean("ghinho" , false)) {
            user.setText(share.getString("user", null));
            pass.setText(share.getString("pass", null));
            dangnhap(null);
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        luutaikhoan();
        super.onPause();
    }
}
