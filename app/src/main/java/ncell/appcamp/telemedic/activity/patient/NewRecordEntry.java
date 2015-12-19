package ncell.appcamp.telemedic.activity.patient;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;

import ncell.appcamp.telemedic.R;
import ncell.appcamp.telemedic.activity.ImageCaptureActivity;
import ncell.appcamp.telemedic.activity.MultipartEntity;


/**
 * Created by iii on 9/2/15.
 */
public class NewRecordEntry extends Activity implements View.OnClickListener {
    Patients p;
    File pdf;
    private String docFilePath;
    private static final String TAG = "upload";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.patient_record_entry);
        p = (Patients) getIntent().getSerializableExtra("patient");
        if (p == null) {
            return;
        }

        findViewById(R.id.lab_test_new).setOnClickListener(this);
        findViewById(R.id.pic).setOnClickListener(this);
        findViewById(R.id.document).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lab_test_new:
                Intent inten = new Intent(getApplicationContext(), PatientTestCheckedItems.class);
                inten.putExtra("id", p);
                startActivity(inten);
                break;
            case R.id.pic:
                Intent intent2 = new Intent(getApplicationContext(), ImageCaptureActivity.class);
                intent2.putExtra("id", p.getId());
                startActivity(intent2);
                break;
            case R.id.document:
                getDocument();
                break;

        }
    }


    private void getDocument() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("application/msword,application/pdf");
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        // Only the system receives the ACTION_OPEN_DOCUMENT, so no need to test.
        startActivityForResult(intent, CONTEXT_INCLUDE_CODE);
    }

    @Override
    protected void onActivityResult(int req, int result, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(req, result, data);
        if (result == RESULT_OK) {
            Uri fileuri = data.getData();
            docFilePath = getFileNameByUri(this, fileuri);
            pdf = new File(docFilePath);
            Log.d("document file path",docFilePath);
            new UploadTask().execute();

        }
    }

// get file path

    private String getFileNameByUri(Context context, Uri uri) {
        String filepath = "";//default fileName
        //Uri filePathUri = uri;
        File file;
        if (uri.getScheme().toString().compareTo("content") == 0) {
            Cursor cursor = context.getContentResolver().query(uri, new String[]{android.provider.MediaStore.Images.ImageColumns.DATA, MediaStore.Images.Media.ORIENTATION}, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            cursor.moveToFirst();

            String mImagePath = cursor.getString(column_index);
            cursor.close();
            filepath = mImagePath;

        } else if (uri.getScheme().compareTo("file") == 0) {
            try {
                file = new File(new URI(uri.toString()));
                if (file.exists())
                    filepath = file.getAbsolutePath();

            } catch (URISyntaxException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        } else {
            filepath = uri.getPath();
        }
        return filepath;
    }





    @Override
    @Deprecated
    protected Dialog onCreateDialog(int id) {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("uploading...");
        return dialog;
    }

    private class UploadTask extends AsyncTask<Void, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showDialog(1);
        }

        protected String doInBackground(Void... bitmaps) {
//            if (bitmaps == null)
//                return null;
//            setProgress(0);

          //  pdf = new File(docFilePath);
          //  ByteArrayOutputStream stream = new ByteArrayOutputStream();
         //   bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream); // convert Bitmap to ByteArrayOutputStream
          // InputStream in = new ByteArrayInputStream();// convert ByteArrayOutputStream to ByteArrayInputStream








            DefaultHttpClient httpclient = new DefaultHttpClient();
          //  httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
            try {
                HttpPost httppost = new HttpPost("http://www.subratgyawali.com.np/api/?action=uploadpdf&id="+p.getId()); // server
                MultipartEntity reqEntity = new MultipartEntity();
                reqEntity.addPart("fileToUpload", System.currentTimeMillis() + ".pdf",pdf);
                reqEntity.addPart("submit","true");
                httppost.setEntity(reqEntity);

                Log.i(TAG, "request " + httppost.getRequestLine());
                HttpResponse response = null;



                try {
                    response = httpclient.execute(httppost);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    if (response != null) {
                        Log.i(TAG, "response " + response.getStatusLine().toString());
                        String res = EntityUtils.toString(response.getEntity());
                        Log.i("Capture", "Response = " + res);
                        return res;
                    }
                } finally {

                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {

            }


//            if (stream != null) {
//                try {
//                    stream.close();
//                } catch (IOException e) {
//                    // TODO Auto-generated catch block
//                    e.printStackTrace();
//                }
//            }


            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            // TODO Auto-generated method stub
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);
            dismissDialog(1);
            Toast.makeText(NewRecordEntry.this,"uploaded", Toast.LENGTH_LONG).show();
        }
    }


}
