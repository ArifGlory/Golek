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
public class RecycleViewHolderListUndangan extends RecyclerView.ViewHolder {

    public TextView txtNamaUserUndagan;
   // public ImageView img_iconlistfriend;
    public RelativeLayout rela_listUndangan;

    public RecycleViewHolderListUndangan(View itemView) {
        super(itemView);

        txtNamaUserUndagan = (TextView) itemView.findViewById(R.id.txt_namauserUndangan);
        rela_listUndangan = (RelativeLayout) itemView.findViewById(R.id.rela_listundangan);


    }
}
