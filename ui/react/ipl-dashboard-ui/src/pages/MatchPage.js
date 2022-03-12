import { React, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import { MatchDetailCard } from '../components/MatchDetailCard';

export const MatchPage = () => {
  
    const [matches, setMatches] = useState([]);
    
    const { teamName, year } = useParams();

    useEffect(
        () => {
            const fetchMatches = async () => {
                console.log("Fetching matches");
                const response = await fetch(`http://localhost:8080/team/${teamName}/matches/${year}`);
                const data = await response.json();
                console.log(data);
                setMatches(data);
            };
            fetchMatches();
        }, []
    );
    
    // ToDo: Redirect to 404 page if team not found
    // if (!team || !team.teamName) return <h1>Team not found</h1>;

    console.log("MatchPage");

    return (
        <div className="MatchPage">
            {matches.map(match => <MatchDetailCard teamName={teamName} match={ match } />)}
        </div>
    );
}

