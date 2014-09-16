package intranet.fw.com.authentication;

import android.app.Activity;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.google.android.gms.auth.GoogleAuthException;
import com.google.android.gms.auth.GoogleAuthUtil;
import com.google.android.gms.auth.UserRecoverableAuthException;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.oauth2.Oauth2;
import com.google.api.services.oauth2.model.Userinfoplus;
import org.json.JSONObject;
import java.io.IOException;
import intranet.fw.com.Database.DatabaseHelper;
import intranet.fw.com.Database.User;

/**
 * Created by kaustubh on 10/9/14.
 */
public class GetUserDetails extends AsyncTask<String,String,String>{
  Activity mActivity;
  String mScope;
  String mEmail;
  DatabaseHelper db;

  GetUserDetails(Activity activity, String name, String scope) {
    this.mActivity = activity;
    this.mScope = scope;
    this.mEmail = name;
    db = new DatabaseHelper(activity);
  }

  @Override
  protected String doInBackground(String... strings) {
    String token;
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
    try {

      JSONObject obj = new JSONObject(result);
      db.addUserDetail(new User(obj.getString("id"),obj.getString("name"),obj.getString("gender"),obj.getString("email"),obj.getString("picture")));

    } catch (Throwable t) {
      Log.e("Intranet", "Could not parse malformed JSON: \"" + result + "\"");
    }
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
