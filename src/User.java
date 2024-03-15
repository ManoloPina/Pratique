import java.util.UUID;

public class User {

  private UUID id;
  private String name;
  private int age;
  private String city;
  private String state;

  private String email;

  public User() {

  }
  public User(UUID id, String name, String email, int age, String city, String state) {
    this.id = id;
    this.name = name;
    this.email = email;
    this.age = age;
    this.city = city;
    this.state = state;
  }


  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public String getCity() {
    return city;
  }

  public String getState() {
    return state;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getEmail() {
    return email;
  }

  public String getLocation() {
    return this.getCity()+","+this.getState();
  }
}
