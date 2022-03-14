package com.ph.ipldashboard.controllers;

import com.ph.ipldashboard.models.MatchOutput;
import com.ph.ipldashboard.models.Team;
import com.ph.ipldashboard.repositories.MatchRepository;
import com.ph.ipldashboard.repositories.TeamRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@CrossOrigin
public class TeamController {

    TeamRepository teamRepository;
    MatchRepository matchRepository;

    // Alternatively you can also use Autowired on repository variables
    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable String teamName) {
        Team team = teamRepository.findByTeamName(teamName);
        team.setLatestMatches(matchRepository.findTop5ByTeam1OrTeam2OrderByDateDesc(teamName, teamName));
        return team;
    }

    @GetMapping("/team/{teamName}/matches/{year}")
    public List<MatchOutput> getMatchesForTeam(@PathVariable String teamName, @PathVariable int year) {
        LocalDate startDate = LocalDate.of(year, 1, 1);
        LocalDate endDate = LocalDate.of(year+1, 1, 1);

        // JPQL Way
//        List<MatchOutput> matches = this.matchRepository.
//                findByTeam1AndDateBetweenOrTeam2AndDateBetweenOrderByDateDesc(teamName, startDate, endDate, teamName, startDate, endDate);

        // Custom Query
        return this.matchRepository.getMatchesForTeam(teamName, startDate, endDate);
    }

    @GetMapping("/years")
    public List<Integer> getYears() {
        return matchRepository.getYears();
    }

    @GetMapping("/team")
    public List<Team> getTeams() {
        return teamRepository.findAll();
    }

}
