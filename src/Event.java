import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Event implements Serializable {

  private UUID id;
  private String name;
  private String  address;
  private LocalDateTime dateTime;
  private String description;

  private Category category;

  public Status getStatus() {
    return status;
  }

  public void setStatus(Status status) {
    this.status = status;
  }

  private Status status;

  private List<User> users = new ArrayList<User>();

  public LocalDateTime getDateTime() {
    return dateTime;
  }

  public Event() {
  }

  public Event(
          UUID id,
          String name,
          String address,
          LocalDateTime dateTime,
          String description,
          Category category,
          List<User> users
  ) {
    this.id = id;
    this.name = name;
    this.address = address;
    this.dateTime = dateTime;
    this.description = description;
    this.category = category;
    this.users = users;
  }

  public List<User> getUsers() {
    return users;
  }

  public void setUsers(User user) {
    this.users.add(user);
  }

  public void removeUser(User user) {
    if(this.users.contains(user)) {
      this.users.remove(user);
    }else {
      System.out.println("Participante não vinculado ao evento");
    };
  }


  public enum Category{
    PARTY("PARTY"),
    SPORT("SPORT"),
    SHOW("SHOW");

    private final String value;

    Category(String value) {
      this.value = value;
    }

    public String getValue() {
      return value;
    }
  }

  public enum Status {
    FINSISHED(1),
    SCHEDULED(2),
    ONGOING(3);

    private final int value;

    Status(int value) {
      this.value = value;
    }
  }
  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }



  public void setDateTime(LocalDateTime dateTime) {
    this.dateTime = dateTime;
  }

  public String getName() {
    return name;
  }

  public String getAddress() {
    return address;
  }

  public String getDescription() {
    return description;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void showEvent(){
    System.out.println(this.name+" "+this.address+" "+this.dateTime.toString()+" "+this.description);
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public String getCategoryName() {
    return switch (this.category) {
      case Category.PARTY -> "Festa";
      case Category.SPORT -> "Eventos Esportivos";
      case Category.SHOW -> "Show";
    };
  }

  public String getUsersEmails() {
    StringBuilder sb = new StringBuilder();
    for(User user : users) {
      sb.append(user.getEmail()).append(", ");
    }
    if(!sb.isEmpty()) {
      sb.delete(sb.length() -2, sb.length());
    }
    return sb.toString();
  }

  public String getStatusName() {
    return switch (this.status) {
      case Status.FINSISHED -> "FINALIZADO";
      case Status.SCHEDULED -> "AGENDADO";
      case Status.ONGOING -> "EM ANDAMENTO";
    };
  }

}
