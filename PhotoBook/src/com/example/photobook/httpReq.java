package com.example.photobook;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.net.http.AndroidHttpClient;
import android.util.Log;


public class httpReq {
	static final String APIBaseURL = "http://cis-linux2.temple.edu/~tuf77221/";
	 /**
     * Make API call with provided data to specified end point.
     * 
     * @param context Context object
     * @param api End point of API call
     * @param values Key/Value pairs for post data
     * @return Server response
     * @throws ClientProtocolException
     * @throws IOException
     */
    private static String makeAPICall(Context context, RequestMethod requestType, String api, JSONObject values, String userName) throws ClientProtocolException, IOException {
    	
    	AndroidHttpClient client = AndroidHttpClient.newInstance("Android", context);
        HttpResponse httpResponse;
        
    	if (requestType == RequestMethod.POST){
	    	HttpPost method = new HttpPost(APIBaseURL + api);
	    	method.addHeader("Accept-Encoding", "gzip");
	    	method.setHeader("Content-type", "application/json");
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	        if (values != null)
	            nameValuePairs.add(new BasicNameValuePair("data", values.toString()));
	        method.setEntity(new StringEntity(values.toString()));
	        httpResponse = client.execute(method);
    	} else if (requestType == RequestMethod.PUT) {
    		HttpPut method = new HttpPut(APIBaseURL + api);
	    	method.addHeader("Accept-Encoding", "gzip");
	    	method.setHeader("Content-type", "application/json");
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
	        if (values != null)
	            nameValuePairs.add(new BasicNameValuePair("data", values.toString()));
	        method.setEntity(new StringEntity(values.toString()));
	        httpResponse = client.execute(method);
    	} else if (requestType == RequestMethod.DELETE){
    		HttpDelete method = new HttpDelete(APIBaseURL + api);
    		method.addHeader("Accept-Encoding", "gzip");
    		httpResponse = client.execute(method);
    	} else {
    		HttpGet method = new HttpGet(APIBaseURL + api);
    		method.addHeader("Accept-Encoding", "gzip");
    		httpResponse = client.execute(method);
    	}
        
    	String response = extractHttpResponse(httpResponse);

        Log.i("API Call", requestType + ":" + api);
        if (values != null)
            Log.i("API Parameters", values.toString());

        Log.i("API Response", response.toString());
        client.close();
        return response.toString();
    }
    
    /**
     * Make API call to upload a single file along with provided data to specified end point.
     * 
     * @param context Context object
     * @param api End point of API call
     * @param values Key/Value pairs for post data
     * @param Map of files to be uploaded
     * @return Server response
     * @throws ClientProtocolException
     * @throws IOException
     */
    private static String makeAPICall(Context context, String api, JSONObject values, Map<String, File> files, String userName) throws ClientProtocolException, IOException {

    	AndroidHttpClient client = AndroidHttpClient.newInstance("Android", context);
    	HttpResponse httpResponse = null;
    	
		HttpPost method = new HttpPost(APIBaseURL + api);
		method.addHeader("Accept-Encoding", "gzip");
		MultipartEntity mEntity = new MultipartEntity();
        
        if (values != null)
        	mEntity.addPart("data", new StringBody(values.toString()));
        
        if (files != null){
	        Iterator<String> keys = files.keySet().iterator();
	        
	        while (keys.hasNext()){
	        	String fieldName = keys.next();
	        	mEntity.addPart(fieldName, new FileBody(files.get(fieldName)));
	        }
	        method.setEntity(mEntity);
        }
        httpResponse = client.execute(method);
    	
    	String response = extractHttpResponse(httpResponse);

        Log.i("API Call", api);
        if (values != null)
            Log.i("API Parameters", values.toString());
        if (files != null)
            Log.i("API File Parameters", files.toString());

        Log.i("API Response", response);
        client.close();
        return response;
    }
    
    private static String extractHttpResponse(HttpResponse httpResponse) throws IllegalStateException, IOException{
    	InputStream instream = httpResponse.getEntity().getContent();

        Header contentEncoding = httpResponse.getFirstHeader("Content-Encoding");
        
        if (contentEncoding != null && contentEncoding.getValue().equalsIgnoreCase("gzip")) {
            instream = new GZIPInputStream(instream);
        }
        
        BufferedReader r = new BufferedReader(new InputStreamReader(instream));
        
        StringBuilder response = new StringBuilder();
        String line = "";
        
        while ((line = r.readLine()) != null) {
            response.append(line);
        }
        return response.toString();
    }
    
    public static boolean uploadPhotos(Context context, String userName, String photoName, String photoPath, String photoCaption, String timeStamp, String gpsLocation, String locAltitude, String locTemp, File image) throws Exception{
        JSONObject requestObject = new JSONObject();
        requestObject.put("userName", userName);
        requestObject.put("photoCaption", photoCaption);
        requestObject.put("photoName", photoName);
        requestObject.put("photoPath", photoPath);
        requestObject.put("gpsLocation", gpsLocation);
        requestObject.put("locAltitude", locAltitude);
        requestObject.put("locTemp", locTemp);
        requestObject.put("timeStamp", timeStamp);

        Map<String, File> files = new HashMap<String, File>();
        
        files.put("image", image);
        
        String response = makeAPICall(context, "/getPhotobook.php", requestObject, files, userName);
        try {
            JSONObject responseObject = new JSONObject(response);
            if (responseObject.getString("status").equalsIgnoreCase("ok"))
            	return true;
        } catch (JSONException e) {
            Log.i("JSON Error in: ", response);
            e.printStackTrace();
        }
        return false;
    }
    
    public static JSONArray getPhotos(Context context, String userName) throws Exception{
    	
        String response = makeAPICall(context,  RequestMethod.GET, "getPhotobook.php", null, userName);
        
        try {
            JSONObject responseObject = new JSONObject(response);
            if (responseObject.getString("status").equalsIgnoreCase("ok")){
                return responseObject.getJSONArray("photos");
            }
        } catch (JSONException e) {
            Log.i("JSON Error in: ", response);
            e.printStackTrace();
        }
        return null;
    }
    
    
    private enum RequestMethod {
    	POST, GET, PUT, DELETE
    }


	
}
