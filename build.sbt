name := "ScalaTest"

version := "0.1"

scalaVersion := "2.12.4"
libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.4" % "test"

lazy val root = (project in file("."))
  .aggregate(account, checkout, inventory, dashboard)
lazy val account = (project in file("account")).settings(libraryDependencies ++= Seq("log4j" % "log4j" % "1.2.17",
  "org.json4s" %% "json4s-jackson" % "3.5.3"))
lazy val checkout = (project in file("checkout")).dependsOn(inventory, account).settings(libraryDependencies ++= Seq("log4j" % "log4j" % "1.2.17",
  "org.json4s" %% "json4s-jackson" % "3.5.3"))
lazy val inventory = (project in file("inventory")).settings(libraryDependencies ++= Seq("log4j" % "log4j" % "1.2.17",
  "org.json4s" %% "json4s-jackson" % "3.5.3"))
lazy val dashboard = (project in file("dashboard")).dependsOn(inventory, account, checkout).settings(libraryDependencies ++= Seq())
