name := "ScalaTest"

version := "0.1"

scalaVersion := "2.12.4"

libraryDependencies += "log4j" % "log4j" % "1.2.17"

libraryDependencies += "org.json4s" %% "json4s-jackson" % "3.5.3"

libraryDependencies += "org.scalatest" % "scalatest_2.12" % "3.0.4" % "test"

lazy val root = (project in file("."))
  .aggregate(account, checkout, inventory)
lazy val account = project in file("account")
lazy val checkout = (project in file("checkout")).dependsOn(inventory, account)
lazy val inventory = project in file("inventory")
lazy val dashboard = (project in file("dashboard")).dependsOn(inventory, account, checkout)
