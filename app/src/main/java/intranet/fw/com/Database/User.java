package intranet.fw.com.Database;

/**
 * Created by kaustubh on 16/9/14.
 */
public class User {

  int uid;
  String oauthId;
  String name;
  String gender;
  String email;
  String picture;

  public User(){}

  public User(String oauthId,String name,String gender,String email,String picture){
    this.oauthId = oauthId;
    this.name= name;
    this.gender = gender;
    this.email = email;
    this.picture = picture;
  }

  // setter
  public void setUid(int uid) {
    this.uid = uid;
  }

  public void setOauthId(String oauthId) {
    this.oauthId = oauthId;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public void setPicture(String picture) {
    this.picture = picture;
  }

  // getter
  public int getUid() {
    return uid;
  }

  public String getOauth() {
    return oauthId;
  }

  public String getName() {
    return name;
  }

  public String getGender() {
    return gender;
  }

  public String getEmail() {
    return email;
  }

  public String getPicture() {
    return picture;
  }
}
