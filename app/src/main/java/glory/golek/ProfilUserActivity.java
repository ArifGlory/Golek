package glory.golek;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class ProfilUserActivity extends AppCompatActivity {

    FloatingActionButton fabEditProfiluser;
    Toolbar toolbar;
    TextView txtEmail,txtPm,txtId,txtAlamat,txtNope;
    String id,nama,key;
    Intent i;
    Firebase ref;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_user);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://golek-feca2.firebaseio.com/user");
        i = getIntent();
        nama = i.getStringExtra("nama");
        id = i.getStringExtra("id");
        getSupportActionBar().setTitle(nama);

        txtEmail = (TextView) findViewById(R.id.tvNumber3);
        txtPm = (TextView) findViewById(R.id.tvNumber);
        txtAlamat = (TextView) findViewById(R.id.tvNumber5);
        txtId = (TextView) findViewById(R.id.txt_idProfil);
        txtNope = (TextView) findViewById(R.id.tvNumber1);
        fabEditProfiluser = (FloatingActionButton) findViewById(R.id.fabEditProfiluser);
        txtId.setText(id);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){

                    String idUser = child.child("id").getValue().toString();
                    String namaUser = child.child("nama").getValue().toString();
                    String alamatUser = child.child("alamat").getValue().toString();
                    String nopeUser = child.child("nope").getValue().toString();
                    String pmUser = child.child("pm").getValue().toString();
                    String emailUser = child.child("email").getValue().toString();
                    try {
                        if (idUser.equals(id) ) {
                            Toast.makeText(getApplicationContext(), "Alamat :"+alamatUser, Toast.LENGTH_LONG).show();
                            txtAlamat.setText(alamatUser);
                            txtPm.setText(pmUser);
                            getSupportActionBar().setTitle(namaUser);
                            txtNope.setText(nopeUser);
                            txtEmail.setText(emailUser);

                        }

                    }
                    catch (Exception e){
                        Toast.makeText(getApplicationContext(),"Profil tidak ditemukan",Toast.LENGTH_LONG).show();
                    }

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        fabEditProfiluser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 i = new Intent(ProfilUserActivity.this,ProfilEditActivity.class);
                i.putExtra("id",txtId.getText().toString());
                i.putExtra("nama",nama);
                i.putExtra("pm",txtPm.getText().toString());
                i.putExtra("nope",txtNope.getText().toString());
                i.putExtra("email",txtEmail.getText().toString());
                i.putExtra("alamat",txtAlamat.getText().toString());
                startActivity(i);
            }
        });
    }
}
