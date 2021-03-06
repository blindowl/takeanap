package takeanap.layout.com.takeanap;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.icu.text.UnicodeSetSpanner;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.util.List;

import takeanap.layout.com.takeanap.domain.Songs;

public class PlayerActivity extends AppCompatActivity implements MediaPlayer.OnPreparedListener {


    //Icons
    private ImageView start;
    private ImageView next;
    private ImageView previous;
    private ImageView timer;

    //Extras
    private int position;
    private String name;
    private String title;
    private String category;

    //Infos
    private MediaPlayer mediaPlayer;
    private TextView titleTextView;
    private TextView categoryTextView;
    private ImageView photoImageView;

    //Layout
    private Toolbar toolbar;
    private LinearLayout linear;

    //Activity
    public PlaylistActivity playlistActivity;

    //Timer
    private int timeConvert = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

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
            position = extra.getInt("position");
            name = extra.getString("name");
            title = extra.getString("title");
            category = extra.getString("category");
        }

        start = (ImageView) findViewById(R.id.startId);
        next = (ImageView) findViewById(R.id.nextId);
        previous = (ImageView) findViewById(R.id.previousId);
        photoImageView = (ImageView) findViewById(R.id.photoId);
        timer = (ImageView) findViewById(R.id.timerId);

        //setTitle
        titleTextView.setText(title);

        //setCategory
        if (category.equals("nature")) {
            categoryTextView.setText("Sons da Natureza");
        } else if (category.equals("music")) {
            categoryTextView.setText("Música Instrumental");
        }

        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        fetchImageUrlFromFirebase();
        fetchAudioUrlFromFirebase();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.pause();
                    start.setImageDrawable(ContextCompat.getDrawable(PlayerActivity.this, R.drawable.ic_action_play));
                } else {
                    mediaPlayer.start();
                    start.setImageDrawable(ContextCompat.getDrawable(PlayerActivity.this, R.drawable.ic_action_pause));
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category.equals("nature")) {
                    if (position > 0) {
                        setMediaPlayer();
                        position--;
                        titleTextView.setText(getNatureTitle(position));
                        name = getNatureName(position);
                        mediaPlayer = new MediaPlayer();
                        fetchImageUrlFromFirebase();
                        fetchAudioUrlFromFirebase();
                    } else if (position == 0) {
                        setMediaPlayer();
                        position = 9;
                        titleTextView.setText(getNatureTitle(position));
                        name = getNatureName(position);
                        mediaPlayer = new MediaPlayer();
                        fetchImageUrlFromFirebase();
                        fetchAudioUrlFromFirebase();
                    }
                } else if (category.equals("music")) {
                        if (position > 0) {
                            setMediaPlayer();
                            position--;
                            titleTextView.setText(getMusicTitle(position));
                            name = getMusicName(position);
                            mediaPlayer = new MediaPlayer();
                            fetchImageUrlFromFirebase();
                            fetchAudioUrlFromFirebase();
                        } else if (position == 0) {
                            setMediaPlayer();
                            position = 9;
                            titleTextView.setText(getMusicTitle(position));
                            name = getMusicName(position);
                            mediaPlayer = new MediaPlayer();
                            fetchImageUrlFromFirebase();
                            fetchAudioUrlFromFirebase();
                        }
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (category.equals("nature")) {
                    if (position < 9) {
                        setMediaPlayer();
                        position++;
                        titleTextView.setText(getNatureTitle(position));
                        name = getNatureName(position);
                        mediaPlayer = new MediaPlayer();
                        fetchImageUrlFromFirebase();
                        fetchAudioUrlFromFirebase();
                    } else if (position == 9) {
                        setMediaPlayer();
                        position = 0;
                        titleTextView.setText(getNatureTitle(position));
                        name = getNatureName(position);
                        mediaPlayer = new MediaPlayer();
                        fetchImageUrlFromFirebase();
                        fetchAudioUrlFromFirebase();
                        ;
                    }
                } else if (category.equals("music")) {
                    if (position < 9) {
                        setMediaPlayer();
                        position++;
                        titleTextView.setText(getMusicTitle(position));
                        name = getMusicName(position);
                        mediaPlayer = new MediaPlayer();
                        fetchImageUrlFromFirebase();
                        fetchAudioUrlFromFirebase();
                    } else if (position == 9) {
                        setMediaPlayer();
                        position = 0;
                        titleTextView.setText(getMusicTitle(position));
                        name = getMusicName(position);
                        mediaPlayer = new MediaPlayer();
                        fetchImageUrlFromFirebase();
                        fetchAudioUrlFromFirebase();
                    }
                }
            }
        });

        timer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertTimePicker();
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
                    mediaPlayer.setLooping(true);
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

                }
            });
        } catch (IOException e) {
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
                setMediaPlayer();
                Intent i = new Intent(PlayerActivity.this, PlaylistActivity.class);
                this.startActivity(i);
                return true;
            case R.id.settingdId:
                Toast.makeText(getApplicationContext(), "Configurações", Toast.LENGTH_LONG).show();
                return true;
            case android.R.id.home:
                setMediaPlayer();
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public String getNatureName(int position) {
        List<Songs> listAux = playlistActivity.getSetNatureList(10);
        name = listAux.get(position).getName();
        return name;
    }

    public String getNatureTitle(int position) {
        List<Songs> listAux = playlistActivity.getSetNatureList(10);
        String title = listAux.get(position).getTitle();
        return title;
    }

    public String getMusicName(int position) {
        List<Songs> listAux = playlistActivity.getSetMusicList(10);
        name = listAux.get(position).getName();
        return name;
    }

    public String getMusicTitle(int position) {
        List<Songs> listAux = playlistActivity.getSetMusicList(10);
        String title = listAux.get(position).getTitle();
        return title;
    }

    public void setMediaPlayer() {
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    public void alertTimePicker() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.time_picker, null, false);

        // the time picker on the alert dialog, this is how to get the value
        final TimePicker myTimePicker = (TimePicker) view
                .findViewById(R.id.myTimePicker);
        myTimePicker.setIs24HourView(true);

        // the alert dialog
        new AlertDialog.Builder(PlayerActivity.this).setView(view)
                .setTitle("Escolha o tempo para a música")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @TargetApi(11)
                    public void onClick(DialogInterface dialog, int id) {

                        String HourText = myTimePicker.getCurrentHour()
                                .toString();
                        int valueHour = Integer.parseInt(HourText);

                        String MinuteText = myTimePicker
                                .getCurrentMinute().toString();
                        int valueMinute = Integer.parseInt(MinuteText);

                        dialog.cancel();
                        timeConvert = valueHour * 3600000 + valueMinute * 60000;

                        setCountDownTimer(timeConvert);
                        Toast.makeText(getApplicationContext(),"A música irá parar em "+HourText+" horas e "+MinuteText+" minutos! :)", Toast.LENGTH_LONG).show();
                    }

                }).show();
    }

    private void setCountDownTimer(int time) {
        new CountDownTimer(time, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                setMediaPlayer();
                start.setImageDrawable(ContextCompat.getDrawable(PlayerActivity.this, R.drawable.ic_action_play));
            }
        }.start();
    }
}


