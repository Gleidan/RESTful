package org.school21.restful.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Date startDate;
    private Date endDate;
    private String name;
    @Enumerated(EnumType.STRING)
    private State state;
    @ManyToMany
    @JoinTable(
            name = "courses_teachers",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "teacher_id")
    )
    private List<User> teachers;
    @ManyToMany
    @JoinTable(
            name = "course_students",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "students_id")
    )
    private List<User> students;
    private String description;
    @OneToMany
    @JoinTable(
            name = "lessons",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "id")
    )
    private List<Lesson> lessons;

    public void publish() {
        if (this.state == State.PUBLISHED) {
            throw new IllegalArgumentException("Course can't be republished");
        }
        this.state = State.PUBLISHED;
    }
}
