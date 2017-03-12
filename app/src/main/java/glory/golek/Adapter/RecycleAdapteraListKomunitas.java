package glory.golek.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import glory.golek.BerandaActivity;
import glory.golek.ProfilKomunitasActivity;
import glory.golek.R;


/**
 * Created by Glory on 03/10/2016.
 */
public class RecycleAdapteraListKomunitas extends RecyclerView.Adapter<RecycleViewHolderListKomunitas> {


    LayoutInflater inflater;
    Context context;
    Intent i;
    public static List<String> list_idKom = new ArrayList();
    public static List<String> list_keyKom = new ArrayList();
    public static List<String> list_namaKom = new ArrayList();
    public static List<String> list_gambarKom = new ArrayList();
    public static String namaCustomer;
    public static String tglCustomer;
    String key = "";
    Firebase Gref,refKom;
    Bitmap bitmap;

    String[] nama ={"Komunitas 1 ","Komunitas 2","Komunitas 3"};

    public RecycleAdapteraListKomunitas(final Context context) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        Firebase.setAndroidContext(this.context);
        Gref = new Firebase("https://golek-feca2.firebaseio.com/user").child(BerandaActivity.key).child("zmykomunitas");
        refKom = new Firebase("https://golek-feca2.firebaseio.com/komunitas");

        try{
        Gref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                list_idKom.clear();
                list_namaKom.clear();
                list_gambarKom.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()){

                    String id = child.child("id").getValue().toString();
                    list_idKom.add(id);
                    String nama = child.child("nama").getValue().toString();
                    list_namaKom.add(nama);

                }

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });}
        catch (Exception e){

        }

        try{
            refKom.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    for (DataSnapshot child : dataSnapshot.getChildren()){

                        String id_kom = child.child("id").getValue().toString();
                        String key_kom = child.getKey();
                        if (list_idKom.contains(id_kom)){
                            list_keyKom.add(key_kom);
                        }


                    }

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        }catch (Exception e){


        }

    }


    @Override
    public RecycleViewHolderListKomunitas onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_listkomunitas, parent, false);
        //View v = inflater.inflate(R.layout.item_list,parent,false);
        RecycleViewHolderListKomunitas viewHolderListKomunitas = new RecycleViewHolderListKomunitas(view);
        return viewHolderListKomunitas;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolderListKomunitas holder, int position) {


        holder.txtNamaKomunitas.setText(list_namaKom.get(position).toString());
        holder.txtTagKomunitas.setText("Tagline Komunitas");
        holder.img_iconlistkomunitas.setImageResource(R.drawable.ic_people_black_24dp);

        holder.txtNamaKomunitas.setOnClickListener(clicklistener);
        holder.txtTagKomunitas.setOnClickListener(clicklistener);
        holder.img_iconlistkomunitas.setOnClickListener(clicklistener);

        holder.txtTagKomunitas.setTag(holder);
        holder.txtNamaKomunitas.setTag(holder);
        holder.img_iconlistkomunitas.setTag(holder);

    }

    View.OnClickListener clicklistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            RecycleViewHolderListKomunitas vHolder = (RecycleViewHolderListKomunitas) v.getTag();
            int position = vHolder.getPosition();
           // Toast.makeText(context.getApplicationContext(), "Kunci  : "+list_keyKom.get(position).toString(), Toast.LENGTH_SHORT).show();
            i = new Intent(v.getContext(),ProfilKomunitasActivity.class);
            i.putExtra("id",list_idKom.get(position).toString());
            i.putExtra("nama",list_namaKom.get(position).toString());
            i.putExtra("key",list_keyKom.get(position).toString());
            context.startActivity(i);



        }
    };


    public int getItemCount() {

        return list_idKom == null ? 0 : list_idKom.size();
        //return nama.length;

    }


}
