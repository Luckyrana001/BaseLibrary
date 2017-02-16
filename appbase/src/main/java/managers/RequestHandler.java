package managers;

import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.extect.appbase.BaseModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.csc.hajj.model.LoginResponseModel;
import org.csc.hajj.model.SignUpModel;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import listeners.IResponseReceivedNotifyInterface;
import listeners.ResponseArgs;
import network.RequestHeaderProvider;
import shared.BaseFlyContext;
import utils.RequestType;
import utils.ResponseStatus;

import static org.csc.hajj.managers.utils.Constants.TIMEOUT_VALUE;

/**
 * Created by Lucky on 9/16/16.
 */
public class RequestHandler {/*

    public static RequestHandler requestHandler;
    protected Gson gson;
    RequestQueue queue;

    public RequestHandler() {
        queue = Volley.newRequestQueue(BaseFlyContext.getInstant().getApplicationContext());
        gson = new GsonBuilder().create();
    }

    public static RequestHandler getRequestHandler() {

        if (requestHandler == null) {
            requestHandler = new RequestHandler();
        }
        return requestHandler;
    }
    public void signUp(String fullName,
                       String userName,
                       String password,
                       String email,
                       String dateOfBirth,
                       String nric,
                       String phoneNumber,
                       String Nationality,
                 String baseEncodedImage,final IResponseReceivedNotifyInterface iResponseReceivedNotifyInterface)
    {
  *//*Post data*//*
        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("Person_Name", fullName);
        jsonParams.put("Person_Email", email);
        jsonParams.put("Org_Name", "CSC-Xchanging");
        jsonParams.put("Username", userName);
        jsonParams.put("User_Password", password);
        jsonParams.put("Passport_No", nric);
        jsonParams.put("PhoneNo", phoneNumber);
        jsonParams.put("Photo", baseEncodedImage);
        jsonParams.put("DeviceType", AppConstants.DEVICE_TYPE);
        jsonParams.put("DeviceID", "djflksdjdfdflszi23kljfl3e4324");
        jsonParams.put("DateOfBirth", dateOfBirth);
        jsonParams.put("Person_National", Nationality);

        jsonParams.put("RFID", "");
        jsonParams.put("AccessToken", "");
        String url = AppConstants.getSignUpUrl();

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url,
                new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.i("data",response.toString());

                       // {"UserID":2907}  userName : 123456789   password 123456 eamil : lucky.rana@xchanging.com
                        Type type = new TypeToken<SignUpModel>() {
                        }.getType();
                        SignUpModel signUpModel = gson.fromJson(response.toString(), type);
                        iResponseReceivedNotifyInterface.responseReceived(new ResponseArgs(signUpModel, ResponseStatus.success, RequestType.SignUp));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iResponseReceivedNotifyInterface.responseReceived(new ResponseArgs(null, ResponseStatus.badRequest, RequestType.SignUp));
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return new RequestHeaderProvider().getRequestHeaders();
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };

        *//*postRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT_VALUE,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*//*
        queue.add(postRequest);
    }

    public void sessionPulse(final IResponseReceivedNotifyInterface iResponseReceivedNotifyInterface, String userName) {

         *//*Post data*//*
        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("username", userName);
        String url = AppConstants.getAPISessionPulse();

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Type type = new TypeToken<BaseModel>() {
                        }.getType();
                        iResponseReceivedNotifyInterface.responseReceived(new ResponseArgs(gson.fromJson(response.toString(), type), ResponseStatus.success, RequestType.SessionPulse));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iResponseReceivedNotifyInterface.responseReceived(new ResponseArgs(null, ResponseStatus.badRequest, RequestType.SessionPulse));
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return new RequestHeaderProvider().getRequestHeaders();
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };


        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT_VALUE,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        queue.add(postRequest);
    }
    public void stopQueueRequests() {
        queue.cancelAll(new RequestQueue.RequestFilter() {
            @Override
            public boolean apply(Request<?> request) {
                //Log.d("DEBUG","request running: "+request.getTag().toString());
                return true;
            }
        });
    }

    public void login(final IResponseReceivedNotifyInterface iResponseReceivedNotifyInterface, String userName, String password) {

    *//*Post data*//*
        Map<String, String> jsonParams = new HashMap<>();
        jsonParams.put("Username", userName);
        jsonParams.put("User_Password", password);
        jsonParams.put("DeviceType", AppConstants.DEVICE_TYPE);
        jsonParams.put("DeviceUID", "djflksdjdfdflszi23kljfl3e4324");
        String url = AppConstants.getLoginApiUrl();

        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonParams),
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Type type = new TypeToken<LoginResponseModel>() {
                        }.getType();
                        iResponseReceivedNotifyInterface.responseReceived(new ResponseArgs(gson.fromJson(response.toString(), type), ResponseStatus.success, RequestType.SignIn));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        iResponseReceivedNotifyInterface.responseReceived(new ResponseArgs(null, ResponseStatus.badRequest, RequestType.SignIn));
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                return new RequestHeaderProvider().getRequestHeaders();
            }

            @Override
            protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
                return super.parseNetworkResponse(response);
            }
        };


        *//*postRequest.setRetryPolicy(new DefaultRetryPolicy(
                TIMEOUT_VALUE,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));*//*
        queue.add(postRequest);
    }*/
}
