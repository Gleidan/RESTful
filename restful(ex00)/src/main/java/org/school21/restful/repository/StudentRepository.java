package org.school21.restful.repository;

import org.school21.restful.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<User, Long> {

    @Query("SELECT c.students FROM Course c WHERE c.id = ?1")
    List<User> getStudentsByCourse(Long id);
}
