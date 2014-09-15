package intranet.fw.com.inranet.authentication;

import android.app.Activity;
import android.os.AsyncTask;
import android.widget.Toast;

import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * Created by kaustubh on 10/9/14.
 */
public class GetUserDetails extends AsyncTask<String,String,String>{
  Activity mActivity;
  String mScope;
  String mEmail;

  GetUserDetails(Activity activity, String name, String scope) {
    this.mActivity = activity;
    this.mScope = scope;
    this.mEmail = name;
  }

  @Override
  protected String doInBackground(String... strings) {
    String token="";
    try {
      token = fetchToken();
      if (token != null) {
        // Insert the good stuff here.
        // Use the token to access the user's Google data.
        GoogleCredential credential = new GoogleCredential().setAccessToken(token);
        Oauth2 oauth2 = new Oauth2.Builder(new NetHttpTransport(), new JacksonFactory(), credential).setApplicationName(
            "Oauth2").build();
        Userinfoplus userinfo = oauth2.userinfo().get().execute();
        return userinfo.toPrettyString();
      }
    } catch (IOException e) {
      // The fetchToken() method handles Google-specific exceptions,
      // so this indicates something went wrong at a higher level.
      // TIP: Check for network connectivity before starting the AsyncTask.
      e.printStackTrace();
    }
    return null;
  }

  /**
   * Executes the asynchronous job. This runs when you call execute()
   * on the AsyncTask instance.
   */

  @Override
  protected void onPostExecute(String result) {
    super.onPostExecute(result);
    Toast.makeText(mActivity,result,Toast.LENGTH_LONG).show();
  }

  /**
   * Gets an authentication token from Google and handles any
   * GoogleAuthException that may occur.
   */
  protected String fetchToken() throws IOException {
    try {
      return GoogleAuthUtil.getToken(mActivity, mEmail, mScope);
    } catch (UserRecoverableAuthException userRecoverableException) {
      // GooglePlayServices.apk is either old, disabled, or not present
      // so we need to show the user some UI in the activity to recover.
      userRecoverableException.printStackTrace();
    } catch (GoogleAuthException fatalException) {
      // Some other type of unrecoverable exception has occurred.
      // Report and log the error as appropriate for your app.
      fatalException.printStackTrace();
    }
    return null;
  }
}
