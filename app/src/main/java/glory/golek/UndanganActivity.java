package glory.golek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import glory.golek.Adapter.RecycleAdapteraListUndangan;

public class UndanganActivity extends AppCompatActivity {

    RecyclerView recycler_listUndangan;
    RecycleAdapteraListUndangan adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_undangan);
        recycler_listUndangan = (RecyclerView) findViewById(R.id.recycler_listUndangan);
        adapter = new RecycleAdapteraListUndangan(this);
        recycler_listUndangan.setAdapter(adapter);
        recycler_listUndangan.setLayoutManager(new LinearLayoutManager(this));

    }
}
