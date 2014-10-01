package intranet.fw.com.CustomListAdapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import intranet.fw.com.Database.Grievance;
import intranet.fw.com.R;

/**
 * Created by kaustubh on 22/9/14.
 */
public class GrievanceListAdapter  extends ArrayAdapter<Grievance> {

  private List<Grievance> grievanceList;
  private final Context context;
//  private final String [] category,status,title,body,urgency,time,comment_count;

  public GrievanceListAdapter(Context context,List<Grievance> grievanceList){
    super(context,R.layout.grievance_custom_listview,grievanceList);
    this.context = context;
    this.grievanceList = grievanceList;
  }

  @Override
  public int getCount() {
    if (grievanceList != null)
      return grievanceList.size();
    return 0;
  }

  @Override
  public Grievance getItem(int position) {
    if (grievanceList != null)
      return grievanceList.get(position);
    return null;
  }

  @Override
  public long getItemId(int position) {
    if (grievanceList != null)
      return grievanceList.get(position).hashCode();
    return 0;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {

    View rowView = convertView;
    if (rowView == null) {
      LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
      rowView = inflater.inflate(R.layout.grievance_custom_listview, null);
    }
    String urgencyText="";
    String statusText="";

    Grievance grievance = grievanceList.get(position);
    if(grievance.getUrgency().equals("1")){
      urgencyText = "Low";
    }else if(grievance.getUrgency().equals("2")){
      urgencyText = "Medium";
    }else{
      urgencyText = "High";
    }

    if(grievance.getStatus().equals("1")){
      statusText = "Submitted";
    }else if(grievance.getStatus().equals("2")){
      statusText = "In Progress";
    }else if(grievance.getStatus().equals("3")){
      statusText = "Closed";
    }else{
      statusText = "Re Opened";
    }

    TextView txtCategory = (TextView)rowView.findViewById(R.id.txtCategory);
    TextView txtStatus= (TextView)rowView.findViewById(R.id.txtStatus);
    TextView txtUrgency= (TextView)rowView.findViewById(R.id.txtUrgency);
    TextView txtTitle = (TextView)rowView.findViewById(R.id.txtTitle);
    TextView txtBody = (TextView)rowView.findViewById(R.id.txtBody);
    TextView txtTime = (TextView)rowView.findViewById(R.id.txtTime);
    Button btnComment = (Button)rowView.findViewById(R.id.btnComment);

    txtCategory.setText(Character.toUpperCase(grievance.getCategory().charAt(0)) + grievance.getCategory().substring(1));
    txtStatus.setText(statusText);
    txtUrgency.setText(urgencyText);
    txtTitle.setText(grievance.getTitle());
    txtBody.setText(Html.fromHtml(grievance.getDescription()));
    txtTime.setText(grievance.getTime_ago());
    btnComment.setText(grievance.getComment_count());

    rowView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(context,""+position,Toast.LENGTH_LONG).show();
      }
    });
    return rowView;
  }

  public List<Grievance> getItemList() {
    return grievanceList;
  }

  public void setItemList(List<Grievance> grievanceList) {
    this.grievanceList = grievanceList;
  }
}
