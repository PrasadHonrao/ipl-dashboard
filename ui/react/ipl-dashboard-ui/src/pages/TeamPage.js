import { React, useEffect, useState } from 'react';
import { useParams, Link } from 'react-router-dom';
import { MatchDetailCard } from '../components/MatchDetailCard';
import { MatchSmallCard } from '../components/MatchSmallCard';
import { PieChart } from 'react-minimal-pie-chart';

import './TeamPage.scss';

export const TeamPage = () => {
  
    const [team, setTeam] = useState({ latestMatches: [] });
    
    const { teamName } = useParams();

    useEffect(
        () => {
                const fetchMatches = async () => {
                console.log("Fetching matches for team: " + teamName);
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
            <div className='team-name-section'>
                <h1 className='team-name'>{team.teamName}</h1>
            </div>
            
            <div className='win-loss-section'>
            Wins / Losses    
            <PieChart
            data={[
                { title: 'Wins', value: team.totalWins, color: '#138D75' },
                { title: 'Losses', value: team.totalMatches - team.totalWins, color: '#A93226' }
            ]}
            />
            </div>
            
            <div className='match-detail-section'>
                <h2>Latest Matches</h2>
                <MatchDetailCard teamName={team.teamName} match={team.latestMatches[0]} />
            </div>
            
            {team.latestMatches.slice(1).map(match => <MatchSmallCard teamName={team.teamName} match={match} />)}
            
            {/* ToDo: Add a link to the previous year's matches */}
            <div className='more-link'>
                <Link to={'#'}>More ></Link>
            </div>
        </div>
    );
}

