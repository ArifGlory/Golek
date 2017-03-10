package glory.golek.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import glory.golek.R;

/**
 * Created by Glory on 03/10/2016.
 */
public class RecycleViewHolderListFriend extends RecyclerView.ViewHolder {

    public TextView txtNamaUserFriend;
    public ImageView img_iconlistfriend;
    public RelativeLayout relaFriend;

    public RecycleViewHolderListFriend(View itemView) {
        super(itemView);

        txtNamaUserFriend = (TextView) itemView.findViewById(R.id.txt_namauserfriend);
        img_iconlistfriend = (ImageView) itemView.findViewById(R.id.img_iconlistfriend);
        relaFriend = (RelativeLayout) itemView.findViewById(R.id.relaFriend);

    }
}
