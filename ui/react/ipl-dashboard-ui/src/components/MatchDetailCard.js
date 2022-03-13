import { React } from "react";
import { Link } from "react-router-dom";

import "./MatchDetailCard.scss";

export const MatchDetailCard = ({ teamName, match }) => {
  if (!match) return null;

    const otherTeam = teamName === match.team1 ? match.team2 : match.team1;
    const isMatchWon = match.winner === teamName;

  return (
    <div className= {isMatchWon ? 'MatchDetailCard won-card': 'MatchDetailCard lost-card'}>
      <div>
        <span className="vs">vs</span>{" "}
        <h1>
          {" "}
          <Link to={`/teams/${otherTeam}`}>{otherTeam}</Link>{" "}
        </h1>
        <h2 className="match-date">{match.date}</h2>
        <h3 className="match-venue">{match.venue}</h3>
        <h3 className="match-result">
          {match.winner} won by {match.resultMargin} {match.result}
        </h3>
      </div>
      <div className="additional-detail">
        <h3>First Innings</h3>
        <p>{match.team1}</p>
        <h3>Second Innings</h3>
        <p>{match.team2}</p>
        <h3>Man of the match</h3>
              <p>{match.playerOfMatch}</p>
              <h3>Umpires</h3>
        <p>{match.umpire1}, {match.umpire2}</p>      
      </div>
    </div>
  );
};
