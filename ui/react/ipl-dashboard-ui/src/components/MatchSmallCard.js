import { React } from 'react';

export const MatchSmallCard = ({ match }) => {
    if (!match) return null;

    return (      
    <div className="MatchSmallCard">
          <p>{match.team1} vs { match.team2}</p>
    </div>
  );
}

