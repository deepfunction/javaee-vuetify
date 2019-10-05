## Wildfly configurations:
### Add user
https://bgasparotto.com/add-user-wildfly
### Datasource
https://www.youtube.com/watch?v=xSHXMcRsF0A

## First App run
### Inside the root directory do a:
```
mvn clean install
```

### Don't forget insert items to database before login.
```
backend/src/main/resources/course.sql
```

## Faster feedback with webpack-dev-server
The webpack-dev-server, which will update and build every change through all the parts of the JavaScript build-chain, is pre-configured in Vue.js out-of-the-box! So the only thing needed to get fast feedback development-cycle is to cd into frontend and run:
```
npm run serve
```

![hello](screenshots/app.png)