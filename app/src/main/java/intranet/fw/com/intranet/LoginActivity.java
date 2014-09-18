package intranet.fw.com.intranet;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.Toast;
import org.json.JSONObject;
import java.net.URLDecoder;
import java.util.List;
import intranet.fw.com.Database.DatabaseHelper;
import intranet.fw.com.Database.User;
import intranet.fw.com.Oauth2.OAuth2Helper;
import intranet.fw.com.Oauth2.Oauth2Params;
import intranet.fw.com.R;
import intranet.fw.com.authentication.OAuth;
import intranet.fw.com.utils.Constants;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener{
  Button btnLogin;
  private WebView  webview;
  private ProgressDialog progressDialog;
  AlertDialog loginScreenDialog;

  DatabaseHelper db;
  OAuth auth;
  private SharedPreferences prefs;
  private OAuth2Helper oAuth2Helper;

  boolean handled=false;
  private boolean hasLoggedIn;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login);
    db= new DatabaseHelper(getApplicationContext());
    final List<User> userDetail= db.getUserDetail();
    /*
    * To check user logged in
    * */
    /*if(userDetail.get(0).getName() != null){
      startActivity(new Intent().setClass(this,Dashboard.class));
      finish();
    }*/

    auth = new OAuth(this);

    btnLogin = (Button)findViewById(R.id.btnLogin);
    btnLogin.setOnClickListener(this);
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()) {
      case R.id.btnLogin:
        openSelectionPopup();
        break;

      default:
        break;
    }
  }

  public void OAuthAccessToken(){
    loginScreenDialog = new AlertDialog.Builder(LoginActivity.this).create();
    loginScreenDialog.getWindow().setSoftInputMode(
        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);

    db =new DatabaseHelper(LoginActivity.this);
    Log.i(Constants.TAG, "Starting task to retrieve request token.");
    this.prefs = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
    oAuth2Helper = new OAuth2Helper(this.prefs);

    webview = new WebView(this);
    webview.getSettings().setJavaScriptEnabled(true);
    webview.setVisibility(View.VISIBLE);
    loginScreenDialog.setView(webview);

    String authorizationUrl = oAuth2Helper.getAuthorizationUrl();
    Log.i(Constants.TAG, "Using authorizationUrl = " + authorizationUrl);

    handled=false;

    webview.setWebViewClient(new WebViewClient() {

      @Override
      public void onPageStarted(WebView view, String url,Bitmap bitmap)  {
        Log.d(Constants.TAG, "onPageStarted : " + url + " handled = " + handled);
      }
      @Override
      public void onPageFinished(final WebView view, final String url)  {
        Log.d(Constants.TAG, "onPageFinished : " + url + " handled = " + handled);
        if (url.startsWith(Constants.OAUTH2PARAMS.getRederictUri())) {
          webview.setVisibility(View.INVISIBLE);

          if (!handled) {
            new ProcessToken(url,oAuth2Helper).execute();
          }
        } else {
          if(getResources().getConfiguration().equals("1")) {
            setDialogWidthHeight(1);
          }else{
            setDialogWidthHeight(2);
          }
          progressDialog.dismiss();
          webview.setVisibility(View.VISIBLE);
        }
      }
    });

    webview.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View view, MotionEvent motionEvent) {
        loginScreenDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE|WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
        return false;
      }
    });

    webview.getSettings().setUseWideViewPort(true);
    webview.loadUrl(authorizationUrl);
    loginScreenDialog.show();
    loginScreenDialog.setCancelable(false);
    loginScreenDialog.getWindow().setLayout(0, 0);


    progressDialog = ProgressDialog.show(LoginActivity.this, "", "Loading...");
    WindowManager.LayoutParams params = progressDialog.getWindow().getAttributes();
    params.width = 300;
    params.height = 200;
    progressDialog.getWindow().setAttributes(params);
  }

  /*
  * Open dialog to select authentication type using already configured account in mobile using Google Plus Services or using
  * google login screen using OAuth2 Google Api JAVA Client
  * */
  public void openSelectionPopup(){
    // Create custom dialog object
    final Dialog dialog = new Dialog(this);
    //remove dialog title
    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
    // Include dialog.xml file
    dialog.setContentView(R.layout.login_selection);
    dialog.show();

    // set values for custom dialog components - text, image and button
    Button btnAlreadyConf = (Button)dialog.findViewById(R.id.btnAlreadyConf);
    btnAlreadyConf.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        auth.authenticateUser();
        // Close dialog
        dialog.dismiss();
      }
    });

    Button btnNotConf = (Button)dialog.findViewById(R.id.btnNotConf);
    btnNotConf.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        startOauthFlow(Oauth2Params.GOOGLE_PLUS);
        // Close dialog
        dialog.dismiss();
      }
    });
  }

  /**
   * Starts the activity that takes care of the OAuth2 flow
   *
   * @param oauth2Params
   */
  private void startOauthFlow(Oauth2Params oauth2Params) {
    Constants.OAUTH2PARAMS = oauth2Params;
    OAuthAccessToken();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.login, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
    setDialogWidthHeight(newConfig.orientation);
  }

  public void setDialogWidthHeight(int orientation){
    if(orientation == 1)
      loginScreenDialog.getWindow().setLayout(700, 600);
    else
      loginScreenDialog.getWindow().setLayout(1000, 600);
  }

  private class ProcessToken extends AsyncTask<Uri, Void, Void> {

    String url;
    boolean startActivity=false;


    public ProcessToken(String url,OAuth2Helper oAuth2Helper) {
      this.url=url;
    }

    @Override
    protected Void doInBackground(Uri...params) {


      if (url.startsWith(Constants.OAUTH2PARAMS.getRederictUri())) {
        Log.i(Constants.TAG, "Redirect URL found" + url);
        handled=true;
        try {
          if (url.contains("code=")) {
            String authorizationCode = extractCodeFromUrl(url);

            Log.i(Constants.TAG, "Found code = " + authorizationCode);

            oAuth2Helper.retrieveAndStoreAccessToken(authorizationCode);
            startActivity=true;
            hasLoggedIn=true;

          } else if (url.contains("error=")) {
            startActivity=false;
          }

        } catch (Exception e) {
          e.printStackTrace();
        }

      } else {
        Log.i(Constants.TAG, "Not doing anything for url " + url);
      }
      return null;
    }

    private String extractCodeFromUrl(String url) throws Exception {
      String encodedCode = url.substring(Constants.OAUTH2PARAMS.getRederictUri().length()+7,url.length());
      loginScreenDialog.dismiss();
      startActivity(new Intent().setClass(LoginActivity.this,Dashboard.class));
      finish();
      return URLDecoder.decode(encodedCode, "UTF-8");
    }

    @Override
    protected void onPreExecute() {

    }

    /**
     * When we're done and we've retrieved either a valid token or an error from the server,
     * we'll return to our original activity
     */
    @Override
    protected void onPostExecute(Void result) {
      if (startActivity) {
        ApiCallExecutor apiCall = new ApiCallExecutor();
        apiCall.execute();
      }else{
        loginScreenDialog.dismiss();
      }
    }
    private class ApiCallExecutor extends AsyncTask<Uri, Void, Void> {

      String apiResponse = null;

      @Override
      protected Void doInBackground(Uri...params) {

        try {
          apiResponse = oAuth2Helper.executeApiCall();
          Log.i(Constants.TAG, "Received response from API : " + apiResponse);
        } catch (Exception ex) {
          ex.printStackTrace();
          apiResponse=ex.getMessage();
        }
        return null;
      }

      @Override
      protected void onPostExecute(Void result) {

        try {

          JSONObject obj = new JSONObject(apiResponse);
          db.addUserDetail(new User(obj.getString("id"),obj.getString("name"),obj.getString("gender"),obj.getString("email"),obj.getString("picture")));

        } catch (Throwable t) {
          Log.e("Intranet", "Could not parse malformed JSON: \"" + result + "\"");
        }
        Toast.makeText(LoginActivity.this,""+apiResponse,Toast.LENGTH_LONG).show();
      }
    }
  }
}
