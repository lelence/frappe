import sbt._
import Keys._

object Settings {

  lazy val basicSetting = Seq(
    organization := "org.maogogo",
    version := sys.env.getOrElse("version", "0.0.1-SNAPSHOT"),
    scalaVersion := "2.12.7",
    organizationName := "Maogogo Workshop",
    scalacOptions := Seq(
      "-deprecation",
      "-feature",
      "-language:implicitConversions",
      "-language:postfixOps"
    ),
    startYear := Some(2019),
    licenses += ("Apache-2.0", new URL(
      "https://www.apache.org/licenses/LICENSE-2.0.txt"
    )),
    resolvers ++= Seq(
      "Maven Nexus" at "http://192.168.0.201:8081/repository/maven-public/",
      "releases" at "http://oss.sonatype.org/content/repositories/releases"
    )
  )

//  scalacOptions ++= Seq(
//    "-unchecked",
//    "-deprecation",
//    "-feature",
//    "-language:existentials",
//    "-language:higherKinds",
//    "-language:implicitConversions",
//    "-language:postfixOps",
//    "-Ywarn-dead-code",
//    "-Ywarn-infer-any",
//    "-Ywarn-unused-import",
//    "-Xfatal-warnings",
//    "-Xlint"
//  ),

}
