package pe.edu.cibertec.camera;

import android.Manifest;
import android.arch.lifecycle.ViewModelStoreOwner;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btCamera;
    ImageView ivPhoto;
    Button btContact;
    Button btCallPhone;

    static final int REQUEST_CAMERA = 1;
    static final int REQUEST_CONTACT = 4;
    static final int REQUEST_TAKE_PICTURE = 2;
    static final int REQUEST_TAKE_CONTACT = 3;
    static final int REQUEST_CALL_PHONE = 5;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btCamera= findViewById(R.id.btCamera);
        btContact = findViewById(R.id.btContact);
        ivPhoto = findViewById(R.id.ivPhoto);
        btCallPhone = findViewById(R.id.btCallPhone);


//        btCamera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                takePicture();
//            }
//        });

        btContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(MainActivity.this,"se dio click en contactos",Toast.LENGTH_SHORT).show();
                takeContact();
            }
        });


        btCallPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeCallPhone();
            }
        });

    }

    private void takeCallPhone() {
        //Intent callphone = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + "Tu Numero de celular"));
        Intent callphoneinten = new Intent(Intent.ACTION_CALL);

        if(Build.VERSION.SDK_INT<23)
        {

            if (ActivityCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                Intent callIntent = new Intent(Intent.ACTION_CALL);
                callIntent.setData(Uri.parse("tel:949389482"));
                MainActivity.this.startActivity(callIntent);
            }else{
                Toast.makeText(MainActivity.this, "No tienes permisos.", Toast.LENGTH_SHORT).show();
            }

        }
        else {

            if(ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE)==PackageManager.PERMISSION_GRANTED)
            {
                if (ActivityCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:12345678900"));
                    MainActivity.this.startActivity(callIntent);
                }else{
                    Toast.makeText(MainActivity.this, "No tienes permisos.", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_STORAGE, REQUEST_TAKE_CONTACT);
            }

        }

//        if(callphoneinten.resolveActivity(getPackageManager())!=null)
//        {
//            if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.CALL_PHONE)!=PackageManager.PERMISSION_GRANTED)
//            {
////            Toast.makeText(MainActivity.this,"se dio click en contactos",Toast.LENGTH_SHORT).show();
//                //requestContactPermission();
//                //ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_CONTACTS});
//
//                ActivityCompat.requestPermissions(MainActivity.this,
//                        new String[]{Manifest.permission.CALL_PHONE},
//                        REQUEST_TAKE_CONTACT);
//                startActivity(callphoneinten);
//            }
//            else
//            {
////            ActivityCompat.requestPermissions(MainActivity.this,
////                    new String[]{Manifest.permission.CALL_PHONE},
////                    REQUEST_TAKE_CONTACT);
//            }
//        }

        //startActivity(callphone);

    }

    private void takeContact() {

        //Intent contactintent = Intent(MediaStore.);

        if(ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_CONTACTS)!=PackageManager.PERMISSION_GRANTED)
        {
//            Toast.makeText(MainActivity.this,"se dio click en contactos",Toast.LENGTH_SHORT).show();
            //requestContactPermission();
            //ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_CONTACTS});
        }
        else
        {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{Manifest.permission.READ_CONTACTS},
                    REQUEST_TAKE_CONTACT);
        }

    }

    private void takePicture() {
        Intent cameraintent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        //Validar que la camara este disponible

        if(cameraintent.resolveActivity(getPackageManager())!=null)
        {
            if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
            {
                requestCameraPermission();
                //requestContactPermission();
            }
            else
            {
                startActivityForResult(cameraintent,REQUEST_TAKE_PICTURE);
            }
        }


    }

    private void requestContactPermission() {
        //Toast.makeText(MainActivity.this,"se dio click en contactos",Toast.LENGTH_SHORT).show();
        //ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_CONTACTS},REQUEST_CONTACT);
        ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this,Manifest.permission.READ_CONTACTS);
    }

    private void requestCameraPermission() {
        ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.CAMERA},REQUEST_CAMERA);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
       // super.onRequestPermissionsResult(requestCode, permissions, grantResults);
//        if(requestCode == REQUEST_CAMERA)
//        {
//            //Toast.makeText(MainActivity.this,"se dio permiso al camara",Toast.LENGTH_SHORT).show();
//            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
//            {
//                //takeContact();
//                takePicture();
//                //Toast.makeText(MainActivity.this,"se dio permiso",Toast.LENGTH_SHORT).show();
//            }
//        }
        if(requestCode==REQUEST_TAKE_CONTACT)
        {
            //Toast.makeText(MainActivity.this,"se dio permiso al calendario",Toast.LENGTH_SHORT).show();
            if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {

                Toast.makeText(MainActivity.this,"se dio permiso a las llamadas",Toast.LENGTH_SHORT).show();
            }
        }
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        if(requestCode== REQUEST_CONTACT && resultCode == PackageManager.PERMISSION_GRANTED){
//            Toast.makeText(MainActivity.this,"se dio permiso a los contactos",Toast.LENGTH_SHORT).show();
//        }
//    }
}
