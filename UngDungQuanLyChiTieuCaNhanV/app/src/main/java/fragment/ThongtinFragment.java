package fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import adapter.AdapterThongtin;
import model.arrThongTin;
import vn.edu.ungdungquanlychitieucanhanv.R;

public class ThongtinFragment extends Fragment {
    private SQLiteDatabase data;
    private String ngay, matk;
    private Activity a;
    private ArrayList<arrThongTin> arr;
    private ListView lv;
    private AdapterThongtin adapterThongtin;


    public ThongtinFragment(String ngay, String matk) {
        this.ngay = ngay;
        this.matk = matk;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_thongtin_listview, container, false);
        lv = (ListView) v.findViewById(R.id.lvThongtin);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        a = getActivity();
        data = a.openOrCreateDatabase("data.db", a.MODE_PRIVATE, null);
        arr = new ArrayList<arrThongTin>();
        adapterThongtin = new AdapterThongtin(getActivity(), R.layout.activity_thongtin_item, arr);
        lv.setAdapter(adapterThongtin);
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                deleteArr(position);
                return false;
            }
        });
        setArr();
    }

    public void deleteArr(final int so) {
        AlertDialog.Builder d = new AlertDialog.Builder(getActivity());
        d.setTitle(arr.get(so).nhom);
        d.setMessage("Bạn muốn xóa giao dịch này?");
        d.setNegativeButton("Có", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        d.setPositiveButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                data.rawQuery("delete from tblgiaodich where magiaodich = '" + arr.get(so).ma + "'", null).moveToFirst();
                data.rawQuery("delete from tblthuchi where mathuchi = '" + arr.get(so).ma + "'", null).moveToFirst();
                setArr();
            }
        });
        d.show();
    }

    public void setArr() {
        arr.clear();
        Cursor c = data.rawQuery("select tblthuchi.mathuchi, ngay, gio, sotien, tennhom, loaitk from tblgiaodich " +
                "inner join tblthuchi on tblgiaodich.mathuchi = tblthuchi.mathuchi " +
                "inner join tblphannhom on tblthuchi.manhom = tblphannhom.manhom " +
                "where ngay = '" + ngay + "' and mataikhoan = '" + matk + "'", null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            arr.add(new arrThongTin(c.getInt(c.getColumnIndex("tblthuchi.mathuchi")), c.getString(c.getColumnIndex("ngay")), c.getString(c.getColumnIndex("gio")), c.getInt(c.getColumnIndex("sotien")), c.getString(c.getColumnIndex("tennhom")), c.getString(c.getColumnIndex("loaitk"))));
            c.moveToNext();
        }
        adapterThongtin.notifyDataSetChanged();
    }

}
