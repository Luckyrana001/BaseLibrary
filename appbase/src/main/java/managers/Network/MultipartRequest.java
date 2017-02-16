package managers.Network;/*
package org.csc.hajj.managers.Network;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;

import org.apache.http.repackaged.entity.mime.MultipartEntity;
import org.apache.http.repackaged.entity.mime.content.FileBody;
import org.apache.http.repackaged.entity.mime.content.StringBody;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;

*/
/**
 * Created by Lucky on 10/14/16.
 *//*

public class MultipartRequest  extends Request<String> {

    private MultipartEntity entity = new MultipartEntity();

    private static final String FILE_PART_NAME = "fileUpload";
    private static final String STRING_PART_NAME = "text";

    private final Response.Listener<String> mListener;
    private final File mFilePart;
    private final String mUserName;
    private final int mPolicyDocTypeId ;


    public MultipartRequest(String url, String userName, int policyDocTypeId1, Response.ErrorListener errorListener, Response.Listener<String> listener, File file)
    {
        super(Method.POST, url, errorListener);

        mListener = listener;
        mFilePart = file;
        mUserName = userName;
        mPolicyDocTypeId = policyDocTypeId1;
        buildMultipartEntity();
    }

    private void buildMultipartEntity()
    {
        entity.addPart(FILE_PART_NAME, new FileBody(mFilePart));
        try
        {
            entity.addPart("username", new StringBody(mUserName));
            entity.addPart("policyDocTypeId", new StringBody(Integer.toString(mPolicyDocTypeId)));
        }
        catch (UnsupportedEncodingException e)
        {
           // VolleyLog.e("UnsupportedEncodingException");
        }
    }

    @Override
    public String getBodyContentType()
    {
        return entity.getContentType().getValue();
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return super.getHeaders();
    }

    @Override
    public byte[] getBody() throws AuthFailureError
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try
        {
            entity.writeTo(bos);
        }
        catch (IOException e)
        {
            //VolleyLog.e("IOException writing to ByteArrayOutputStream");
        }
        return bos.toByteArray();
    }

    @Override
    protected Response<String> parseNetworkResponse(NetworkResponse response)
    {
        return Response.success("Uploaded", getCacheEntry());
    }

    @Override
    protected void deliverResponse(String response)
    {
        mListener.onResponse(response);
    }
}*/
