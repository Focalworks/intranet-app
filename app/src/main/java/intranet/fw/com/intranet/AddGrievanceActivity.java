package intranet.fw.com.intranet;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import org.json.JSONObject;

import intranet.fw.com.Database.DatabaseHelper;
import intranet.fw.com.Database.Grievance;
import intranet.fw.com.R;
import intranet.fw.com.utils.CommonFunctions;
import intranet.fw.com.utils.Constants;

/**
 * Created by kaustubh on 19/9/14.
 */
public class AddGrievanceActivity extends Activity implements View.OnClickListener{

  Button btnSave,btnBack,btnUploadImage;
  Spinner spCategory,spUrgency;
  EditText etTitle,etBody;
  private ImageView mImageView;
  DatabaseHelper db;

  private AlertDialog dialog;
  private Uri mImageCaptureUri;
  String TEMP_PHOTO_FILE;
  String picturePath;
  private static final int PICK_FROM_CAMERA = 1;
  private static final int PICK_FROM_FILE = 2;
  Uri cameraImagePath;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.add_grievence);

    db = new DatabaseHelper(this);

    etTitle = (EditText)findViewById(R.id.et_title);
    etBody = (EditText)findViewById(R.id.et_body);

    mImageView		= (ImageView) findViewById(R.id.uploaded_photo);

    btnSave = (Button) findViewById(R.id.btn_save);
    btnBack = (Button) findViewById(R.id.btn_back);
    btnUploadImage = (Button) findViewById(R.id.btn_upload_img);

    btnSave.setOnClickListener(this);
    btnBack.setOnClickListener(this);
    btnUploadImage.setOnClickListener(this);

    spCategory = (Spinner)findViewById(R.id.sp_category);
    spUrgency = (Spinner)findViewById(R.id.sp_urgency);

    spCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if(position == 0){
//          Toast.makeText(getApplicationContext(),"Please select Category",Toast.LENGTH_LONG).show();
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });

    spUrgency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if(position == 0){
//          Toast.makeText(getApplicationContext(),"Please select Urgency",Toast.LENGTH_LONG).show();
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });

    TEMP_PHOTO_FILE = String.valueOf(System.currentTimeMillis());

    final String [] items			= new String [] {"Take from camera", "Select from gallery"};
    ArrayAdapter<String> adapter	= new ArrayAdapter<String> (this, android.R.layout.select_dialog_item,items);
    AlertDialog.Builder builder		= new AlertDialog.Builder(this);

    builder.setTitle("Select Image");
    builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
      public void onClick( DialogInterface dialog, int item ) { //pick from camera
        if (item == 0) {
          Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
          ContentValues values = new ContentValues(3);

          values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
          cameraImagePath = getContentResolver().insert(
              MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
          takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraImagePath);
          startActivityForResult(takePictureIntent, PICK_FROM_CAMERA);
          /*Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
          startActivityForResult(cameraIntent, PICK_FROM_CAMERA);*/
        } else {
          //pick from file
          Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
          startActivityForResult(i, PICK_FROM_FILE);
        }
      }
    } );

    dialog = builder.create();
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()){
      case R.id.btn_save:
        PostGrievance post = new PostGrievance();
        post.execute(Constants.urlSaveGrievance);
        break;

      case R.id.btn_back:
        Toast.makeText(getApplicationContext(),"Back",Toast.LENGTH_LONG).show();
        break;

      case R.id.btn_upload_img:
        dialog.show();
        break;

      default:
        break;

    }
  }

  public void validation(){
    if(etTitle.getText().toString().equals(""))
      etTitle.setError("Enter Title");
    else
      etTitle.setError(null);

    if(etBody.getText().toString().equals(""))
      etBody.setError("Enter Body");
    else
      etBody.setError(null);
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode != RESULT_OK) return;

    String[] filePathColumn = {MediaStore.Images.Media.DATA};
    switch (requestCode) {
      case PICK_FROM_CAMERA:
        if (cameraImagePath != null) {
          Cursor imageCursor = getContentResolver().query(
              cameraImagePath, filePathColumn, null, null, null);
          if (imageCursor != null && imageCursor.moveToFirst()) {
            int columnIndex = imageCursor.getColumnIndex(filePathColumn[0]);
            picturePath = imageCursor.getString(columnIndex);
            imageCursor.close();

            mImageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
            mImageView.setVisibility(View.VISIBLE);
            btnUploadImage.setVisibility(View.GONE);
          }
        }

        break;

      case PICK_FROM_FILE:
        mImageCaptureUri = data.getData();

        Cursor cursor = getContentResolver().query(mImageCaptureUri,
            filePathColumn, null, null, null);
        cursor.moveToFirst();

        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        picturePath = cursor.getString(columnIndex);
        Log.i("File Size", CommonFunctions.getImageFileSize(picturePath));
        cursor.close();

        mImageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        mImageView.setVisibility(View.VISIBLE);
        btnUploadImage.setVisibility(View.GONE);
        break;
    }
}

  private class PostGrievance extends AsyncTask<String, String, String> {

  @Override
  protected void onPreExecute() {
    // TODO Auto-generated method stub
    super.onPreExecute();

  }

  @Override
  protected String doInBackground(String... url) {

    String resultString=null;
    Integer urgency = 0;

    if(spUrgency.getSelectedItem().toString().equals("Low"))
      urgency = 1;
    else if(spUrgency.getSelectedItem().toString().equals("Medium"))
      urgency = 2;
    else if(spUrgency.getSelectedItem().toString().equals("High"))
      urgency = 3;
    try {
      JSONObject jsonGrievanceObject=new JSONObject();
      jsonGrievanceObject.put("title", etTitle.getText().toString());
      jsonGrievanceObject.put("body", Html.toHtml(etBody.getText()));
      jsonGrievanceObject.put("category", spCategory.getSelectedItem().toString().toLowerCase());
      jsonGrievanceObject.put("urgency", urgency);
      jsonGrievanceObject.put("image",CommonFunctions.getBase64Format(picturePath));
      jsonGrievanceObject.put("file_size",CommonFunctions.getImageFileSize(picturePath));

      Log.i("image",CommonFunctions.getBase64Format(picturePath));
      //Code to Post data to server
      if(CommonFunctions.checkNetworkConnection(getApplicationContext())){
        resultString = CommonFunctions.httpPost(url[0],jsonGrievanceObject);
        Log.i("response",resultString);
      }else{
        db.addGrievances(new Grievance(etTitle.getText().toString(),Html.toHtml(etBody.getText()),spCategory.getSelectedItem().toString().toLowerCase(),Integer.toString(urgency),CommonFunctions.getBase64Format(picturePath),CommonFunctions.getImageFileSize(picturePath)));
      }
    } catch(Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  protected void onPostExecute(String result) {
    super.onPostExecute(result);

  }
}
}
