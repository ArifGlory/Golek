package glory.golek.Adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import glory.golek.BerandaActivity;
import glory.golek.ChatActivity;
import glory.golek.ChatKomunitasActivity;
import glory.golek.R;


/**
 * Created by Glory on 03/10/2016.
 */
public class RecycleAdapteraChatKom extends RecyclerView.Adapter<RecycleViewHolderChatKom> {


    LayoutInflater inflater;
    Context context;
    Intent i;
    public static List<String> list_toId = new ArrayList();
    public static List<String> list_fromID= new ArrayList();
    public static List<String> list_pesan = new ArrayList();
    public static List<String> list_time = new ArrayList();
    public static List<String> list_nama = new ArrayList();
    public static List<String> list_toNew = new ArrayList();
    public static List<String> list_fromNew = new ArrayList();
    String key = "";
    Firebase Gref,refLagi;
    Bitmap bitmap;

    String[] nama ={"Pesan 1","Pesan 2","Pesan 3"};

    public RecycleAdapteraChatKom(final Context context) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        Firebase.setAndroidContext(this.context);
        Gref = new Firebase("https://golek-feca2.firebaseio.com/komunitas").child(ChatKomunitasActivity.keyKom).child("zchat");

        try {

        Gref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                list_toId.clear();
                list_fromID.clear();
                list_pesan.clear();
                list_time.clear();

                for (DataSnapshot child : dataSnapshot.getChildren()){
                    String to_id = child.child("to_id").getValue().toString();
                    String from_id = child.child("from_id").getValue().toString();
                    String pesan = child.child("pesan").getValue().toString();
                    String waktu = child.child("time").getValue().toString();

                        list_fromID.add(from_id);
                        list_toId.add(to_id);
                        list_pesan.add(pesan);
                        list_time.add(waktu);


                }
                Toast.makeText(context.getApplicationContext(), "Data berhasil diambil", Toast.LENGTH_SHORT).show();
                notifyDataSetChanged();

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
        }catch (Exception e){
            Log.e("eror chat : ",""+e);
        }
        cekNotif();
    }


    @Override
    public RecycleViewHolderChatKom onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chatkom_message, parent, false);
        //View v = inflater.inflate(R.layout.item_list,parent,false);
        RecycleViewHolderChatKom viewHolderChatKom = new RecycleViewHolderChatKom(view);
        return viewHolderChatKom;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolderChatKom holder, int position) {

        Resources res = context.getResources();

        holder.txtMessageKom.setText(list_pesan.get(position).toString());
        holder.txtTimeKom.setText(list_time.get(position).toString());
       // holder.txtMessageKom.setText(nama[position].toString());
        //holder.contentWithBackground.setGravity(Gravity.LEFT);


        if (BerandaActivity.id.equals(list_fromID.get(position).toString())){
        holder.contentWithBackgroundKom.setBackground(res.getDrawable(R.drawable.out_message_bg));
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.contentWithBackgroundKom.getLayoutParams();
        layoutParams.gravity = Gravity.LEFT;
        holder.contentWithBackgroundKom.setLayoutParams(layoutParams);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.contentKom.getLayoutParams();
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        holder.contentKom.setLayoutParams(lp);
        layoutParams = (LinearLayout.LayoutParams) holder.txtMessageKom.getLayoutParams();
        layoutParams.gravity = Gravity.LEFT;
        holder.txtMessageKom.setLayoutParams(layoutParams);
        layoutParams = (LinearLayout.LayoutParams) holder.txtNamaSenderKom.getLayoutParams();
        layoutParams.gravity = Gravity.LEFT;
        holder.txtNamaSenderKom.setLayoutParams(layoutParams);
            layoutParams = (LinearLayout.LayoutParams) holder.txtTimeKom.getLayoutParams();
            layoutParams.gravity = Gravity.LEFT;
            holder.txtTimeKom.setLayoutParams(layoutParams);
            holder.txtNamaSenderKom.setText("Saya");
        }else {
            holder.contentWithBackgroundKom.setBackgroundResource(R.drawable.in_message_bg);

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.contentWithBackgroundKom.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.contentWithBackgroundKom.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.contentKom.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.contentKom.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.txtMessageKom.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.txtMessageKom.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) holder.txtNamaSenderKom.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.txtNamaSenderKom.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) holder.txtTimeKom.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.txtTimeKom.setLayoutParams(layoutParams);

            holder.txtNamaSenderKom.setText(list_fromID.get(position).toString());
        }




        holder.txtNamaSenderKom.setOnClickListener(clicklistener);
        holder.txtTimeKom.setOnClickListener(clicklistener);
        holder.txtMessageKom.setOnClickListener(clicklistener);
        holder.contentWithBackgroundKom.setOnClickListener(clicklistener);
        holder.contentKom.setOnClickListener(clicklistener);


        holder.txtNamaSenderKom.setTag(holder);
        holder.txtMessageKom.setTag(holder);
        holder.txtTimeKom.setTag(holder);
        holder.contentWithBackgroundKom.setTag(holder);
        holder.contentKom.setTag(holder);

    }

    View.OnClickListener clicklistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            RecycleViewHolderChatKom vHolder = (RecycleViewHolderChatKom) v.getTag();
            int position = vHolder.getPosition();
            Toast.makeText(context.getApplicationContext(), "l_Nama : "+list_nama.get(position).toString(), Toast.LENGTH_SHORT).show();


        }
    };


    public int getItemCount() {

       return list_pesan == null ? 0 : list_pesan.size();
        //return nama.length;

    }

    public void cekNotif(){

       // Gref = new Firebase("https://golek-feca2.firebaseio.com/chat");
        try{
        Gref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {



                    String a =  dataSnapshot.child("to_id").getValue().toString();

                    if (a.equals(BerandaActivity.id)){

                        Toast.makeText(context.getApplicationContext(),"Ada pesan komunitas baru",Toast.LENGTH_SHORT).show();
                    }



            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });}
        catch (Exception e){

            Log.e("eror add child : ",""+e);
        }

    }


}
