# Cinema Paradiso

Cinema Paradiso is a online movie rating application that allows users to obtain information on movies and TV shows. 

**Development environment**
  - Frontend: Angular 2, Bootstrap
  - Backend: Spring Boot, Spring MVC, Hibernate, MySQL
  
**Developers:** [YanLiang Li](https://github.com/yanliangli), [Nan Jiang](https://github.com/BoofOfFools)

## How to run the project <a id="how-to-run-the-project"></a>
>>This project requires [Node.js](https://nodejs.org/) v6+ and [Angular CLI](https://cli.angular.io) v1.7.4+ to run the frontend, and [python](https://www.python.org/download/releases/3.0/) v3.0 to set up the database.

To set up the backend environment, open ``./cinema-paradiso-backend/pom.xml`` as project in IntelliJ. Run ``CinemaParadisoApplication.java``.

To set up the database, create a database called ``cinema-paradiso`` in MySQL, and enter your database username and password in backend's configuration file ``application.yml`` line 5 and 6. Then, run the following command to import the data (make sure your backend is running). This process will take approximately 5 minutes. 
```sh
$ python3 database/add_movie_json.py
```

To set up the frontend environment, install the following dependencies.

```sh
$ cd cinema-paradiso-frontend
$ npm install
$ npm install -g @angular/cli
```
then, run
```sh
$ ng serve
```

Finally, open up your favorate browser, and go to http://localhost:4200/
