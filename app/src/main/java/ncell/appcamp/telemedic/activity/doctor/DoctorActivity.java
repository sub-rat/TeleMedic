package ncell.appcamp.telemedic.activity.doctor;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import ncell.appcamp.telemedic.R;
import ncell.appcamp.telemedic.activity.HTTPConnection;


public class DoctorActivity extends Activity {
	Button viewpatient;
	TextView  id;
	String btn;
	String URL;
	HTTPConnection http;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doctor_activity);
		Doctors d = (Doctors) getIntent().getSerializableExtra("doctor");
	//	btn = getIntent().getStringExtra("button");

		btn = "view Patient";
		try {
			//JSONObject obj = new JSONObject(json);
			id = (TextView) findViewById(R.id.dtextView2);
					id.setText(d.id);
			((TextView) findViewById(R.id.dtextView4)).setText(d.name);
			((TextView) findViewById(R.id.dtextView6)).setText(d.username);
			((TextView) findViewById(R.id.dtextView8)).setText(d.address);
			((TextView) findViewById(R.id.dtextView10)).setText(d.email);
			((TextView) findViewById(R.id.dtextView12)).setText(d.cell_no);
			((TextView) findViewById(R.id.dtextView14)).setText(d.home_no);
			((TextView) findViewById(R.id.dtextView16)).setText(d.speciality);
			((TextView) findViewById(R.id.dtextView18)).setText(d.tele_location);
			viewpatient = (Button) findViewById(R.id.dbutton1);
			viewpatient.setText(btn);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		viewpatient.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (btn.equals("View Patient")) {
					Intent p = new Intent(getApplicationContext(),
							ViewPatient.class);
					p.putExtra("id", id.getText().toString());
					startActivity(p);
				} else {
					Intent p = new Intent(getApplicationContext(),
							ListPatient.class);
					p.putExtra("id", id.getText().toString());
					startActivity(p);
				}

			}
		});

	}

}
