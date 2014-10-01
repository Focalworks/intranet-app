package intranet.fw.com.Service;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import org.json.JSONObject;
import java.util.List;
import intranet.fw.com.Database.DatabaseHelper;
import intranet.fw.com.Database.Grievance;
import intranet.fw.com.utils.CommonFunctions;
import intranet.fw.com.utils.Constants;

/**
 * Created by kaustubh on 25/9/14.
 */
public class GrievanceService extends Service {
  private boolean running;

  @Override
  public void onCreate() {
    super.onCreate();
    Log.d("Service", "Service Created.....");
    GrievancePost grievancePost = new GrievancePost();
    grievancePost.execute();
  }

  @Override
  public IBinder onBind(Intent intent) {
    return null;
  }

  @Override
  public synchronized void onDestroy() {
    super.onDestroy();
    Log.d("Service", "Service Destroyed.....");
  }

  private class GrievancePost extends AsyncTask<Uri, Void, Void> {
    DatabaseHelper db;
    String str;
    @Override
    protected void onPreExecute() {
      super.onPreExecute();
      db = new DatabaseHelper(GrievanceService.this);
    }

    @Override
    protected Void doInBackground(Uri...params) {
      final List<Grievance> grievances= db.getAddGrievanceData();

      for (Grievance gr : grievances) {
        try {
          if (gr.getTitle() != null){
            JSONObject jsonGrievanceObject = new JSONObject();
            jsonGrievanceObject.put("title", gr.getTitle());
            jsonGrievanceObject.put("body", gr.getDescription());
            jsonGrievanceObject.put("category", gr.getCategory());
            jsonGrievanceObject.put("urgency", gr.getUrgency());
            jsonGrievanceObject.put("image", CommonFunctions.getBase64Format(gr.getImg_path()));
            jsonGrievanceObject.put("file_size", CommonFunctions.getImageFileSize(gr.getImg_size()));
            str = Integer.toString(grievances.size()) + gr.getTitle();
            CommonFunctions.httpPost(Constants.urlSaveGrievance,jsonGrievanceObject);
          }else{
            str = "null";
          }
        }catch (Exception e){
          e.printStackTrace();
        }
      }
      return null;
    }

    @Override
    protected void onPostExecute(Void result) {
      Log.i("count",str);
      stopSelf();
    }
  }
}
