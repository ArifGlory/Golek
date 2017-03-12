package glory.golek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

public class ProfilKomunitasEditActivity extends AppCompatActivity {

    TextView txtId,txtAlamat;
    String id,nama,key,alamat,nope,tag,email,tipe;
    Intent i;
    Firebase ref;
    EditText etTag,etEmail,etPhone;
    MaterialBetterSpinner spn_tipeKomunitas;
    String [] spinerList = {"Olahraga","Komputer","Rohani","Sosial","Budaya","Other"};
    private Double Glat, Glon;
    private int PLACE_PICKER_REQUEST = 1;
    Button btn_editKom,btn_editCamp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_komunitas_edit);
        Firebase.setAndroidContext(this);

        i = getIntent();
        id = i.getStringExtra("id");
        nama = i.getStringExtra("nama");
        key = i.getStringExtra("key");
        alamat = i.getStringExtra("alamat");
        nope = i.getStringExtra("nope");
        tag = i.getStringExtra("tag");
        email = i.getStringExtra("email");
        tipe = i.getStringExtra("tipe");
        Glat = i.getDoubleExtra("lat",0.000000);
        Glon = i.getDoubleExtra("lon",0.000000);
        ref = new Firebase("https://golek-feca2.firebaseio.com/komunitas/").child(key);

        etTag = (EditText) findViewById(R.id.etEditTag);
        etEmail = (EditText) findViewById(R.id.etEditEmail);
        txtId = (TextView) findViewById(R.id.txt_idKom);
        txtAlamat = (TextView) findViewById(R.id.txt_alamat_editKom);
        spn_tipeKomunitas = (MaterialBetterSpinner) findViewById(R.id.spn_EdittipeKomunitas);
        etPhone = (EditText) findViewById(R.id.etEditPhone);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, spinerList);
        spn_tipeKomunitas.setAdapter(arrayAdapter);
        btn_editKom = (Button) findViewById(R.id.btnEditKomunitas);
        btn_editCamp = (Button) findViewById(R.id.btn_editBasecamp);
        //Toast.makeText(getApplicationContext()," key :  "+key,Toast.LENGTH_SHORT).show();

        txtId.setText(id);
        txtAlamat.setText(alamat);
        etPhone.setText(nope);
        etEmail.setText(email);
        etTag.setText(tag);
        spn_tipeKomunitas.setText(tipe);

        spn_tipeKomunitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tipe = spinerList[position].toString();
            }
        });

        btn_editCamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PlacePicker.IntentBuilder builder  = new PlacePicker.IntentBuilder();
                try {
                    //menjalankan place picker
                    startActivityForResult(builder.build(ProfilKomunitasEditActivity.this), PLACE_PICKER_REQUEST);

                    // check apabila <a title="Solusi Tidak Bisa Download Google Play Services di Android" href="http://www.twoh.co/2014/11/solusi-tidak-bisa-download-google-play-services-di-android/" target="_blank">Google Play Services tidak terinstall</a> di HP
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });

        btn_editKom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                if (formcek() == false){
                    Toast.makeText(getApplicationContext()," Ada data yang kurang ",Toast.LENGTH_SHORT).show();
                }
                else {
                    ref.child("tagline").setValue(etTag.getText().toString());
                    ref.child("alamat").setValue(txtAlamat.getText().toString());
                    ref.child("nope").setValue(etPhone.getText().toString());
                    ref.child("email").setValue(etEmail.getText().toString());
                    ref.child("tipe").setValue(tipe);
                    ref.child("lat").setValue(Glat);
                    ref.child("lon").setValue(Glon);
                    i = new Intent(ProfilKomunitasEditActivity.this, BerandaActivity.class);
                    i.putExtra("id", BerandaActivity.id);
                    i.putExtra("nama", BerandaActivity.nama);
                    i.putExtra("key", BerandaActivity.key);
                    i.putExtra("gambar", BerandaActivity.gambar);
                    startActivity(i);

                    Toast.makeText(getApplicationContext(), " Berhasil diedit ", Toast.LENGTH_SHORT).show();
                }
                }catch (Exception e){

                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super.onActivityResult(requestCode, resultCode, data);
        // menangkap hasil balikan dari Place Picker, dan menampilkannya pada TextView
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == RESULT_OK) {
                Place place = PlacePicker.getPlace(data, this);
                String toastMsg = String.format(
                        "Place: %s \n" +
                                "Alamat: %s \n" +
                                "Latlng %s \n", place.getName(), place.getAddress(), place.getLatLng().latitude+" "+place.getLatLng().longitude);
                //tvPlaceAPI.setText(toastMsg);
                //Toast.makeText(getApplicationContext()," "+toastMsg,Toast.LENGTH_SHORT).show();

                txtAlamat.setText(place.getAddress());
                alamat = (String) place.getAddress();
                Glat = place.getLatLng().latitude;
                Glon = place.getLatLng().longitude;

            }
        }

    }

    private boolean validateEmail() {
        if (etEmail.getText().toString().trim().isEmpty()) {
            etEmail.setError("Tidak boleh kosong!");
            etEmail.requestFocus();
            return false;
        } else {
        }
        return true;
    }

    private boolean validateTag() {
        if (etTag.getText().toString().trim().isEmpty()) {
            etTag.setError("Tidak boleh kosong!");
            etTag.requestFocus();
            return false;
        } else {
        }
        return true;
    }

    private boolean validateNope() {
        if (etPhone.getText().toString().trim().isEmpty()) {
            etPhone.setError("Tidak boleh kosong!");
            etPhone.requestFocus();
            return false;
        } else {
        }
        return true;
    }
    private boolean validateTipe() {
        if (tipe.toString().trim().isEmpty()) {
            spn_tipeKomunitas.setError("Tidak boleh kosong!");
            spn_tipeKomunitas.requestFocus();
            return false;
        } else {
        }
        return true;
    }

    private boolean formcek() {

        if (!validateEmail()) {
            return false;
        }
        if (!validateTag()) {
            return false;
        }

        if (!validateTipe()) {
            return false;
        }
        if (!validateNope()) {
            return false;
        }

        return true;
    }
}
