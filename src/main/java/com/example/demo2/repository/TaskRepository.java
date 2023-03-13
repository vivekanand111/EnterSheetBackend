package com.example.demo2.repository;

import com.example.demo2.model.Task;
import com.example.demo2.response.UserTasksResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {

    //SQL
    @Query(value = "select " +
            "t.id,t.project,u.username,t.name,t.date,t.hours,t.notes " +
            "from tasks t join reports r on t.star_id=r.id join users u on u.id=r.user_id " +
            "where t.date>=:startDate and t.date<=:endDate",
            nativeQuery = true)
    List<Object[]> findAllBetweenDates1(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

    //HQL
    @Query("select " +
            "new com.example.demo2.response.UserTasksResponse(" +
            "t.id,t.project,u.username,t.name,t.date,t.hours,t.notes) " +
            "from User u join u.reports r join r.tasks t " +
            "where t.date>=:startDate and t.date<=:endDate")
    List<UserTasksResponse> findAllBetweenDates2(@Param("startDate") Date startDate, @Param("endDate") Date endDate);


}
