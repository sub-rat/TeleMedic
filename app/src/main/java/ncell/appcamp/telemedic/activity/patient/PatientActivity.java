package ncell.appcamp.telemedic.activity.patient;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ncell.appcamp.telemedic.R;
import ncell.appcamp.telemedic.activity.HTTPConnection;


public class PatientActivity extends Activity {
	Patients p;
	HTTPConnection http;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.patient_activity);
		String btn = (String) getIntent().getStringExtra("button");
		p = (Patients) getIntent().getSerializableExtra("patient");
		http = new HTTPConnection(getApplicationContext());
		if(p == null){
			return;
		}


		/*String json = login.getStringExtra("json");
		String btn = login.getStringExtra("button");*/
		
		try {
			//JSONObject obj = new JSONObject(json);
			//Patients p = new Patients(obj);

			((TextView) findViewById(R.id.ptextView2)).setText(p.id);
			((TextView) findViewById(R.id.ptextView4)).setText(p.name);
			((TextView) findViewById(R.id.ptextView6)).setText(p.address);
			((TextView) findViewById(R.id.ptextView8)).setText(p.address);
			((TextView) findViewById(R.id.ptextView10)).setText(p.email);
			((TextView) findViewById(R.id.ptextView12)).setText(p.cell_no);
			((TextView) findViewById(R.id.ptextView14)).setText(p.home_no);
			((Button) findViewById(R.id.pbutton3)).setText("lab Tests");
			((Button) findViewById(R.id.pbutton3)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if (http.isNetworkConnection()) {
						Intent intent = new Intent(getApplicationContext(), ViewLabTests.class);
						intent.putExtra("id", p.id);
						startActivity(intent);
					} else {
						Toast.makeText(getApplicationContext(), "not connected", Toast.LENGTH_SHORT).show();
					}

				}
			});
			((Button) findViewById(R.id.pbutton4)).setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					if(http.isNetworkConnection()) {
						Intent intent = new Intent(getApplicationContext(), PatientReport.class);
						intent.putExtra("id", p.id);
						startActivity(intent);
					}else{
						Toast.makeText(getApplicationContext(), "not connected", Toast.LENGTH_SHORT).show();
					}
				}
			});
		} catch (Exception e) {

			e.printStackTrace();
		}
		
	
	}


}
