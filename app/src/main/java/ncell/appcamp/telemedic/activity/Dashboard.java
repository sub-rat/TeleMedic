package ncell.appcamp.telemedic.activity;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import ncell.appcamp.telemedic.R;
import ncell.appcamp.telemedic.activity.doctor.DoctorRecord;
import ncell.appcamp.telemedic.activity.patient.PatientRecord;


public class Dashboard extends Activity implements OnClickListener{

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		
		findViewById(R.id.patient).setOnClickListener(this);
		findViewById(R.id.doctor).setOnClickListener(this);
		findViewById(R.id.record).setOnClickListener(this);
		findViewById(R.id.info).setOnClickListener(this);
	
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.patient:
				Intent i = new Intent(getApplicationContext(),PatientRecord.class);
				startActivity(i);
			break;
		
		case R.id.doctor:
			Intent intent = new Intent(getApplicationContext(),DoctorRecord.class);
			startActivity(intent);
			break;
			
		case R.id.record:
			
			break;
			
		case R.id.info:
			
			break;
		default:
			break;
		}
		
	}

}
