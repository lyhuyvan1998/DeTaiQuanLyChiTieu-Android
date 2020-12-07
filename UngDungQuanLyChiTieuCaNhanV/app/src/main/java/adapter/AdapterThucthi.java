package adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.logging.Handler;

import model.arrGiaoDien;
import vn.edu.ungdungquanlychitieucanhanv.R;

public class AdapterThucthi extends ArrayAdapter<arrGiaoDien> {
    private Activity a;
    private int id;
    private ArrayList<arrGiaoDien> arr;
    private TextView name , money;
    private Handler h;
    private Animation animation = new AnimationUtils().loadAnimation(getContext(), android.R.anim.slide_in_left);

    public AdapterThucthi(Activity context, int resource, ArrayList<arrGiaoDien> objects) {
        super(context, resource, objects);
        this.a = context;
        this.id = resource;
        this.arr = objects;
    }


    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        LayoutInflater in = a.getLayoutInflater();
        view = in.inflate(id, null);
        name = view.findViewById(R.id.txtGiaodienName);
        money = view.findViewById(R.id.txtGiaodienMoney);
        if(arr.get(position).name.equals("Số dư")) {
            ImageView image = view.findViewById(R.id.imgGiaodienImage);
            image.setImageResource(R.mipmap.calculator_icon);
            money.setBackgroundResource(R.drawable.giaodien_background_money);
            money.setTextColor(Color.WHITE);
        }
        name.setText(arr.get(position).name);
        money.setText("" + arr.get(position).money);

        return view;
    }
}
