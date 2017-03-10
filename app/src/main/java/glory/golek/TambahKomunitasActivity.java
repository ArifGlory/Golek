package glory.golek;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class TambahKomunitasActivity extends AppCompatActivity {

    EditText et_addKomunitas;
    Button btn_addKomunitas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_komunitas);
        et_addKomunitas = (EditText) findViewById(R.id.et_addcomunity);
        btn_addKomunitas = (Button) findViewById(R.id.btn_addComunity);

    }
}
