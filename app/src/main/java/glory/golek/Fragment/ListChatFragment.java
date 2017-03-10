package glory.golek.Fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import glory.golek.Adapter.RecycleAdapteraListChat;
import glory.golek.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListChatFragment extends Fragment {

    RecyclerView recycler_listchat;
    RecycleAdapteraListChat adapter;

    public ListChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_chat, container, false);
        recycler_listchat = (RecyclerView) view.findViewById(R.id.recycler_listchat);
        adapter = new RecycleAdapteraListChat(view.getContext());
        recycler_listchat.setAdapter(adapter);
        recycler_listchat.setLayoutManager(new LinearLayoutManager(view.getContext()));

        return view;
    }

}
