package intranet.fw.com.CustomListAdapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import intranet.fw.com.R;

/**
 * Created by kaustubh on 22/9/14.
 */
public class GrievanceListAdapter  extends BaseAdapter {

  private final Context context;
  private final String [] category,status,title,body,urgency,time,comment_count;

  public GrievanceListAdapter(Context context,String [] category,String [] status,String [] title, String [] body, String [] urgency, String [] time,String [] comment_count){
    super();
    this.context = context;
    this.category = category;
    this.status = status;
    this.title = title;
    this.body = body;
    this.urgency = urgency;
    this.time = time;
    this.comment_count = comment_count;
  }

  @Override
  public int getCount() {
    return category.length;
  }

  @Override
  public Object getItem(int i) {
    return null;
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(final int position, View convertView, ViewGroup parent) {
    LayoutInflater inflater = LayoutInflater.from(context);
    View rowView =inflater.inflate(R.layout.grievance_custom_listview,null,true);
    rowView.setFocusable(false);
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

    rowView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        Toast.makeText(context,""+position,Toast.LENGTH_LONG).show();
      }
    });
    return rowView;
  }
}
