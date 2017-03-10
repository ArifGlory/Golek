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

import java.util.ArrayList;
import java.util.List;

import glory.golek.Fragment.FragmentMaps;

public class ProfilKomunitasActivity extends AppCompatActivity {

    TextView txtTag,txtId,txtEmail,txtNope,txtAlamat,txtAdmin;
    Toolbar toolbar;
    FloatingActionButton fabChatKom;
    Intent i;
    Firebase ref,refAnggota;
    String id,nama,key;
    private String admin;
    public static List<String> list_idAnggota = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_komunitas);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://golek-feca2.firebaseio.com/komunitas");

        i = getIntent();
        nama = i.getStringExtra("nama");
        id = i.getStringExtra("id");
        key = i.getStringExtra("key");
        getSupportActionBar().setTitle(nama);
        admin = "";
        refAnggota = new Firebase("https://golek-feca2.firebaseio.com/komunitas").child(key).child("zanggota");

        txtEmail = (TextView) findViewById(R.id.tvNumber3);
        txtTag = (TextView) findViewById(R.id.tvNumber);
        txtAlamat = (TextView) findViewById(R.id.tvNumber5);
        txtAdmin = (TextView) findViewById(R.id.txt_admin);
        txtId = (TextView) findViewById(R.id.txt_idProfil);
        txtNope = (TextView) findViewById(R.id.tvNumber1);
        fabChatKom = (FloatingActionButton) findViewById(R.id.fabChatKomunitas);
        txtId.setText(id);

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot child : dataSnapshot.getChildren()) {

                        String idUser = child.child("id").getValue().toString();
                        String namaUser = child.child("nama").getValue().toString();
                        String alamatUser = child.child("alamat").getValue().toString();
                        String nopeUser = child.child("nope").getValue().toString();
                        String tagline = child.child("tagline").getValue().toString();
                        String emailUser = child.child("email").getValue().toString();
                        String adm = child.child("admin").getValue().toString();
                        admin = adm;
                        try {
                            if (idUser.equals(id)) {
                                Toast.makeText(getApplicationContext(), "Admin :" + admin, Toast.LENGTH_LONG).show();
                                txtAlamat.setText(alamatUser);
                                txtTag.setText(tagline);
                                getSupportActionBar().setTitle(namaUser);
                                txtNope.setText(nopeUser);
                                txtEmail.setText(emailUser);
                                txtAdmin.setText(adm);
                               // txtAdmin.setVisibility(View.VISIBLE);

                            }

                            if (adm.equals(BerandaActivity.id)){
                                fabChatKom.setVisibility(View.VISIBLE);
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Profil tidak ditemukan", Toast.LENGTH_LONG).show();
                        }

                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        if ((FragmentMaps.list_anggotaKom.contains(BerandaActivity.id))){
            fabChatKom.setVisibility(View.VISIBLE);
        }

        fabChatKom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                i = new Intent(ProfilKomunitasActivity.this,ChatKomunitasActivity.class);
                i.putExtra("nama",nama);
                i.putExtra("id",id);
                i.putExtra("key",key);
                startActivity(i);
                //Toast.makeText(getApplicationContext(), "Ke Chat Komunitas", Toast.LENGTH_LONG).show();
            }
        });

    }
}
