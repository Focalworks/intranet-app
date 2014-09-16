package intranet.fw.com.intranet;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import intranet.fw.com.Oauth2.OAuthAccessTokenActivity;
import intranet.fw.com.Oauth2.Oauth2Params;
import intranet.fw.com.R;
import intranet.fw.com.authentication.OAuth;
import intranet.fw.com.utils.Constants;


public class LoginActivity extends ActionBarActivity implements View.OnClickListener{
  Button btnLogin;
  OAuth auth;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.login);

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
    startActivity(new Intent().setClass(this,OAuthAccessTokenActivity.class));
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

}
