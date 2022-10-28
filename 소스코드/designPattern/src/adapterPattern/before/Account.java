package adapterPattern.before;

/**
 * fileName     : Account
 * author       : jungwoo
 * description  :
 */
public class Account {
  private String name;
  private String password;
  private String email;

  public void setName(String name) {
    this.name = name;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getName() {
    return name;
  }

  public String getPassword() {
    return password;
  }

  public String getEmail() {
    return email;
  }
}
