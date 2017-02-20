package takeanap.layout.com.takeanap.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import takeanap.layout.com.takeanap.PlayerActivity;
import takeanap.layout.com.takeanap.PlaylistActivity;
import takeanap.layout.com.takeanap.R;
import takeanap.layout.com.takeanap.adapters.RecyclerViewAdapter;
import takeanap.layout.com.takeanap.domain.Songs;
import takeanap.layout.com.takeanap.interfaces.RecyclerViewOnClickListenerHack;

public class MusicFragment extends Fragment implements RecyclerViewOnClickListenerHack {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    private RecyclerView musicRecyclerView;
    private List<Songs> list;

    public MusicFragment() {

    }

    public static MusicFragment newInstance() {
        MusicFragment fragment = new MusicFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_musicas, container, false);

        musicRecyclerView = (RecyclerView) view.findViewById(R.id.musicRecyclerViewId);
        musicRecyclerView.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();
                RecyclerViewAdapter adapter = (RecyclerViewAdapter) recyclerView.getAdapter();

                if (list.size() == llm.findLastCompletelyVisibleItemPosition() + 1) {
                    List<Songs> listAux = ((PlaylistActivity) getActivity()).getSetMusicList(10);

                    for (int i = 0; i < listAux.size(); i++) {
                        adapter.addListItem(listAux.get(i),list.size());
                    }
                }
            }
        });

        musicRecyclerView.setHasFixedSize(true);

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        musicRecyclerView.setLayoutManager(llm);

        list = ((PlaylistActivity) getActivity()).getSetMusicList(10);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getActivity(), list);
        adapter.setRecyclerViewOnClickListenerHack(this);
        musicRecyclerView.setAdapter(adapter);

        return view;
    }

    public String getName(int position){
        List<Songs> listAux = ((PlaylistActivity) getActivity()).getSetNatureList(10);
        String name = listAux.get(position).getName();
        return name;
    }

    @Override
    public void onClickListener(View view, int position) {
        String name = getName(position);
        Intent intent = new Intent(MusicFragment.this.getActivity(), PlayerActivity.class);
        intent.putExtra("name", name);
        startActivity(intent);
    }

}
