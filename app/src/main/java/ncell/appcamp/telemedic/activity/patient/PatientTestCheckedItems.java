package ncell.appcamp.telemedic.activity.patient;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;



import org.w3c.dom.Text;

import java.util.ArrayList;

import ncell.appcamp.telemedic.R;

/**
 * Created by iii on 8/13/15.
 */
public class PatientTestCheckedItems extends Activity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener {
    ArrayList<CheckBox> data1 = new ArrayList<CheckBox>();
    ArrayList<CheckBox> data2 = new ArrayList<CheckBox>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkup_activity);

//for hamatolgy
        LinearLayout lll = ((LinearLayout) findViewById(R.id.ll_ha));
        int sizem = lll.getChildCount();
        for (int i = 0; i < sizem; i++) {
            LinearLayout ll = ((LinearLayout) lll.getChildAt(i));
            int size = ll.getChildCount();
            for (int j = 0; j < size; j++) {
                CheckBox c = (CheckBox) ll.getChildAt(j);
                data1.add(c);
            }
        }

//        LinearLayout res = (LinearLayout) findViewById(R.id.ll_result);
//        for (int i = 0; i < data1.size(); i++) {
//            TextView tv = new TextView(this);
//            tv.setText(data1.get(i).getText().toString());
//            res.addView(tv);
//
//        }

        //cbArray.add((CheckBox) findViewById(R.id.ct));

//        ((CheckBox)findViewById(R.id.btn_next)).setOnCheckedChangeListener(this);
//        ((CheckBox)findViewById(R.id.btn_next)).setOnCheckedChangeListener(this);
//        ((CheckBox)findViewById(R.id.btn_next)).setOnCheckedChangeListener(this);
//        ((CheckBox)findViewById(R.id.btn_next)).setOnCheckedChangeListener(this);
//        ((CheckBox)findViewById(R.id.btn_next)).setOnCheckedChangeListener(this);
//        ((CheckBox)findViewById(R.id.btn_next)).setOnCheckedChangeListener(this);


        //
//for pathology
        LinearLayout lll1 = ((LinearLayout) findViewById(R.id.ll_pa));
        int sizem1 = lll1.getChildCount();
        for (int i = 0; i < sizem1; i++) {
            LinearLayout ll1 = ((LinearLayout) lll1.getChildAt(i));
            int size = ll1.getChildCount();
            for (int j = 0; j < size; j++) {
                CheckBox c = (CheckBox) ll1.getChildAt(j);
                data2.add(c);
            }
        }


        Log.i("Array", "data size " + data1.size());
        findViewById(R.id.btn_next).setOnClickListener(this);
    }

    @TargetApi(Build.VERSION_CODES.GINGERBREAD)
    @Override
    public void onClick(View v) {


        String hamatology = "";
        String pathology = "";
        for (int i = 0; i < data1.size(); i++) {
            if (data1.get(i).isChecked()) {
                //Toast.makeText(getApplicationContext(),cbArray.get(i).getText().toString(),Toast.LENGTH_LONG).show();
                Log.i("PatientTest", data1.get(i).getText().toString());

                hamatology += data1.get(i).getText().toString() + ",";
            }
        }
        if (hamatology.isEmpty()) {

        } else {
            hamatology = hamatology.substring(0, hamatology.length() - 1);
            Log.i("PatientTest", "Checked data1 = " + hamatology);
        }
//
//        LinearLayout res = (LinearLayout) findViewById(R.id.ll_result);
//
//        String s[] = hamatology.split(",");
//        for (int j = 0; j < s.length; j++) {
//            Log.i("PatientTest ", "printed = " + s[j]);
//            TextView tv = new TextView(this);
//            tv.setText(s[j]);
//            res.addView(tv);
//        }

        for (int i = 0; i < data2.size(); i++) {
            if (data2.get(i).isChecked()) {
                //Toast.makeText(getApplicationContext(),cbArray.get(i).getText().toString(),Toast.LENGTH_LONG).show();
                Log.i("PatientTest", data2.get(i).getText().toString());

                pathology += data2.get(i).getText().toString() + ",";
            }
        }
        if (pathology.isEmpty()) {

        } else {
            pathology = pathology.substring(0, pathology.length() - 1);
            Log.i("PatientTest", "Checked data = " + pathology);
        }

        if (data1.equals("")) {
            return;
        } else if (data2.equals("")) {
            return;
        } else {
            Intent intent = new Intent(getApplication(), PatientSelectedCheckups.class);
            intent.putExtra("hamatology", hamatology);
            intent.putExtra("pathology", pathology);
            startActivity(intent);
        }

    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        Toast.makeText(getApplicationContext(), buttonView.getText().toString(), Toast.LENGTH_LONG).show();

    }
}
