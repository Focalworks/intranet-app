package intranet.fw.com.intranet;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import intranet.fw.com.CustomListAdapter.GrievanceListAdapter;
import intranet.fw.com.R;
import intranet.fw.com.utils.CommonFunctions;
import intranet.fw.com.utils.Constants;

/**
 * Created by kaustubh on 19/9/14.
 */
public class ListGrievanceActivity extends Activity{

  ListView list;
  String [] category,status,title,body,urgency,time,comment_count;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.list_grievance);

    list =(ListView)findViewById(R.id.grievanceList);

    new GrievanceDataPostCall().execute();

  }

  private class GrievanceDataPostCall extends AsyncTask<Uri, Void, Void> {

    String apiResponse = null;

    @Override
    protected Void doInBackground(Uri...params) {

      apiResponse = CommonFunctions.httpGet(Constants.urlListGrievance);
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      try {
        Log.i("api response",""+apiResponse);
        JSONArray jsonArray = new JSONArray(apiResponse);
        category = new String[jsonArray.length()];
        status = new String[jsonArray.length()];
        title = new String[jsonArray.length()];
        urgency = new String[jsonArray.length()];
        body = new String[jsonArray.length()];
        time = new String[jsonArray.length()];
        comment_count = new String[jsonArray.length()];
        for(int i=0;i<jsonArray.length();i++){
          JSONObject objResponse = jsonArray.getJSONObject(i);
          category[i]= objResponse.get("category").toString();
          status[i]= objResponse.get("status").toString();
          title[i]= objResponse.get("title").toString();
          urgency[i]= objResponse.get("urgency").toString();
          body[i]= objResponse.get("description").toString();
          time[i]= objResponse.get("time_ago").toString();
          comment_count[i] = objResponse.get("comment_count").toString();
        }
        GrievanceListAdapter grievanceAdapter = new GrievanceListAdapter(ListGrievanceActivity.this,category,status,title,body,urgency,time,comment_count);
        list.setAdapter(grievanceAdapter);

      }catch (JSONException e){
        Log.e("JSON exception",""+e);
      }
    }
  }
}
