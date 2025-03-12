package kz.dimash.crud.dao;

import kz.dimash.crud.model.Person;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PersonDAO {

    private static int PERSON_ID = 1;
    private List<Person> persons = new ArrayList<>();

    {
        persons.add(new Person(++PERSON_ID, "Dimash", 24, "d.esengeldiev@mail.ru"));
        persons.add(new Person(++PERSON_ID, "Laka", 20, "maxi@mail.ru"));
        persons.add(new Person(++PERSON_ID, "Shaka", 21, "shaka@mail.ru"));
    }

    public List<Person> index() {
        return persons;
    }

    public Person getPersonById(int id) {
        return persons.stream().filter(person -> person.getId() == id).findFirst().orElse(null);
    }

    public void save(Person person) {
        person.setId(++PERSON_ID);
        persons.add(person);

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
