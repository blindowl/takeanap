package takeanap.layout.com.takeanap.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import takeanap.layout.com.takeanap.R;
import java.util.List;
import takeanap.layout.com.takeanap.domain.Songs;
import takeanap.layout.com.takeanap.interfaces.RecyclerViewOnClickListenerHack;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {
    private List<Songs> list;
    private LayoutInflater layoutInflater;
    private RecyclerViewOnClickListenerHack recyclerViewOnClickListenerHack;

    public RecyclerViewAdapter (Context c, List<Songs> l) {
        list = l;
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.i("LOG", "onCreateViewHolder()");
        View v = layoutInflater.inflate(R.layout.item, parent, false);
        MyViewHolder mvh = new MyViewHolder(v);
        return mvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Log.i("LOG", "onBindViewHolder()");
        holder.title.setText(list.get(position).getTitle());
        holder.phrase.setText(list.get(position).getPhrase());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void addListItem(Songs s, int position){
        list.add(s);
        notifyItemInserted(position);
    }

    public void removeListItem(int position){
        list.remove(position);
        notifyItemRemoved(position);
    }

    public void setRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack r){
        recyclerViewOnClickListenerHack = r;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView icon;
        public TextView title;
        public TextView phrase;

        public MyViewHolder(View itemView) {
            super(itemView);

            icon = (ImageView) itemView.findViewById(R.id.iconId);
            title = (TextView) itemView.findViewById(R.id.titleId);
            phrase = (TextView) itemView.findViewById(R.id.phraseId);

            itemView.setOnClickListener(this);
        }

        public void onClick(View v) {
            if(recyclerViewOnClickListenerHack != null){
                recyclerViewOnClickListenerHack.onClickListener(v, getPosition());
            }
        }

    }
}
