import { React, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { MatchDetailCard } from '../components/MatchDetailCard';
import { MatchSmallCard } from '../components/MatchSmallCard';

export const TeamPage = () => {
  
    const [team, setTeam] = useState({ latestMatches: [] });
    
    const { teamName } = useParams();

    useEffect(
        () => {
            
            const fetchMatches = async () => {
                console.log("Fetching matches");
                const response = await fetch(`http://localhost:8080/team/${teamName}`);
                const data = await response.json();
                console.log(data);
                setTeam(data);
            };

            fetchMatches();
        }, [teamName]
    );
    
    // ToDo: Redirect to 404 page if team not found
    if (!team || !team.teamName) return <h1>Team not found</h1>;

    return (
        <div className="TeamPage">
            <h1>{team.teamName}</h1>
            <MatchDetailCard teamName={team.teamName} match={team.latestMatches[0]} />
            {team.latestMatches.slice(1).map(match => <MatchSmallCard teamName={team.teamName} match={ match } />)}
        </div>
    );
}

