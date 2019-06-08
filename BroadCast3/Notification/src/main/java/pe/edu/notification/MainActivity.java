package pe.edu.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register();
        createNotificationchannel();
        
    }

    //Creacion del canal
    private void createNotificationchannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
//            CharSequence charSequence = "canal";
            String name = "pe.edu.cibertect.broadcast.ACTION";
            String descripcion = "Descripcion del Canal";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Canal",name,importance);
            channel.setDescription(descripcion);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }







    //Registro del canal
    private void register() {
        MyBroadCastReceiver receiver = new MyBroadCastReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("pe.edu.cibertect.broadcast.ACTION");
//        intentFilter.addAction("android.intent.action.AIRPLANE_MODE");
        intentFilter.addAction("android.intent.action.ACTION_POWER_CONNECTED");
        this.registerReceiver(receiver,intentFilter);
    }
}
