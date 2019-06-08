package pe.edu.notification;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import java.util.Random;


public class MyBroadCastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        Intent Newintent1 = new Intent(context, NotificationActivity.class); //recibe un accion

        PendingIntent pendingIntent = PendingIntent.getActivity(context,1,Newintent1,0);
        //PendingIntent pendingIntent = PendingIntent.getActivity(context,1,Newintent1,0);

        Toast.makeText(context,"Accion ocurrio",Toast.LENGTH_SHORT).show();

        if(action.equals("pe.edu.cibertect.broadcast.ACTION"))
        {
            NotificationCompat.Builder notification = new NotificationCompat.Builder(context,"Canal")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Notificacion nueva")
                    .setContentText("Esta es una nueva notificacion")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
            managerCompat.notify(0,notification.build()); //nos ayuda a mostrar las otificaciones de forma apilada
            //Toast.makeText(context,"Accion ocurrio",Toast.LENGTH_SHORT).show();
        }

        if(action.equals("android.intent.action.AIRPLANE_MODE"))
        {
            NotificationCompat.Builder notification = new NotificationCompat.Builder(context,"Canal")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Notificacion modo avion")
                    .setContentText("Nuevo Modo Avion de Eder")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            Random random = new Random();
            int unique_Id = random.nextInt(999);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
            managerCompat.notify(unique_Id,notification.build());
        }

        if(action.equals("android.intent.action.ACTION_POWER_CONNECTED"))
        {
            NotificationCompat.Builder notification = new NotificationCompat.Builder(context,"Canal")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle("Notificacion Conexion de bateria")
                    .setContentText("Nueva Conexion de baterias")
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true);

            Random randomP = new Random();
            int unique_Id = randomP.nextInt(99);

            NotificationManagerCompat managerCompat = NotificationManagerCompat.from(context);
            managerCompat.notify(unique_Id,notification.build());
        }


    }
}
