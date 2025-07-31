
# MoodFood App

**MoodFood** is a fullstack web application that allows users to:

- Log meals along with their current mood  
- Fetch detailed nutrition data from the **USDA FoodData Central API**  
- Receive **AI-powered recommendations** based on mood, nutritional balance, and psychological impact of food  

Using the **Google Gemini API**, MoodFood doesn’t just suggest random meals — it **analyzes the logged food's nutritional content**, **evaluates how that might affect the user's mood**, and recommends **balanced meals** to improve or stabilize emotional wellbeing.

Users can also explore their meal history with advanced filtering, trends, and upcoming features like nutrition analytics and mood-based charts.

---

## Features

### 🔧 Backend (Spring Boot)
- **Meal Logging:** Log meals with mood and timestamp.
- **AI-Powered Nutrition + Mood Analysis:**
  - Analyzes meals for macro breakdown and emotional impact
  - Suggests meals that are both nutritionally balanced and mood-appropriate
- **Nutrition Integration:** Fetch calories, protein, fat, and carbs using USDA API.
- **Meal History:** Filter, sort, and paginate logs by meal name, mood, and date range.
- **Robust Error Handling:** Falls back to default values if external APIs fail.
- **Clean Architecture:** Layered services with Spring Data JPA and Specifications.

### Frontend (React)
- Log meals with mood in an intuitive interface.
- View meal history and nutrition insights visually.
- Designed for responsiveness and user-friendliness.

---

## Technologies Used

- **Backend:** Java 17+, Spring Boot 3.x, Spring Data JPA, Hibernate, Maven, Oracle DB
- **Frontend:** React.js, HTML, CSS, JavaScript
- **External API:** USDA FoodData Central (for nutrition info)
- **AI (Upcoming):** OpenAI or Gemini for meal recommendations

---

## Project Structure

```
moodfood-recommender-app/
├── backend/       # Spring Boot backend
├── frontend/      # React frontend
├── README.md
├── .gitignore
```

---

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven installed
- Node.js and npm for frontend
- Oracle or any JPA-compatible database
- USDA API key (optional but recommended)

---

## Backend Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/yaminivasudevan/moodfood-recommender-app.git
   cd moodfood-recommender-app/backend
   ```

2. Configure your database in `src/main/resources/application.properties`:

   ```properties
   spring.datasource.url=jdbc:oracle:thin:@//localhost:1521/XEPDB1
   spring.datasource.username=your_db_user
   spring.datasource.password=your_db_password
   spring.jpa.hibernate.ddl-auto=update
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true
   ```

3. (Optional) Add USDA API key to environment or config for nutrition data.

4. Run backend:

   ```bash
   mvn spring-boot:run
   ```

Backend will run at `http://localhost:8080`.

---

## Frontend Setup

1. Navigate to frontend folder:

   ```bash
   cd ../frontend
   ```

2. Install dependencies:

   ```bash
   npm install
   ```

3. Run frontend:

   ```bash
   npm start
   ```

App runs at `http://localhost:3000`. Ensure backend is running at port 8080.

---

## API Endpoints

| Endpoint                    | Method | Description                                |
|----------------------------|--------|--------------------------------------------|
| `/api/meals/log`           | POST   | Log a meal with mood; returns nutrition analysis + mood-based suggestions  |
| `/api/meals/getAllMeals`   | GET    | Retrieve all logged meals |
| `/api/meals/history`       | GET    | Retrieve filtered, sorted, paginated meal logs |
| `/api/meals/filter`        | GET    | Retrieve meals filtered by mood |

---

## Example Request

**POST** `/api/meals/log`

```json
{
  "meal": "100g chicken breast",
  "mood": "Happy"
}
```

**Response:**

```json
{
  "id": 1,
  "meal": "100g chicken breast",
  "mood": "Happy",
  "calories": 165,
  "protein": 31,
  "fat": 3.6,
  "carbs": 0,
  "loggedAt": "2025-07-22T10:30:00Z"
}
```

---

## Upcoming Features

- AI-powered meal recommendations using OpenAI or Gemini
- Nutrition charts and mood trends
- CSV export of meal logs
- Authentication and user-specific meal history

---

## License

This project is open-source and available under the [MIT License](LICENSE).

---

## Author

**Yamini Vasudevan**  
[LinkedIn](https://www.linkedin.com/in/yaminivasudevan) | [GitHub](https://github.com/yaminivasudevan)
