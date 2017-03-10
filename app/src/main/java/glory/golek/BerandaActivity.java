package glory.golek;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ListFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import glory.golek.Fragment.FragmentMaps;
import glory.golek.Fragment.FriendFragment;
import glory.golek.Fragment.ListChatFragment;
import glory.golek.Fragment.ListUpdateFragment;

public class BerandaActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Intent i;
    DialogInterface.OnClickListener listener;
    TabLayout tabLayout;
    ImageView imgProfil;
    public static String nama,key,id;
    double lat,lon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Resources res = getResources();
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        imgProfil = (ImageView) findViewById(R.id.imageViewNavHeader);
        i = getIntent();
        nama = i.getStringExtra("nama");
        key = i.getStringExtra("key");
        id = i.getStringExtra("id");
       // lat = i.getDoubleExtra("lat",lat);
        //lon = i.getDoubleExtra("lon",lon);



        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_map_black_36dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_mode_comment_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_update_black_24dp));
        tabLayout.setTabTextColors(res.getColor(R.color.colorHitam),res.getColor(R.color.colorAccent));

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int pos = tab.getPosition();
              //  String judul = tab.getText().toString();
                if (pos == 0){

                    FragmentMaps mapsFragment = new FragmentMaps();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmen_home, mapsFragment);
                    fragmentTransaction.commit();

                }else if (pos == 1){

                    ListChatFragment listChatFragment= new ListChatFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmen_home, listChatFragment);
                    fragmentTransaction.commit();

                }else if (pos == 2){

                    ListUpdateFragment listUpdateFragment= new ListUpdateFragment();
                    android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.fragmen_home, listUpdateFragment);
                    fragmentTransaction.commit();

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
      /*  imgProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(BerandaActivity.this,ProfilFriendActivity.class);
                startActivity(i);
            }
        });*/

        FragmentMaps mapsFragment = new FragmentMaps();
        android.support.v4.app.FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.fragmen_home, mapsFragment);
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        ImageView imgProfil = (ImageView) headerView.findViewById(R.id.imageViewNavHeader);
        TextView txtProfil = (TextView) headerView.findViewById(R.id.txtViewNavHeader);
        txtProfil.setText(nama);
        imgProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(BerandaActivity.this,ProfilUserActivity.class);
                i.putExtra("id",id);
                i.putExtra("nama",nama);
                startActivity(i);

            }
        });
        txtProfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i = new Intent(BerandaActivity.this,ProfilUserActivity.class);
                i.putExtra("id",id);
                i.putExtra("nama",nama);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        /*DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }*/
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Apakan anda tetap ingin menutup aplikasi?");
        builder.setCancelable(false);

        listener = new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(which == DialogInterface.BUTTON_POSITIVE){
                    finishAffinity();
                    System.exit(0);
                }

                if(which == DialogInterface.BUTTON_NEGATIVE){
                    dialog.cancel();
                }
            }
        };
        builder.setPositiveButton("Ya",listener);
        builder.setNegativeButton("Tidak", listener);
        builder.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.beranda, menu);
        return true;
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_komunitas) {
            // Handle the camera action
            i = new Intent(this,ListKomunitasActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_friend) {

            i = new Intent(this,ListFriendActivity.class);
            startActivity(i);

        } else if (id == R.id.nav_logout) {
            i = new Intent(this,MainActivity.class);
            startActivity(i);
        }else if (id == R.id.nav_undangan){

            i = new Intent(this,UndanganActivity.class);
            startActivity(i);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
