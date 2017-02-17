package takeanap.layout.com.takeanap;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerNonConfig;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import takeanap.layout.com.takeanap.adapters.TabsPagerAdapter;
import takeanap.layout.com.takeanap.domain.Songs;
import takeanap.layout.com.takeanap.fragments.MusicasFragment;
import takeanap.layout.com.takeanap.fragments.NatureFragment;

public class PlaylistActivity extends AppCompatActivity {

    public List<Songs> getSetSongsList(int qtd){
        String[] musicas = {"Quedas d'Água", "Tempestado no Campo", "Chuvas e Trovões", "Canto dos Pássaros",
                "Crepúsculo", "Rio da Selva", "Ondas do Oceano", "Pequenos Sapos Verdes", "Florestas Profundas",
                "Serenata da Meia-Noite"};
        String[] name = {"waterfall", "storm", "rain", "birds", "twilight", "river", "ocean", "froggies", "woods", "midnight"};
        List<Songs> listAux = new ArrayList<>();
        return (listAux);
    }

    public Toolbar toolbar;
    public TabLayout tabLayout;

    FragmentPagerAdapter adapterViewPager;
    public ViewPager pager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Fragments e ViewPager
        pager = (ViewPager) findViewById(R.id.viewpager);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapterViewPager);

        //TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tabLayout.getSelectedTabPosition();
                pager.setCurrentItem(position);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {
        private static int NUM_ITEMS = 2;

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        // Returns total number of pages
        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        // Returns the fragment to display for that page
        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MusicasFragment.newInstance();
                case 1:
                    return NatureFragment.newInstance();
                default:
                    return null;
            }
        }
    }
}



