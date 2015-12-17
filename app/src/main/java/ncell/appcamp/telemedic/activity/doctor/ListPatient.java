package ncell.appcamp.telemedic.activity.doctor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;



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
import android.widget.AdapterView.OnItemClickListener;

import ncell.appcamp.telemedic.R;
import ncell.appcamp.telemedic.activity.HTTPConnection;
import ncell.appcamp.telemedic.activity.patient.PatientActivity;
import ncell.appcamp.telemedic.activity.patient.Patients;

public class ListPatient extends Activity {

	HTTPConnection http;
	List<Patients> patient = new ArrayList<Patients>();
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
			String data = http.HTTPGetData("http://www.subratgyawali.com.np/api/?action=patientlist");
			return data;
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
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
			// TODO: handle exception
		}
		
		
	}
	
	
	protected void displayList() {
		ArrayAdapter<Patients> adapter = new ArrayAdapter<Patients>(this, R.layout.list_item,R.id.list_name,patient){

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
		((TextView)findViewById(R.id.textView111)).setText("Patient List");
		final ListView patientlist = (ListView) findViewById(R.id.listView1);
		patientlist.setAdapter(adapter);
		
		patientlist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
//				Toast.makeText(
//						getApplicationContext(),
//						"You clicked position " + position + "with item id "
//								+ patient.get(position).getId(),
//						Toast.LENGTH_LONG).show();

                Intent i = new Intent(getApplicationContext(), PatientActivity.class);
				//i.putExtra("id",patient.get(position).getId().toString());

				i.putExtra("patient",patient.get(position));
				startActivity(i);
			}
		
		});
	
	}

	
}
