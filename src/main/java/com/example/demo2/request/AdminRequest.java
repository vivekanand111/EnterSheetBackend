package com.example.demo2.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Date;


@Data
@Builder
public class AdminRequest {
    @NotNull
    private Date startDate;
    @NotNull
    private Date endDate;

}
