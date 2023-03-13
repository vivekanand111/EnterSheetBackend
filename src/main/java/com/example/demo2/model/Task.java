package com.example.demo2.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @NotBlank(message = "The Project name is required.")
    private String name;
    @NotBlank(message = "The Project name is required.")
    private String project;
    private String module;
    @NotNull(message = "The Date is required.")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date date;
    private Integer hours;
    private String notes;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "star_id")
    private Report report;

}
