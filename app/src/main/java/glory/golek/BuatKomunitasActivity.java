package glory.golek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import glory.golek.Kelas.IsiDataKomunitas;
import glory.golek.Kelas.TambahKomunitasClass;

public class BuatKomunitasActivity extends AppCompatActivity {

    MaterialBetterSpinner spn_tipeKomunitas;
    EditText etNamaKom,etNopeKom,etEmailKom;
    String [] spinerList = {"Olahraga","Komputer","Rohani","Sosial","Budaya","Other"};
    String nm,em,np,tipe,gambar,adm,id,tag,alamat;
    Button btn_pickBasecamp,btn_createKomunitas;
    private int PLACE_PICKER_REQUEST = 1;
    TextView txt_alamat;
    RelativeLayout rela_alamat;
    private Double Glat, Glon;
    Firebase ref,refMykomunitas;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buat_komunitas);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://golek-feca2.firebaseio.com/komunitas");
        refMykomunitas = new Firebase("https://golek-feca2.firebaseio.com/user").child(BerandaActivity.key).child("zmykomunitas");
        spn_tipeKomunitas = (MaterialBetterSpinner) findViewById(R.id.spn_tipeKomunitas);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, spinerList);
        spn_tipeKomunitas.setAdapter(arrayAdapter);
        etNamaKom = (EditText) findViewById(R.id.etNamaKom);
        etNopeKom = (EditText) findViewById(R.id.etEditPhone);
        etEmailKom = (EditText) findViewById(R.id.etEditEmail);
        btn_pickBasecamp = (Button) findViewById(R.id.btn_pickBasecamp);
        rela_alamat = (RelativeLayout) findViewById(R.id.rela_alamat);
        txt_alamat = (TextView) findViewById(R.id.txt_alamat_buatKom);
        btn_createKomunitas = (Button) findViewById(R.id.btnCreateKOmunitas);

        adm = BerandaActivity.id;
        gambar = "akun2.png";
        tag = "Find ur Comunity with Golek!";

        btn_pickBasecamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                formcek();
                PlacePicker.IntentBuilder builder  = new PlacePicker.IntentBuilder();
                try {
                    //menjalankan place picker
                    startActivityForResult(builder.build(BuatKomunitasActivity.this), PLACE_PICKER_REQUEST);

                    // check apabila <a title="Solusi Tidak Bisa Download Google Play Services di Android" href="http://www.twoh.co/2014/11/solusi-tidak-bisa-download-google-play-services-di-android/" target="_blank">Google Play Services tidak terinstall</a> di HP
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }

            }
        });

        spn_tipeKomunitas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tipe = spinerList[position].toString();
                //Toast.makeText(getApplicationContext()," "+tipe,Toast.LENGTH_SHORT).show();
            }
        });

        btn_createKomunitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nm = etNamaKom.getText().toString();
                id = "@"+nm;
                np = etNopeKom.getText().toString();
                em = etEmailKom.getText().toString();

                formcek();
                IsiDataKomunitas dataKomunitas = new IsiDataKomunitas(nm,adm,id,alamat,Glat,Glon,np,em,gambar,tag,tipe);
                ref.push().setValue(dataKomunitas);

                TambahKomunitasClass tambahKomunitas = new TambahKomunitasClass(id,nm);
                refMykomunitas.push().setValue(tambahKomunitas);

                Toast.makeText(getApplicationContext(),"Komunitas Berhasil dibuat",Toast.LENGTH_SHORT).show();

                i = new Intent(BuatKomunitasActivity.this,BerandaActivity.class);
                i.putExtra("id",BerandaActivity.id);
                i.putExtra("nama",BerandaActivity.nama);
                i.putExtra("key",BerandaActivity.key);
                startActivity(i);
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

                txt_alamat.setText(place.getAddress());
                alamat = (String) place.getAddress();
                Glat = place.getLatLng().latitude;
                Glon = place.getLatLng().longitude;
                rela_alamat.setVisibility(View.VISIBLE);
                btn_createKomunitas.setVisibility(View.VISIBLE);

            }
        }

    }

    private boolean validateEmail() {
        if (etEmailKom.getText().toString().trim().isEmpty()) {
            etEmailKom.setError("Tidak boleh kosong!");
            etEmailKom.requestFocus();
            return false;
        } else {
        }
        return true;
    }
    private boolean validateNama() {
        if (etNamaKom.getText().toString().trim().isEmpty()) {
            etNamaKom.setError("Tidak boleh kosong!");
            etNamaKom.requestFocus();
            return false;
        } else {
        }
        return true;
    }
    private boolean validateNope() {
        if (etNopeKom.getText().toString().trim().isEmpty()) {
            etNopeKom.setError("Tidak boleh kosong!");
            etNopeKom.requestFocus();
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

    private void formcek() {

        if (!validateNama()) {
            return;
        }
        if (!validateEmail()) {
            return;
        }

        if (!validateTipe()) {
            return;
        }
        if (!validateNope()) {
            return;
        }

    }



}
