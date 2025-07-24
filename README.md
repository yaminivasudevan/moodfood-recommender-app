
# MoodFood App

MoodFood is a fullstack web application that allows users to log meals along with their mood, fetch accurate nutrition information from the USDA FoodData Central API, and view meal history with advanced filtering and analytics. Future updates will include AI-powered mood-based meal recommendations.

---

## Features

### Backend (Spring Boot)
- **Meal Logging:** Log meals with mood and timestamp.
- **Nutrition Integration:** Fetch calories, protein, fat, and carbs from USDA FoodData Central API.
- **Meal History:** Filter, sort, and paginate logs by meal name, mood, and date range.
- **Robust Error Handling:** Falls back to zero values if external nutrition API fails.
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
| `/api/meals/log`           | POST   | Log a new meal with mood and view nutrition details |
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
