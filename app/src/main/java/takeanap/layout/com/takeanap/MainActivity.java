package takeanap.layout.com.takeanap;


import android.content.ClipData;
import android.content.Intent;
import android.graphics.Typeface;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.api.BooleanResult;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.IOException;

public class MainActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    private Toolbar toolbar;

    public ImageView start;
    public MediaPlayer mediaPlayer;
    public TextView nomeMusica;
    public TextView nomecategoria;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Fonts
        nomeMusica = (TextView) findViewById(R.id.musicNome);
        nomecategoria = (TextView) findViewById(R.id.categoriaNome);
        Typeface robotThin = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        nomeMusica.setTypeface(robotThin);
        nomecategoria.setTypeface(robotThin);


        start = (ImageView) findViewById(R.id.playButton);

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        fetchAudioUrlFromFirebase();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    start.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_play_circle_filled_white_24dp));
                } else {
                    mediaPlayer.start();
                    start.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_pause_circle_filled_white_24dp));
                }
            }

        });

    }

    private void fetchAudioUrlFromFirebase() {
        final FirebaseStorage storage = FirebaseStorage.getInstance();

        StorageReference storageRef = storage.getReferenceFromUrl("gs://take-a-nap-50654.appspot.com").child("ocean.mp3");

        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    final String url = uri.toString();
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.setOnPreparedListener(MainActivity.this);
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.i("TAG", e.getMessage());
            }
        });
    }

    public void onPrepared(MediaPlayer mp) {
        mp.start();
        start.setImageDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.ic_pause_circle_filled_white_24dp));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mPlaylist:
                Intent i = new Intent(MainActivity.this,PlaylistActivity.class);
                this.startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

