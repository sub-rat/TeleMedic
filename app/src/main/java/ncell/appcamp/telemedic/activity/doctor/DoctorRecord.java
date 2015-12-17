package ncell.appcamp.telemedic.activity.doctor;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

;import ncell.appcamp.telemedic.R;

public class DoctorRecord extends Activity{

	Button dr,dl;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.doctor_record);
		dr = (Button) findViewById(R.id.dregister);
		dl = (Button) findViewById(R.id.dList);
	
		dr.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Intent reg = new Intent(getApplicationContext(),DoctorRegistration.class);
				startActivity(reg);
			}
		});
		
		dl.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent li = new Intent(getApplicationContext(),DoctorsList.class);
				startActivity(li);
				
			}
		});
		
		
		
	}
	

}
