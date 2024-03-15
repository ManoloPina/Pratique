import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {
  private static final List<User> users = new ArrayList<User>();
  private static final List<Event> events = new ArrayList<Event>();


  public static void main(String[] args) {
    File eventsFile = new File("events.data");
    if(eventsFile.exists()) {
      events.addAll(readEventsFromFile());
    }
    File usersFile = new File("users.data");
    if(usersFile.exists()) {
      users.addAll(readUsersFromFile());
    }

    selectRegistrationOpt();
  }

  private static void selectRegistrationOpt() {
    Scanner scanner = new Scanner(System.in);
    int choice;

    do {
      System.out.println("Qual função deseja executar?");
      System.out.println("1 - Registrar usuário");
      System.out.println("2 - Registrar evento");
      System.out.println("3 - Consultar usuários");
      System.out.println("4 - Consultar eventos");
      System.out.println("5 - Adicionar participante ao evento:");
      System.out.println("5 - Remover participante do evento:");

      choice = scanner.nextInt();

      switch (choice) {
        case 1:
          System.out.println("Registrar usuário selecionado:");
          registerUser();
          break;
        case 2:
          System.out.println("Registrar evento selecionado:");
          registerEvent();
          break;
        case 3:
          System.out.println("Consultar usuários selecionado:");
          printUsers();
          break;
        case 4:
          System.out.println("Consultar eventos selecionado:");
          printEvents();
          break;
        case 5:
          System.out.println("Adicionar participante ao evento:");
          addUserToEvent();
          break;
        case 6:
          System.out.println("Remover participante do evento:");
          removeUserFromEvent();
          break;
        default:
          System.out.println("Opção inválida, tente novamente");
      }

    } while (choice != 0);

  }

  private static LocalDateTime formatStringToDate(String dateTimeStr) {
    try {
      DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
      return LocalDateTime.parse(dateTimeStr, formatter);
    } catch (DateTimeParseException e) {
      System.out.println("Invalid date format:"+ e.getParsedString());
    }
    return null;
  }

  private static void  printEvents() {
    events.sort(Comparator.comparing(Event::getDateTime).reversed());
    System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");
    System.out.printf("| %-30s | %-20s | %-10s | %-30s | %-15s | %-40s | %-30s |\n", "Nome", "Data", "Status", "Endereço", "Categoria", "Descrição", "Participantes");
    System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");

    for (Event event : events) {
      System.out.printf("| %-30s | %-20s | %-10s | %-30s | %-15s | %-40s | %-30s |\n",
              event.getName(),
              event.getDateTime(),
              event.getStatusName(),
              event.getAddress(),
              event.getCategoryName(),
              event.getDescription(),
              event.getUsersEmails()
      );
    }

    System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------");
  }

  private  static void printUsers() {
    System.out.println("----------------------------------------------------------------------------------------------------------------------------");
    System.out.printf("| %-20s | %-40s | %-20s | %-20s |\n", "Nome", "Email", "Idade", "Localização");
    System.out.println("----------------------------------------------------------------------------------------------------------------------------");

    for (User user : users) {
      System.out.printf("| %-20s | %-40s | %-20s | %-20s |\n", user.getName(), user.getEmail(), user.getAge(), user.getLocation());
    }

    // Print table footer
    System.out.println("----------------------------------------------------------------------------------------------------------------------------");
  }

  private static Event.Category selectEventCategory() {
    int choice;
    Scanner scanner = new Scanner(System.in);

    do {

      System.out.println("Categoria de enventos:");
      System.out.println("1. Festas");
      System.out.println("2. Eventos esportivos");
      System.out.println("3. Shows");
      System.out.println("0. Encerrar");
      System.out.println("Selecione uma categoria:");
      choice = scanner.nextInt();

      switch (choice) {
        case 1:
          System.out.println("Festas");
          return Event.Category.PARTY;
        case 2:
          System.out.println("Eventos esportivos");
          return Event.Category.SPORT;
        case 3:
          System.out.println("Shows");
          return Event.Category.SHOW;
        case 0:
          System.out.println("Exiting the program...");
          return  Event.Category.PARTY;
        default:
          return Event.Category.PARTY;
      }
    }while (choice != 0);
  }

  private static void registerEvent() {
    UUID uuid = UUID.randomUUID();
    Scanner scanner = new Scanner(System.in);
    Event event = new Event();

    event.setId(uuid);

    System.out.println("Informe o nome do evento:");
    event.setName(scanner.nextLine());

    System.out.println("Informe o endereço do evento:");
    event.setAddress(scanner.nextLine());

    System.out.println("Informe a categoria do evento:");
    event.setCategory(selectEventCategory());

    System.out.println("Informe a data e horário do evento (YYYY-MM-DD HH:MM):");
    String dateTimeString = scanner.nextLine();
    LocalDateTime eventDateTime = formatStringToDate(dateTimeString);
    event.setDateTime(eventDateTime);

    LocalDateTime currentDateTime = LocalDateTime.now();

    assert eventDateTime != null;
    if(eventDateTime.isBefore(currentDateTime)) {
      event.setStatus(Event.Status.FINSISHED);
    }else if(eventDateTime.isEqual(currentDateTime)) {
      event.setStatus(Event.Status.ONGOING);
    }else {
      event.setStatus(Event.Status.SCHEDULED);
    }

    System.out.println("Informe a descrição do evento:");
    event.setDescription(scanner.nextLine());
    events.add(event);
    saveEventsToFile(events);

    event.showEvent();
    printEvents();
  }

  private static void registerUser() {
    Scanner scanner = new Scanner(System.in);
    UUID uuid = UUID.randomUUID();
    User user = new User();

    user.setId(uuid);

    System.out.println("Informe o nome do usuário:");
    user.setName(scanner.nextLine());

    System.out.println("Informe o e-mail do usuário:");
    user.setEmail(scanner.nextLine());

    System.out.println("Informe a idade do usuário:");
    user.setAge(scanner.nextInt());
    scanner.nextLine();

    System.out.println("Informe a cidade do usuário:");
    user.setCity(scanner.nextLine());
    scanner.nextLine();

    System.out.println("Informe a estado do usuário:");
    user.setState(scanner.nextLine());

    users.add(user);
    saveUsersToFile(users);
    printUsers();
  }

  private static User findUserByEmail(String email) {
    for(User user :users) {
      if(user.getEmail().equals(email)) {
        return user;
      }
    }
    return null;
  }

  private  static Event findEventByName(String name) {
    for(Event event: events) {
      if(event.getName().equals(name))
        return event;
    }
    return null;
  }

  private static void addUserToEvent() {
    Scanner scanner = new Scanner(System.in);
    User user;
    Event event;

    System.out.println("Informe um usuário pelo e-mail:");
    user = findUserByEmail(scanner.nextLine());

    System.out.println("Informe um evento pelo seu nome:");
    event = findEventByName(scanner.nextLine());

    if(event != null && user != null) {
      event.setUsers(user);
      System.out.println("Participante atribuído ao evento com sucesso!");
    }else {
      System.out.println("Não foi possível atribuir um usuário a um evento existente");
    }

  }

  private static void removeUserFromEvent() {
    var scanner = new Scanner(System.in);
    User user;
    Event event;

    System.out.println("Informe um usuário pelo e-mail:");
    user = findUserByEmail(scanner.nextLine());

    System.out.println("Informe um evento pelo seu nome:");
    event = findEventByName(scanner.nextLine());

    if(user != null && event != null) {
      event.removeUser(user);
      System.out.println("Usuário removido com sucesso");
    } else {
      System.out.println("Não foi possível remover o participante do evento");
    }

  }

  public static void saveEventsToFile(List<Event> events) {
    try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream("events.data"))) {
      outputStream.writeObject(events);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static  void saveUsersToFile(List<User> users) {
    try(ObjectOutputStream outputStream  = new ObjectOutputStream(new FileOutputStream("users.data"))){
        outputStream.writeObject(users);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static List<Event> readEventsFromFile() {
    List<Event> events = new ArrayList<>();
    try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream("events.data"))) {
      events = (List<Event>) inputStream.readObject();
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return events;
  }

  public static  List<User> readUsersFromFile() {
    List<User> users = new ArrayList<>();
    try (ObjectInputStream inputStream =  new ObjectInputStream(new FileInputStream("users.data"))) {
      users = (List<User>) inputStream.readObject();
    }catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return users;
  }
}