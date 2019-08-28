import Settings._
import Dependencies._

lazy val common = (project in file("common"))
  .enablePlugins(AutomateHeaderPlugin)
  .dependsOn(proto)
  .settings(basicSetting, libraryDependencies ++= dependency4Common)

lazy val rest = (project in file("rest"))
  .enablePlugins(AutomateHeaderPlugin)
  .dependsOn(common)
  .settings(basicSetting, libraryDependencies ++= dependency4Rest)

lazy val rpc = (project in file("rpc"))
  .enablePlugins(AutomateHeaderPlugin)
  .settings(basicSetting, libraryDependencies ++= dependency4Rpc)

lazy val proto = (project in file("proto"))
  .settings(
    PB.targets in Compile := Seq(
      scalapb.gen() -> (sourceManaged in Compile).value
    ),
    libraryDependencies ++= scalapbDependency
  )

lazy val frappe = (project in file("."))
  .aggregate(common, rest, rpc, proto)
