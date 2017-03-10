package glory.golek.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;

import glory.golek.Adapter.RecycleAdapteraListUpdate;
import glory.golek.BerandaActivity;
import glory.golek.Kelas.IsiDataTimeline;
import glory.golek.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListUpdateFragment extends Fragment {


    public ListUpdateFragment() {
        // Required empty public constructor
    }


    public static RecyclerView recycler_listupdate;
    RecycleAdapteraListUpdate adapter;
    EditText etPM;
    Button btn_pm;
    Firebase ref,refTimeline;
    String status,gambar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_update, container, false);
        recycler_listupdate = (RecyclerView) view.findViewById(R.id.recycler_listupdate);
        Firebase.setAndroidContext(this.getActivity());
        ref = new Firebase("https://golek-feca2.firebaseio.com/user/").child(BerandaActivity.key);
        refTimeline = new Firebase("https://golek-feca2.firebaseio.com/timeline");
        adapter = new RecycleAdapteraListUpdate(view.getContext());
        recycler_listupdate.setAdapter(adapter);
        recycler_listupdate.setLayoutManager(new LinearLayoutManager(view.getContext()));

        btn_pm = (Button) view.findViewById(R.id.btn_sendPM);
        etPM = (EditText) view.findViewById(R.id.etEditPM);
        status = "text";
        gambar = " ";


        btn_pm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.child("pm").setValue(etPM.getText().toString());
                IsiDataTimeline dataTimeline = new IsiDataTimeline(BerandaActivity.id,BerandaActivity.key,etPM.getText().toString(),gambar,status
                                                ,BerandaActivity.nama);
                refTimeline.push().setValue(dataTimeline);
                Toast.makeText(getActivity().getApplication(),"Berhasil",Toast.LENGTH_SHORT).show();
                etPM.setText(null);
            }
        });

        return view;
    }

}
