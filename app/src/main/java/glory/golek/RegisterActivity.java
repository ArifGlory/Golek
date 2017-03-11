package glory.golek;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.io.IOException;

import glory.golek.Kelas.IsiDataUser;

public class RegisterActivity extends AppCompatActivity implements android.location.LocationListener {

    Intent i;
    Firebase ref;
    EditText etUsername, etEmail, etPass;
    private String username, email, pass, nope, alamat, provider, nama,gambar,pm;
    private Double Glat, Glon;
    private LocationManager locationManager;
    Location lokasiterahir;
    IsiDataUser isiDataUser;
    TextView txt_idRegister;
    Button btn_Generate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Firebase.setAndroidContext(this);
        ref = new Firebase("https://golek-feca2.firebaseio.com/user");
        etUsername = (EditText) findViewById(R.id.etUserName);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPass);
        txt_idRegister = (TextView) findViewById(R.id.txt_idRegister);
        btn_Generate = (Button) findViewById(R.id.btn_generateID);
        nope = " -- ";
        alamat = " -- ";
        gambar = "akun2.png";
        pm = "-";


        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000, 1, this);
        Location location = locationManager.getLastKnownLocation(provider);

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Toast.makeText(RegisterActivity.this, "Anda Terhubung Ke Server", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        btn_Generate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = "@"+etUsername.getText().toString();
                txt_idRegister.setText(username);
                Snackbar snackbar =  Snackbar.make(v,"ID berhasil dibuat !, silakan gunakan ID "+username+" sebagai ID untuk login",Snackbar.LENGTH_SHORT)
                        .setAction("ID dibuat",null);
                snackbar.show();
            }
        });

    }

    public void signin(View view) {
        i = new Intent(this,MainActivity.class);
        startActivity(i);

    }



    public void create(View view) {
        username = "@"+etUsername.getText().toString();
        nama = etUsername.getText().toString();
        email = etEmail.getText().toString();
        pass = etPass.getText().toString();

        if (lokasiterahir != null) {


                if (formcek() == false){
                    Toast.makeText(getApplicationContext(), "Ada data yang kurang", Toast.LENGTH_SHORT).show();
                }else {


                    isiDataUser = new IsiDataUser(nama, pass, username, alamat, Glat, Glon, nope, email, gambar, pm);
                    ref.push().setValue(isiDataUser);
                    clear();
                    Toast.makeText(getApplicationContext(), "Berhasil Daftar,Silakan Login", Toast.LENGTH_LONG).show();

                    finish();
                    Intent i = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(i);
                    System.exit(0);
                }

        } else {
            Toast.makeText(getApplicationContext(), "Sedang mengambil lokasi", Toast.LENGTH_LONG).show();
        }

    }

    private void clear() {
        etEmail.setText(null);
        etUsername.setText(null);
        etUsername.setText(null);
    }
    private boolean validateName() {
        if (etUsername.getText().toString().trim().isEmpty()) {
            etUsername.setError("Tidak boleh kosong!");
            etUsername.requestFocus();
            return false;
        } else {
        }
        return true;
    }
    private boolean validateEmail() {
        if (etEmail.getText().toString().trim().isEmpty()) {
            etEmail.setError("Tidak boleh kosong!");
            etEmail.requestFocus();
            return false;
        } else {
        }
        return true;
    }private boolean validatePass() {
        if (etPass.getText().toString().trim().isEmpty()) {
            etPass.setError("Tidak boleh kosong!");
            etPass.requestFocus();
            return false;
        } else {

        }

        return true;
    }
    private boolean formcek() {

        if (!validateName()) {
            return false;
        }
        if (!validatePass()) {
            return false;
        }

        if (!validateEmail()) {
            return false;
        }

        return true;

    }

    @Override
    public void onLocationChanged(Location location) {

            lokasiterahir = location;
            double lat = location.getLatitude();
            double lon = location.getLongitude();

            Glat = lat;
            Glon = lon;
        //Toast.makeText(getApplicationContext(), "Lokasi : "+lat+" "+lon, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
