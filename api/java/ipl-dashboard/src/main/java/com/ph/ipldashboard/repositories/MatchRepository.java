package com.ph.ipldashboard.repositories;

import com.ph.ipldashboard.models.MatchOutput;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MatchRepository extends JpaRepository<MatchOutput, Long> {
    //List<MatchOutput> getByTeam1OrTeam2(String teamName1, String teamName2);
    List<MatchOutput> findTop5ByTeam1OrTeam2OrderByDateDesc(String team1, String team2);

    // JPQL query to get matches for a team in a given year
//    List<MatchOutput> findByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(
//            String team1, LocalDate team1StartDate, LocalDate team1EndDate,
//            String team2, LocalDate team2StartDate, LocalDate team2EndDate
//    );

    @Query("SELECT m from Match m WHERE (m.team1 = :teamName OR m.team2 = :teamName) AND m.date BETWEEN :startDate and :endDate")
    List<MatchOutput> getMatchesForTeam(
            @Param("teamName") String teamName,
            @Param("startDate") LocalDate startDate,
            @Param("endDate")LocalDate endDate
    );
}
