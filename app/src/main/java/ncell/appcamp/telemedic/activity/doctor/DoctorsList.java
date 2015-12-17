package ncell.appcamp.telemedic.activity.doctor;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ncell.appcamp.telemedic.R;
import ncell.appcamp.telemedic.activity.HTTPConnection;

/**
 * Created by iii on 8/10/15.
 */
public class DoctorsList extends Activity{

    HTTPConnection http;
    List<Doctors> doctor = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        http = new HTTPConnection(getApplicationContext());
        if (http.isNetworkConnection()) {
            task.execute();
        } else {
            Toast.makeText(getApplicationContext(), "check your connection",
                    Toast.LENGTH_LONG).show();
        }

    }

    AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
        @Override
        protected String doInBackground(Void... params) {
            String data = http.HTTPGetData("http://www.subratgyawali.com.np/api/?action=doctorlist");
            return data;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            populatelist(s);
            display();


        }
    };

    protected void populatelist(String result){
        try {
            JSONObject obj = new JSONObject(result);
            String res = obj.getString("success");
            if(res.equals("true")){
                JSONArray data = obj.getJSONArray("msg");
                    for(int i = 0;i < data.length(); i++){
                      JSONObject doc = data.getJSONObject(i);
                        Doctors d = new Doctors(doc.getString("id"),doc.getString("name"),doc.getString("username"),doc.getString("cell_no"),doc.getString("home_no"),doc.getString("email"),
                                doc.getString("address"),doc.getString("sex"),doc.getString("speciality"),doc.getString("tele_location"));
                        doctor.add(d);
                    }

            }else {
                Toast.makeText(getApplicationContext(), "JSON Error",
                        Toast.LENGTH_LONG).show();
                return;
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void display(){
        ArrayAdapter<Doctors> adapter = new ArrayAdapter<Doctors>(this,R.layout.list_item,doctor){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View view = getLayoutInflater().inflate(R.layout.list_item,null);
                //set values
                Doctors d = doctor.get(position);
                ((TextView)view.findViewById(R.id.list_name)).setText(d.getName());
                ((TextView)view.findViewById(R.id.list_id)).setText(d.getId());
                ((TextView)view.findViewById(R.id.list_phone)).setText(d.getCell_no());
                return view;
            }
        };

        ((TextView)findViewById(R.id.textView111)).setText("Doctors List");
        final ListView doctorlist = (ListView) findViewById(R.id.listView1);
        doctorlist.setAdapter(adapter);

        doctorlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(
                        getApplicationContext(),
                        "You clicked position " + position + "with item name "
                                + doctor.get(position).getId(),
                        Toast.LENGTH_LONG).show();
                Intent i = new Intent(getApplicationContext(),DoctorActivity.class);
               // i.putExtra("id",doctor.get(position).getId().toString());

                i.putExtra("doctor",doctor.get(position));
                startActivity(i);
            }
        });


    }

}
