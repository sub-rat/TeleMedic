package ncell.appcamp.telemedic.activity.patient;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.squareup.picasso.Picasso;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import ncell.appcamp.telemedic.R;
import ncell.appcamp.telemedic.activity.HTTPConnection;

/**
 * Created by iii on 9/7/15.
 */
public class ViewLabTests extends Activity {
    HTTPConnection http;
    String id;
    LinearLayout layout1,layout2,layout3;
    String name,desc,group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewlabtests);
        layout1 = (LinearLayout) findViewById(R.id.view_test);
        layout2 = (LinearLayout) findViewById(R.id.view_test1);
        layout3 = (LinearLayout) findViewById(R.id.view_test2);
        http = new HTTPConnection(getApplicationContext());
        id = getIntent().getStringExtra("id");
        if (http.isNetworkConnection()) {
            callserver();

        } else {
            Toast.makeText(getApplicationContext(), "not connected", Toast.LENGTH_SHORT);
            return;
        }
    }

    private void callserver() {
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                ArrayList<BasicNameValuePair> param = new ArrayList<BasicNameValuePair>();
                param.add(new BasicNameValuePair("action", "viewreport"));
                param.add(new BasicNameValuePair("id", id));
                String data = http.HTTPGetData("http://www.subratgyawali.com.np/api/", param);
                return data;
            }

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
                    if (res.equals("true")) {
                        JSONObject msg = new JSONObject(jobj.getString("msg"));

                        JSONArray data1 = msg.getJSONArray("test");
                        Log.d("json data ", data1.toString());

                        for (int i = 0; i < data1.length(); i++) {
                            JSONObject data = data1.getJSONObject(i);
                            Log.i("data", data.toString());
                            name = data.getString("test_name");
                            desc = data.getString("description");
                            group = data.getString("group_id");
                          //  View v = getLayoutInflater().inflate(R.layout.row_labtest, null);
                          //  View v1 =  getLayoutInflater().inflate(R.layout.labtestimage,null);
                            TextView tv = new TextView(ViewLabTests.this);
                            TextView ev = new TextView(ViewLabTests.this);
                            TextView tv1 = new TextView(ViewLabTests.this);
                            ImageView iv = new ImageView(ViewLabTests.this);

                            if(group.equals("radiology")){
                                Log.d("value", group);
                           //     ((TextView)v1.findViewById(R.id.abcd)).setText(name);
                                String url = "http://www.subratgyawali.com.np/uploads/"+desc;
                                Log.d("value",url);
                              //  ImageView iv = (ImageView) findViewById(R.id.img);
                                // iv.setVisibility(View.VISIBLE);

                                iv.setMaxHeight(400);
                                iv.setMaxWidth(400);
                                Picasso.with(ViewLabTests.this)
                                        .load(url)
                                        .resize(400,400)
                                        .into(iv);

                                    tv1.setText(name);

                            }else {
                             //   TextView tv = (TextView) v.findViewById(R.id.abd);
                             //   TextView ev = (TextView) v.findViewById(R.id.def);
                                tv.setText(name);
                                ev.setText(desc);

                            }
                            layout1.addView(tv);
                            layout2.addView(ev);
                            layout3.addView(tv1);
                            layout3.addView(iv);



                            //   ev.setInputType(InputType.TYPE_CLASS_NUMBER);

                           // layout1.addView(v1);

                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }.execute();
    }
}
