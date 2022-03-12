package com.ph.ipldashboard.repositories;

import com.ph.ipldashboard.models.MatchOutput;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<MatchOutput, Long> {
    //List<MatchOutput> getByTeam1OrTeam2(String teamName1, String teamName2);
    List<MatchOutput> findTop5ByTeam1OrTeam2OrderByDateDesc(String team1, String team2);
}
