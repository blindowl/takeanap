package takeanap.layout.com.takeanap;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ActionMenuView;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;

public class PlayerActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {

    private Toolbar toolbar;
    private ImageView start;
    private String name;
    private String title;
    private String category;
    private MediaPlayer mediaPlayer;
    private TextView titleTextView;
    private TextView categoryTextView;
    private ImageView photoImageView;
    private LinearLayout linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //BG
        linear = (LinearLayout) findViewById(R.id.linerId);
        linear.bringToFront();

        //Toolbar
        toolbar = (Toolbar) findViewById(R.id.toolbarId);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.teste);


        //Fonts
        titleTextView = (TextView) findViewById(R.id.titleId);
        categoryTextView = (TextView) findViewById(R.id.categoryId);
        Typeface robotThin = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Thin.ttf");
        titleTextView.setTypeface(robotThin);
        categoryTextView.setTypeface(robotThin);

        //Extras
        Bundle extra = getIntent().getExtras();

        if (extra != null) {
            name = extra.getString("name");
            title = extra.getString("title");
            category = extra.getString("category");
        }

        start = (ImageView) findViewById(R.id.startId);
        photoImageView = (ImageView) findViewById(R.id.photoId);

        //setTitle
        titleTextView.setText(title);

        //setCategory
        if (category.equals("nature")) {
            categoryTextView.setText("Sons da Natureza");
        } else if (category.equals("music")){
            categoryTextView.setText("Música Instrumental");
        }

        mediaPlayer = new MediaPlayer();

        /*
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {

        }
        */

        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        fetchAudioUrlFromFirebase();
        fetchImageUrlFromFirebase();


        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    mediaPlayer.setLooping(true);
                    start.setImageDrawable(ContextCompat.getDrawable(PlayerActivity.this, R.drawable.ic_action_play));
                } else {
                    mediaPlayer.start();
                    start.setImageDrawable(ContextCompat.getDrawable(PlayerActivity.this, R.drawable.ic_action_pause));
                }
            }
        });
    }

    private void fetchAudioUrlFromFirebase() {
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://take-a-nap-50654.appspot.com").child(name + ".mp3");
        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                try {
                    final String url = uri.toString();
                    mediaPlayer.setDataSource(url);
                    mediaPlayer.setOnPreparedListener(PlayerActivity.this);
                    mediaPlayer.prepareAsync();
                } catch (IOException e) {
                    Log.i("erro", "Erro em puxar o audio");
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

    private void fetchImageUrlFromFirebase() {
        final FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://take-a-nap-50654.appspot.com").child(name + ".png");
        try {
            final File localFile = File.createTempFile("image", "png");
            storageRef.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Drawable photo = Drawable.createFromPath(localFile.getAbsolutePath());
                    photoImageView.setImageDrawable(photo);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    Log.i("erro", "Erro em puxar a imagem");
                }
            });
        } catch (IOException e ) {
            Log.i("erro", "Erro em puxar a imagem 2");
            e.printStackTrace();
        }
    }

    public void onPrepared(MediaPlayer mp) {
        mp.start();
        start.setImageDrawable(ContextCompat.getDrawable(PlayerActivity.this, R.drawable.ic_action_pause));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.playlistId:
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                Intent i = new Intent(PlayerActivity.this,PlaylistActivity.class);
                this.startActivity(i);
                return true;
            case R.id.settingdId:
                Toast.makeText(getApplicationContext(), "Configurações Selecionada", Toast.LENGTH_LONG).show();
                return true;
            case android.R.id.home:
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /*
    @Override
    protected void onStop() {
        //Toast.makeText(getApplicationContext(), "OnStop", Toast.LENGTH_LONG).show();
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
            super.onStop();
        }
    }
    */
}

