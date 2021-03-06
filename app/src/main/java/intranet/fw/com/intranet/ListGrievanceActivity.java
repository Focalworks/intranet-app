package intranet.fw.com.intranet;

import android.app.Activity;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;
import intranet.fw.com.CustomListAdapter.GrievanceListAdapter;
import intranet.fw.com.Database.Grievance;
import intranet.fw.com.R;
import intranet.fw.com.utils.CommonFunctions;
import intranet.fw.com.utils.Constants;

/**
 * Created by kaustubh on 19/9/14.
 */
public class ListGrievanceActivity extends Activity{

  ListView list;
  GrievanceListAdapter grievanceAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.list_grievance);

    list =(ListView)findViewById(R.id.grievanceList);
    grievanceAdapter = new GrievanceListAdapter(this,new ArrayList<Grievance>());
    list.setAdapter(grievanceAdapter);


    new GrievanceDataPostCall().execute();

  }

  private class GrievanceDataPostCall extends AsyncTask<Uri, Void, List<Grievance>> {
    List<Grievance> result = new ArrayList<Grievance>();
    String apiResponse = null;

    @Override
    protected List<Grievance> doInBackground(Uri...params) {

      apiResponse = CommonFunctions.httpGet(Constants.urlListGrievance);
      Log.i("Response api",""+apiResponse);
      try {
        JSONArray jsonArray = new JSONArray(apiResponse);
        for(int i=0;i<jsonArray.length();i++){
          result.add(convertData(jsonArray.getJSONObject(i)));
        }
      }catch (JSONException e){
        Log.e("Response Error",""+e);
      }
      return result;
    }

    @Override
    protected void onPostExecute(List<Grievance> grievanceListData) {
      grievanceAdapter.setItemList(grievanceListData);
      grievanceAdapter.notifyDataSetChanged();
    }
  }

  private Grievance convertData(JSONObject obj) throws JSONException {
    String urgency ="";
    String status = "";
    if(obj.getString("urgency").equals("1")){
      urgency = "Low";
    }else if(obj.getString("urgency").equals("2")){
      urgency = "Medium";
    }else{
      urgency = "High";
    }

    if(obj.getString("status").equals("1")){
      status = "Submitted";
    }else if(obj.getString("status").equals("2")){
      status = "In Progress";
    }else if(obj.getString("status").equals("3")){
      status = "Closed";
    }else{
      status = "Re Opened";
    }

    String id = obj.getString("id");
    String title = obj.getString("title");
    String description = obj.getString("description");
    String category = Character.toUpperCase(obj.getString("category").charAt(0)) + obj.getString("category").substring(1);
    String user_id = obj.getString("user_id");
    String url = obj.getString("url");
    String fileMime = obj.getString("filemime");
    String time_ago = obj.getString("time_ago");
    String comment_count = obj.getString("comment_count");
    String comments = "";
    if(obj.has("comments")){
      comments = obj.getString("comments");
    }

    return new Grievance(id, title, description, category, urgency, user_id, status, url, fileMime, time_ago, comment_count, comments);
  }
}
