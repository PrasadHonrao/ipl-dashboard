import { React, useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';

import { MatchDetailCard } from '../components/MatchDetailCard';
import { YearSelector } from '../components/YearSelector';
import './MatchPage.scss';

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
        }, [teamName, year]
    );
    
    // ToDo: Redirect to 404 page if team not found
    // if (!team || !team.teamName) return <h1>Team not found</h1>;

    console.log("MatchPage");

    return (
        <div className="MatchPage">
            <div className='year-selector'>
                <h3>Select Year</h3>
                <YearSelector teamName={teamName}></YearSelector>
            </div>
            <div>
                <h1 className='page-heading'>{teamName} matches in {year}</h1>
                {matches.map(match => <MatchDetailCard teamName={teamName} match={match} />)}
            </div>
        </div>
    );
}

