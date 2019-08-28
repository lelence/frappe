import sbt._
import Keys._

object Settings {

  lazy val basicSetting = Seq(
    organization := "org.maogogo",
    version := "0.0.1-SNAPSHOT",
    scalaVersion := "2.12.7",
    organizationName := "Maogogo Workshop",
    scalacOptions := Seq("-deprecation",
      "-feature",
      "-language:implicitConversions",
      "-language:postfixOps"),
    startYear := Some(2019),
    licenses += ("Apache-2.0", new URL("https://www.apache.org/licenses/LICENSE-2.0.txt"))
  )

}
