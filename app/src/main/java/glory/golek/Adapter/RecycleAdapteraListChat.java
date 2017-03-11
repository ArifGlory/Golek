package glory.golek.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import glory.golek.BerandaActivity;
import glory.golek.ChatActivity;
import glory.golek.R;


/**
 * Created by Glory on 03/10/2016.
 */
public class RecycleAdapteraListChat extends RecyclerView.Adapter<RecycleViewHolderListChat> {


    LayoutInflater inflater;
    Context context;
    Intent i;
    public static List<String> list_idLS= new ArrayList();
    public static List<String> list_namaLs = new ArrayList();
    public static List<String> list_gambarLs = new ArrayList();
    public static List<String> list_pesanLS = new ArrayList();
    public static List<String> list_pesan2LS = new ArrayList();
    public static List<String> list_fromIdLS = new ArrayList();
    public static List<String> list_toIdLS = new ArrayList();
    public static String namaCustomer;
    public static String tglCustomer;
    public static Stack stack_friendLS = new Stack();
    String key = "";
    Firebase Gref,refNama,refIcon;
    Bitmap bitmap;

    String[] nama ={"@arif","@pras","@tikul","@seijuro"};
    public RecycleViewHolderListChat viewHolderListChat;

    public RecycleAdapteraListChat(final Context context) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        Firebase.setAndroidContext(this.context);
        Gref = new Firebase("https://golek-feca2.firebaseio.com/chat");
        refNama = new Firebase("https://golek-feca2.firebaseio.com/user");
        try {
            Gref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    list_idLS.clear();
                    list_pesanLS.clear();
                    list_fromIdLS.clear();
                    list_toIdLS.clear();
                    list_pesan2LS.clear();

                       int jml = (int) dataSnapshot.getChildrenCount();
                    for (DataSnapshot child : dataSnapshot.getChildren()){
                        String to_id = child.child("to_id").getValue().toString();
                        String from_id = child.child("from_id").getValue().toString();

                            //algo mencari pesan terakir
                            if (to_id.equals(BerandaActivity.id)){

                                //list_fromIdLS.add(from_id);
                                if (!list_fromIdLS.contains(from_id)){
                                    list_fromIdLS.add(from_id);
                                    //Toast.makeText(context.getApplicationContext(), "Ini data tidak sama", Toast.LENGTH_SHORT).show();
                                }else {
                                    //list_fromIdLS.add(from_id);
                                }
                            }
                    }
                    Toast.makeText(context.getApplicationContext(), "Data berhasil diambil", Toast.LENGTH_SHORT).show();
                  //  Toast.makeText(context.getApplicationContext(), "Jumlah chat : "+jml, Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged();
                }
                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });

        }catch (Exception e){

        }

        try{

            refNama.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    list_gambarLs.clear();
                    for (DataSnapshot child : dataSnapshot.getChildren()){

                        String gambar = child.child("gambar").getValue().toString();
                        String uID = child.child("id").getValue().toString();
                        if (list_fromIdLS.contains(uID)){
                            list_gambarLs.add(gambar);
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
    public RecycleViewHolderListChat onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_listchat, parent, false);
        //View v = inflater.inflate(R.layout.item_list,parent,false);
        /*RecycleViewHolderListChat*/ viewHolderListChat = new RecycleViewHolderListChat(view);
        return viewHolderListChat;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolderListChat holder, int position) {


        holder.txtNamaUser.setText(list_fromIdLS.get(position).toString());
        holder.txtLastChat.setText(" ");
        try {
            showbyte(list_gambarLs.get(position).toString());
        }catch (Exception e){

        }
        //holder.img_iconlistchat.setImageResource(R.drawable.akun);

        holder.txtNamaUser.setOnClickListener(clicklistener);
        holder.txtLastChat.setOnClickListener(clicklistener);
        holder.img_iconlistchat.setOnClickListener(clicklistener);

        holder.txtLastChat.setTag(holder);
        holder.txtNamaUser.setTag(holder);
        holder.img_iconlistchat.setTag(holder);

    }

    View.OnClickListener clicklistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            RecycleViewHolderListChat vHolder = (RecycleViewHolderListChat) v.getTag();
            int position = vHolder.getPosition();
            //Toast.makeText(context.getApplicationContext(), "Kunci Cusnya : "+Glist_dari_keyCus.get(position).toString(), Toast.LENGTH_SHORT).show();
            i = new Intent(v.getContext(),ChatActivity.class);
           // i.putExtra("kunciCus",Glist_dari_keyCus.get(position).toString());
            i.putExtra("to_id",list_fromIdLS.get(position).toString());
            context.startActivity(i);



        }
    };


    public int getItemCount() {

        return list_fromIdLS == null ? 0 : list_fromIdLS.size();
       // return nama.length;

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
                viewHolderListChat.img_iconlistchat.setImageBitmap(bitmap);
            }
        });

    }


}
