package managers.Network;/*
package org.csc.hajj.managers.Network;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.extect.appbase.BaseFragment;
import com.csc.hajjApp.iswift.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;

import base.AppConstants;
import listeners.IResponseReceivedNotifyInterface;
import listeners.ResponseArgs;
import models.DocModel;
import shared.BaseFlyContext;
import shared.SharedFunctions;
import utils.Constants;
import utils.RequestType;
import utils.ResponseStatus;
import utils.SettingServices;
import utils.Utils;

*/
/**
 * Created by Sanka on 10/19/16.
 *//*

public class UploadFileToServer extends AsyncTask<Void, Integer, String> {

    public final BaseFragment documentsUploadWindow;
    public View rootView;
    public ProgressBar progressBar;
    public TextView cancelTextView;
    public TextView sizeTextView;
    public TextView progressTextView;
    public DocModel docModel = null;
    boolean isStopProgress = false;
    public IResponseReceivedNotifyInterface iResponseReceivedNotifyInterface;
    long totalSize = 0;
    private static final String FILE_PART_NAME = "fileUpload";

    public UploadFileToServer(View view, final DocModel doc, BaseFragment uploadWindow) {
        rootView = view;
        iResponseReceivedNotifyInterface = (IResponseReceivedNotifyInterface)uploadWindow;
        progressBar = (ProgressBar) view.findViewById(R.id.row_upload_progressbar);
        cancelTextView = (TextView) view.findViewById(R.id.row_upload_progress_cancel);
        progressTextView = (TextView) view.findViewById(R.id.row_upload_doc_upload_progress);
        sizeTextView = (TextView) view.findViewById(R.id.row_upload_doc_size);
        sizeTextView.setText(""+ SharedFunctions.getDocumentSize(doc.docBitmap)+"KB");
        cancelTextView.setVisibility(View.GONE);
        documentsUploadWindow = uploadWindow;
        docModel = doc;
    }

    @Override
    protected void onPreExecute() {
        // setting progress bar to zero
        progressBar.setProgress(0);
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... progress) {
        // Making progress bar visible
        progressBar.setVisibility(View.VISIBLE);

        // updating progress bar value
        progressBar.setProgress(progress[0]);

        // updating percentage value
        progressTextView.setText(String.valueOf(progress[0]) + "%");
    }

    @Override
    protected String doInBackground(Void... params) {
        return uploadFile();
    }

    @SuppressWarnings("deprecation")
    private String uploadFile() {
        String responseString = null;

        HttpClient httpclient = new DefaultHttpClient();
        String pathUrl = AppConstants.getAPIUploadDocument();
        HttpPost httppost = new HttpPost(pathUrl);
        httppost.addHeader("deviceType","Android");
        String token =  SettingServices.getInstance().getUserToken(BaseFlyContext.getInstant().getApplicationContext());
        httppost.addHeader("sessionToken",token);

        try {
            AndroidMultiPartEntity entity = new AndroidMultiPartEntity(
                    new AndroidMultiPartEntity.ProgressListener() {

                        @Override
                        public void transferred(long num) {
                            publishProgress((int) ((num / (float) totalSize) * 100));
                        }
                    });

            File sourceFile = SharedFunctions.savebitmap("fileUpload",docModel.docBitmap);

            // Adding file data to http body
            entity.addPart(FILE_PART_NAME, new FileBody(sourceFile));
            String username = SettingServices.getInstance().getUserName(BaseFlyContext.getInstant().getActivity());
            entity.addPart("username", new StringBody(username));
            entity.addPart("moduleName",new StringBody("CLAIM"));

            totalSize = entity.getContentLength();
            httppost.setEntity(entity);

            // Making server call
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity r_entity = response.getEntity();

            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode == 200) {
                // Server response
                responseString = EntityUtils.toString( r_entity);
                //iResponseReceivedNotifyInterface.responseReceived(new ResponseArgs(docModel, ResponseStatus.success, RequestType.UploadDocument));
            } else {
                responseString = "Error occurred! Http Status Code: "
                        + statusCode;
                iResponseReceivedNotifyInterface.responseReceived(new ResponseArgs(docModel, ResponseStatus.fail, RequestType.UploadDocument));
            }
        }
        catch (Exception e) {
            Log.v("UploadFileToServer Exception",""+e);
            //iResponseReceivedNotifyInterface.responseReceived(new ResponseArgs(docModel, ResponseStatus.fail, RequestType.UploadDocument));
            responseString = Constants.ERROR_CODE_1001;
        }

        return responseString;

    }

    @Override
    protected void onPostExecute(String result) {
        //if (!isStopProgress) {
       // Log.v("UploadFileToServer",""+result);
        if (!Utils.isNullOrEmptyString(result)) {
            if (result.contains(Constants.ERROR_CODE_1001)){
                //Log.v("UploadFileToServer","ERROR_CODE_1001");
                // Toast.makeText()
                docModel.isHistory = false;
                docModel.isSubmit = false;
                docModel.respCode = Constants.ERROR_CODE_1001;
                docModel.respDesc ="Submission failed.";//add
                cancelTextView.setVisibility(View.GONE);
                progressBar.setProgress(0);
                iResponseReceivedNotifyInterface.responseReceived(new ResponseArgs(docModel, ResponseStatus.fail, RequestType.UploadDocument));
            }
            else {
                JSONObject jsonObj;
                try {
                    jsonObj = new JSONObject(result);//submittedDocsList
                    //Log.v("UploadFileToServer",""+jsonObj);
                    docModel.respCode = jsonObj.getString("respCode");
                    docModel.respDesc = jsonObj.getString("respDesc");
                    docModel.docName = ((JSONObject)jsonObj.getJSONArray("submittedDocsList").get(0)).getString("imageName");
                    if (docModel.respCode.contains(Constants.ERROR_CODE_1001)){
                        docModel.isHistory = false;
                        docModel.isSubmit = false;
                        cancelTextView.setVisibility(View.GONE);
                        progressBar.setProgress(0);
                       // Log.v("UploadFileToServer","false");
                        iResponseReceivedNotifyInterface.responseReceived(new ResponseArgs(docModel, ResponseStatus.fail, RequestType.UploadDocument));

                    }
                    else {
                        docModel.isHistory = true;
                        docModel.isSubmit = true;
                        cancelTextView.setVisibility(View.VISIBLE);
                        progressBar.setProgress(100);
                       // Log.v("UploadFileToServer","docModel.isHistory");
                        iResponseReceivedNotifyInterface.responseReceived(new ResponseArgs(docModel, ResponseStatus.success, RequestType.UploadDocument));
                    }
                }
                catch (Exception e){
                    Log.v("UploadFileToServer",""+e);
                    e.printStackTrace();
                }
                //}
            }
        }
        super.onPostExecute(result);
    }

}

*/
