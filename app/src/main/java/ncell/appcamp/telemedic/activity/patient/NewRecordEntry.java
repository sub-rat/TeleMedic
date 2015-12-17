package ncell.appcamp.telemedic.activity.patient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ncell.appcamp.telemedic.R;
import ncell.appcamp.telemedic.activity.ImageCaptureActivity;


/**
 * Created by iii on 9/2/15.
 */
public class NewRecordEntry extends Activity implements View.OnClickListener{
    Patients p;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_record_entry);
       p = (Patients) getIntent().getSerializableExtra("patient");
        if(p == null){
            return;
        }

        findViewById(R.id.lab_test_new).setOnClickListener(this);
        findViewById(R.id.pic).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.lab_test_new:
                Intent inten = new Intent(getApplicationContext(), PatientTestCheckedItems.class);
                inten.putExtra("id",p);
                startActivity(inten);
                break;
            case R.id.pic:
                Intent intent2 = new Intent(getApplicationContext(), ImageCaptureActivity.class);
                intent2.putExtra("id",p.getId());
               startActivity(intent2);
                break;
        }
    }
}
