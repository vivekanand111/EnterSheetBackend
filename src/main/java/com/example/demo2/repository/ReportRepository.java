package com.example.demo2.repository;

import com.example.demo2.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReportRepository extends JpaRepository<Report, Integer> {

    @Query(value = "select * from reports r where r.user_id= :uid order by r.week_start",nativeQuery = true)
    List<Report> findAllByUserId(@Param("uid") Integer uid);

    @Query(value = "SELECT * FROM reports WHERE user_id = :userId ORDER BY week_start DESC LIMIT 1", nativeQuery = true)
    Report findLatestReportByUserId(@Param("userId") Integer userId);
    Report findFirstByUserIdOrderByWeekStartDesc(Integer userId);
}
