package glory.golek;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class ProfilEditActivity extends AppCompatActivity {

    Toolbar toolbar;
    TextView txtId;
    String id,nama,key,alamat,nope,pm,email;
    Intent i;
    Firebase ref;
    EditText etPM,etEmail,etAlamat,etPhone;
    FloatingActionButton fabEditOke;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_edit);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://golek-feca2.firebaseio.com/user");
        i = getIntent();
        nama = i.getStringExtra("nama");
        id = i.getStringExtra("id");
        nope = i.getStringExtra("nope");
        pm = i.getStringExtra("pm");
        alamat = i.getStringExtra("alamat");
        email = i.getStringExtra("email");

        getSupportActionBar().setTitle(nama);
        etAlamat = (EditText) findViewById(R.id.etEditPlace);
        etEmail = (EditText) findViewById(R.id.etEditEmail);
        etPhone = (EditText) findViewById(R.id.etEditPhone);
        etPM = (EditText) findViewById(R.id.etEditPM);
        txtId = (TextView) findViewById(R.id.txt_idProfil);
        fabEditOke = (FloatingActionButton) findViewById(R.id.fabEditOke);

        etPM.setText(pm);
        etPhone.setText(nope);
        etEmail.setText(email);
        etAlamat.setText(alamat);

        fabEditOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref.child(BerandaActivity.key).child("alamat").setValue(etAlamat.getText().toString());
                ref.child(BerandaActivity.key).child("pm").setValue(etPM.getText().toString());
                ref.child(BerandaActivity.key).child("nope").setValue(etPhone.getText().toString());
                ref.child(BerandaActivity.key).child("email").setValue(etEmail.getText().toString());
                Toast.makeText(getApplicationContext(),"Berhasil diedit",Toast.LENGTH_SHORT).show();
                /*i = new Intent(ProfilEditActivity.this,ProfilUserActivity.class);
                i.putExtra("id",id);
                i.putExtra("nama",nama);*/
                i = new Intent(ProfilEditActivity.this,BerandaActivity.class);
                i.putExtra("id",id);
                i.putExtra("nama",BerandaActivity.nama);
                i.putExtra("key",BerandaActivity.key);
                startActivity(i);

            }
        });

    }
}
