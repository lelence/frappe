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
  .enablePlugins(AutomateHeaderPlugin)
  .dependsOn(common, oauth2)
  .settings(basicSetting, libraryDependencies ++= restDeps)

lazy val rpc = (project in file("rpc"))
  .enablePlugins(AutomateHeaderPlugin)
  .dependsOn(common)
  .settings(basicSetting, libraryDependencies ++= rpcDeps)

lazy val proto = (project in file("proto"))
  .settings(
    PB.targets in Compile := Seq(
      scalapb.gen() -> (sourceManaged in Compile).value
    ),
    libraryDependencies ++= scalapbDependency
  )

lazy val frappe = (project in file("."))
  .aggregate(common, rest, rpc, proto)
