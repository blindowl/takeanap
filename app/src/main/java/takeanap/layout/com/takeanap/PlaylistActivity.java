package takeanap.layout.com.takeanap;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import takeanap.layout.com.takeanap.domain.Songs;
import takeanap.layout.com.takeanap.fragments.FavoritesFragment;
import takeanap.layout.com.takeanap.fragments.MusicFragment;
import takeanap.layout.com.takeanap.fragments.NatureFragment;

public class PlaylistActivity extends AppCompatActivity {

    private Menu menu;
    private Toolbar toolbar;
    private TabLayout tabLayout;

    private ViewPager pager;

    private int[] tabIcons = {
            R.drawable.ic_tab_musics,
            R.drawable.ic_tab_nature,
            R.drawable.ic_tab_favourite};

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
        setupViewPager(pager);

        //TabLayout
        tabLayout = (TabLayout) findViewById(R.id.tabsId);
        tabLayout.setupWithViewPager(pager);
        setupTabIcons();

    }

    private void setupTabIcons() {
        /*
        TextView tabOne = (TextView) LayoutInflater.from(this).inflate(R.layout.tab1, null);
        tabOne.setText("MÚSICAS");
        tabOne.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_musics, 0, 0);
        tabLayout.getTabAt(0).setCustomView(tabOne);

        TextView tabTwo = (TextView) LayoutInflater.from(this).inflate(R.layout.tab1, null);
        tabTwo.setText("NATUREZA");
        tabTwo.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_nature, 0, 0);
        tabLayout.getTabAt(1).setCustomView(tabTwo);

        TextView tabThree = (TextView) LayoutInflater.from(this).inflate(R.layout.tab1, null);
        tabThree.setText("FAVORITOS");
        tabThree.setCompoundDrawablesWithIntrinsicBounds(0, R.drawable.ic_tab_favourite, 0, 0);
        tabLayout.getTabAt(2).setCustomView(tabThree);
        */

        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new MusicFragment(), "MÚSICAS");
        adapter.addFrag(new NatureFragment(), "NATUREZA");
        adapter.addFrag(new FavoritesFragment(), "FAVORITOS");
        viewPager.setAdapter(adapter);
    }


    public static class MyPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
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



