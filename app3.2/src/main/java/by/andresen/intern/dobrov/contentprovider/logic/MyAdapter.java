package by.andresen.intern.dobrov.contentprovider.logic;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import by.andresen.intern.dobrov.contentprovider.R;
import by.andresen.intern.dobrov.contentprovider.entities.Song;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


    private static final String TAG = "MyAdapter";

    private List<Song> songListFromDB;
    private onNoteClickListener onNoteClickListener;
    private Song song;


    public MyAdapter(@NonNull List<Song> songListFromDB) {
        this.songListFromDB = songListFromDB;
    }

    public void setOnNoteClickListener(MyAdapter.onNoteClickListener onNoteClickListener) {
        this.onNoteClickListener = onNoteClickListener;
    }


    interface onNoteClickListener {
        void onNoteClick(int position);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, @NonNull int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {

        return songListFromDB.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView songName;
        private TextView artist;
        private TextView songStyle;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            songName = itemView.findViewById(R.id.song_name);
            artist = itemView.findViewById(R.id.artist);
            songStyle = itemView.findViewById(R.id.style);

            itemView.setOnClickListener(v -> {
                if (onNoteClickListener != null) {
                    onNoteClickListener.onNoteClick(MyViewHolder.this.getAdapterPosition());

                    Log.d(TAG, "onClick: ELEMENT with POS " + MyViewHolder.this.getAdapterPosition() + " WAS PUSHED");
                }
            });

        }
        public void bind(@NonNull int position) {
            Song song = songListFromDB.get(position);

            songName.setText(song.getName());
            artist.setText(song.getArtist());
            songStyle.setText(song.getStyleOfMusic());
        }

    }

}

