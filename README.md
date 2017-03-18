# Ranking Backend
Simple universal ranking system application.

## Requirements
- Java SDK v1.8
- Maven 3.2 +
- MySQL

## Configuration
Open file `application-dev.properties` and provide your database connection info.

## Running app
```
mvn spring-boot:run
```

##API
Run app and open url:
```
http://localhost:8080/jsondoc-ui.html
```
Pasete JSONDoc URL:
```
http://localhost:8080/jsondoc
```
and run 'Get documentation'.
You can play around with API.

## Example use case
Let's say you have book store an you wan to add ranking system for books.

### 1. Configure rest data provider
Open configuration file: `rest-data-provider.properties` and provide url to your system where from wee can get book details by ID.
Example:
```
https://awesome.books.com/rest/books/%{id}
```
following placecholder `%{id}` will be replaced by real ID.

Example response from your server:
```json
{
  "title": "The Way Through the Woods",
  "author": "Shaylee Lakin",
  "genre": "Fiction narrative",
  "publisher": "Book Works",
  "cover": "http://lorempixel.com/1366/768/cats/"
}
```

You can disable this integration and integrate on frontend side. To disable change configuration property:
```
ranking.externalItemDataEnabled=false
```

### 2. Add book rating
Make call to url:
`POST http://localhost:8080/v1/rating`
Request body:
```json
{
  "itemId": "BookIdFromMyService",
  "raterId": "LoggedInUserIdFromMyService",
  "value": 5
}
```

### 3. Get books ranking
`GET http://localhost:8080/v1/ranking?page=0&size=20`
Example response:
```json
{
  "content": [
    {
      "id": 1,
      "itemId": "6",
      "rating": 7,
      "ratingsCount": 4,
      "rated": null,
      "itemData": {
        "cover": "http://lorempixel.com/g/640/350/people/",
        "author": "Miss Eunice Quitzon",
        "genre": "Comic/Graphic Novel",
        "publisher": "Hay House",
        "title": "Paths of Glory"
      }
    },
    {
      "id": 2,
      "itemId": "7",
      "rating": 6,
      "ratingsCount": 4,
      "rated": null,
      "itemData": {
        "cover": "http://lorempixel.com/640/480/food/",
        "author": "Dr. Dashawn Lockman",
        "genre": "Comic/Graphic Novel",
        "publisher": "Orchard Books",
        "title": "All Passion Spent"
      }
    }
  ],
  "last": true,
  "totalElements": 2,
  "totalPages": 1,
  "numberOfElements": 2,
  "sort": null,
  "first": true,
  "size": 20,
  "number": 0
}
```

For more info see `http://localhost:8080/jsondoc-ui.html`