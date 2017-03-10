package glory.golek;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import glory.golek.Kelas.TambahTemanClass;

public class TambahTemanActivity extends AppCompatActivity {

    Button btn_cariTeman;
    EditText et_cariTeman;
    Firebase refTeman,ref,refInvite;
    private String id,nama,key,key2,pm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_teman);
        Firebase.setAndroidContext(this);
        btn_cariTeman = (Button) findViewById(R.id.btn_addTeman);
        et_cariTeman = (EditText) findViewById(R.id.et_addteman);
        ref = new Firebase("https://golek-feca2.firebaseio.com/user/");
     //   refTeman = new Firebase("https://golek-feca2.firebaseio.com/user/").child(BerandaActivity.key).child("zfriend");

        btn_cariTeman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    ref.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            for (final DataSnapshot child : dataSnapshot.getChildren()){

                                id = child.child("id").getValue().toString();
                                nama = child.child("nama").getValue().toString();
                                pm = child.child("pm").getValue().toString();
                                key = (String) child.getKey();
                                key2 = child.getKey();
                                try {
                                    if (id.equals(et_cariTeman.getText().toString())){
                                        Toast.makeText(getApplicationContext(),"Id Ditemukan",Toast.LENGTH_SHORT).show();
                                        LayoutInflater inflater = LayoutInflater.from(TambahTemanActivity.this);
                                        View v = inflater.inflate(R.layout.dialog_add_friend, null);
                                        final AlertDialog alertDialog = new AlertDialog.Builder(TambahTemanActivity.this).create();
                                        alertDialog.setView(v);
                                        Button btn_addDialog = (Button) v.findViewById(R.id.btn_dialogaddTeman);
                                        TextView txtNama = (TextView) v.findViewById(R.id.user_profile_nameAdd);
                                        TextView txtPm = (TextView) v.findViewById(R.id.user_profile_short_bioAdd);

                                        txtNama.setText(nama);
                                        txtPm.setText(pm);

                                        btn_addDialog.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                               TambahTemanClass temanClass = new TambahTemanClass(BerandaActivity.id,BerandaActivity.nama
                                                        ,BerandaActivity.key);
                                                refInvite = new Firebase("https://golek-feca2.firebaseio.com/user/").child(child.getKey().toString())
                                                        .child("zinvite");
                                                refInvite.push().setValue(temanClass);
                                                Toast.makeText(getApplicationContext(),"Berhasil,silakan tunggu konfirmasi dari teman anda",Toast.LENGTH_SHORT).show();
                                               // Toast.makeText(getApplicationContext(),"key tujuan : "+child.getKey().toString(),Toast.LENGTH_SHORT).show();
                                                et_cariTeman.setText("");
                                                alertDialog.dismiss();
                                            }
                                        });
                                        alertDialog.show();

                                    }
                                }catch (Exception e){
                                    Toast.makeText(getApplicationContext(),"Id ga Ditemukan",Toast.LENGTH_SHORT).show();
                                    Log.e("Salahnya : ",""+e);
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
