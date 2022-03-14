import "./App.scss";
import { TeamPage } from "./pages/TeamPage";
import { MatchPage } from "./pages/MatchPage";
import { HomePage } from "./pages/HomePage";
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
          <Route
            path="/"
            element={<HomePage />}
          />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
