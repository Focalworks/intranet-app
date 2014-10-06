package intranet.fw.com.CustomListAdapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import intranet.fw.com.Database.Grievance;
import intranet.fw.com.R;
import intranet.fw.com.intranet.GrievanceFullViewActivity;

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

    Grievance grievance = grievanceList.get(position);

    TextView txtCategory = (TextView)rowView.findViewById(R.id.txtCategory);
    TextView txtStatus= (TextView)rowView.findViewById(R.id.txtStatus);
    TextView txtUrgency= (TextView)rowView.findViewById(R.id.txtUrgency);
    TextView txtTitle = (TextView)rowView.findViewById(R.id.txtTitle);
    TextView txtBody = (TextView)rowView.findViewById(R.id.txtBody);
    TextView txtTime = (TextView)rowView.findViewById(R.id.txtTime);
    Button btnComment = (Button)rowView.findViewById(R.id.btnComment);

    txtCategory.setText(grievance.getCategory());
    txtStatus.setText(grievance.getStatus());
    txtUrgency.setText(grievance.getUrgency());
    txtTitle.setText(grievance.getTitle());
    txtBody.setText(Html.fromHtml(grievance.getDescription()));
    txtTime.setText(grievance.getTime_ago());
    btnComment.setText(grievance.getComment_count());

    rowView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Grievance grievance = grievanceList.get(position);

        Bundle mBundle = new Bundle();
        mBundle.putParcelable("grievance", grievance);

        Intent intent = new Intent(context, GrievanceFullViewActivity.class);
        intent.putExtras(mBundle);
        context.startActivity(intent);
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
