package glory.golek;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AutoCompleteTextView;

import glory.golek.Adapter.RecycleAdapteraListFriend;

public class ListFriendActivity extends AppCompatActivity {

    RecyclerView recycler_listfriend;
    RecycleAdapteraListFriend adapter;
    private Boolean isFabOpen = false;
    private FloatingActionButton fab,fab1,fab2;
    Intent i;
    private Animation fab_open,fab_close,rotate_forward,rotate_backward;
    AutoCompleteTextView et_cari;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_friend);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab1 = (FloatingActionButton) findViewById(R.id.fab1);
        fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab_open = AnimationUtils.loadAnimation(this,R.anim.fab_open);
        fab_close = AnimationUtils.loadAnimation(this,R.anim.fab_close);
        rotate_forward = AnimationUtils.loadAnimation(this,R.anim.rotate_forward);
        rotate_backward = AnimationUtils.loadAnimation(this,R.anim.rotate_backward);


        adapter = new RecycleAdapteraListFriend(this);
        recycler_listfriend = (RecyclerView) findViewById(R.id.recycler_listfriend);
        recycler_listfriend.setAdapter(adapter);
        recycler_listfriend.setLayoutManager(new LinearLayoutManager(this));

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //animateFB();
                i = new Intent(getApplicationContext(),TambahTemanActivity.class);
                startActivity(i);
            }
        });
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  i = new Intent(getApplicationContext(),TambahTemanActivity.class);
                startActivity(i);*/
            }
        });


    }

    public void animateFB(){

        if(isFabOpen){

            fab.startAnimation(rotate_backward);
            fab1.startAnimation(fab_close);
           // fab2.startAnimation(fab_close);
            fab1.setClickable(false);
            //fab2.setClickable(false);
            isFabOpen = false;
            Log.d("fab", "close");

        } else {

            fab.startAnimation(rotate_forward);
            fab1.startAnimation(fab_open);
            //fab2.startAnimation(fab_open);
            fab1.setClickable(true);
            //fab2.setClickable(true);
            isFabOpen = true;
            Log.d("fab","open");

        }

    }
}
