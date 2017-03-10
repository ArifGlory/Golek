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
import com.firebase.client.realtime.util.StringListReader;

import java.util.ArrayList;
import java.util.List;

import glory.golek.BerandaActivity;
import glory.golek.ChatActivity;
import glory.golek.ProfilFriendActivity;
import glory.golek.R;


/**
 * Created by Glory on 03/10/2016.
 */
public class RecycleAdapteraChat extends RecyclerView.Adapter<RecycleViewHolderChat> {


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

    public RecycleAdapteraChat(final Context context) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        Firebase.setAndroidContext(this.context);
        Gref = new Firebase("https://golek-feca2.firebaseio.com/chat");

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
                   // list_toId.add(to_id);
                    //list_nama.add(to_id);
                    String from_id = child.child("from_id").getValue().toString();
                    //list_fromID.add(from_id);
                    //list_nama.add(from_id);

                    if (from_id.equals(BerandaActivity.id) && to_id.equals(ChatActivity.to_id)
                            || to_id.equals(BerandaActivity.id) && from_id.equals(ChatActivity.to_id)){

                        list_fromID.add(from_id);
                        list_toId.add(to_id);
                        String pesan = child.child("pesan").getValue().toString();
                        list_pesan.add(pesan);
                        String waktu = child.child("time").getValue().toString();
                        list_time.add(waktu);
                    }

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
    public RecycleViewHolderChat onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_chat_message, parent, false);
        //View v = inflater.inflate(R.layout.item_list,parent,false);
        RecycleViewHolderChat viewHolderChat = new RecycleViewHolderChat(view);
        return viewHolderChat;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolderChat holder, int position) {

        Resources res = context.getResources();

        holder.txtMessage.setText(list_pesan.get(position).toString());
        //holder.contentWithBackground.setGravity(Gravity.LEFT);

        if (BerandaActivity.id.equals(list_fromID.get(position).toString())){
        holder.contentWithBackground.setBackground(res.getDrawable(R.drawable.out_message_bg));
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.contentWithBackground.getLayoutParams();
        layoutParams.gravity = Gravity.LEFT;
        holder.contentWithBackground.setLayoutParams(layoutParams);
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, 0);
        lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
        holder.content.setLayoutParams(lp);
        layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
        layoutParams.gravity = Gravity.LEFT;
        holder.txtMessage.setLayoutParams(layoutParams);
        layoutParams = (LinearLayout.LayoutParams) holder.txtNamaSender.getLayoutParams();
        layoutParams.gravity = Gravity.LEFT;
        holder.txtNamaSender.setLayoutParams(layoutParams);

            holder.txtNamaSender.setText("Saya");
        }else {
            holder.contentWithBackground.setBackgroundResource(R.drawable.in_message_bg);

            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.contentWithBackground.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.contentWithBackground.setLayoutParams(layoutParams);

            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) holder.content.getLayoutParams();
            lp.addRule(RelativeLayout.ALIGN_PARENT_LEFT, 0);
            lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            holder.content.setLayoutParams(lp);
            layoutParams = (LinearLayout.LayoutParams) holder.txtMessage.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.txtMessage.setLayoutParams(layoutParams);

            layoutParams = (LinearLayout.LayoutParams) holder.txtNamaSender.getLayoutParams();
            layoutParams.gravity = Gravity.RIGHT;
            holder.txtNamaSender.setLayoutParams(layoutParams);
            holder.txtNamaSender.setText("Teman");
        }



        holder.txtNamaSender.setOnClickListener(clicklistener);
        holder.txtMessage.setOnClickListener(clicklistener);
        holder.contentWithBackground.setOnClickListener(clicklistener);
        holder.content.setOnClickListener(clicklistener);


        holder.txtNamaSender.setTag(holder);
        holder.txtMessage.setTag(holder);
        holder.contentWithBackground.setTag(holder);
        holder.content.setTag(holder);

    }

    View.OnClickListener clicklistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            RecycleViewHolderChat vHolder = (RecycleViewHolderChat) v.getTag();
            int position = vHolder.getPosition();
            Toast.makeText(context.getApplicationContext(), "l_Nama : "+list_nama.get(position).toString(), Toast.LENGTH_SHORT).show();


        }
    };


    public int getItemCount() {

        return list_pesan == null ? 0 : list_pesan.size();
       // return nama.length;

    }

    public void cekNotif(){

       // Gref = new Firebase("https://golek-feca2.firebaseio.com/chat");
        try{
        Gref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {



                    String a =  dataSnapshot.child("to_id").getValue().toString();

                    if (a.equals(BerandaActivity.id)){

                        Toast.makeText(context.getApplicationContext(),"Ada pesan baru",Toast.LENGTH_SHORT).show();
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
