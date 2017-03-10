package glory.golek.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import glory.golek.R;

/**
 * Created by Glory on 03/10/2016.
 */
public class RecycleViewHolderChatKom extends RecyclerView.ViewHolder {

    public TextView txtNamaSenderKom,txtMessageKom,txtTimeKom;
    public LinearLayout contentKom;
    public LinearLayout contentWithBackgroundKom;

    public RecycleViewHolderChatKom(View itemView) {
        super(itemView);

        txtNamaSenderKom = (TextView) itemView.findViewById(R.id.txtNamaSenderKom);
        txtMessageKom = (TextView) itemView.findViewById(R.id.txtMessageKom);
        txtTimeKom= (TextView) itemView.findViewById(R.id.txtTimeSendKom);
        contentKom = (LinearLayout) itemView.findViewById(R.id.contentKom);
        contentWithBackgroundKom = (LinearLayout) itemView.findViewById(R.id.contentWithBackgroundKom);



    }
}
