package glory.golek;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import glory.golek.Kelas.TambahKomunitasClass;

public class TambahKomunitasActivity extends AppCompatActivity {

    EditText et_addKomunitas;
    Button btn_addKomunitas;
    Firebase refTeman,ref,refInvite,refAnggota;
    private String id,nama,key,key2,pm;
    public static List<String> list_anggota = new ArrayList();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_komunitas);
        Firebase.setAndroidContext(this);
        et_addKomunitas = (EditText) findViewById(R.id.et_addcomunity);
        btn_addKomunitas = (Button) findViewById(R.id.btn_addComunity);
        ref = new Firebase("https://golek-feca2.firebaseio.com/komunitas/");


        btn_addKomunitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        for (DataSnapshot child : dataSnapshot.getChildren()){

                            String id_kom = child.child("id").getValue().toString();
                            String nm_kom = child.child("nama").getValue().toString();
                            String key = child.getKey();

                            if (id_kom.equals(et_addKomunitas.getText().toString())){

                                Toast.makeText(getApplicationContext(),"Id Ditemukan",Toast.LENGTH_SHORT).show();
                                LayoutInflater inflater = LayoutInflater.from(TambahKomunitasActivity.this);
                                View v = inflater.inflate(R.layout.dialog_profil_komunitas, null);
                                final AlertDialog alertDialog = new AlertDialog.Builder(TambahKomunitasActivity.this).create();
                                alertDialog.setView(v);
                               // Button btn_addDialog = (Button) v.findViewById(R.id.btn_dialogaddTeman);
                                TextView txtNama = (TextView) v.findViewById(R.id.txt_dialogNamaKom);
                                TextView txtId = (TextView) v.findViewById(R.id.txt_dialogTag);

                                txtNama.setText(nm_kom);
                                txtId.setText(id_kom);

                                alertDialog.show();

                            }



                        }

                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });


            }
        });

    }
}
