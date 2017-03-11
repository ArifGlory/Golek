package glory.golek.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import glory.golek.BerandaActivity;
import glory.golek.Kelas.IsiDataUser;
import glory.golek.Kelas.TambahTemanClass;
import glory.golek.ListFriendActivity;
import glory.golek.R;


/**
 * Created by Glory on 03/10/2016.
 */
public class RecycleAdapteraListUndangan extends RecyclerView.Adapter<RecycleViewHolderListUndangan> {


    LayoutInflater inflater;
    Context context;
    Intent i;
    public static List<String> list_idudn = new ArrayList();
    public static List<String> list_namaudn = new ArrayList();
    public static List<String> list_keyudn = new ArrayList();
    public static List<String> list_kunciInvite = new ArrayList();
    String key = "";
    Firebase Gref,GrefFriend,GrefFriendFrom;
    Bitmap bitmap;
    AlertDialog alert;

    String[] nama ={"Friend 1 ","Friend 2","Friend 3"};

    public RecycleAdapteraListUndangan(final Context context) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        Firebase.setAndroidContext(this.context);
        key = BerandaActivity.key;
        Gref = new Firebase("https://golek-feca2.firebaseio.com/user").child(key).child("zinvite");
        GrefFriend = new Firebase("https://golek-feca2.firebaseio.com/user").child(key).child("zfriend");


        Toast.makeText(context.getApplicationContext(), "Mengambil data...", Toast.LENGTH_LONG).show();
        try {
            Gref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    //Toast.makeText(context.getApplicationContext(),"Anda terhubung ke server",Toast.LENGTH_SHORT).show();
                    list_idudn.clear();
                    list_namaudn.clear();
                    list_keyudn.clear();
                    list_kunciInvite.clear();
                    for (DataSnapshot child : dataSnapshot.getChildren()){
                    String id = child.child("id").getValue().toString();
                        list_idudn.add(id);
                        String nama = child.child("nama").getValue().toString();
                        list_namaudn.add(nama);
                        String kunci = child.child("key").getValue().toString();
                        list_keyudn.add(kunci);
                        String keyInvite = child.getKey();
                        list_kunciInvite.add(keyInvite);
                    }
                    notifyDataSetChanged();

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        }catch (Exception e){
            Toast.makeText(context.getApplicationContext(), "gagal ambil data : "+e.toString(), Toast.LENGTH_LONG).show();
        }

    }


    @Override
    public RecycleViewHolderListUndangan onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_listundangan, parent, false);
        //View v = inflater.inflate(R.layout.item_list,parent,false);
        RecycleViewHolderListUndangan viewHolderListUndangan = new RecycleViewHolderListUndangan(view);
        return viewHolderListUndangan;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolderListUndangan holder, int position) {


        holder.txtNamaUserUndagan.setText(list_namaudn.get(position).toString());

        holder.txtNamaUserUndagan.setOnClickListener(clicklistener);
        holder.rela_listUndangan.setOnClickListener(clicklistener);

        holder.txtNamaUserUndagan.setTag(holder);
        holder.rela_listUndangan.setTag(holder);


    }

    View.OnClickListener clicklistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            RecycleViewHolderListUndangan vHolder = (RecycleViewHolderListUndangan) v.getTag();
            final int position = vHolder.getPosition();
            Toast.makeText(context.getApplicationContext(), "Kunci Cusnya : "+list_kunciInvite.get(position).toString(), Toast.LENGTH_SHORT).show();

            LayoutInflater inflater = (LayoutInflater) v.getContext().getSystemService(v.getContext().LAYOUT_INFLATER_SERVICE);
            View tampilan = inflater.inflate(R.layout.dialog_acc_friend, null);
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(v.getContext());
            alertDialogBuilder.setView(tampilan);

            TextView txt_namaAcc = (TextView) tampilan.findViewById(R.id.user_profile_nameAcc);
            txt_namaAcc.setText(list_namaudn.get(position).toString());
            Button btn_acc = (Button) tampilan.findViewById(R.id.btn_dialogaccTeman);
            Button btn_ignore = (Button) tampilan.findViewById(R.id.btn_dialogtolakTeman);
            btn_acc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GrefFriendFrom = new Firebase("https://golek-feca2.firebaseio.com/user").child(list_keyudn.get(position).toString()).child("zfriend");
                    TambahTemanClass tambahTeman = new TambahTemanClass(list_idudn.get(position).toString(),list_namaudn.get(position).toString(),
                            list_keyudn.get(position).toString());
                    TambahTemanClass tambahFrom = new TambahTemanClass(BerandaActivity.id,BerandaActivity.nama,BerandaActivity.key);

                    //GrefFriend.child(list_keyudn.get(position).toString()).push().setValue(tambahTeman);
                    //GrefFriendFrom.child(BerandaActivity.key).push().setValue(tambahFrom);
                    GrefFriend.child(list_keyudn.get(position).toString()).setValue(tambahTeman);
                    GrefFriendFrom.child(BerandaActivity.key).setValue(tambahFrom);

                    Gref.child(list_kunciInvite.get(position).toString()).setValue(null);
                    Toast.makeText(context.getApplicationContext(), "Ditambahkan", Toast.LENGTH_SHORT).show();
                    alert.dismiss();

                }
            });
            btn_ignore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Gref.child(list_kunciInvite.get(position).toString()).setValue(null);
                    Toast.makeText(context.getApplicationContext(), "Ditolak", Toast.LENGTH_SHORT).show();
                    alert.dismiss();
                }
            });

            alert = alertDialogBuilder.create();
            alert.show();




        }
    };


    public int getItemCount() {

        return list_namaudn == null ? 0 : list_namaudn.size();
        //return nama.length;

    }


}
