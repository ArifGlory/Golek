package glory.golek.Fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.StringDef;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
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

import java.util.ArrayList;
import java.util.List;

import glory.golek.BerandaActivity;
import glory.golek.ProfilFriendActivity;
import glory.golek.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendFragment extends Fragment implements OnMapReadyCallback {


    public FriendFragment() {
        // Required empty public constructor
    }

    Firebase ref, refZfriend;
    private GoogleMap mMap;
    public Marker marker_ghost;
    private Button btn_filter, btnfilter_friend, btnfilter_komunitas;
    LinearLayout lineFilter;
    private Animation fab_open, fab_close, rotate_forward, rotate_backward;
    private Boolean isFabOpen = false;
    private int pos;
    Intent i;
    public static List<String> list_idMapsFriend = new ArrayList();
    private int iconmarker = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_friend, container, false);
        Firebase.setAndroidContext(this.getActivity());
        ref = new Firebase("https://golek-feca2.firebaseio.com/user");
        refZfriend = new Firebase("https://golek-feca2.firebaseio.com/user").child(BerandaActivity.key).child("zfriend");
        lineFilter = (LinearLayout) view.findViewById(R.id.line_btnFilter);
        btn_filter = (Button) view.findViewById(R.id.btnFilter);
        btnfilter_friend = (Button) view.findViewById(R.id.btnfilter_Friend);
        btnfilter_komunitas = (Button) view.findViewById(R.id.btnfilter_komunitas);
        fab_open = AnimationUtils.loadAnimation(view.getContext(), R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(view.getContext(), R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(view.getContext(), R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(view.getContext(), R.anim.rotate_backward);
        final FragmentManager manager = getFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        SupportMapFragment fragment = new SupportMapFragment();
        transaction.add(R.id.mapViewfriend, fragment);
        transaction.commit();
        pos = 0;

        fragment.getMapAsync(this);

        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animateFB();
                //lineFilter.setVisibility(v.VISIBLE);
            }
        });
        btnfilter_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FriendFragment friendFragment = new FriendFragment();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmen_home, friendFragment);
                fragmentTransaction.commit();
                lineFilter.setVisibility(v.INVISIBLE);
            }
        });
        btnfilter_komunitas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentMaps mapsFragment = new FragmentMaps();
                android.support.v4.app.FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmen_home, mapsFragment);
                fragmentTransaction.commit();
                lineFilter.setVisibility(v.INVISIBLE);
            }
        });
        refZfriend.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    String idUM = (String) child.child("id").getValue();
                    list_idMapsFriend.add(idUM);

                    // Toast.makeText(getActivity().getApplication(),"listid : "+list_idMapsFriend.get(pos).toString()+"&pos:"+pos,Toast.LENGTH_SHORT).show();
                    //Toast.makeText(getActivity().getApplication(),"itungan nomer child : "+child.getChildrenCount(),Toast.LENGTH_SHORT).show();
                    pos++;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


        return view;
    }


    public void animateFB() {

        if (isFabOpen) {

            lineFilter.startAnimation(rotate_backward);
            lineFilter.startAnimation(fab_close);
            // fab2.startAnimation(fab_close);
            lineFilter.setClickable(false);
            //fab2.setClickable(false);
            isFabOpen = false;
            Log.d("fab", "close");

        } else {

            lineFilter.startAnimation(rotate_forward);
            lineFilter.startAnimation(fab_open);
            //fab2.startAnimation(fab_open);
            lineFilter.setClickable(true);
            //fab2.setClickable(true);
            isFabOpen = true;
            Log.d("fab", "open");

        }

    }


    public void ambil() {

        try {

            ref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (mMap != null) {
                        mMap.clear();
                    }

                    iconmarker = R.drawable.marker_user;
                    for (int c = 0; c < list_idMapsFriend.size(); c++) {

                        for (DataSnapshot child : dataSnapshot.getChildren()) {
                            String idUser = (String) child.child("id").getValue();

                            if (idUser.equals(list_idMapsFriend.get(c).toString())) {

                                String username = (String) child.child("nama").getValue();
                                String key = (String) child.getKey();
                                Double latt = (Double) child.child("lat").getValue();
                                Double lonn = (Double) child.child("lon").getValue();
                                LatLng FlLokasi = new LatLng(latt, lonn);

                                mMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(iconmarker)).position(FlLokasi).title(username)).setSnippet(idUser);
                                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(FlLokasi, 17));
                            }

                        }
                    }


                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        } catch (Exception e) {
            Log.e("Eror Maps Ambildata", "Erornya : " + e);
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;


        mMap.clear();
        LatLng lampung = new LatLng(-5.382351, 105.257791);
        LatLng sydney = new LatLng(-34, 151);
        //mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        CircleOptions mOptions = new CircleOptions()
                .center(sydney).radius(100)
                .strokeColor(0x110000FF).strokeWidth(8).fillColor(0x110000FF);
        mMap.addCircle(mOptions);

       // mMap.addMarker(new MarkerOptions().position(sydney).title("naruto"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 17));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));

        Toast.makeText(getActivity().getApplication(),"Mengambil lokasi..." ,Toast.LENGTH_LONG).show();
        ambil();


        mMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
            @Override
            public void onInfoWindowClick(final Marker marker) {

                     marker_ghost = marker;
               LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
               View v = inflater.inflate(R.layout.dialog_profil_friend, null);
               AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
               alertDialogBuilder.setView(v);
               TextView txt_namaProfil = (TextView) v.findViewById(R.id.user_profile_name);
               TextView txt_id = (TextView) v.findViewById(R.id.user_profile_short_bio);


               txt_namaProfil.setText(marker.getTitle());
                txt_id.setText(marker.getSnippet());
                final String nm = txt_namaProfil.getText().toString();
                final String idKirim = txt_id.getText().toString();

                ImageButton img_profil = (ImageButton) v.findViewById(R.id.user_profile_photo);
               AlertDialog alert = alertDialogBuilder.create();
               alert.show();


                img_profil.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        i = new Intent(getActivity(), ProfilFriendActivity.class);
                        i.putExtra("id",idKirim);
                        i.putExtra("nama",nm);
                        startActivity(i);
                    }
                });
              /* btnKeProfil.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                         /* i = new Intent(getActivity(),ProfilUserActivity.class);
                          i.putExtra("userKirim",marker.getTitle());
                          //i.putExtra("key",marker.getSnippet());
                            startActivity(i);
                   }
               });
            */


            }
        });
    }
}
