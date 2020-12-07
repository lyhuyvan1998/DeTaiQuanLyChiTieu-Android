package adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

import model.arrCaiDat;
import vn.edu.ungdungquanlychitieucanhanv.R;

public class AdapterCaiDat extends ArrayAdapter<arrCaiDat> {
    // fragment Thống ke
        private Activity activity;
        private int id;
        private ArrayList<arrCaiDat> arr;
        TextView name , money;


    public AdapterCaiDat(Activity context, int resource, ArrayList<arrCaiDat> objects) {
        super(context, resource, objects);
        this.activity = context;
        this.id = resource;
        this.arr = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        LayoutInflater inflater = activity.getLayoutInflater();
        view = inflater.inflate(id , null);
        if(arr.size() > 0 && position >=0) {
            name = view.findViewById(R.id.txtThongkeName);
            money = view.findViewById(R.id.txtThongkeMoney);
            name.setText(arr.get(position).tenhom);
            money.setText(arr.get(position).tenkhoan);
        }
        return view;

    }
}
