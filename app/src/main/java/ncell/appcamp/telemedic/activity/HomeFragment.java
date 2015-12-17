package ncell.appcamp.telemedic.activity;

/**
 * Created by Ravi on 29/07/15.
 */

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.NotificationCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import ncell.appcamp.telemedic.R;
import ncell.appcamp.telemedic.activity.doctor.DoctorActivity;
import ncell.appcamp.telemedic.activity.doctor.Doctors;
import ncell.appcamp.telemedic.activity.patient.PatientActivity;
import ncell.appcamp.telemedic.activity.patient.Patients;
import ncell.appcamp.telemedic.register.RegisterNewUserActivity;


public class HomeFragment extends Fragment {
    private EditText inputName, inputPassword;
    private TextInputLayout inputLayoutName, inputLayoutPassword;
    private Button btnSignUp;
    private TextView registerUser;
    RadioGroup grp;
    RadioButton users;
    String role;
    String URL = "http://www.subratgyawali.com.np/api/";
    HTTPConnection jsonParser;

    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        inputLayoutName = (TextInputLayout) rootView.findViewById(R.id.input_layout_name);
        jsonParser = new HTTPConnection(getActivity());
        inputLayoutPassword = (TextInputLayout) rootView.findViewById(R.id.input_layout_password);
        inputName = (EditText) rootView.findViewById(R.id.input_name);
        registerUser = (TextView) rootView.findViewById(R.id.register);
        inputPassword = (EditText) rootView.findViewById(R.id.input_password);
        btnSignUp = (Button) rootView.findViewById(R.id.btn_signup);
        grp = (RadioGroup) rootView.findViewById(R.id.users);
        inputName.addTextChangedListener(new MyTextWatcher(inputName));

        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm(rootView);
            }
        });
        registerUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(getActivity(), RegisterNewUserActivity.class);
                startActivity(register);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }

    private void submitForm(View rootView) {
        if (!validateName()) {
            return;
        }

        if (!validatePassword()) {
            return;
        }

        Toast.makeText(getActivity(), "Thank You!", Toast.LENGTH_SHORT).show();
        login(rootView);

    }

    private void login(View rootView) {
        int sId = grp.getCheckedRadioButtonId();
        users = (RadioButton) rootView.findViewById(sId);
        role = users.getText().toString();
        if (jsonParser.isNetworkConnection()) {
            if (role.equals("Teleuser"))
                URL = "http://www.subratgyawali.com.np/api/?action=login&role=teleuser";
            else if (role.equals("Doctor"))
                URL = "http://www.subratgyawali.com.np/api/?action=login&role=doctor";
            else
                URL = "http://www.subratgyawali.com.np/api/?action=login&role=patient";
            callServer();
        } else {
//					Toast.makeText(getApplicationContext(), "connet to network and try again", Toast.LENGTH_LONG).show();
            final Dialog dialog = new Dialog(getActivity());
            dialog.setContentView(R.layout.nointernet);
            dialog.setTitle("Sorry");

            Button button = (Button) dialog.findViewById(R.id.dismiss);

            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    dialog.dismiss();
//							finish();
                    startActivityForResult(new Intent(Settings.ACTION_WIFI_SETTINGS), 0);
                }
            });
            dialog.show();
        }
    }

    private void callServer() {
        final AsyncTask<Void, Void, String> execute = new AsyncTask<Void, Void, String>() {

            @SuppressWarnings("deprecation")
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                // showDialog(1);
            }

            @Override
            protected String doInBackground(Void... arg0) {
                return getLogin(inputName.getText().toString(),
                        (inputPassword.getText().toString()),
                        (btnSignUp.getText().toString()));

            }

            @SuppressWarnings("deprecation")
            @Override
            protected void onPostExecute(String result) {
                super.onPostExecute(result);
                Log.d("activity", "result = " + result);
                //dismissDialog(1);
                try {
                    JSONObject jobj = new JSONObject(result);
                    String res = jobj.getString("success");
                    if (res.equals("true")) {
                        //username.setText("");
                        inputPassword.setText("");

                        //JSONObject res = result.getJSONObject("msg");

                        JSONObject data = new JSONObject(jobj.getString("msg"));

                        //String data = jobj.getString("msg");

                        // username.setText("User:= "
                        // + res.getString("name"));
                        if (role.equals("Teleuser")) {


                            Intent login = new Intent(getActivity(),
                                    Dashboard.class);
                            login.putExtra("json", data.toString());
                            startActivity(login);
                        } else if (role.equals("Doctor")) {
//                            DoctorActivity fr=new DoctorActivity();
//                            FragmentManager fm=getFragmentManager();
//                            FragmentTransaction ft=fm.beginTransaction();
//                            Bundle args = new Bundle();
//                            Doctors d = new Doctors(data);
//                            //args.putParcelable("doctor", (Parcelable) d);
//                            args.putSerializable("doctor", d);
//                            fr.setArguments(args);
//                            ft.replace(R.id.container_body, fr);
//                            ft.commit();

//
                            Doctors d = new Doctors(data);
                            Intent login = new Intent(getActivity(),
                                    DoctorActivity.class);

                            login.putExtra("doctor", d);
                            login.putExtra("button", "View Patient");
                            startActivity(login);
                        } else {
                            Patients p = new Patients(data);
//							NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this);
//							mBuilder.setSmallIcon(R.drawable.tele);
//							mBuilder.setContentTitle("Notification Alert, Click Me!");
//							mBuilder.setContentText("Hi, This is Android Notification Detail!");
//                                    String status = data.getString("status");
//                                    Log.d("status value = ", status);
//                            	Notify("test1", "You've received Report");
//                            if(status.equals("1")) {
//                                Notify("Report", "You've received Report",p.getId());
//                                Log.d("class id =",p.getId());
//                            }
//                            	Notify("test2", "You've received Report");

                            Intent login = new Intent(getActivity(),
                                    PatientActivity.class);
                            login.putExtra("patient", p);
                            login.putExtra("button", "view lab tests");
                            startActivity(login);
//                        }
//
                        }
                    } else {
                        // If the user could not be found
                        //.setText("User could not be found");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }.execute();

    }

    public String getLogin(String username, String password, String submit) {

        ArrayList<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>();
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        params.add(new BasicNameValuePair("submit", submit));
        String json = jsonParser.HTTPPostData(URL, params);
        return json;
    }


    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError(getString(R.string.err_msg_name));
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError(getString(R.string.err_msg_password));
            requestFocus(inputPassword);
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }
}
