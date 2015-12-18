package ncell.appcamp.telemedic.activity.doctor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import ncell.appcamp.telemedic.R;
import ncell.appcamp.telemedic.activity.AboutUsFragment;
import ncell.appcamp.telemedic.activity.FragmentDrawerDoctor;
import ncell.appcamp.telemedic.activity.HTTPConnection;


public class DoctorActivity extends AppCompatActivity implements FragmentDrawerDoctor.FragmentDrawerListener{
	Button viewpatient;
	TextView  id;
	String btn;
	String URL;
	HTTPConnection http;
	private Toolbar mToolbar;
	private FragmentDrawerDoctor drawerFragment;
	Doctors d;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doctor_activity);
		mToolbar = (Toolbar) findViewById(R.id.toolbar_doctor);


		setSupportActionBar(mToolbar);
		getSupportActionBar().setDisplayShowHomeEnabled(true);

		 drawerFragment = (FragmentDrawerDoctor)
				getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer_doctor);
		drawerFragment.setUp(R.id.fragment_navigation_drawer_doctor, (DrawerLayout) findViewById(R.id.drawer_layout_doctor), mToolbar);
		drawerFragment.setDrawerListener(this);

		// display the first navigation drawer view on app launch
		//displayView(0);


		d = (Doctors) getIntent().getSerializableExtra("doctor");
	//	btn = getIntent().getStringExtra("button");

		btn = "view Patient";
//		try {
//			//JSONObject obj = new JSONObject(json);
//			id = (TextView) findViewById(R.id.dtextView2);
//					id.setText(d.id);
//			((TextView) findViewById(R.id.dtextView4)).setText(d.name);
//			((TextView) findViewById(R.id.dtextView6)).setText(d.username);
//			((TextView) findViewById(R.id.dtextView8)).setText(d.address);
//			((TextView) findViewById(R.id.dtextView10)).setText(d.email);
//			((TextView) findViewById(R.id.dtextView12)).setText(d.cell_no);
//			((TextView) findViewById(R.id.dtextView14)).setText(d.home_no);
//			((TextView) findViewById(R.id.dtextView16)).setText(d.speciality);
//			((TextView) findViewById(R.id.dtextView18)).setText(d.tele_location);
//			viewpatient = (Button) findViewById(R.id.dbutton1);
//			viewpatient.setText(btn);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

//		viewpatient.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				if (btn.equals("View Patient")) {
//					Intent p = new Intent(getApplicationContext(),
//							ViewPatient.class);
//					p.putExtra("id", id.getText().toString());
//					startActivity(p);
//				} else {
//					Intent p = new Intent(getApplicationContext(),
//							ListPatient.class);
//					p.putExtra("id", id.getText().toString());
//					startActivity(p);
//				}
//
//			}
//		});

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

	@Override
	public void onDrawerItemSelected(View view, int position) {
		displayView(position);
	}

	private void displayView(int position) {
		Fragment fragment = null;
		String title = getString(R.string.app_name);
		switch (position) {
			case 0:
				fragment = new Fragment();
				title = getString(R.string.title_home);

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

				break;
			case 1:
				fragment = new Fragment();
				title = "Patient List";
				Intent p = new Intent(getApplicationContext(),
							ListPatient.class);
					p.putExtra("id", d.id.toString());
					startActivity(p);
				break;
			case 2:
				fragment = new AboutUsFragment();
				title = getString(R.string.title_aboutus);
				break;
			default:
				break;
		}

		if (fragment != null) {
			FragmentManager fragmentManager = getSupportFragmentManager();
			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
			fragmentTransaction.replace(R.id.container_body_doctor, fragment);
			fragmentTransaction.commit();

			// set the toolbar title
			getSupportActionBar().setTitle(title);
		}
	}

}
