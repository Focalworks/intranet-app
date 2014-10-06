package intranet.fw.com.intranet;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import java.io.InputStream;

import intranet.fw.com.Database.Grievance;
import intranet.fw.com.R;
import intranet.fw.com.utils.Constants;

/**
 * Created by kaustubh on 6/10/14.
 */
public class GrievanceFullViewActivity extends Activity {

  TextView txtCategory,txtStatus,txtTitle,txtBody;
  ImageView imgUploadImg;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.grievance_fullview);

    Grievance grievance = (Grievance)getIntent().getParcelableExtra("grievance");

    txtCategory = (TextView) findViewById(R.id.txtCategory);
    txtStatus = (TextView) findViewById(R.id.txtStatus);
    txtTitle = (TextView) findViewById(R.id.txtTitle);
    txtBody = (TextView) findViewById(R.id.txtBody);

    imgUploadImg = (ImageView)findViewById(R.id.imgUploaded_photo);

    txtCategory.setText(grievance.getCategory());
    txtStatus.setText(grievance.getStatus());
    txtTitle.setText(grievance.getTitle());
    txtBody.setText(Html.fromHtml(grievance.getDescription()));

    if(!grievance.getImg_path().equals("null")) {
      new UploadImage(imgUploadImg)
          .execute(Constants.baseUrl + grievance.getImg_path());
    }else{
      imgUploadImg.setVisibility(View.GONE);
    }
  }

  private class UploadImage extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public UploadImage(ImageView bmImage) {
      this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
      String urldisplay = urls[0];
      Bitmap mIcon11 = null;
      try {
        InputStream in = new java.net.URL(urldisplay).openStream();
        mIcon11 = BitmapFactory.decodeStream(in);
      } catch (Exception e) {
        Log.e("Error", e.getMessage());
        e.printStackTrace();
      }
      return mIcon11;
    }

    protected void onPostExecute(Bitmap result) {
      bmImage.setImageBitmap(result);
    }
  }
}
