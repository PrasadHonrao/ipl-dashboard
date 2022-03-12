package com.ph.ipldashboard.data;

import com.ph.ipldashboard.models.Team;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

    private final JdbcTemplate jdbcTemplate;
    private final EntityManager em;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate, EntityManager em) {
        this.jdbcTemplate = jdbcTemplate;
        this.em = em;
    }

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

//            jdbcTemplate
//                .query("SELECT team1, team2, date FROM Match", (rs, row) ->
//                        "[" + rs.getString(1) + "] vs " +"[" + rs.getString(2) + "] Date[" + rs.getString(3)+ "]")
//                .forEach(System.out::println);

            Map<String, Team> teamData = new HashMap<>();
            em.createQuery("SELECT m.team1, count(*) from Match m group by m.team1", Object[].class)
                    .getResultList()
                    .stream()
                    .map(e -> new Team((String)e[0], (long) e[1]))
                    .forEach(team -> teamData.put(team.getTeamName(), team) );

            em.createQuery("SELECT m.team2, count(*) from Match m group by m.team2", Object[].class)
                    .getResultList()
                    .stream()
                    .forEach(e -> {
                        Team team = teamData.get((String)e[0]);
                        team.setTotalMatches(team.getTotalMatches() + (long)e[1]);
                    });

            em.createQuery("SELECT m.winner, count(*) from Match m group by m.winner", Object[].class)
                    .getResultList()
                    .stream()
                    .forEach(e -> {
                        Team team = teamData.get((String)e[0]);
                        if (team != null) {
                            team.setTotalWins((long)e[1]);
                        }
                    });

            teamData.values().forEach(System.out::println);
//            teamData.values().forEach(team -> em.persist(team));
    
        }
    }
}
