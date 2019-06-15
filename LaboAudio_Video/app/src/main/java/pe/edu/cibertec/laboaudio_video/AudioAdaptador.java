package pe.edu.cibertec.laboaudio_video;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Converter;

public class AudioAdaptador extends RecyclerView.Adapter<AudioAdaptador.ViewHolder> {


    ArrayList<String> dataset;
    Context context;
    MediaPlayer player = null;
    String RutaN = null;
    boolean flag=true;

    public AudioAdaptador(ArrayList<String> datasets,Context context)
    {
        this.dataset = datasets;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
       View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.lista_audios_videos,viewGroup,false);
       return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        viewHolder.Prueba(dataset.get(i));

        viewHolder.ivPlayView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(flag)
                {
                    player = new MediaPlayer();
                    RutaN = ""+viewHolder.datos.getText();

                    player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                        @Override
                        public void onCompletion(MediaPlayer mediaPlayer) {
                            viewHolder.ivPlayView.setImageResource(R.drawable.ic_play);
                        }
                    });

                    try {
                        player.setDataSource(RutaN.trim());
                        player.prepare();
                        player.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    viewHolder.ivPlayView.setImageResource(R.drawable.ic_pause);
                    flag=false;
                }
//                if(player.isPlaying())
//                {
//                    player.pause();
//                    viewHolder.ivPlayView.setImageResource(R.drawable.ic_play);
//                }
                else
                {
                    //Toast.makeText(context,"3",Toast.LENGTH_SHORT).show();
                    player.stop();
                    flag=true;
                    viewHolder.ivPlayView.setImageResource(R.drawable.ic_play);
                }
                //viewHolder.ivPlayView.setImageResource(R.drawable.ic_play);
//                if(!flag)
//                {
//                    viewHolder.ivPlayView.setImageResource(R.drawable.ic_play);
//                }

            }
        });


        viewHolder.datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              MediaPlayer players = new MediaPlayer();
               String RutaNS = ""+viewHolder.datos.getText();

               //Toast.makeText(context,"url: "+RutaNS,Toast.LENGTH_SHORT).show();

                try {
                    players.setDataSource(RutaNS.trim());
                    players.prepare();
                    players.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView datos;
        ImageView ivPlayView;
        //ImageView ivStopView;

        public ViewHolder(View itemView) {
            super(itemView);
            datos = (TextView) itemView.findViewById(R.id.listaAudios);
            ivPlayView = (ImageView) itemView.findViewById(R.id.ivPlay);
            //ivStopView = (ImageView)itemView.findViewById(R.id.ivStop);
        }

        public void Prueba(String s) {
            datos.setText(s);
            //ivPlayView.setTe
        }
    }
}
