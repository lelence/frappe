import Settings._
import Dependencies._

lazy val common = (project in file("common"))
  .enablePlugins(AutomateHeaderPlugin)
  .dependsOn(proto)
  .settings(basicSetting, libraryDependencies ++= commonDeps)

lazy val oauth2 = (project in file("oauth2"))
  .enablePlugins(AutomateHeaderPlugin)
  .settings(
    PB.targets in Compile := Seq(
      scalapb.gen() -> (sourceManaged in Compile).value
    ),
    basicSetting,
    libraryDependencies ++= scalapbDependency,
    libraryDependencies += "com.typesafe.akka" %% "akka-http" % "10.1.9"
  )

lazy val rest = (project in file("rest"))
  .enablePlugins(JavaAppPackaging, AutomateHeaderPlugin)
  .dependsOn(common, oauth2)
  .settings(basicSetting, libraryDependencies ++= restDeps)

lazy val rpc = (project in file("rpc"))
  .enablePlugins(JavaAppPackaging, AutomateHeaderPlugin, DockerSpotifyClientPlugin)
  .dependsOn(common)
  .settings(
    basicSetting,
    mappingSettings,
    dockerSettings,
    libraryDependencies ++= rpcDeps
    // libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.6"
  )

lazy val proto = (project in file("proto"))
  .settings(
    PB.targets in Compile := Seq(
      scalapb.gen() -> (sourceManaged in Compile).value
    ),
    libraryDependencies ++= scalapbDependency
  )

lazy val frappe = (project in file("."))
  .aggregate(common, rest, rpc, proto)
