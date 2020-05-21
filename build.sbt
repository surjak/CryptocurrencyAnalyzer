name := "PlayApp"
 
version := "1.0" 
      
lazy val `playapp` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"

resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"

scalaVersion := "2.12.2"

libraryDependencies ++= Seq(jdbc, ehcache, ws, specs2 % Test, guice)
// https://mvnrepository.com/artifact/org.postgresql/postgresql
libraryDependencies ++= Seq("org.postgresql" % "postgresql" % "42.2.12")
libraryDependencies ++= Seq("org.mindrot" % "jbcrypt" % "0.4")
libraryDependencies += "com.typesafe.play" %% "play-slick" % "5.0.0"
libraryDependencies += "com.typesafe.slick" %% "slick-codegen" % "3.3.2"
libraryDependencies += "com.jason-goodwin" %% "authentikat-jwt" % "0.4.5"
libraryDependencies += "com.github.daddykotex" %% "courier" % "2.0.0"
unmanagedResourceDirectories in Test <+= baseDirectory(_ / "target/web/public/test")
libraryDependencies ++= Seq(
  "be.objectify" %% "deadbolt-scala" % "2.6.0"
)

// https://mvnrepository.com/artifact/javax.mail/mail
libraryDependencies += "javax.mail" % "mail" % "1.4.1"
libraryDependencies += "org.apache.commons" % "commons-email" % "1.5"
// https://mvnrepository.com/artifact/javax.mail/javax.mail-api
libraryDependencies += "javax.mail" % "javax.mail-api" % "1.6.0"

resolvers += Resolver.sonatypeRepo("snapshots")
      