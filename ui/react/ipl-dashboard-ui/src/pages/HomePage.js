import { React, useEffect, useState } from 'react';
import { TeamTile } from '../components/TeamTile';

import './HomePage.scss';

export const HomePage = () => {
  
    const [teams, setTeams] = useState([]);

    useEffect(
        () => {
            const fetchTeams = async () => {
                console.log("Fetching teams");
                const response = await fetch(`http://localhost:8080/team`);
                const data = await response.json();
                console.log(data);
                setTeams(data);
            };
            fetchTeams();
        }, []
    );
    
    // ToDo: Redirect to 404 page if team not found
    // if (!team || !team.teamName) return <h1>Team not found</h1>;

    return (
        <div className="HomePage">
            <div className='header-section'>
                <h1 className='page-heading'>IPL Dashboard</h1>
            </div>
            <div className='team-grid'>
                {teams.map(team => <TeamTile teamName={team.teamName} />)}
            </div>
        </div>
    );
}

