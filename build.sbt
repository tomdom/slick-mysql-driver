lazy val root = (project in file(".")).
  settings(
    organization := "com.github.tomdom",
    name := "slick-mysql-driver",
    version := "0.2-SNAPSHOT",
    scalaVersion := "2.11.8",
    crossScalaVersions := Seq("2.10.6", "2.11.8"),
      libraryDependencies ++= Seq (
      "com.typesafe.slick" %% "slick-codegen" % "3.1.1"
      ),
    publishTo := {
      val tomdomMvn = Path.userHome.absolutePath + "/projects/github/tomdom/tomdom-mvn"
      if (isSnapshot.value)
        Some(Resolver.file("file", new File(tomdomMvn + "/snapshots")))
      else
        Some(Resolver.file("file", new File(tomdomMvn + "/releases")))
    }
  ).
  settings(scalariformSettings: _*)
