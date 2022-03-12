import "./App.css";
import { TeamPage } from "./pages/TeamPage";
import { MatchPage } from "./pages/MatchPage";
import { BrowserRouter, Routes, Route } from "react-router-dom";

function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route
            path="/teams/:teamName/matches/:year"
            element={<MatchPage />}
          />
          <Route
            path="/teams/:teamName"
            element={<TeamPage />}
          />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
