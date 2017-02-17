package takeanap.layout.com.takeanap.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import takeanap.layout.com.takeanap.fragments.MusicasFragment;
import takeanap.layout.com.takeanap.fragments.NatureFragment;

/**
 * Created by WorkOnly on 17/02/2017.
 */

public class TabsPagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_TABS = 3;

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return MusicasFragment.newInstance();
            default:
                return NatureFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0){
            return "Sons da Natureza";
        }

        return "Músicas";
    }
}
