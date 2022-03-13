import { React } from "react";
import { Link } from "react-router-dom";

import './MatchSmallCard.scss';

export const MatchSmallCard = ({ teamName, match }) => {
  if (!match) return null;

  const otherTeam = teamName === match.team1 ? match.team2 : match.team1;
  const isMatchWon = match.winner === teamName;

  return (
    <div className={isMatchWon ? "MatchSmallCard won-card" : "MatchSmallCard lost-card"}> 
      <span className="vs">vs</span>
      <h3> {" "}
        <Link to={`/teams/${otherTeam}`}>{otherTeam}</Link>{" "}
      </h3>
      <p className="match-result">
        {match.winner} won by {match.resultMargin} {match.result}
      </p>
    </div>
  );
};
