package glory.golek;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.TransitionDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    RelativeLayout relaUtama;
    Intent i;
    Firebase ref;
    EditText etUser,etPass;
    DialogInterface.OnClickListener listener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        Resources res = this.getResources();
        relaUtama = (RelativeLayout) findViewById(R.id.relaUtama);
        etUser = (EditText) findViewById(R.id.etUserNameLogin);
        etPass = (EditText) findViewById(R.id.etPassLogin);
        ref = new Firebase("https://golek-feca2.firebaseio.com/user");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(MainActivity.this,"Data Berhasil Diambil", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        /*TransitionDrawable transitionDrawable = (TransitionDrawable) res.getDrawable(R.drawable.transition1);
        relaUtama.setBackground(transitionDrawable);
        transitionDrawable.startTransition(3000);*/

    }


    public void signin(View view) {

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot child : dataSnapshot.getChildren()){

                    String id = child.child("id").getValue().toString();
                    String pass = child.child("password").getValue().toString();
                    String nama = child.child("nama").getValue().toString();
                    String image = child.child("gambar").getValue().toString();

                    double lat   = (Double) child.child("lat").getValue();
                    double lon = (Double) child.child("lon").getValue();
                    try {
                        if (id.equals(etUser.getText().toString()) && pass.equals(etPass.getText().toString()) ) {
                            //Toast.makeText(getApplicationContext(), "lat :"+lat+",lon : "+lon, Toast.LENGTH_LONG).show();
                           // Toast.makeText(getApplicationContext(), "id :"+id+",nama : "+nama, Toast.LENGTH_LONG).show();
                            finish();
                            i = new Intent(MainActivity.this, BerandaActivity.class);
                            i.putExtra("key",child.getKey().toString());
                            i.putExtra("nama",nama);
                            i.putExtra("id",id);
                            i.putExtra("gambar",image);
                           // i.putExtra("lat",lat);
                            //i.putExtra("lon",lon);
                            startActivity(i);
                            System.exit(0);
                        }else {
                            Toast.makeText(getApplicationContext(),"Username atau password anda salah",Toast.LENGTH_SHORT).show();
                        }

                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Username dan password salah",Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

       /* i = new Intent(this,BerandaActivity.class);
        startActivity(i);*/
    }

    public void signup(View view) {
        i = new Intent(this,RegisterActivity.class);
        startActivity(i);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage("Apakan anda tetap ingin menutup aplikasi?");
            builder.setCancelable(false);

            listener = new DialogInterface.OnClickListener()
            {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(which == DialogInterface.BUTTON_POSITIVE){
                        finishAffinity();
                    }

                    if(which == DialogInterface.BUTTON_NEGATIVE){
                        dialog.cancel();
                    }
                }
            };
            builder.setPositiveButton("Ya",listener);
            builder.setNegativeButton("Tidak", listener);
            builder.show();

        }
        return super.onKeyDown(keyCode, event);
    }
}
