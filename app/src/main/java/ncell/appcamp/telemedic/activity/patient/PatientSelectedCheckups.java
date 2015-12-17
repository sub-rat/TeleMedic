package ncell.appcamp.telemedic.activity.patient;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;



import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ncell.appcamp.telemedic.R;
import ncell.appcamp.telemedic.activity.HTTPConnection;

/**
 * Created by iii on 8/14/15.
 */
public class PatientSelectedCheckups extends Activity {
    HTTPConnection http;
    ArrayList<EditText> ham = new ArrayList<>();
    ArrayList<EditText> pat = new ArrayList<>();
    EditText did,pid;
    String hamatology,pathology;
    String data1 = "";
    String data2 = "";
    Button btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_selected_checkup_form);
        hamatology = (String) getIntent().getStringExtra("hamatology");
        pathology = (String) getIntent().getStringExtra("pathology");

        LinearLayout res = (LinearLayout) findViewById(R.id.pform);
        String s[] = hamatology.split(",");
        for (int j = 0; j < s.length; j++) {
            Log.i("PatientTest ", "printed = " + s[j]);
            TextView tv = new TextView(this);
            EditText ev = new EditText(this);
            tv.setText(s[j]);
            ev.setInputType(InputType.TYPE_CLASS_NUMBER);
            res.addView(tv);
            res.addView(ev);
            ham.add(ev);
        }


        LinearLayout res1 = (LinearLayout) findViewById(R.id.pform2);
        String s1[] = pathology.split(",");
        for (int j = 0; j < s1.length; j++) {
            Log.i("PatientTest ", "printed = " + s1[j]);
            TextView tv1 = new TextView(this);
            EditText ev1 = new EditText(this);
            ev1.setInputType(InputType.TYPE_CLASS_NUMBER);
            tv1.setText(s1[j]);
            res1.addView(tv1);
            res1.addView(ev1);
            pat.add(ev1);
        }

                btn = (Button) findViewById(R.id.submit);
            btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                http = new HTTPConnection(getApplicationContext());
                did = (EditText)findViewById(R.id.did);
                pid = (EditText)findViewById(R.id.pid);


                //pathology
                for (int i = 0; i < pat.size(); i++) {


                    Log.i("PatientTest", pat.get(i).getText().toString());

                    data2 += pat.get(i).getText().toString() + ",";

                }

                data2 = data2.substring(0, data2.length() - 1);
                Toast.makeText(getApplicationContext(),data2.toString(), Toast.LENGTH_LONG).show();
                Log.i("PatientTest", "data2 = " + data2);


//hamatology
                for (int i = 0; i < ham.size(); i++) {


                    Log.i("PatientTest", ham.get(i).getText().toString());

                    data1 += ham.get(i).getText().toString() + ",";

                }

                data1 = data1.substring(0, data1.length() - 1);
                Toast.makeText(getApplicationContext(),data1.toString(), Toast.LENGTH_LONG).show();
                Log.i("PatientTest", "data1 = " + data1);
                if(http.isNetworkConnection()){
                    callServer();
                }else {
                    Toast.makeText(getApplicationContext(),"no network connection", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

private void callServer(){
    new AsyncTask<Void, Void, String>(){
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jobj = new JSONObject(s);
                String res = jobj.getString("success");
                if(res.equals("true")){
                    Log.d("patientcheckupdate","data updated");
                }
                else {
                    Log.d("Patientcheckupdate", "data not uploaded");
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... param) {
            ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
            params.add(new BasicNameValuePair("doctor_username", did.getText().toString()));
            params.add(new BasicNameValuePair("patient_username", pid.getText().toString()));
            params.add(new BasicNameValuePair("hamatologyid", hamatology));
            params.add(new BasicNameValuePair("hamatology",data1));
            params.add(new BasicNameValuePair("pathologyid",pathology));
            params.add(new BasicNameValuePair("pathology",data2));
            params.add(new BasicNameValuePair("submit",btn.getText().toString()));
            String data = http.HTTPPostData("http://www.subratgyawali.com.np/api/?action=checkup&code=1", params);
            Log.d("data ", data);
            return data;
        }
    }.execute();

}

}
