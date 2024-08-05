import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import Home from "./Home";
import ApiKeyManager from "./ApiKeyManager";

const Navbar: React.FC = () => {
    return (
        <nav className="bg-blue-500 p-4 text-white">
            <div className="container mx-auto flex items-center justify-between">
                <Link to="/" className="text-xl font-bold">MyApp</Link>
                <Link to="/api-key" className="px-4 py-2 bg-blue-700 rounded">Add API Key</Link>
            </div>
        </nav>
    );
};

const App: React.FC = () => {
    return (
        <Router>
            <Navbar />
            <Routes>
                <Route path="/" element={<Home />} />
                <Route path="/api-key" element={<ApiKeyManager />} />
            </Routes>
        </Router>
    );
};

export default App;
