package intranet.fw.com.intranet;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.ByteArrayBuffer;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import intranet.fw.com.R;

/**
 * Created by kaustubh on 19/9/14.
 */
public class ListGrievanceActivity extends Activity {

  TextView txtGrievance;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.list_grievance);

    txtGrievance =(TextView)findViewById(R.id.txt_grievance);
    new GrievanceDataPostCall().execute();
  }

  private class GrievanceDataPostCall extends AsyncTask<Uri, Void, Void> {

    String apiResponse = null;
    HttpResponse response;

    @Override
    protected Void doInBackground(Uri...params) {

      // Create a new HttpClient and Post Header
      HttpClient httpclient = new DefaultHttpClient();
      HttpPost httppost = new HttpPost("http://192.168.3.115/focalworks-intranet/public/api/grievance-list");
//      HttpPost httppost = new HttpPost("http://staging.focalworks.in/intranet/public/api/grievance-list");

      try {
        // Add header
        httppost.setHeader("EMAIL","amitav.roy@focalworks.in");

        // Execute HTTP Post Request
        response = httpclient.execute(httppost);

        InputStream is = response.getEntity().getContent();
        BufferedInputStream bis = new BufferedInputStream(is);
        ByteArrayBuffer baf = new ByteArrayBuffer(20);

        int current = 0;

        while((current = bis.read()) != -1){
          baf.append((byte)current);
        }

      /* Convert the Bytes read to a String. */
        apiResponse = new String(baf.toByteArray());

      } catch (ClientProtocolException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      Toast.makeText(getApplicationContext(),""+response.getStatusLine().getStatusCode(),Toast.LENGTH_LONG).show();
      txtGrievance.setText(apiResponse);
    }
  }
}
