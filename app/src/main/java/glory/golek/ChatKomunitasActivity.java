package glory.golek;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.firebase.client.Firebase;

import java.util.Calendar;

import glory.golek.Adapter.RecycleAdapteraChat;
import glory.golek.Adapter.RecycleAdapteraChatKom;
import glory.golek.Kelas.ChatModel;

public class ChatKomunitasActivity extends AppCompatActivity {

    RecyclerView recycler_chatKom;
    RecycleAdapteraChatKom adapter;
    Intent i;
    public static String namaKom,keyKom,idKom;
    Firebase ref;
    Button btn_kirim;
    EditText et_pesan;
    String time,pesan,from_id,to_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_komunitas);
        Firebase.setAndroidContext(this);


        i = getIntent();
        namaKom = i.getStringExtra("nama");
        keyKom = i.getStringExtra("key");
        idKom = i.getStringExtra("id");
        getSupportActionBar().setTitle(namaKom);
        ref = new Firebase("https://golek-feca2.firebaseio.com/komunitas").child(ChatKomunitasActivity.keyKom).child("zchat");

        recycler_chatKom = (RecyclerView) findViewById(R.id.recycler_chatKom);
        adapter = new RecycleAdapteraChatKom(this);
        recycler_chatKom.setAdapter(adapter);
        recycler_chatKom.setLayoutManager(new LinearLayoutManager(this));
        btn_kirim = (Button) findViewById(R.id.btn_send);
        et_pesan = (EditText) findViewById(R.id.etEditPesan);


        btn_kirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pesan = et_pesan.getText().toString();
                from_id = BerandaActivity.id;
                to_id = idKom;
                Calendar calendar = Calendar.getInstance();
                int bulan = calendar.get(Calendar.MONTH)+1;
                long oneDayInMillis = 24 * 60 * 60 * 1000;
                long timeDifference = System.currentTimeMillis() - System.currentTimeMillis();
                if(timeDifference < oneDayInMillis){
                    //formattedTime = DateFormat.format("hh:mm a", System.currentTimeMillis()).toString();
                    //Toast.makeText(this,""+calendar.get(Calendar.DATE)+" - "+bulan+" - "+calendar.get(Calendar.YEAR)+" "+DateFormat.format("hh:mm a", System.currentTimeMillis()).toString(),Toast.LENGTH_LONG).show();
                    time = ""+calendar.get(Calendar.DATE)+"-"+bulan+"-"+calendar.get(Calendar.YEAR)+" "+ DateFormat.format("hh:mm a", System.currentTimeMillis()).toString();

                }else{
                    //formattedTime = DateFormat.format("dd MMM - hh:mm a", System.currentTimeMillis()).toString();
                    //Toast.makeText(this,""+calendar.get(Calendar.DATE)+" - "+bulan+" - "+calendar.get(Calendar.YEAR)+" "+DateFormat.format("dd MMM - hh:mm a", System.currentTimeMillis()).toString(),Toast.LENGTH_LONG).show();
                    time = ""+calendar.get(Calendar.DATE)+" - "+bulan+" - "+calendar.get(Calendar.YEAR)+" "+DateFormat.format("dd MMM - hh:mm a", System.currentTimeMillis()).toString();

                }

                ChatModel chatModel = new ChatModel(from_id,to_id,pesan,time);
                ref.push().setValue(chatModel);
                et_pesan.setText(null);
            }
        });

    }
}
