package ncell.appcamp.telemedic.activity.patient;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebView;
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
 * Created by iii on 9/6/15.
 */
public class PatientReport extends Activity {
    HTTPConnection http;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_report);
        id = (String) getIntent().getStringExtra("id");
        Log.d("id",id);
//        WebView browser = (WebView) findViewById(R.id.webView);
//        browser.loadUrl("http://www.subratgyawali.com.np/telemedic/displayreport.php?id="+id);
        http = new HTTPConnection(getApplicationContext());
        if (http.isNetworkConnection()) {


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
                    JSONObject jobj = null;
                    try {
                        jobj = new JSONObject(s);
                        String res = jobj.getString("success");
                        if (res.equals("true")) {
                            JSONObject msg = new JSONObject(jobj.getString("msg"));

                            JSONArray data1 = msg.getJSONArray("report");
                            Log.d("json data ", data1.toString());
                            for (int i = 0; i < data1.length(); i++) {
                                JSONObject data = data1.getJSONObject(i);
                                ((TextView) findViewById(R.id.general_findings1)).setText(data.getString("general_findings"));
                                ((TextView) findViewById(R.id.Assessment1)).setText(data.getString("assesment"));
                                ((TextView) findViewById(R.id.Subjective1)).setText(data.getString("subjective"));
                                ((TextView) findViewById(R.id.Objective1)).setText(data.getString("objective"));
                                ((TextView) findViewById(R.id.Plan1)).setText(data.getString("plan"));
                                ((TextView) findViewById(R.id.Advice1)).setText(data.getString("advice"));
                                ((TextView) findViewById(R.id.Disease1)).setText(data.getString("disease"));
                            }
                            }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }

                @Override
                protected String doInBackground(Void... params) {
                    ArrayList<BasicNameValuePair> arr =  new ArrayList<BasicNameValuePair>();
                    arr.add(new BasicNameValuePair("action","viewreport"));
                    arr.add(new BasicNameValuePair("id",id));
                    String data = http.HTTPGetData("http://www.subratgyawali.com.np/api/",arr);
                    Log.d("report data ", data);
                    return data;
                }
            }.execute();

        } else {
            Toast.makeText(getApplicationContext(),"not connected",Toast.LENGTH_SHORT);
            finish();
        }
    }

    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Getting Report Please Wait...");
        return dialog;
    }
}
