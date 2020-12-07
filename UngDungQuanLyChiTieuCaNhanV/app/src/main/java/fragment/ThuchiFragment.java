package fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Calendar;

import adapter.AdapterGridview;
import adapter.AdapterThucthi;
import model.arrGiaoDien;
import model.arrNavigation;
import vn.edu.ungdungquanlychitieucanhanv.GiaodichActivity;
import vn.edu.ungdungquanlychitieucanhanv.R;

public class ThuchiFragment extends Fragment {
    private String matk , ngaythang;
    private Activity a;
    private SQLiteDatabase database;
    private ListView lvThuChi;

    private Calendar today;
    private ArrayList<arrGiaoDien> arrThuChi;
    private AdapterThucthi adapterThucthi;
    private ArrayList<arrNavigation> arrGirdview;

    private GridView gridView;
    private AdapterGridview adapterGridview;

    private int thang , nam;

    public ThuchiFragment(String matk) {
        this.matk = matk;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_thucthi , container,false );
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        a = getActivity();
        database = a.openOrCreateDatabase("data.db" , a.MODE_PRIVATE, null);
        today =  Calendar.getInstance();
        thang = today.get(Calendar.MONTH) + 1;
        nam = today.get(Calendar.YEAR);
        setListView();
        setGridview();
    }

    public void setListView() {
        arrThuChi = new ArrayList<arrGiaoDien>();
        arrThuChi.add(new arrGiaoDien(R.mipmap.gift_box_icon,"Tiền mặt" ,0 ));
        arrThuChi.add(new arrGiaoDien(R.mipmap.gift_box_icon,"Tiền tiết kiệm" ,0));
        arrThuChi.add(new arrGiaoDien(R.mipmap.gift_box_icon,"Thẻ tín dụng" ,0));
        arrThuChi.add(new arrGiaoDien(R.mipmap.gift_box_icon,"Số dư" ,0));
        lvThuChi = a.findViewById(R.id.lvThuchi);
        adapterThucthi = new AdapterThucthi(getActivity(), R.layout.activity_giaodien_item, arrThuChi);
        lvThuChi.setAdapter(adapterThucthi);

    }

    public void setToday() {
        String tuan4 = "= '" + "2" + "'";
        ngaythang = today.get(Calendar.DAY_OF_MONTH) + "/" + thang + "/" + nam;
        setArrayThuChi(ngaythang , tuan4);
    }
    public void setArrayThuChi(String date , String week) {
        for ( int i=0 ; i< 4 ; i++) {
            arrThuChi.get(i).money = 0;
        }
        Cursor c = database.rawQuery("select loaitk, sum(sotien) from tblthuchi " +
                "inner join tblphannhom on tblthuchi.manhom = tblphannhom.manhom " +
                "where ngay like '%" + date + "%' and tuan " + week + " and  mataikhoan = '" + matk + "' group by loaitk", null);
        c.moveToFirst();

        while (c.isAfterLast() == false) {
            if (c.getString(c.getColumnIndex("loaitk")).equals("Tiền mặt")) {
                arrThuChi.get(0).money = c.getInt(c.getColumnIndex("sum(sotien)"));
            } else if (c.getString(c.getColumnIndex("loaitk")).equals("Tiết kiệm")) {
                arrThuChi.get(1).money = c.getInt(c.getColumnIndex("sum(sotien)"));
            } else if (c.getString(c.getColumnIndex("loaitk")).equals("Thẻ tín dụng")) {
                arrThuChi.get(2).money = c.getInt(c.getColumnIndex("sum(sotien)"));
            }
            c.moveToNext();
        }
        arrThuChi.get(3).money = arrThuChi.get(0).money + arrThuChi.get(1).money + arrThuChi.get(2).money;
        adapterThucthi.notifyDataSetChanged();
    }

    public void setGridview() {
        gridView = (GridView) a.findViewById(R.id.gvThuchi);
        arrGirdview = new ArrayList<arrNavigation>();
        arrGirdview.add(new arrNavigation(R.mipmap.deal_icon_red, "Giao dịch"));
        arrGirdview.add(new arrNavigation(R.mipmap.calendar, "Hôm nay"));
        arrGirdview.add(new arrNavigation(R.mipmap.calendar, "Tuần này"));
        arrGirdview.add(new arrNavigation(R.mipmap.calendar, "Tháng này"));
        arrGirdview.add(new arrNavigation(R.mipmap.calendar, "Năm nay"));
        arrGirdview.add(new arrNavigation(R.mipmap.about_us_icon_red, "Giới thiệu"));
        adapterGridview = new AdapterGridview(getActivity(), arrGirdview);
        gridView.setAdapter(adapterGridview);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                eventGridview(position);
            }
        });
    }

    public void eventGridview(int so) {
        switch (so) {
            case 0:
                Intent intent = new Intent(getActivity(), GiaodichActivity.class);
                intent.putExtra("matk", matk);
                startActivity(intent);
                break;
            case 1:
                // thống kê theo hôm nay
                String tuan4 = "= '" + "Không được trống" + "'";
                ngaythang = today.get(Calendar.DAY_OF_MONTH) + "/" + thang + "/" + nam;
                System.out.println("Ngay hom nay: " + ngaythang);
                setArrayThuChi(ngaythang , "= '2'");
                break;
            case 2:
                //// thống kê theo tuần này
                String tuan = "= '" + today.get(Calendar.WEEK_OF_MONTH) + "'";
                System.out.println("tuan  "+tuan);
                ngaythang = thang + "/" + nam;
                System.out.println(ngaythang);
                setArrayThuChi(ngaythang, tuan);
                break;
            case 3:
                //// thống kê theotháng này
                int thangnay = today.get(Calendar.MONTH)+1;
                String thang2 = "='" + thangnay + "'";
                System.out.println("Ca"+ thang2);
                ngaythang = thang + "/" + nam;
                System.out.println(ngaythang);
                setArrayThuChi(ngaythang, "= '2'");
                break;
            case 4:
                // nam này
//                String tuan3 = "= '" + "Không được trống" + "'";
                ngaythang = "" + nam;
                setArrayThuChi(ngaythang, " = '2'");
                break;
            case 5:
                Dialog d = new Dialog(getActivity());
                d.requestWindowFeature(Window.FEATURE_NO_TITLE);
                d.setContentView(R.layout.activity_aboutus);
                d.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                d.show();
                break;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        setToday();
    }
}
