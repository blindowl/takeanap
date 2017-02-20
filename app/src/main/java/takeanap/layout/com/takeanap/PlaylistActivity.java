package takeanap.layout.com.takeanap;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import java.util.ArrayList;
import java.util.List;
import takeanap.layout.com.takeanap.domain.Songs;
import takeanap.layout.com.takeanap.fragments.MusicFragment;
import takeanap.layout.com.takeanap.fragments.NatureFragment;

public class PlaylistActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;

    FragmentPagerAdapter adapterViewPager;
    private ViewPager pager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbarId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Fragments & ViewPager
        pager = (ViewPager) findViewById(R.id.viewpagerId);
        adapterViewPager = new MyPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(adapterViewPager);

        //TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabsId);
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

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return MusicFragment.newInstance();
                case 1:
                    return NatureFragment.newInstance();
                default:
                    return null;
            }
        }
    }

    public List<Songs> getSetNatureList(int qtd){
        String[] titles = {"Quedas d'Água", "Tempestado no Campo", "Chuvas e Trovões", "Canto dos Pássaros",
                "Crepúsculo", "Rio da Selva", "Ondas do Oceano", "Pequenos Sapos Verdes", "Florestas Profundas",
                "Serenata da Meia-Noite"};
        String[] phrases = {"frase1", "frase2", "frase3", "frase4", "frase5", "frase6", "frase7", "frase8", "frase9", "frase10"};
        String[] names = {"waterfall", "storm", "rain", "birds", "twilight", "river", "ocean", "froggies", "woods", "midnight"};
        List<Songs> listAux = new ArrayList<>();

        for(int i = 0; i < qtd; i++){
            Songs s = new Songs(titles[i % titles.length], phrases[i % phrases.length], names[i % names.length]);
            listAux.add(s);
        }
        return (listAux);
    }

    public List<Songs> getSetMusicList(int qtd){
        String[] titles = {"Música Tibetana", "Música Hawaiiana", "Música Indiana", "Música Chinesa",
                "Música Tailandesa", "Piano", "Harpa", "Violino", "Violão", "Flauta"};
        String[] phrases = {"frase1", "frase2", "frase3", "frase4", "frase5", "frase6", "frase7", "frase8", "frase9", "frase10"};
        String [] names = {"tibet", "hawaii", "india", "china", "thailand", "piano", "harp", "violin", "guitar", "flute"};
        List<Songs> listAux = new ArrayList<>();

        for(int i = 0; i < qtd; i++){
            Songs s = new Songs(titles[i % titles.length], phrases[i % phrases.length], names[i % names.length]);
            listAux.add(s);
        }
        return (listAux);
    }

}



