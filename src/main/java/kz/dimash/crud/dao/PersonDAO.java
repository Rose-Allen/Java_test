package kz.dimash.crud.dao;

import kz.dimash.crud.model.Person;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private static int PERSON_ID = 1;
    private List<Person> persons = new ArrayList<>();
    private static final  String URL = "jdbc:postgresql://localhost:5432/crud_test";
    private static final  String USER = "postgres";
    private static final  String PASSWORD = "2000";

    private static Connection connection;
    private static final  String DRIVER = "org.postgresql.Driver";

    static{
        try{
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try{
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        }catch(
                SQLException e){
            e.printStackTrace();
        }
    }




//    connection = DriverManager.getConnection(URL, USER, PASSWORD)

//    {
//        persons.add(new Person(++PERSON_ID, "Dimash", 24, "d.esengeldiev@mail.ru"));
//        persons.add(new Person(++PERSON_ID, "Laka", 20, "maxi@mail.ru"));
//        persons.add(new Person(++PERSON_ID, "Shaka", 21, "shaka@mail.ru"));
//    }

    public List<Person> index() {
        List<Person> people = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String query = "SELECT * FROM person";
           ResultSet resultSet =  statement.executeQuery(query);
           while (resultSet.next()) {
               Person person = new Person();
               person.setId(resultSet.getInt("id"));
               person.setName(resultSet.getString("name"));
               person.setAge(resultSet.getInt("age"));
               person.setEmail(resultSet.getString("email"));
               people.add(person);

           }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return people;
    }

    public Person getPersonById(int id) {
        return persons.stream().filter(person -> person.getId() == id).findFirst().orElse(null);
    }

    public void save(Person person) {
//        person.setId(++PERSON_ID);
//        persons.add(person);
        try {
            Statement statement = connection.createStatement();
            String SQL = "INSERT INTO Person VALUES(" + 1 + ",'" + person.getName() +
                    "'," + person.getAge() + ",'" + person.getEmail() + "')";

            statement.executeUpdate(SQL);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }


    }

    public void update(Integer id, Person person) {
        Person persons = getPersonById(id);
        if(persons != null) {
            persons.setName(person.getName());
            persons.setAge(person.getAge());
            persons.setEmail(person.getEmail());
        }
    }

    public void delete(Integer id) {
        persons.removeIf(person -> person.getId() == id);
    }

}
