package it.dalpra.acme.bse.anagrafiche.service;

import java.time.LocalDate;
import java.util.List;

import it.dalpra.acme.bse.anagrafiche.dao.PersonDao;
import it.dalpra.acme.bse.anagrafiche.entity.Person;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;

@ApplicationScoped
public class PersonService {

    public List<Person> getAllPersons() {
        List<Person> result = Person.listAll();
        return result;
    }

    // using PersonDao

    @Inject
    PersonDao personDao;

    public List<Person> getAllPersonsWithLastName(String lastName) {
        List<Person> result = personDao.findByName(lastName);
        return result;
    }

    public List<Person> getAll() {
        List<Person> result = personDao.findAll();
        return result;
    }

    public List<Person> findBornAfter(int year) {
        //Date date = new Date(year - 1900, 11, 31);  // 11 is December
        LocalDate localDate = LocalDate.of(year, 12, 31);
        List<Person> result = personDao.findBornAfter(localDate);
        return result;
    }



    @Counted("count_ComputeAge")
    @Timed("time_ComputeAge")
    public double calculateAverageOfAgesOfPersonsBornAfter(int year) {
        List<Person> persons = personDao.findBornAfter(LocalDate.of(year, 12, 31));
        int count = persons.size();
        int sum = 0 ;
        for (Person person : persons) {
            int age = age(person);
            sum = sum + age ;
        }
        double average = sum / count ;
        return average;
    }

    @Counted("count_ComputeAge")
    public int age(Person person) {     // must be public in order to be counted!
        return (LocalDate.now().getYear() - person.birthDate.getYear());
    }

}