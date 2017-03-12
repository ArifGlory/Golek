package glory.golek;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import glory.golek.Fragment.FragmentMaps;

public class ProfilKomunitasActivity extends AppCompatActivity {

    TextView txtTag,txtId,txtEmail,txtNope,txtAlamat,txtAdmin;
    Toolbar toolbar;
    FloatingActionButton fabChatKom;
    Intent i;
    Firebase ref,refAnggota,refGambar;
    String id,nama,key,gambar;
    private String admin,tipe;
    public static List<String> list_idAnggota = new ArrayList();
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    private FloatingActionButton fab,fab1,fab2,fab3;
    private Boolean isFabOpen = false;
    private Double Glat, Glon;
    Bitmap bitmap;
    private Uri filePath;
    private StorageReference storageReference;
    private static final int PICK_IMAGE_REQUEST = 234;
    ImageView img_DPKom;

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
        refGambar = new Firebase("https://golek-feca2.firebaseio.com/komunitas").child(key).child("gambar");

        txtEmail = (TextView) findViewById(R.id.tvNumber3);
        txtTag = (TextView) findViewById(R.id.tvNumber);
        txtAlamat = (TextView) findViewById(R.id.tvNumber5);
        txtAdmin = (TextView) findViewById(R.id.txt_admin);
        txtId = (TextView) findViewById(R.id.txt_idProfil);
        txtNope = (TextView) findViewById(R.id.tvNumber1);
        fabChatKom = (FloatingActionButton) findViewById(R.id.fabChatKomunitas);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab_open = AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this,R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(this,R.anim.rotate_backward);
        img_DPKom = (ImageView) findViewById(R.id.img_dpKom);
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
                        String tipeKom = child.child("tipe").getValue().toString();
                        String gmbarDP = child.child("gambar").getValue().toString();
                        Double latt = (Double) child.child("lat").getValue();
                        Double lonn = (Double) child.child("lon").getValue();
                        admin = adm;
                        try {
                            if (idUser.equals(id)) {

                                showbyte(gmbarDP);
                                txtAlamat.setText(alamatUser);
                                txtTag.setText(tagline);
                                getSupportActionBar().setTitle(namaUser);
                                txtNope.setText(nopeUser);
                                txtEmail.setText(emailUser);
                                txtAdmin.setText(adm);
                                tipe = tipeKom;
                                Glat = latt;
                                Glon = lonn;
                                //Toast.makeText(getApplicationContext(), "latt :" + Glat, Toast.LENGTH_LONG).show();
                               // txtAdmin.setVisibility(View.VISIBLE);

                            }

                            if (adm.equals(BerandaActivity.id)){
                                fabChatKom.setVisibility(View.VISIBLE);
                                fab.setVisibility(View.VISIBLE);
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
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFB();
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Browse();
                Toast.makeText(getApplicationContext(), "Silakan Pilih Gambar", Toast.LENGTH_LONG).show();
                fab3.setVisibility(View.VISIBLE);
            }
        });
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Ke Edit Komunitas", Toast.LENGTH_LONG).show();
                i = new Intent(ProfilKomunitasActivity.this,ProfilKomunitasEditActivity.class);
                i.putExtra("id",id);
                i.putExtra("key",key);
                i.putExtra("nama",nama);
                i.putExtra("tag",txtTag.getText().toString());
                i.putExtra("alamat",txtAlamat.getText().toString());
                i.putExtra("nope",txtNope.getText().toString());
                i.putExtra("email",txtEmail.getText().toString());
                i.putExtra("tipe",tipe);
                i.putExtra("lat",Glat);
                i.putExtra("lon",Glon);
                startActivity(i);
            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase.setAndroidContext(v.getContext());
                try {
                    gambar = filePath.getLastPathSegment();
                    refGambar.setValue(filePath.getLastPathSegment());
                    uploadFile(filePath.getLastPathSegment());
                }catch (Exception e){
                    Toast.makeText(ProfilKomunitasActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
                fab3.setVisibility(View.INVISIBLE);
                //Toast.makeText(getApplicationContext(), "Uploadd Gambar", Toast.LENGTH_LONG).show();
            }
        });



    }

    public void animateFB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
            fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            fab2.setClickable(false);
            isFabOpen = false;
            Log.d("fab", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            fab2.setClickable(true);
            isFabOpen = true;
            Log.d("fab","open");

        }
    }


    private void showbyte(String nama){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://golek-feca2.appspot.com/komunitas/");
        StorageReference islandRef = storageRef.child(nama);
        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                img_DPKom.setImageBitmap(bitmap);
            }
        });

    }

    public void Browse() {

        try {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent, "Pilih Gambar"), PICK_IMAGE_REQUEST);

        } catch (Exception e) {
            Toast.makeText(ProfilKomunitasActivity.this, e.toString(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
                filePath = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                    img_DPKom.setImageBitmap(bitmap);
                  //  Toast.makeText(ProfilKomunitasActivity.this," "+ resultCode, Toast.LENGTH_SHORT).show();

                    //gambar = filePath.getLastPathSegment();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            Toast.makeText(ProfilKomunitasActivity.this, e.toString(), Toast.LENGTH_LONG).show();
            e.printStackTrace();
            Log.e("eror upload : ",""+e);
        }
    }

    private void uploadFile(String nama) {
        storageReference = FirebaseStorage.getInstance().getReference();
        // storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://golek-feca2.appspot.com/");

        //if there is a file to upload
        if (filePath != null) {
            //displaying a progress dialog while upload is going on
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading");
            progressDialog.show();

            String pic = "komunitas/" + nama;
            StorageReference riversRef = storageReference.child(pic);
            riversRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), "File Uploaded ", Toast.LENGTH_LONG).show();
                            // txtHarga.setText(null);
                           /* txtJumlah.setText(null);
                            txtKomoditas.setText(null);
                            txtTanggal.setText(null);
                            img_upload.setImageBitmap(null);
                            btnPublish.setEnabled(false);*/
                            finish();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {
                            progressDialog.dismiss();
                            Toast.makeText(getApplicationContext(), exception.getMessage(), Toast.LENGTH_LONG).show();
                            //  Toast.makeText(ProfilUserActivity.this," "+ resultCode, Toast.LENGTH_SHORT).show();
                            Log.e("eror uplod : ",""+exception.toString());
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            //calculating progress percentage
                            double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();

                            //displaying percentage in progress dialog
                            progressDialog.setMessage("Uploaded " + ((int) progress) + "%...");
                        }
                    });
        }
        //if there is not any file
        else {
            //you can display an error toast
        }
    }
}
