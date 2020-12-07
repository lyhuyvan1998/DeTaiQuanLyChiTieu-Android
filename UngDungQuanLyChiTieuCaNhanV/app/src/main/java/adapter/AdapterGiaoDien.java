package adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import fragment.CaidatFragment;
import fragment.ThongkeFragment;
import fragment.ThuchiFragment;

public class AdapterGiaoDien extends FragmentPagerAdapter {
    String matk;

    public AdapterGiaoDien(FragmentManager fm , String matk) {
        super(fm);
        this.matk = matk;
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new ThuchiFragment(matk);
            case 1:
                return new ThongkeFragment(matk);
            case 2:
                return new CaidatFragment(matk);
        }
        return null;
    }

    @Override
    public int getCount() {
        return 3;
    }
}
