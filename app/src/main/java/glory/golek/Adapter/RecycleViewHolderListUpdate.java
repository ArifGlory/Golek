package glory.golek.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import glory.golek.R;

/**
 * Created by Glory on 03/10/2016.
 */
public class RecycleViewHolderListUpdate extends RecyclerView.ViewHolder {

    public TextView txtNamaUserUpdate,txtTipeUpdate,txtPMUpdate;
    public ImageView img_dpUpdate,img_userUpdate;

    public RecycleViewHolderListUpdate(View itemView) {
        super(itemView);

        txtNamaUserUpdate = (TextView) itemView.findViewById(R.id.txt_namauserUpdate);
        txtTipeUpdate = (TextView) itemView.findViewById(R.id.txt_tipeupdate);
        img_dpUpdate = (ImageView) itemView.findViewById(R.id.img_dpUpdate);
        txtPMUpdate = (TextView) itemView.findViewById(R.id.txt_pmUpdate);
        img_userUpdate = (ImageView) itemView.findViewById(R.id.img_iconuserupdate);
    }
}
