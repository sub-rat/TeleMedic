package ncell.appcamp.telemedic.activity.doctor;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import ncell.appcamp.telemedic.R;
import ncell.appcamp.telemedic.activity.HTTPConnection;
import ncell.appcamp.telemedic.activity.patient.PatientActivity;
import ncell.appcamp.telemedic.activity.patient.Patients;


public class ViewPatient extends Activity {

	HTTPConnection http;
	String ids;
	List<Patients> patient = new ArrayList<Patients>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		http = new HTTPConnection(getApplicationContext());
		Intent p = getIntent();
		ids = p.getStringExtra("id");
		if (http.isNetworkConnection()) {
			task.execute();
		} else {
			Toast.makeText(getApplicationContext(), "check your connection",
					Toast.LENGTH_LONG).show();
		}
	}
	
	
	AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {

		@Override
		protected String doInBackground(Void... arg0) {
			ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
			params.add(new BasicNameValuePair("action", "patientlist"));
			params.add(new BasicNameValuePair("id", ids));
			String data = http.HTTPGetData("http://www.subratgyawali.com.np/api/",params);
			Log.e("ViewPatient", "data = " + data);
			return data;
			
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			Log.i("ViewPatient", "Result = " + result);
			populateList(result);
			displayList();
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}
	
	};
	protected void populateList(String result) {
		try {
			JSONObject jobj = new JSONObject(result);
			String res = jobj.getString("success");
			if (!res.equals("true")) {
				Toast.makeText(getApplicationContext(), "JSON Error",
						Toast.LENGTH_LONG).show();
				return;
			} else {
				JSONArray data = jobj.getJSONArray("msg");
				for (int i = 0; i < data.length(); i++) {
					JSONObject pat = data.getJSONObject(i);
					Patients pt = new Patients(pat.getString("id"), pat.getString("name"), pat.getString("username"), pat.getString("cell_no"),pat.getString("home_no"),pat.getString("email"),pat.getString("address"),pat.getString("sex"),pat.getString("status"));
					patient.add(pt);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		Log.i("ViewPatient", "Populate complete with size = " + patient.size());
		
	}
	
	
	protected void displayList() {
		ArrayAdapter<Patients> adapter = new ArrayAdapter<Patients>(this, 
				R.layout.list_item, patient){

			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				View view = getLayoutInflater().inflate(R.layout.list_item,null);
				
				//set values
				Patients p = patient.get(position);
				((TextView)view.findViewById(R.id.list_name)).setText(p.getName());
				((TextView)view.findViewById(R.id.list_id)).setText(p.getId());
				((TextView)view.findViewById(R.id.list_phone)).setText(p.getCell_no());
				
				return view;
			}
			
		};
		
		ListView patientlist = (ListView) findViewById(R.id.listView1);
		patientlist.setAdapter(adapter);
		
		patientlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
//				Toast.makeText(
//						getApplicationContext(),
//						"You clicked position " + position + " with item name "
//								+ patient.get(position).getName(),
//						Toast.LENGTH_LONG).show();
//
				Intent i = new Intent(getApplicationContext(), PatientActivity.class);

				i.putExtra("patient",patient.get(position));

				startActivity(i);

			}
		
		});
	
	}

	
}
