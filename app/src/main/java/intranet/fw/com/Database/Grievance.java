package intranet.fw.com.Database;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by kaustubh on 24/9/14.
 */
public class Grievance implements Parcelable {

  String title;
  String description;
  String category;
  String urgency;
  String img_path;
  String img_size;

  String id;
  String user_id;
  String status;
  String fileMime;
  String time_ago;
  String comment_count;
  String comments;

  public Grievance(){}

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel parcel, int i) {
    parcel.writeString(title);
    parcel.writeString(description);
    parcel.writeString(category);
    parcel.writeString(urgency);
    parcel.writeString(img_path);
    parcel.writeString(img_size);
    parcel.writeString(id);
    parcel.writeString(user_id);
    parcel.writeString(status);
    parcel.writeString(fileMime);
    parcel.writeString(time_ago);
    parcel.writeString(comment_count);
    parcel.writeString(comments);
  }

  public static final Parcelable.Creator<Grievance> CREATOR = new Creator<Grievance>() {

    @Override
    public Grievance createFromParcel(Parcel parcel) {
      Grievance grievance = new Grievance();
      grievance.title = parcel.readString();
      grievance.description = parcel.readString();
      grievance.category = parcel.readString();
      grievance.urgency = parcel.readString();
      grievance.img_path = parcel.readString();
      grievance.img_size = parcel.readString();
      grievance.id = parcel.readString();
      grievance.user_id = parcel.readString();
      grievance.status = parcel.readString();
      grievance.fileMime = parcel.readString();
      grievance.time_ago = parcel.readString();
      grievance.comment_count = parcel.readString();
      grievance.comments = parcel.readString();
      return grievance;
    }

    @Override
    public Grievance[] newArray(int i) {
      return new Grievance[0];
    }
  };

  public Grievance(String title, String description, String category, String urgency, String img_path, String img_size){
    this.title = title;
    this.description = description;
    this.category = category;
    this.urgency = urgency;
    this.img_path = img_path;
    this.img_size = img_size;
  }

  public Grievance(String id,String title,String description,String category,String urgency,String user_id,String status,String img_path,String fileMime,String time_ago,String comment_count,String comments){
    this.id = id;
    this.title = title;
    this.description = description;
    this.category = category;
    this.urgency = urgency;
    this.user_id = user_id;
    this.status = status;
    this.img_path = img_path;
    this.fileMime = fileMime;
    this. time_ago = time_ago;
    this.comment_count = comment_count;
    this.comments = comments;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getDescription() {
    return description;
  }

  public String getCategory() {
    return category;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public String getUrgency() {
    return urgency;
  }

  public void setUrgency(String urgency) {
    this.urgency = urgency;
  }

  public String getImg_path() {
    return img_path;
  }

  public void setImg_path(String img_path) {
    this.img_path = img_path;
  }

  public String getImg_size() {
    return img_size;
  }

  public void setImg_size(String img_size) {
    this.img_size = img_size;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getUser_id() {
    return user_id;
  }

  public void setUser_id(String user_id) {
    this.user_id = user_id;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public String getFileMime() {
    return fileMime;
  }

  public void setFileMime(String fileMime) {
    this.fileMime = fileMime;
  }

  public String getTime_ago() {
    return time_ago;
  }

  public void setTime_ago(String time_ago) {
    this.time_ago = time_ago;
  }

  public String getComment_count() {
    return comment_count;
  }

  public void setComment_count(String comment_count) {
    this.comment_count = comment_count;
  }

  public String getComments() {
    return comments;
  }

  public void setComments(String comments) {
    this.comments = comments;
  }
}
