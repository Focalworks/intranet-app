package intranet.fw.com.intranet;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import intranet.fw.com.R;

/**
 * Created by kaustubh on 19/9/14.
 */
public class AddGrievanceActivity extends Activity implements View.OnClickListener{

  Button btnSave,btnBack,btnUploadImage;
  Spinner spCategory,spUrgency;
  EditText etTitle,etBody;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.add_grievence);

    etTitle = (EditText)findViewById(R.id.et_title);
    etBody = (EditText)findViewById(R.id.et_body);

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
        if(position > 0){
          Toast.makeText(getApplicationContext(),adapterView.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
        }else{
          Toast.makeText(getApplicationContext(),"Please select Category",Toast.LENGTH_LONG).show();
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });

    spUrgency.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        if(position > 0){
          Toast.makeText(getApplicationContext(),adapterView.getItemAtPosition(position).toString(),Toast.LENGTH_LONG).show();
        }else{
          Toast.makeText(getApplicationContext(),"Please select Urgency",Toast.LENGTH_LONG).show();
        }
      }

      @Override
      public void onNothingSelected(AdapterView<?> adapterView) {

      }
    });
  }

  @Override
  public void onClick(View view) {
    switch (view.getId()){
      case R.id.btn_save:
//        validation();
        break;

      case R.id.btn_back:
        Toast.makeText(getApplicationContext(),"Back",Toast.LENGTH_LONG).show();
        break;

      case R.id.btn_upload_img:
        Toast.makeText(getApplicationContext(),"Upload",Toast.LENGTH_LONG).show();
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
}
