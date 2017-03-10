package glory.golek.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import glory.golek.BerandaActivity;
import glory.golek.ProfilFriendActivity;
import glory.golek.R;


/**
 * Created by Glory on 03/10/2016.
 */
public class RecycleAdapteraListFriend extends RecyclerView.Adapter<RecycleViewHolderListFriend> {


    LayoutInflater inflater;
    Context context;
    Intent i;
    public static List<String> list_idfrnd = new ArrayList();
    public static List<String> list_namafrnd = new ArrayList();
    public static List<String> list_keyfrnd = new ArrayList();
    public static List<String> list_gambarfrnd = new ArrayList();
    public static List<String> list_emailfrnd = new ArrayList();
    public static List<String> list_alamatfrnd = new ArrayList();
    public static List<String> list_pmfrnd = new ArrayList();
    public static List<String> list_nopefrnd = new ArrayList();
    String key = "";
    Firebase Gref,refLagi;
    Bitmap bitmap;

    String[] nama ={"Friend 1 ","Friend 2","Friend 3"};

    public RecycleAdapteraListFriend(final Context context) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        Firebase.setAndroidContext(this.context);
        key = BerandaActivity.key;
        Gref = new Firebase("https://golek-feca2.firebaseio.com/user").child(key).child("zfriend");
        try {

            Gref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Toast.makeText(context.getApplicationContext(),"Anda terhubung ke server",Toast.LENGTH_SHORT).show();
                    list_idfrnd.clear();
                    list_namafrnd.clear();
                    list_keyfrnd.clear();
                    list_gambarfrnd.clear();

                    for (DataSnapshot child : dataSnapshot.getChildren()){
                        String id = child.child("id").getValue().toString();
                        list_idfrnd.add(id);
                        String nama = child.child("nama").getValue().toString();
                        list_namafrnd.add(nama);
                        String kunci = child.child("key").getValue().toString();
                        list_keyfrnd.add(kunci);
                        /*String email = child.child("email").getValue().toString();
                        list_emailfrnd.add(email);
                        String alamat = child.child("alamat").getValue().toString();
                        list_alamatfrnd.add(alamat);
                        String pm = child.child("pm").getValue().toString();
                        list_pmfrnd.add(pm);
                        String nope = child.child("nope").getValue().toString();
                        list_nopefrnd.add(nope);*/
                    }
                    notifyDataSetChanged();
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        }catch (Exception e){

        }


    }


    @Override
    public RecycleViewHolderListFriend onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_listfriend, parent, false);
        //View v = inflater.inflate(R.layout.item_list,parent,false);
        RecycleViewHolderListFriend viewHolderListFriend = new RecycleViewHolderListFriend(view);
        return viewHolderListFriend;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolderListFriend holder, int position) {


        holder.txtNamaUserFriend.setText(list_namafrnd.get(position).toString());
        holder.img_iconlistfriend.setImageResource(R.drawable.greencircle);

        holder.txtNamaUserFriend.setOnClickListener(clicklistener);
        holder.img_iconlistfriend.setOnClickListener(clicklistener);
        holder.relaFriend.setOnClickListener(clicklistener);

        holder.txtNamaUserFriend.setTag(holder);
        holder.img_iconlistfriend.setTag(holder);
        holder.relaFriend.setTag(holder);

    }

    View.OnClickListener clicklistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            RecycleViewHolderListFriend vHolder = (RecycleViewHolderListFriend) v.getTag();
            int position = vHolder.getPosition();
            //Toast.makeText(context.getApplicationContext(), "Kunci Cusnya : "+Glist_dari_keyCus.get(position).toString(), Toast.LENGTH_SHORT).show();
            i = new Intent(v.getContext(),ProfilFriendActivity.class);
            i.putExtra("key",list_keyfrnd.get(position).toString());
            i.putExtra("nama",list_namafrnd.get(position).toString());
            i.putExtra("id",list_idfrnd.get(position).toString());
            /*i.putExtra("email",list_emailfrnd.get(position).toString());
            i.putExtra("alamat",list_alamatfrnd.get(position).toString());
            i.putExtra("pm",list_pmfrnd.get(position).toString());
            i.putExtra("nope",list_nopefrnd.get(position).toString());*/
            context.startActivity(i);




        }
    };


    public int getItemCount() {

        return list_namafrnd == null ? 0 : list_namafrnd.size();
       // return nama.length;

    }


}
