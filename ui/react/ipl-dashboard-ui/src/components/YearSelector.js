import { React, useEffect, useState } from 'react';
import { Link } from "react-router-dom";

import './YearSelector.scss';

export const YearSelector = ({ teamName }) => {

    const [years, setYears] = useState([]);

    useEffect(
        () => {
            
            const fetchYears = async () => {
                console.log("Fetching years");
                const response = await fetch(`http://localhost:8080/years`);
                const data = await response.json();
                console.log(data);
                setYears(data);
            };

            fetchYears();
        }, []
    );

    return (
    <ol className='YearSelector'>
            {years.map(year =>
                <li key={year}>
                    <Link to={`/teams/${teamName}/matches/${year}`}>{year}</Link>
                </li>)
            }
    </ol>
    );

};
