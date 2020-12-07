package adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;

import fragment.ThongtinFragment;

public class AdapterThongtinFragment extends FragmentPagerAdapter {
    private ArrayList<String> arr;
    private String matk;
    public AdapterThongtinFragment(FragmentManager fm, ArrayList<String> arr, String matk) {
        super(fm);
        this.arr = arr;
        this.matk = matk;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        Fragment fragment = new ThongtinFragment(arr.get(position), matk);
        return fragment;
    }

    @Override
    public int getCount() {
        return arr.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return arr.get(position);
    }
}
