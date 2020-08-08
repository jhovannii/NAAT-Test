package mx.com.naatandroidexam.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import mx.com.naatandroidexam.ui.fragments.AdministracionFragment;
import mx.com.naatandroidexam.ui.fragments.RecargasFragment;
import mx.com.naatandroidexam.ui.fragments.RecaudacionFragment;

public class CustomPagerAdapter extends FragmentPagerAdapter {
    private int tabCount;

    public CustomPagerAdapter(@NonNull FragmentManager fm, int count) {
        super(fm, count);
        this.tabCount = count;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new RecargasFragment();
            case 1:
                return new RecaudacionFragment();
            case 2:
                return new AdministracionFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
