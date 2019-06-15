package pe.edu.cibertec.labovideo;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    private Toolbar toolbar;
    private RecyclerView recyclerViewRecordings;
    private ArrayList<Audio> recordingArraylist;
    private ListaAudiosAdapter recordingAdapter;
    private TextView textViewNoRecordings;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recordingArraylist = new ArrayList<Audio>();

        initViews();

        //fetchRecordings();
    }
    private void fetchRecordings() {

        File root = android.os.Environment.getExternalStorageDirectory();
        String path = root.getAbsolutePath() + "/AudioRecorderLite/Audios";

        //Toast.makeText(this,"aver: "+,Toast.LENGTH_SHORT).show();

        Log.d("Files", "Path: " + path);
        File directory = new File(path);
        File[] files = directory.listFiles();

        //Toast.makeText(this,"aver: "+files,Toast.LENGTH_SHORT).show();


        Log.d("Files", "Size: "+ files.length);
        if( files!=null ){

            for (int i = 0; i < files.length; i++) {

                Log.d("Files", "FileName:" + files[i].getName());
                String fileName = files[i].getName();
                String recordingUri = root.getAbsolutePath() + "/AudioRecorderLite/Audios/" + fileName;

                Audio recording = new Audio(recordingUri,fileName,false);
                recordingArraylist.add(recording);
            }

            textViewNoRecordings.setVisibility(View.GONE);
            recyclerViewRecordings.setVisibility(View.VISIBLE);
            setAdaptertoRecyclerView();

        }else{
            textViewNoRecordings.setVisibility(View.VISIBLE);
            recyclerViewRecordings.setVisibility(View.GONE);
        }

    }

    private void setAdaptertoRecyclerView() {
        recordingAdapter = new ListaAudiosAdapter(this,recordingArraylist);
        recyclerViewRecordings.setAdapter(recordingAdapter);
    }

    private void initViews() {

        /** setting up the toolbar  **/
//        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        toolbar.setTitle(R.string.recording_list);
//        toolbar.setTitleTextColor(getResources().getColor(android.R.color.holo_orange_dark));
//        setSupportActionBar(toolbar);

        /** enabling back button ***/
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /** setting up recyclerView **/
        recyclerViewRecordings = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerViewRecordings.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL, false));
        recyclerViewRecordings.setHasFixedSize(true);

        textViewNoRecordings = (TextView) findViewById(R.id.textViewNoRecordings);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }

    }



    public void onClick(View view) {


    }

}
