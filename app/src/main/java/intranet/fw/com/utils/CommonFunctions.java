package intranet.fw.com.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by kaustubh on 11/9/14.
 */
public class CommonFunctions {
  protected Context context;

  public CommonFunctions(Context context){
    this.context = context;
  }

  //check wifi and internet availability
  public static boolean checkNetworkConnection(Context context)
  {
    final ConnectivityManager connMgr = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

    final android.net.NetworkInfo wifi =connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    final android.net.NetworkInfo mobile =connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

    if(wifi.isAvailable())
      return true;
    else if(mobile.isAvailable())
      return true;
    else
      return false;
  }

  //Converting http response to string
  public static String convertStreamToString(InputStream is) {
    String line = "";
    StringBuilder total = new StringBuilder();
    BufferedReader rd = new BufferedReader(new InputStreamReader(is));
    try {
      while ((line = rd.readLine()) != null) {
        total.append(line);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return total.toString();
  }

  public static String getBase64Format(final String imgPath)
  {
    Bitmap bitmapOrg = BitmapFactory.decodeFile(imgPath);
    ByteArrayOutputStream bao = new ByteArrayOutputStream();
    bitmapOrg.compress(Bitmap.CompressFormat.PNG, 90, bao);
    byte [] ba = bao.toByteArray();
    String ba1=Base64.encodeBytes(ba);

    return ba1;
  }

  public static String getImageFileSize(final String imgPath){

    File file = new File(imgPath);
    String length = Long.toString(file.length());

    return length;
  }

  public static String httpPost(final String url, final JSONObject jsonObj){
    String resultString="";
    try {
      HttpParams httpParams = new BasicHttpParams();
      HttpConnectionParams.setConnectionTimeout(httpParams, 10000); //Timeout Limit
      HttpResponse response;
      HttpClient client = new DefaultHttpClient();
      HttpPost post = new HttpPost(url);
      post.setHeader("Content-Type", "application/json");
      post.setHeader("EMAIL", "amitav.roy@focalworks.in");

      //Add Data to Post
      post.setEntity(new StringEntity(jsonObj.toString()));
      Log.d("Json: ", jsonObj.toString());
      // Execute HTTP Post Request
      response = client.execute(post);

      int res_code = response.getStatusLine().getStatusCode();
      Log.d("res_code: ", Integer.toString(res_code));

					/*Checking response */
      HttpEntity resultEntity = response.getEntity();
      InputStream is = resultEntity.getContent();

      resultString = convertStreamToString(is);

      is.close();
    }catch (Exception e){
      e.printStackTrace();
    }
    return resultString;
  }

  public static String httpGet(final String url){
    String resultString="";

    // Create a new HttpClient and Post Header
    HttpClient httpclient = new DefaultHttpClient();
    HttpGet httpGet = new HttpGet(url);

    try {
      // Add header
      httpGet.setHeader("EMAIL","amitav.roy@focalworks.in");

      // Execute HTTP Post Request
      HttpResponse response = httpclient.execute(httpGet);

      InputStream is = response.getEntity().getContent();
      resultString = CommonFunctions.convertStreamToString(is);

    } catch (ClientProtocolException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }

    return resultString;
  }
}
