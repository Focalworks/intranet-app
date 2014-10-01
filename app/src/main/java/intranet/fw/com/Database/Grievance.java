package intranet.fw.com.Database;

/**
 * Created by kaustubh on 24/9/14.
 */
public class Grievance {

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
