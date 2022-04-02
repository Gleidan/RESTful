package org.school21.restful.repository;

import org.school21.restful.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TeachersRepository extends JpaRepository<User, Long> {

    @Query(value = "SELECT * FROM users " +
            "INNER JOIN courses_teachers ct on users.id = ct.teacher_id " +
            "INNER JOIN course c on c.id = ?1",
            nativeQuery = true)
    Page<User> getTeachersByCourse(Long id, Pageable pageable);
}
