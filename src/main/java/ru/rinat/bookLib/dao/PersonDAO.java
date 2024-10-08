package ru.rinat.bookLib.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.rinat.bookLib.models.Book;
import ru.rinat.bookLib.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person show(int id) {
        return jdbcTemplate.queryForObject("SELECT * FROM person WHERE id=?",
                new BeanPropertyRowMapper<>(Person.class), id);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(full_name, birth_year) VALUES(?, ?)",
                person.getFullName(), person.getBirthYear());
    }

    public void update(int id, Person newPerson) {
        jdbcTemplate.update("UPDATE person SET full_name=?, birth_year=? WHERE id=?",
                newPerson.getFullName(), newPerson.getBirthYear(), id);
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM Person WHERE id=?", id);
    }

    public List<Book> getBooksByPersonId(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id=?",
                new BeanPropertyRowMapper<>(Book.class), id);
    }

    public Optional<Person> getPersonByFullName(String fullName) {
        return jdbcTemplate.query("SELECT * FROM person WHERE full_name=?",
                        new BeanPropertyRowMapper<>(Person.class), fullName)
                .stream().findFirst();
    }
}
