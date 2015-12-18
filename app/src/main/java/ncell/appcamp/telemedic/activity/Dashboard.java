package ncell.appcamp.telemedic.activity;


import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import ncell.appcamp.telemedic.R;
import ncell.appcamp.telemedic.activity.doctor.DoctorRecord;
import ncell.appcamp.telemedic.activity.patient.PatientRecord;


public class Dashboard extends AppCompatActivity implements OnClickListener{

	private Toolbar mToolbar;
	private FragmentDrawer drawerFragment;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		mToolbar = (Toolbar) findViewById(R.id.toolbar_dashboard);
		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		
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

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		if (id == R.id.action_search) {
			Toast.makeText(getApplicationContext(), "Search action is selected!", Toast.LENGTH_SHORT).show();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}


}
