# MoodFood Recommender

Basic backend project for mood-based meal logging and recommendations.

## APIs

- `GET /meals` - Get all meals
- `GET /meals?mood=` - Get meals filtered by mood
- `POST /meals` - Log a meal with mood

## Setup

- Oracle DB configured in `application.properties`
- Run with `mvn spring-boot:run`