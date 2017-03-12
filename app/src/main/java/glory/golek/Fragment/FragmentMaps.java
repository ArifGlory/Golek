package glory.golek.Fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.List;

import glory.golek.BerandaActivity;
import glory.golek.ChatActivity;
import glory.golek.Kelas.TambahAnggotaClass;
import glory.golek.Kelas.TambahKomunitasClass;
import glory.golek.ProfilFriendActivity;
import glory.golek.ProfilKomunitasActivity;
import glory.golek.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMaps extends Fragment implements OnMapReadyCallback {


    public FragmentMaps() {
        // Required empty public constructor
    }

    Firebase ref, refMyKomunitas, refAnggota;
    private GoogleMap mMap;
    public Marker marker_ghost;
    Intent i;
    private String keyKom, alamat, email, keyKirim;
    private Button btn_filter, btnfilter_komunitas, btnfilter_friend;
    private LinearLayout linefilter;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    private Boolean isFabOpen = false;
    int iconMarker = 0;
    public static List<String> list_idMyKom = new ArrayList();
    public static List<String> list_idKom = new ArrayList();
    public static List<String> list_keyKom = new ArrayList();
    public static List<String> list_anggotaKom = new ArrayList();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_maps, container, false);
        Firebase.setAndroidContext(this.getActivity());
        ref = new Firebase("https://golek-feca2.firebaseio.com/komunitas");
        refMyKomunitas = new Firebase("https://golek-feca2.firebaseio.com/user").child(BerandaActivity.key).child("zmykomunitas");

        btn_filter = (Button) view.findViewById(R.id.btnFilter);
        btnfilter_friend = (Button) view.findViewById(R.id.btnfilter_Friend);
        btnfilter_komunitas = (Button) view.findViewById(R.id.btnfilter_komunitas);
        linefilter = (LinearLayout) view.findViewById(R.id.line_btnFilter);
        fab_open = AnimationUtils.loadAnimation(view.getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(view.getContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(view.getContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(view.getContext(), R.anim.rotate_backward);
        keyKom = " ";
        keyKirim = " ";
        final FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        final SupportMapFragment fragment = new SupportMapFragment();
        transaction.add(R.id.mapView, fragment);
        transaction.commit();

        fragment.getMapAsync(this);

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFB();
                //linefilter.setVisibility(v.VISIBLE);

            }
        });
        btnfilter_komunitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentMaps mapsFragment = new FragmentMaps();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmen_home, mapsFragment);
                fragmentTransaction.commit();
                linefilter.setVisibility(v.INVISIBLE);
            }
        });
        btnfilter_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendFragment friendFragment = new FriendFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmen_home, friendFragment);
                fragmentTransaction.commit();
                linefilter.setVisibility(v.INVISIBLE);
            }
        });

        try {
            refMyKomunitas.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    list_idMyKom.clear();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        String id_kom = child.child("id").getValue().toString();
                        list_idMyKom.add(id_kom);
                        //Toast.makeText(getActivity().getApplication(),""+id_kom ,Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }


        /*

        */


        return view;
    }

    public void animateFB() {

        if (isFabOpen) {

            linefilter.startAnimation(rotate_backward);
            linefilter.startAnimation(fab_close);
            // fab2.startAnimation(fab_close);
            linefilter.setClickable(false);
            //fab2.setClickable(false);
            isFabOpen = false;
            Log.d("fab", "close");

        } else {

            linefilter.startAnimation(rotate_forward);
            linefilter.startAnimation(fab_open);
            //fab2.startAnimation(fab_open);
            linefilter.setClickable(true);
            //fab2.setClickable(true);
            isFabOpen = true;
            Log.d("fab", "open");

        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        mMap.clear();
        LatLng lampung = new LatLng(-5.382351, 105.257791);
        //mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CircleOptions mOptions = new CircleOptions()
                .center(lampung).radius(100)
                .strokeColor(0x110000FF).strokeWidth(8).fillColor(0x110000FF);
        mMap.addCircle(mOptions);

      //  mMap.addMarker(new MarkerOptions().position(lampung).title("lokasi"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lampung, 17));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lampung));

        Toast.makeText(getActivity().getApplication(),"Mengambil lokasi..." ,Toast.LENGTH_LONG).show();
        ambil();


       mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
           @Override
           public void onInfoWindowClick(final Marker marker) {

               marker_ghost = marker;
               LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
               View v = inflater.inflate(R.layout.dialog_profil_komunitas, null);
               AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
               alertDialogBuilder.setView(v);
               final TextView txt_namaKomunitas = (TextView) v.findViewById(R.id.txt_dialogNamaKom);
               final TextView txt_id = (TextView) v.findViewById(R.id.txt_dialogTag);
               ImageButton btn_keProfilKom = (ImageButton) v.findViewById(R.id.btn_keProfilKom);
               final Button btn_join = (Button) v.findViewById(R.id.btn_dialogJoinKom);

               txt_namaKomunitas.setText(marker.getTitle());
               txt_id.setText(marker.getSnippet());
               final String nm = txt_namaKomunitas.getText().toString();
               final String idKirim = txt_id.getText().toString();
               //kasih if idsini
               if (!list_idMyKom.contains(marker.getSnippet())){
                   btn_join.setVisibility(View.VISIBLE);
               }

               final AlertDialog alert = alertDialogBuilder.create();
               alert.show();

               btn_keProfilKom.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {


                       for (int c=0;c<list_idKom.size();c++){

                           if (list_idKom.get(c).toString().equals(txt_id.getText())){
                               keyKirim = list_keyKom.get(c).toString();
                           }

                       }

                       //ambil liat anggota
                       try {
                       refAnggota = new Firebase("https://golek-feca2.firebaseio.com/komunitas").child(keyKirim).child("zanggota");
                       refAnggota.addValueEventListener(new ValueEventListener() {
                           @Override
                           public void onDataChange(DataSnapshot dataSnapshot) {

                               list_anggotaKom.clear();
                               for (DataSnapshot child : dataSnapshot.getChildren()){

                                   String idAnggota = child.child("id").getValue().toString();
                                   list_anggotaKom.add(idAnggota);
                              //     Toast.makeText(getActivity(), "anggota : "+idAnggota, Toast.LENGTH_LONG).show();
                               }
                           }

                           @Override
                           public void onCancelled(FirebaseError firebaseError) {

                           }
                       });}
                       catch (Exception e){
                           e.printStackTrace();
                       }

                       i = new Intent(getActivity(), ProfilKomunitasActivity.class);
                       i.putExtra("id",idKirim);
                       i.putExtra("nama",nm);
                       i.putExtra("key",keyKirim);
                       startActivity(i);
                       //Toast.makeText(getActivity().getApplication(),"key Kom : "+keyKirim,Toast.LENGTH_SHORT).show();
                   }
               });
               btn_join.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                       TambahKomunitasClass tambahKomunitas = new TambahKomunitasClass(txt_id.getText().toString(),txt_namaKomunitas.getText().toString());
                       TambahAnggotaClass tambahAnggota = new TambahAnggotaClass(BerandaActivity.id,BerandaActivity.nama);
                       refMyKomunitas.push().setValue(tambahKomunitas);

                       for (int c=0;c<list_idKom.size();c++){

                           if (list_idKom.get(c).toString().equals(txt_id.getText())){
                               keyKom = list_keyKom.get(c).toString();
                           }

                       }

                       ref.child(keyKom).child("zanggota").child(BerandaActivity.key).setValue(tambahAnggota);
                       btn_join.setVisibility(View.INVISIBLE);
                       //Toast.makeText(getActivity().getApplication(),"Kamu join ke : "+marker.getTitle() ,Toast.LENGTH_LONG).show();
                       alert.dismiss();
                   }
               });


           }
       });


    }


    public void sendLocation(){



    }



    public void ambil() {

        try {

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (mMap != null) {
                        mMap.clear();
                    }

                    list_idKom.clear();
                    list_keyKom.clear();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {

                            String username = (String) child.child("nama").getValue();
                            String kunci = (String) child.getKey();
                            String id_komunitas = child.child("id").getValue().toString();
                            String tag = child.child("tagline").getValue().toString();
                            Double latt = (Double) child.child("lat").getValue();
                            Double lonn = (Double) child.child("lon").getValue();
                            String tipe = child.child("tipe").getValue().toString();
                            list_idKom.add(id_komunitas);
                            list_keyKom.add(kunci);
                            LatLng FlKomoditi = new LatLng(latt, lonn);
                            //Toast.makeText(getActivity().getApplication(),""+FlKomoditi ,Toast.LENGTH_LONG).show();

                            switch (tipe){

                                case "Komputer" :
                                    iconMarker = R.drawable.marker_komputer;
                                    break;
                                case "Olahraga" :
                                    iconMarker = R.drawable.mark_olahraga;
                                    break;
                                case "Rohani" :
                                    iconMarker = R.drawable.marker_rohani;
                                    break;
                                case "Sosial" :
                                    iconMarker = R.drawable.marker_sosial;
                                    break;
                                case "Budaya" :
                                    iconMarker = R.drawable.marker_culturer;
                                    break;
                                case "Other" :
                                    iconMarker = R.drawable.marker_other;
                                    break;
                            }

                            mMap.addMarker(new MarkerOptions().position(FlKomoditi).icon(BitmapDescriptorFactory.fromResource(iconMarker)).title(username)).setSnippet(id_komunitas);
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(FlKomoditi, 17));


                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });


        }catch (Exception e ){
            Log.e("Eror Maps Ambildata","Erornya : "+e);
            Toast.makeText(getActivity().getApplication(),"Gagal mengambil lokasi : "+e.toString() ,Toast.LENGTH_LONG).show();
        }

    }




}
