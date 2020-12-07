package adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;


import model.arrThongTin;
import vn.edu.ungdungquanlychitieucanhanv.R;

public class AdapterThongtin extends ArrayAdapter<arrThongTin> {
    private Activity a;
    private int id;
    private ArrayList<arrThongTin> arr;
    private TextView date, money, nhom, taikhoan;

    public AdapterThongtin(Activity context, int resource, ArrayList<arrThongTin> objects) {
        super(context, resource, objects);
        this.a = context;
        this.id = resource;
        this.arr = objects;
    }
    //  getView

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        LayoutInflater in = a.getLayoutInflater();
        view = in.inflate(id , null);
        if(arr.size() > 0 && position >=0) {
            date = view.findViewById(R.id.txtThongtinDate);
            money = view.findViewById(R.id.txtThongtinMoney);
            nhom = view.findViewById(R.id.txtThongtinNhom);
            taikhoan = view.findViewById(R.id.txtThongtinTaikhoan);
            date.setText(arr.get(position).date+ " " + arr.get(position).time);
            money.setText("" + arr.get(position).money);
            nhom.setText(arr.get(position).nhom);
            taikhoan.setText(arr.get(position).taikhoan);
        }
        return view;
    }
}
