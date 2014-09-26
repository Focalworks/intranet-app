package intranet.fw.com.authentication;

import android.accounts.Account;
import android.app.Activity;
import android.widget.Toast;

import com.google.android.gms.drive.internal.ac;
import com.google.api.client.googleapis.extensions.android.accounts.GoogleAccountManager;
import java.util.ArrayList;
import java.util.List;
import intranet.fw.com.utils.CommonFunctions;

/**
 * Created by kaustubh on 10/9/14.
 */
public class OAuth {

  protected Activity activity;
  List<String> names;
  GoogleAccountManager googleAccountManager;
  CommonFunctions commonFunc;
  private final static String G_PLUS_SCOPE = "oauth2:https://www.googleapis.com/auth/plus.me";
  private final static String USERINFO_SCOPE = "https://www.googleapis.com/auth/userinfo.profile https://www.googleapis.com/auth/userinfo.email";
  private final static String SCOPES = G_PLUS_SCOPE + " " + USERINFO_SCOPE;

  public OAuth(Activity activity){
    this.activity = activity;
  }

  public void authenticateUser(){
    commonFunc = new CommonFunctions(activity);
    googleAccountManager = new GoogleAccountManager(activity);
    Account[] accounts = googleAccountManager.getAccounts();

    names= new ArrayList<String>();
    for (int i=0 ; i < accounts.length ; i++){
      names.add(accounts[i].name);
      if(accounts[i].name.contains("focalworks")) {
        if (commonFunc.checkNetworkConnection(activity)) {
          new GetUserDetails(activity, accounts[i].name, SCOPES).execute();
        } else {
          Toast.makeText(activity, "No network connection available.", Toast.LENGTH_LONG).show();
        }
      }
      else{
        Toast.makeText(activity, "Focalworks Account Not Found.", Toast.LENGTH_LONG).show();
      }
    }
  }

}
