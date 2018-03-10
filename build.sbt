name := "distributed-stack"
version := "1.0"

scalaVersion := "2.11.7"


libraryDependencies ++= Seq(
  "com.twitter" %% "finagle-thrift" % "6.40.0" exclude("com.twitter", "libthrift"),
  "org.apache.thrift" % "libthrift" % "0.9.3"
)

libraryDependencies ++= Seq(
  "io.ckite" % "ckite-core" % "0.2.1",
  "io.ckite" % "ckite-finagle" % "0.2.1",
  "io.ckite" % "ckite-mapdb" % "0.2.1"
)
