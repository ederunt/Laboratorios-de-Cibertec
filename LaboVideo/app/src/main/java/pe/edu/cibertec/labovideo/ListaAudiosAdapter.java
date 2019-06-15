package pe.edu.cibertec.labovideo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Handler;

public class ListaAudiosAdapter extends RecyclerView.Adapter<ListaAudiosAdapter.ViewHolder> {

    private Context context;
    private ArrayList<Audio> recordingArrayList;
    private MediaPlayer mPlayer;
    private boolean isPlaying = false;
    private int last_index = -1;
    private int adapterPosition;
    public Audio audio ;



    public ListaAudiosAdapter(Context context, ArrayList<Audio> recordingArrayList){
        this.context = context;
        this.recordingArrayList = recordingArrayList;
    }

    @NonNull
    @Override
    public ListaAudiosAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_audio, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaAudiosAdapter.ViewHolder viewHolder, int i) {
        setUpData(viewHolder,i);
    }

    @Override
    public int getItemCount() {
      return  recordingArrayList.size();
    }

//    public void onClick(View view) {
//        removeAt(getAdapterPosition());
//    }

    private void setUpData(ViewHolder holder, int position) {

        Audio audio = recordingArrayList.get(position);
        holder.textViewName.setText(audio.getFileName());

        if( audio.isPlaying()){
            holder.imageViewPlay.setImageResource(R.drawable.bb_bottom_bar_top_shadow);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition((ViewGroup) holder.itemView);
            }
            holder.seekBar.setVisibility(View.VISIBLE);
            holder.seekUpdation(holder);
        }else{
            holder.imageViewPlay.setImageResource(R.drawable.ic_launcher_background);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                TransitionManager.beginDelayedTransition((ViewGroup) holder.itemView);
            }
            holder.seekBar.setVisibility(View.GONE);
        }

        holder.manageSeekBar(holder);

    }

//    public void onClick(View view) {
//        removeAt(getAdapterPosition());
//    }

//    public void removeAt(int position) {
//        recordingArrayList.remove(position);
//        notifyItemRemoved(position);
//        notifyItemRangeChanged(position, recordingArrayList.size());
//
//        String uri = audio.getUri();
//        File file = new File (uri);
//        file.delete();
//    }

    private void stopPlaying() {
        try{
            mPlayer.release();
        }catch (Exception e){
            e.printStackTrace();
        }
        mPlayer = null;
        isPlaying = false;
    }

    public int getAdapterPosition() {
        return adapterPosition;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        ImageView imageViewPlay;
        SeekBar seekBar;
        TextView textViewName;


        private String recordingUri;
        private int lastProgress = 0;
        //private Handler mHandler = new Handler();
        ViewHolder holder;

        public ViewHolder(View itemView) {
            super(itemView);

            //Button btn_delete;
            imageViewPlay = itemView.findViewById(R.id.imageViewPlay);
            seekBar = itemView.findViewById(R.id.seekBar);
            textViewName = itemView.findViewById(R.id.textViewRecordingname);

//            btn_delete = itemView.findViewById(R.id.button2);
//            btn_delete.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    removeAt(getAdapterPosition());
//                }
//            });

            imageViewPlay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    Audio audio = recordingArrayList.get(position);

                    recordingUri = audio.getUri();

                    if (isPlaying) {
                        stopPlaying();
                        if (position == last_index) {
                            audio.setPlaying (false);
                            stopPlaying();
                            notifyItemChanged(position);
                        } else {
                            markAllPaused();
                            audio.setPlaying(true);
                            notifyItemChanged(position);
                            startPlaying(audio, position);
                            last_index = position;
                        }

                    } else {
                        if (audio.isPlaying()) {
                            audio.setPlaying(false);
                            stopPlaying();
                            Log.d("isPlayin", "True");
                        } else {
                            startPlaying(audio, position);
                            audio.setPlaying(true);
                            seekBar.setMax(mPlayer.getDuration());
                            Log.d("isPlayin", "False");
                        }
                        notifyItemChanged(position);
                        last_index = position;
                    }

                }

            });
        }
        public void manageSeekBar(ViewHolder holder){
            holder.seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if( mPlayer!=null && fromUser ){
                        mPlayer.seekTo(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }

        private void markAllPaused() {
            for( int i=0; i < recordingArrayList.size(); i++ ){
                recordingArrayList.get(i).setPlaying(false);
                recordingArrayList.set(i,recordingArrayList.get(i));
            }
            notifyDataSetChanged();
        }

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                seekUpdation(holder);
            }
        };

        private void seekUpdation(ViewHolder holder) {
            this.holder = holder;
            if(mPlayer != null){
                int mCurrentPosition = mPlayer.getCurrentPosition() ;
                holder.seekBar.setMax(mPlayer.getDuration());
                holder.seekBar.setProgress(mCurrentPosition);
                lastProgress = mCurrentPosition;
            }
            //mHandler.postDelayed(runnable, 100);
        }

        private void stopPlaying() {
            try{
                mPlayer.release();
            }catch (Exception e){
                e.printStackTrace();
            }
            mPlayer = null;
            isPlaying = false;
        }

        private void startPlaying(final Audio audio, final int position) {
            mPlayer = new MediaPlayer();
            try {
                mPlayer.setDataSource(recordingUri);
                mPlayer.prepare();
                mPlayer.start();
            } catch (IOException e) {
                Log.e("LOG_TAG", "prepare() failed");
            }
            //showing the pause button
            seekBar.setMax(mPlayer.getDuration());
            isPlaying = true;

            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    audio.setPlaying(false);
                    notifyItemChanged(position);
                }
            });



        }

    }


    //this method will remove the item from the list
//    private void removeHero(final int position) {
//        //Creating an alert dialog to confirm the deletion
//        AlertDialog.Builder builder = new AlertDialog.Builder(context);
//        builder.setTitle("Are you sure you want to delete this?");
//
//        //if the response is positive in the alert
//        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//                //removing the item
//                recordingArrayList.remove(position);
//
//                //reloading the list
//                notifyDataSetChanged();
//            }
//        });
//
//        //if response is negative nothing is being done
//        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//
//            }
//        });
//
//        //creating and displaying the alert dialog
//        AlertDialog alertDialog = builder.create();
//        alertDialog.show();
//    }
}
