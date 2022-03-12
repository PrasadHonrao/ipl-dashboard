package com.ph.ipldashboard.data;
import com.ph.ipldashboard.models.MatchInput;
import com.ph.ipldashboard.models.MatchOutput;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class MatchDataProcessor implements ItemProcessor<MatchInput, MatchOutput> {

    private static final Logger log = LoggerFactory.getLogger(MatchDataProcessor.class);

    @Override
    public MatchOutput process(final MatchInput input) throws Exception {

        MatchOutput output = new MatchOutput();

        output.setId(Long.parseLong(input.getId()));
        output.setCity(input.getCity());
        output.setDate(LocalDate.parse(input.getDate()));
        output.setPlayerOfMatch(input.getPlayer_of_match());
        output.setVenue(input.getVenue());
        output.setTeam1(input.getTeam1());
        output.setTeam2(input.getTeam2());
        output.setTossWinner(input.getToss_winner());
        output.setTossDecision(input.getToss_decision());
        output.setWinner(input.getWinner());
        output.setResult(input.getResult());
        output.setResultMargin(input.getResult_margin());
        output.setUmpire1(input.getUmpire1());
        output.setUmpire2(input.getUmpire2());

        log.info("Converting (" + input + ") into (" + output + ")");
        return output;

    }

}