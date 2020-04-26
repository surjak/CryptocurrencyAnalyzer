
# Cryptocurrency analyzer



## Contributors
- [Mateusz Surjak](https://github.com/surjak)

- [Radosław Kopeć](https://github.com/radekkpc)

- [Andrzej Podobiński](https://github.com/andrzejPodo)

## Technologies

- Scala
- Play Framework


# Run
Install [docker](https://docs.docker.com/get-docker/) and run
```bash
docker run --name my_postgres -d -p 5432:5432 -e POSTGRES_USER=sample -e POSTGRES_PASSWORD=sample -e POSTGRES_DB=sample postgres
```
or you can install postgresql locally and connect to it changing properties in *conf/application.conf*

Clone this repository, and run your play application using either the ```play run``` or the ```sbt run``` command. Then, open your browser at http://localhost:9000/
