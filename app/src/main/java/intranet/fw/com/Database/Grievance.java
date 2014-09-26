package intranet.fw.com.Database;

/**
 * Created by kaustubh on 24/9/14.
 */
public class Grievance {

  String title;
  String body;
  String category;
  String urgency;
  String img_path;
  String img_size;

  public Grievance(){}

  public Grievance(String title, String body, String category, String urgency, String img_path, String img_size){
    this.title = title;
    this.body = body;
    this.category = category;
    this.urgency = urgency;
    this.img_path = img_path;
    this.img_size = img_size;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
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
}
