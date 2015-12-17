package ncell.appcamp.telemedic.activity.patient;

import java.util.ArrayList;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import ncell.appcamp.telemedic.R;
import ncell.appcamp.telemedic.activity.HTTPConnection;


public class PatientRegistration extends Activity {

	Button usignup;
	EditText name;
	EditText username;
	EditText password;
	EditText address;
	EditText email;
	EditText cell_no;
	EditText tel_no;
	EditText repassword;
	RadioButton sex;
	RadioGroup grp;
	String URL = "http://www.subratgyawali.com.np/api/?action=adduser&users=patient";

	 HTTPConnection jsonParser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_patientregistration);

		name = (EditText) findViewById(R.id.p_name);
		username = (EditText) findViewById(R.id.p_username);
		password = (EditText) findViewById(R.id.p_password);
		repassword = (EditText) findViewById(R.id.p_retypepassword);
		address = (EditText) findViewById(R.id.p_address);
		email = (EditText) findViewById(R.id.p_email);
		cell_no = (EditText) findViewById(R.id.p_cell_no);
		tel_no = (EditText) findViewById(R.id.p_home_no);
		usignup = (Button) findViewById(R.id.prb_signup);
		grp = (RadioGroup) findViewById(R.id.sex);
		jsonParser = new HTTPConnection(getApplicationContext());

		usignup.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				int sId = grp.getCheckedRadioButtonId();
				sex = (RadioButton) findViewById(sId);
				if (jsonParser.isNetworkConnection()) {
					if ((password.getText().toString()).equals(repassword
							.getText().toString())) {
						Toast.makeText(getApplicationContext(), "servercalled",
								Toast.LENGTH_SHORT).show();
						callserver();
					} else {
						password.setText("mismatch");
						repassword.setText("");
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"check your internt connection", Toast.LENGTH_LONG)
							.show();
				}

			}
		});

	}

	@Override
	@Deprecated
	protected Dialog onCreateDialog(int id) {
		ProgressDialog dialog = new ProgressDialog(this);
		dialog.setMessage("Registering...");
		return dialog;
	}

	private void callserver() {
		new AsyncTask<Void, Void, String>() {

			@SuppressWarnings("deprecation")
			@Override
			protected void onPostExecute(String result) {
				super.onPostExecute(result);
				dismissDialog(1);
				
				try {
					JSONObject jobj = new JSONObject(result);
					String res = jobj.getString("success");
					if (res.equals("true")) {
						String data = jobj.getString("msg");
						
						Intent login = new Intent(getApplicationContext(),
								PatientActivity.class);
							login.putExtra("json", data.toString());
							login.putExtra("button", "Enter lab Details");
						startActivity(login);
						
						
						Toast.makeText(getApplicationContext(),
								"patient Registered", Toast.LENGTH_SHORT)
								.show();
						finish();
					} else {
						Toast.makeText(getApplicationContext(),
								"patient not Registered", Toast.LENGTH_SHORT)
								.show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

			@SuppressWarnings("deprecation")
			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showDialog(1);

			}

			@Override
			protected String doInBackground(Void... params) {
				return patientRegister(name.getText().toString(), username
						.getText().toString(), password.getText().toString(),
						address.getText().toString(), email.getText()
								.toString(), cell_no.getText().toString(),
						tel_no.getText().toString(), sex.getText().toString(),
						usignup.getText().toString());
			}
		}.execute();

	}

	public String patientRegister(String name, String username,
			String password, String address, String email, String cell_no,
			String tel_no, String sex, String submit) {
		
		ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
		params.add(new BasicNameValuePair("name", name));
		params.add(new BasicNameValuePair("username", username));
		params.add(new BasicNameValuePair("password", password));
		params.add(new BasicNameValuePair("address", address));
		params.add(new BasicNameValuePair("email", email));
		params.add(new BasicNameValuePair("cell_no", cell_no));
		params.add(new BasicNameValuePair("tel_no", tel_no));
		params.add(new BasicNameValuePair("sex", sex));
		params.add(new BasicNameValuePair("submit", submit));
		Log.i("Register", "Params = " + params);
		String json = jsonParser.HTTPPostData(URL, params);
		return json;
	}

}
