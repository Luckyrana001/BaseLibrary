package managers;



/**
 * Created by Lucky on 9/25/16.
 */

public class AppConstants {


    public static int SELECTED_INDEX = 0;
    public static String SELECTED_PANNEL = "";

    public static final boolean IS_DEBUG = false;
    public static final String TOKEN_HEADER_AUTHORIZATION = "Token-Authorization";
    public static final String CONTENT_TYPE_KEY = "Content-Type";
    public static final String CONTENT_TYPE_VALUE = "application/json";

    public static final String MULTIPART_BOUNDARY_VALUE = "multipart/form-data;";



    /*public static String BASE_ROOT_URL = ConstantBaseUrl.BASE_URL;
    public static String ROOT_URL = ConstantBaseUrl.ROOT_URL;
    public static boolean IS_HTTPS = ConstantBaseUrl.IS_HTTPS;*//*

    //public static String ROOT_URL = "iswift/";
    //constant value
    public static final String DEVICE_TYPE = "Android";


    public static String getRootUrl(String filePath){
        return BASE_ROOT_URL + ROOT_URL + filePath;
    }
    public static String getConfigUrl()
    {
        return BASE_ROOT_URL + ROOT_URL + "getAppConfig/";
    }
    public static String getSignUpUrl()
    {
        return BASE_ROOT_URL + ROOT_URL + "signup";
    }
    public static String getAPISessionPulse(){
        return BASE_ROOT_URL + ROOT_URL + "sessionPulse/";
    }

    public static String getLoginApiUrl(){
        return BASE_ROOT_URL + ROOT_URL + "login";
    }*/

}

