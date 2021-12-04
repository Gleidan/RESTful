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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Date startDate;
    private Date endDate;
    private String name;
    @ManyToOne
    private User teacher;
    @ManyToMany
    private List<User> students;
    private String description;
    @OneToMany
    private List<Lesson> lessons;
}
