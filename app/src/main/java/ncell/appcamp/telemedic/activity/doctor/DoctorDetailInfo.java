package ncell.appcamp.telemedic.activity.doctor;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;



import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ncell.appcamp.telemedic.activity.HTTPConnection;
import ncell.appcamp.telemedic.activity.patient.PatientActivity;

/**
 * Created by iii on 8/10/15.
 */
public class DoctorDetailInfo extends Activity{

    HTTPConnection http;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent i = getIntent();
        id = i.getStringExtra("id");
        http = new HTTPConnection(getApplicationContext());
        if(http.isNetworkConnection()){
            callServer();
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(),"no network conncetion",Toast.LENGTH_LONG).show();
        }
    }

    private void callServer() {
        new AsyncTask<Void, Void, String>() {

            @SuppressWarnings("deprecation")
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //   showDialog(1);
            }

            @Override
            protected String doInBackground(Void... arg0) {
                ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
                params.add(new BasicNameValuePair("action","doctorinfo"));
                params.add(new BasicNameValuePair("id",id));
                return http.HTTPGetData("http://www.subratgyawali.com.np/api/",params);

            }

            @SuppressWarnings("deprecation")
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                //  dismissDialog(1);
                try {
                    JSONObject jobj = new JSONObject(result);
                    String res = jobj.getString("success");
                    if (res.equals("true")) {
                        String data = jobj.getString("msg");
                        Intent login = new Intent(getApplicationContext(),
                                PatientActivity.class);
                        login.putExtra("json", data.toString());
                        login.putExtra("button", "view Patients");
                        startActivity(login);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }.execute();
    }

}
