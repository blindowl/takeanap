package takeanap.layout.com.takeanap.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import takeanap.layout.com.takeanap.fragments.FavoritesFragment;
import takeanap.layout.com.takeanap.fragments.MusicFragment;
import takeanap.layout.com.takeanap.fragments.NatureFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_TABS = 3;

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch(position){
            case 0:
                return MusicFragment.newInstance();
            case 1:
                return NatureFragment.newInstance();
            default:
                return FavoritesFragment.newInstance();
        }
    }

    @Override
    public int getCount() {
        return NUM_TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        if (position == 0){
            return "sons da natureza";
        }
        if (position == 1){
            return "músicas instrumentais";
        }else{
            return "favoritos";
        }
    }
}
