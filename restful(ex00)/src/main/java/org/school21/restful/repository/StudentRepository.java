package org.school21.restful.repository;

import org.school21.restful.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface StudentRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users " +
            "INNER JOIN course_students cs on users.id = cs.students_id " +
            "INNER JOIN course c on c.id = ?1",
            nativeQuery = true)
    Page<User> getStudentsByCourse(Long id, Pageable pageable);
}
