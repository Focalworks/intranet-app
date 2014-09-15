package intranet.fw.com.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by kaustubh on 11/9/14.
 */
public class CommonFunctions {
  protected Context context;

  public CommonFunctions(Context context){
    this.context = context;
  }

  public boolean isConnected(){
    Boolean value;

    ConnectivityManager connMgr = (ConnectivityManager)
        context.getSystemService(Context.CONNECTIVITY_SERVICE);
    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
    if (networkInfo != null && networkInfo.isConnected())
      value = true;
    else
      value = false;

    return value;
  }

}
