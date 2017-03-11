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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseApp;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class ProfilUserActivity extends AppCompatActivity {

    FloatingActionButton fabEditProfiluser,fabGantiGambar,fabEditOke;
    Toolbar toolbar;
    TextView txtEmail,txtPm,txtId,txtAlamat,txtNope;
    ImageView img_user;
    String id,nama,key,gambar;
    Intent i;
    Firebase ref,refGambar;
    Bitmap bitmap;
    private Uri filePath;
    private StorageReference storageReference;
    private static final int PICK_IMAGE_REQUEST = 234;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil_user);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Firebase.setAndroidContext(this);
        storageReference = FirebaseStorage.getInstance().getReference();
        //storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://golek-feca2.appspot.com/");
        ref = new Firebase("https://golek-feca2.firebaseio.com/user");
        refGambar = new Firebase("https://golek-feca2.firebaseio.com/user/").child(BerandaActivity.key).child("gambar");
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
        fabGantiGambar = (FloatingActionButton) findViewById(R.id.fabEditGambar);
        fabEditOke = (FloatingActionButton) findViewById(R.id.fabEditOke);
        img_user = (ImageView) findViewById(R.id.img_user);
        txtId.setText(id);


        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()){

                    String idUser = child.child("id").getValue().toString();
                    String namaUser = child.child("nama").getValue().toString();
                    String gambar = child.child("gambar").getValue().toString();
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
                            showbyte(gambar);

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
        fabGantiGambar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Browse();
                fabEditOke.setVisibility(View.VISIBLE);
                //gambar = filePath.getLastPathSegment();
               // Toast.makeText(ProfilUserActivity.this, "gambar : "+gambar, Toast.LENGTH_LONG).show();
            }
        });
        fabEditOke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Firebase.setAndroidContext(v.getContext());
                try {
                    gambar = filePath.getLastPathSegment();
                    refGambar.setValue(filePath.getLastPathSegment());
                    uploadFile(filePath.getLastPathSegment());
                }catch (Exception e){
                    Toast.makeText(ProfilUserActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                }
                fabEditOke.setVisibility(View.INVISIBLE);
            }
        });

    }

    private void showbyte(String nama){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://golek-feca2.appspot.com/file/");
        StorageReference islandRef = storageRef.child(nama);
        final long ONE_MEGABYTE = 1024 * 1024;
        islandRef.getBytes(ONE_MEGABYTE).addOnSuccessListener(new OnSuccessListener<byte[]>() {
            @Override
            public void onSuccess(byte[] bytes) {
                Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                img_user.setImageBitmap(bitmap);
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
            Toast.makeText(ProfilUserActivity.this, e.toString(), Toast.LENGTH_LONG).show();
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
                    img_user.setImageBitmap(bitmap);
                    Toast.makeText(ProfilUserActivity.this," "+ resultCode, Toast.LENGTH_SHORT).show();

                    //gambar = filePath.getLastPathSegment();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            Toast.makeText(ProfilUserActivity.this, e.toString(), Toast.LENGTH_LONG).show();
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

            String pic = "file/" + nama;
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
