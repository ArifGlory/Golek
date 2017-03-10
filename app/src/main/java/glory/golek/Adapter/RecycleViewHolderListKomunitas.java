package glory.golek.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import glory.golek.R;

/**
 * Created by Glory on 03/10/2016.
 */
public class RecycleViewHolderListKomunitas extends RecyclerView.ViewHolder {

    public TextView txtNamaKomunitas,txtTagKomunitas;
    public ImageView img_iconlistkomunitas;

    public RecycleViewHolderListKomunitas(View itemView) {
        super(itemView);

        txtNamaKomunitas = (TextView) itemView.findViewById(R.id.txt_namakomunitas);
        txtTagKomunitas = (TextView) itemView.findViewById(R.id.txt_tagkomunitas);
        img_iconlistkomunitas = (ImageView) itemView.findViewById(R.id.img_iconlistkomunitas);

    }
}
