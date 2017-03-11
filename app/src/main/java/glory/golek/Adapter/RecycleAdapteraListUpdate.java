package glory.golek.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import glory.golek.BerandaActivity;
import glory.golek.Fragment.ListUpdateFragment;
import glory.golek.R;


/**
 * Created by Glory on 03/10/2016.
 */
public class RecycleAdapteraListUpdate extends RecyclerView.Adapter<RecycleViewHolderListUpdate> {


    LayoutInflater inflater;
    Context context;
    Intent i;
    public static List<String> list_keyTL = new ArrayList();
    public static List<String> list_idTL = new ArrayList();
    public static List<String> list_pmTL = new ArrayList();
    public static List<String> list_gambarTL = new ArrayList();
    public static List<String> list_statusTL = new ArrayList();
    public static List<String> list_namaTL = new ArrayList();
    public static List<String> list_friendTL = new ArrayList();


    public static Stack stack_pmTL = new Stack();
    public static Stack stack_keyTL = new Stack();
    public static Stack stack_idTL = new Stack();
    public static Stack stack_gambarTL = new Stack();
    public static Stack stack_statusTL = new Stack();
    public static Stack stack_namaTL = new Stack();
    public static Stack stack_friendTL = new Stack();
    Firebase Gref,refLagi,refZfriend;
    Bitmap bitmap;

    String[] nama ={"update 1 ","update 2","update 3"};

    public RecycleAdapteraListUpdate(final Context context) {

        this.context = context;
        inflater = LayoutInflater.from(context);
        Firebase.setAndroidContext(this.context);
        Gref = new Firebase("https://golek-feca2.firebaseio.com/timeline");
        refZfriend = new Firebase("https://golek-feca2.firebaseio.com/user").child(BerandaActivity.key).child("zfriend");

        try {

            refZfriend.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    list_friendTL.clear();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        String idUM = (String) child.child("id").getValue();
                        list_friendTL.add(idUM);
                        stack_friendTL.push(idUM);

                    }
                    list_friendTL.add(BerandaActivity.id);
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }catch (Exception e){
           // Toast.makeText(context.getApplicationContext(), "eror refZfriend :"+e.toString(), Toast.LENGTH_SHORT).show();
        }
        //list_friendTL.add(BerandaActivity.id);

        try {


            Gref.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    list_gambarTL.clear();
                    list_idTL.clear();
                    list_keyTL.clear();
                    list_pmTL.clear();
                    list_statusTL.clear();
                    list_namaTL.clear();

                    stack_pmTL.clear();
                    stack_gambarTL.clear();
                    stack_idTL.clear();
                    stack_keyTL.clear();
                    stack_statusTL.clear();
                    stack_namaTL.clear();

                    for (int c=0;c<list_friendTL.size();c++) {

                        for (DataSnapshot child : dataSnapshot.getChildren()) {

                            String idTL = child.child("id").getValue().toString();
                            if (idTL.equals(list_friendTL.get(c).toString())) {

                                String id = child.child("id").getValue().toString();
                                list_idTL.add(id);
                                stack_idTL.push(id);
                                String statusTL = child.child("status").getValue().toString();
                                list_statusTL.add(statusTL);
                                stack_statusTL.push(statusTL);
                                String kunci = child.child("key").getValue().toString();
                                list_keyTL.add(kunci);
                                stack_keyTL.push(kunci);
                                String gambar = child.child("gambar").getValue().toString();
                                list_gambarTL.add(gambar);
                                stack_gambarTL.push(gambar);
                                String pm = child.child("pm").getValue().toString();
                                list_pmTL.add(pm);
                                stack_pmTL.push(pm);
                                String anam = child.child("nama").getValue().toString();
                                list_namaTL.add(anam);
                                stack_namaTL.push(anam);

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
                    }

                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
        }catch (Exception e){

            Log.e("salah get data nya : "," "+e);
           // Toast.makeText(context.getApplicationContext(), "error ref  :"+e.toString(), Toast.LENGTH_SHORT).show();
        }


    }


    @Override
    public RecycleViewHolderListUpdate onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemlist_listupdate, parent, false);
        //View v = inflater.inflate(R.layout.item_list,parent,false);
        RecycleViewHolderListUpdate viewHolderListUpdate = new RecycleViewHolderListUpdate(view);
        return viewHolderListUpdate;
    }

    @Override
    public void onBindViewHolder(RecycleViewHolderListUpdate holder, int position) {


        try {
            holder.txtNamaUserUpdate.setText(stack_namaTL.pop().toString());
            holder.txtTipeUpdate.setText(stack_statusTL.pop().toString());
            //holder.img_dpUpdate.setImageResource(R.drawable.greencircle);
            // holder.img_userUpdate.setImageResource(R.drawable.greencircle);
            //holder.txtPMUpdate.setText(list_pmTL.get(position).toString());
            holder.txtPMUpdate.setText(stack_pmTL.pop().toString());
        }catch (Exception e){
           // Toast.makeText(context.getApplicationContext(), "eror holder :"+e.toString(), Toast.LENGTH_SHORT).show();
        }

        holder.txtNamaUserUpdate.setOnClickListener(clicklistener);
        holder.txtPMUpdate.setOnClickListener(clicklistener);
        holder.img_dpUpdate.setOnClickListener(clicklistener);
        holder.img_userUpdate.setOnClickListener(clicklistener);
        holder.txtTipeUpdate.setOnClickListener(clicklistener);

        holder.txtTipeUpdate.setTag(holder);
        holder.txtPMUpdate.setTag(holder);
        holder.txtNamaUserUpdate.setTag(holder);
        holder.img_dpUpdate.setTag(holder);
        holder.img_userUpdate.setTag(holder);

    }

    View.OnClickListener clicklistener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {

            RecycleViewHolderListUpdate vHolder = (RecycleViewHolderListUpdate) v.getTag();
            int position = vHolder.getPosition();
            //Toast.makeText(context.getApplicationContext(), "Kunci Cusnya : "+Glist_dari_keyCus.get(position).toString(), Toast.LENGTH_SHORT).show();
            /*i = new Intent(v.getContext(),DetailPemesananActivity.class);
            i.putExtra("kunciCus",Glist_dari_keyCus.get(position).toString());
            context.startActivity(i);*/



        }
    };


    public int getItemCount() {

        return list_idTL == null ? 0 : list_idTL.size();
        //return nama.length;

    }


}
