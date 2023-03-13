package com.example.demo2.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserTasksResponse {

    @JsonIgnore
    private Integer id;
    private String project;
    private String username;
    private String name;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;
    private Integer hours;
    private String notes;


}
