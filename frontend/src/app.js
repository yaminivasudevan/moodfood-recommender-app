import React, { useState } from 'react';

const BACKEND_URL = 'http://localhost:8080'; // Replace with your backend URL

const moodOptions = [
  "Happy",
  "Sad",
  "Energetic",
  "Tired",
  "Stressed",
  "Relaxed",
];

function App() {
  const [meal, setMeal] = useState('');
  const [mood, setMood] = useState('');
  const [mealLogs, setMealLogs] = useState([]);
  const [loading, setLoading] = useState(false);
  const [visible, setVisible] = useState(false);
  const [error, setError] = useState(null);
  const [nutrition, setNutrition] = useState(null);
  const [successMessage, setSuccessMessage] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    setLoading(true);
    setError(null);
    setNutrition(null);
    setSuccessMessage('');

    fetch(`${BACKEND_URL}/api/meals/log`, {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify({ meal, mood })
    })
      .then(res => {
        if (!res.ok) throw new Error('Failed to log meal');
        return res.json();
      })
      .then(data => {
        setSuccessMessage(data.message || 'Meal logged successfully');

        if (data.data && data.data.calories !== undefined) {
          setNutrition({
            calories: data.data.calories,
            protein: data.data.protein,
            fat: data.data.fat,
            carbs: data.data.carbs,
          });
          console.log("Nutrition set:", data.data);
        } else {
          console.warn("Nutrition data missing in response:", data);
        }

        setMeal('');
        setMood('');
      })
      .catch(err => {
        setError(err.message);
      })
      .finally(() => setLoading(false));
  };

  const fetchMealLogs = () => {
    if (visible) {
      setVisible(false);
      return;
    }

    setLoading(true);
    setError(null);

    fetch(`${BACKEND_URL}/api/meals/getAllMeals`)
      .then(res => {
        if (!res.ok) throw new Error('Failed to fetch meal logs');
        return res.json();
      })
      .then(data => {
        setMealLogs(data.data || []);
        setVisible(true);
      })
      .catch(err => setError(err.message))
      .finally(() => setLoading(false));
  };

  return (
    <div style={{
      maxWidth: '600px',
      margin: '2rem auto',
      padding: '1rem',
      border: '1px solid #ccc',
      borderRadius: '8px',
      fontFamily: 'Arial, sans-serif'
    }}>
      <h2 style={{ marginBottom: '1rem' }}>🍽️ Log Your Meal</h2>

      <form onSubmit={handleSubmit}>

        {/* Meal text input */}
        <input
          type="text"
          placeholder="Enter meal (e.g., 100g chicken)"
          value={meal}
          onChange={(e) => setMeal(e.target.value)}
          required
          style={{ width: '100%', marginBottom: '0.5rem', padding: '0.5rem' }}
        />

        {/* Mood dropdown */}
        <div style={{ marginBottom: '0.5rem' }}>
          <label htmlFor="mood" style={{ display: 'block', fontWeight: 'bold', marginBottom: '4px' }}>
            Select Mood:
          </label>
          <select
            id="mood"
            value={mood}
            onChange={(e) => setMood(e.target.value)}
            required
            style={{ width: '100%', padding: '0.5rem' }}
          >
            <option value="" disabled>-- Choose a mood --</option>
            {moodOptions.map(option => (
              <option key={option} value={option}>{option}</option>
            ))}
          </select>
        </div>

        <button type="submit" disabled={loading} style={{ padding: '0.5rem 1rem' }}>
          {loading ? 'Logging...' : 'Log Meal'}
        </button>
      </form>

      {successMessage && (
        <div style={{ color: 'green', marginTop: '1rem' }}>{successMessage}</div>
      )}

      {error && (
        <div style={{ color: 'red', marginTop: '1rem' }}>{error}</div>
      )}

      {nutrition && (
        <div style={{
          marginTop: '1rem',
          padding: '1rem',
          background: '#f9f9f9',
          border: '1px solid #ddd',
          borderRadius: '6px'
        }}>
          <h4>🍎 Nutrition Info</h4>
          <p>Calories: {nutrition.calories}</p>
          <p>Protein: {nutrition.protein}g</p>
          <p>Fat: {nutrition.fat}g</p>
          <p>Carbs: {nutrition.carbs}g</p>
        </div>
      )}

      <button onClick={fetchMealLogs} disabled={loading} style={{ marginTop: '1rem', padding: '0.5rem 1rem' }}>
        {visible ? 'Hide Meal Logs' : 'Show Meal Logs'}
      </button>

      {visible && mealLogs.length > 0 && (
        <div style={{ marginTop: '1rem' }}>
          <h3>📜 Logged Meals</h3>
          <ul style={{ paddingLeft: '1rem' }}>
            {mealLogs.map((log) => (
              <li key={log.id}>
                <strong>{log.meal}</strong> - {log.mood} - {new Date(log.loggedAt).toLocaleString()}
              </li>
            ))}
          </ul>
        </div>
      )}
    </div>
  );
}

export default App;
