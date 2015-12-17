package ncell.appcamp.telemedic.activity.patient;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.Toast;



import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ncell.appcamp.telemedic.R;
import ncell.appcamp.telemedic.activity.HTTPConnection;
import ncell.appcamp.telemedic.activity.doctor.ListPatient;

public class PatientRecord extends Activity implements OnClickListener {

    EditText username;
    HTTPConnection http;
    Patients patient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_record);

        (findViewById(R.id.pregister)).setOnClickListener(this);
        (findViewById(R.id.plist)).setOnClickListener(this);
        (findViewById(R.id.pnew)).setOnClickListener(this);
        username = (EditText) findViewById(R.id.new_entry);
        http = new HTTPConnection(getApplicationContext());
      //  (findViewById(R.id.pic)).setOnClickListener(this);

    }

    @Override
    public void onClick(View arg0) {
        switch (arg0.getId()) {
            case R.id.pregister:
                Intent intent = new Intent(getApplicationContext(), PatientRegistration.class);
                startActivity(intent);
                break;
            case R.id.plist:
                Intent intent1 = new Intent(getApplicationContext(), ListPatient.class);
                startActivity(intent1);
                break;

            case R.id.pnew:
                checkPatient();
//
                break;

//            case R.id.pic:
//
//                break;
            default:
                break;
        }
    }


    private void checkPatient() {
        if(http.isNetworkConnection()) {
            task.execute();
        }else{
            Toast.makeText(getApplicationContext(),"connection fail",Toast.LENGTH_SHORT);
        }
    }


    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        ProgressDialog dialog1 = new ProgressDialog(this);
        dialog1.setMessage("checking...");
        return dialog1;
    }

    AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(1);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dismissDialog(1);
            try {
                JSONObject job = new JSONObject(s);
                String res = job.getString("success");
                if (res.equals("true")) {
                    JSONObject data = new JSONObject(job.getString("msg"));
                    patient = new Patients(data);
                    Intent login = new Intent(getApplicationContext(),NewRecordEntry.class);
                    login.putExtra("patient", patient);
                    startActivity(login);
                }
                else {
                    Toast.makeText(getApplicationContext(),"Enter correct username/registered username",Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        protected String doInBackground(Void... params) {
            ArrayList<BasicNameValuePair> par = new ArrayList<BasicNameValuePair>();
            par.add(new BasicNameValuePair("action", "patientinfo"));
            par.add(new BasicNameValuePair("uname",username.getText().toString()));
            String data = http.HTTPGetData("http://www.subratgyawali.com.np/api/",par);
            Log.d("data",data);
            return data;
        }
    };

}
