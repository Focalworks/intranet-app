package intranet.fw.com.utils;

import intranet.fw.com.Oauth2.Oauth2Params;

/**
 * Created by kaustubh on 15/9/14.
 */
public class Constants {

  public static final String TAG = "AndroidOauth2";
  public static Oauth2Params OAUTH2PARAMS = Oauth2Params.GOOGLE_PLUS;
//  public static final String baseUrl = "http://staging.focalworks.in/intranet/public/";
  public static final String baseUrl = "http://192.168.3.170/focalworks-intranet/public/";

  public static final String urlListGrievance = baseUrl + "api/grievance-list";
  public static final String urlSaveGrievance = baseUrl + "api/grievance-save";

}
