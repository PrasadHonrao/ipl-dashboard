package com.ph.ipldashboard.controllers;

import com.ph.ipldashboard.models.Team;
import com.ph.ipldashboard.repositories.MatchRepository;
import com.ph.ipldashboard.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

}
