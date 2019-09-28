import com.typesafe.sbt.SbtNativePackager._
import com.typesafe.sbt.packager.Keys._
import sbt.Keys._
import sbt._
// .packager.archetypes.scripts.BashStartScriptPlugin.autoImport._

object Settings {

  lazy val basicSetting = Seq(
    organization := "org.maogogo",
    maintainer := "toan@maogogo.org",
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

  lazy val mappingSettings: Seq[Setting[_]] = Seq(
//    javaOptions in Universal ++= Seq(
//      // -J params will be added as jvm parameters
//      "-J-Xmx640m",
//      "-J-Xms350m",
//      s"-Dconfig.file=${targetDirectory}/${confFileMapping.value._2}",
//      s"-Dlogback.configurationFile=${targetDirectory}/${logbackConfMapping.value._2}"
//    ),
    // maintainer in Linux := "Toan <toan@maogogo.com>",
    bashScriptExtraDefines ++= Seq(
      """addJava "-Xms2048m"""",
      """addJava "-Xmx4096m"""",
      """addJava "-Dconfig.file=${app_home}/../conf/application.conf"""",
      """addJava "-Dlogback.configurationFile=${app_home}/../conf/logback.xml""""
    ),
    mappings in (Universal, packageBin) ++= Seq(
      // file("src/main/resources/logback.xml")
      (resourceDirectory in Compile).value / "logback.xml" -> "conf/logback.xml",
      (resourceDirectory in Compile).value / "application.conf" -> "conf/application.conf"
    )
    /// mappings in (Universal, packageBin) +=
  )

  lazy val dockerSettings: Seq[Setting[_]] = Seq(
    maintainer in Docker := "Toan <toan@maogogo.com>",
    dockerBaseImage := "node201:5000/dev/jdk8-stretch",
    dockerRepository := Some("node201:5000/dev"),
    daemonUserUid in Docker := None,
    daemonUser in Docker := "root",
    dockerUpdateLatest := true
  )

  // for publish
  val publishSettings = Seq(
    publishArtifact in (Compile, packageSrc) := false,
    publishArtifact in (Compile, packageDoc) := false,
    publishTo := {
      val nexus = "http://192.168.0.201:8081/repository/maven"
      // http://192.168.0.201:8081/repository/maven-releases/
      // http://192.168.0.201:8081/repository/maven-snapshots/
      if (isSnapshot.value) {
        Some("snapshots" at s"${nexus}-snapshots")
      } else Some("releases" at s"${nexus}-releases")
    },
    credentials += Credentials(Path.userHome / ".sbt" / ".credentials")
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
