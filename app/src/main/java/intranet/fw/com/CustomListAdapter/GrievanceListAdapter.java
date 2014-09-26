package intranet.fw.com.CustomListAdapter;

import android.app.Activity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;

import intranet.fw.com.R;

/**
 * Created by kaustubh on 22/9/14.
 */
public class GrievanceListAdapter  extends ArrayAdapter<String> {

  private final Activity activity;
  private final String [] category,status,title,body,urgency,time,comment_count;

  public GrievanceListAdapter(Activity activity,String [] category,String [] status,String [] title, String [] body, String [] urgency, String [] time,String [] comment_count){
    super(activity, R.layout.grievance_custom_listview,title);
    this.activity = activity;
    this.category = category;
    this.status = status;
    this.title = title;
    this.body = body;
    this.urgency = urgency;
    this.time = time;
    this.comment_count = comment_count;
  }

  @Override
  public View getView(int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = activity.getLayoutInflater();
    View rowView =inflater.inflate(R.layout.grievance_custom_listview,null,true);
    String urgencyText="";

    if(urgency[position].equals("1")){
      urgencyText = "Low";
    }else if(urgency[position].equals("2")){
      urgencyText = "Medium";
    }else{
      urgencyText = "High";
    }

    TextView txtCategory = (TextView)rowView.findViewById(R.id.txtCategory);
    TextView txtStatus= (TextView)rowView.findViewById(R.id.txtStatus);
    TextView txtUrgency= (TextView)rowView.findViewById(R.id.txtUrgency);
    TextView txtTitle = (TextView)rowView.findViewById(R.id.txtTitle);
    TextView txtBody = (TextView)rowView.findViewById(R.id.txtBody);
    TextView txtTime = (TextView)rowView.findViewById(R.id.txtTime);
    Button btnComment = (Button)rowView.findViewById(R.id.btnComment);

    txtCategory.setText(txtCategory.getText() + category[position]);
    txtStatus.setText(txtStatus.getText() + status[position]);
    txtUrgency.setText(urgencyText);
    txtTitle.setText(title[position]);
    txtBody.setText(Html.fromHtml(body[position]));
    txtTime.setText(time[position]);
    btnComment.setText(comment_count[position]);
    return rowView;
  }
}
