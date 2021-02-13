package accella.test.accellatest.repositories;

import accella.test.accellatest.entities.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
