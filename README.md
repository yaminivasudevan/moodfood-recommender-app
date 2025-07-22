# MoodFood Backend

MoodFood is a RESTful backend API built with Spring Boot that enables users to log their meals along with their mood, fetch detailed nutrition information from the USDA FoodData Central API, and view meal history with advanced filtering, sorting, and pagination.

---

## Features

- **Meal Logging:** Log meals with mood and timestamp.  
- **Nutrition Integration:** Fetches calories, protein, fat, and carbs from USDA FoodData Central API.  
- **Meal History:** Filter, sort, and paginate meal logs based on meal name, mood, and date range.  
- **Robust Error Handling:** Falls back to zero nutrition values if the external nutrition API fails.  
- **Clean Architecture:** Uses Spring Data JPA, Specifications, and layered services.

---

## Technologies Used

- Java 17+  
- Spring Boot 3.x  
- Spring Data JPA (Hibernate)
- React.js
- Lombok  
- Maven  
- USDA FoodData Central API

---

## Getting Started

### Prerequisites

- Java 17 or higher installed  
- Maven installed  
- Oracle or any JPA-compatible database
- USDA API key (optional but recommended for nutrition data)

### Setup

1. Clone the repository:

   ```bash
   git clone https://github.com/yaminivasudevan/moodfood-recommender-app
   cd moodfood-recommender-app
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

3. (Optional) Add your USDA API key to environment variables or your application configuration so the nutrition service can fetch data.

---

## Running the Application

Start the backend server by running:

```bash
mvn spring-boot:run
```

This will start the server at `http://localhost:8080`.

---

## API Endpoints

| Endpoint                    | Method | Description                                |
|-----------------------------|--------|--------------------------------------------|
| `/api/meals/log`            | POST   | Log a new meal with mood and view its nutrition details |
| `/api/meals/getAllMeals`     | GET    | Retrieve all logged meals |
| `/api/meals/history`  | GET    | Retrieve filtered, sorted, paginated meal logs |
| `/api/meals/filter`  | GET    | Retrieve meal logs based on the mood |

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

## Notes

- Nutrition data is fetched live from the USDA FoodData Central API.  
- If the external API call fails, the system falls back to zero nutrition values to ensure meal logging still works.  
- The meal history endpoint supports filters such as meal name, mood, date range, and includes pagination and sorting options for performance.

---
