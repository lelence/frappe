import sbt._

object Dependencies {

  private object Internal {

    val slf4jVersion = "1.7.25"
    val logbackVersion = "1.2.3"
    val akkaVersion = "2.5.25"
    val json4sVersion = "3.6.2"
    val scalaTestVersion = "3.0.8"

    val guiceVersion = "4.2.2"

    lazy val testDependency = Seq(
      "org.scalatest" %% "scalatest" % scalaTestVersion,
      "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream-testkit" % akkaVersion,
      "com.typesafe.akka" %% "akka-http-testkit" % "10.1.9",
      "org.scalamock" %% "scalamock" % "4.4.0"
    ) map (_ % Test)

    lazy val commonDependency = Seq(
      "org.slf4j" % "slf4j-api" % slf4jVersion,
      "ch.qos.logback" % "logback-core" % logbackVersion,
      "ch.qos.logback" % "logback-classic" % logbackVersion,
      "com.typesafe.scala-logging" %% "scala-logging" % "3.9.2",

      "com.github.scopt" %% "scopt" % "3.7.1"

      //      "com.github.nscala-time" %% "nscala-time" % "2.20.0"
      //      "org.clapper" %% "classutil" % "1.4.0",
      //      "org.reflections" % "reflections" % "0.9.11",
      //      "org.bouncycastle" % "bcprov-jdk15on" % "1.60"

    )

    lazy val json4sDependency = Seq(
      "com.thesamet.scalapb" %% "scalapb-json4s" % "0.9.3",
      "org.json4s" %% "json4s-native" % json4sVersion,
      "org.json4s" %% "json4s-jackson" % json4sVersion,
      "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.9"
    )

    lazy val guiceDependency = Seq(
      "com.google.inject" % "guice" % guiceVersion,
      "com.google.inject.extensions" % "guice-assistedinject" % guiceVersion,
      "net.codingwell" %% "scala-guice" % "4.2.6"
    )

    lazy val akkaDependency = Seq(
      "com.typesafe.akka" %% "akka-actor" % akkaVersion,
      "com.typesafe.akka" %% "akka-slf4j" % akkaVersion,
      "com.typesafe.akka" %% "akka-remote" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream" % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster-sharding" % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster-metrics" % akkaVersion,
      "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion,
      "com.typesafe.akka" %% "akka-slf4j" % akkaVersion
    )

    lazy val httpDependency = Seq(
      "com.typesafe.akka" %% "akka-http" % "10.1.9",
      "de.heikoseeberger" %% "akka-http-json4s" % "1.22.0"
    )

    //  lazy val socketIODependency = Seq(
    //
    //  )

    lazy val driverDependency = Seq(
      "com.github.etaty" %% "rediscala" % "1.9.0",
      "com.lightbend.akka" %% "akka-stream-alpakka-slick" % "1.1.1",
      "com.typesafe.akka" %% "akka-stream-kafka" % "1.0.5",
      // "com.lightbend.akka" %% "akka-stream-alpakka-mongodb" % "0.20",
      // "org.mongodb.scala" %% "mongo-scala-driver" % "2.5.0",
      "mysql" % "mysql-connector-java" % "5.1.48"
    )
  }

  lazy val scalapbDependency = Seq(
    "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion,
    "com.thesamet.scalapb" %% "scalapb-runtime" % scalapb.compiler.Version.scalapbVersion % "protobuf"
  )


  import Internal._

  lazy val dependency4Common = commonDependency ++ guiceDependency ++ akkaDependency ++ driverDependency ++ testDependency

  lazy val dependency4Rest = dependency4Common ++ httpDependency ++ json4sDependency

  lazy val dependency4Rpc = dependency4Common ++ httpDependency ++ json4sDependency

}
