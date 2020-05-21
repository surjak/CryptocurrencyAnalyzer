package models

object CodeGen extends App {
  slick.codegen.SourceCodeGenerator.run(
    "slick.jdbc.PostgresProfile",
    "org.postgresql.Driver",
    "jdbc:postgresql://grzegorzpach.pl:5432/pollution?user=admin&password=litery98",
    "C:/Users/surja/Documents/Programowanie/Scala/PlayApp/app",
    "models", None, None, false, false
  )

}
