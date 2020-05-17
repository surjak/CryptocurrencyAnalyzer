package models

object CodeGen extends App {
  slick.codegen.SourceCodeGenerator.run(
    "slick.jdbc.PostgresProfile",
    "org.postgresql.Driver",
    "jdbc:postgresql://localhost:5432/sample?user=sample&password=sample",
    "C:/Users/surja/Documents/Programowanie/Scala/PlayApp/app",
    "models", None, None, false, false
  )

}
