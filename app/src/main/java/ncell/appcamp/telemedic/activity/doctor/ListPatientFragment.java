package ncell.appcamp.telemedic.activity.doctor;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ncell.appcamp.telemedic.R;
import ncell.appcamp.telemedic.activity.HTTPConnection;
import ncell.appcamp.telemedic.activity.doctor.dummy.DummyContent;
import ncell.appcamp.telemedic.activity.patient.PatientActivity;
import ncell.appcamp.telemedic.activity.patient.Patients;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class ListPatientFragment extends Fragment implements AbsListView.OnItemClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    HTTPConnection http;
    List<Patients> patient = new ArrayList<Patients>();

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;

    // TODO: Rename and change types of parameters
    public static ListPatientFragment newInstance(String param1, String param2) {
        ListPatientFragment fragment = new ListPatientFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListPatientFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        // TODO: Change Adapter to display your content
        mAdapter = new ArrayAdapter<DummyContent.DummyItem>(getActivity(),
                android.R.layout.simple_list_item_1, android.R.id.text1, DummyContent.ITEMS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item2, container, false);

        // Set the adapter
        mListView = (AbsListView) view.findViewById(android.R.id.list);
        ((AdapterView<ListAdapter>) mListView).setAdapter(mAdapter);

        // Set OnItemClickListener so we can be notified on item clicks
        mListView.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (null != mListener) {
            // Notify the active callbacks interface (the activity, if the
            // fragment is attached to one) that an item has been selected.
            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
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
                Toast.makeText(getActivity(), "JSON Error",
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
        //ArrayAdapter<Patients> adapter = new ArrayAdapter<Patients>(this, R.layout.list_item,R.id.list_name,patient){

   //         @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View view = getLayoutInflater().inflate(R.layout.list_item,null);
//
//                //set values
//                Patients p = patient.get(position);
//                ((TextView)view.findViewById(R.id.list_name)).setText(p.getName());
//                ((TextView)view.findViewById(R.id.list_id)).setText(p.getId());
//                ((TextView)view.findViewById(R.id.list_phone)).setText(p.getCell_no());
//
//                return view;
//            }

        };
      //  ((TextView)getView().findViewById(R.id.textView111)).setText("Patient List");
    //    final ListView patientlist = (ListView) getView().findViewById(R.id.listView1);
//        patientlist.setAdapter(adapter);
//
//        patientlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
//                                    long arg3) {
////				Toast.makeText(
////						getApplicationContext(),
////						"You clicked position " + position + "with item id "
////								+ patient.get(position).getId(),
////						Toast.LENGTH_LONG).show();
//
//                Intent i = new Intent(getActivity(), PatientActivity.class);
//                //i.putExtra("id",patient.get(position).getId().toString());
//
//                i.putExtra("patient",patient.get(position));
//                startActivity(i);
//            }
//
//        });

//    }

}
