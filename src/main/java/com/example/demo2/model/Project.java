package com.example.demo2.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "projects")
@Builder
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String project_name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "project_id",referencedColumnName = "id")
    private List<Module> modules;
}
