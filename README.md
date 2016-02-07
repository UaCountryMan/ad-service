# AD service

Web application with embedded http server based on Spring Boot.
Application provides API for advertising issuing. Advertisement issued randomly from the entire set.

# API

API points:
 - /rest/ad/list
 - /rest/optimized/ad/list

Request:
```js
{
    "country":"UK",
    "os":"ios",
    "limit": 4
}
```
Response:
```js
{"ads":
    [{
        "id":1,
        "description":"funny ads",
        "url":"http://google.com"
    },...]
}
```
