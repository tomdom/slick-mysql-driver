lazy val root = (project in file(".")).
  settings(
    organization := "com.github.tomdom",
    name := "slick-mysql-driver",
    version := "0.1-SNAPSHOT",
    scalaVersion := "2.11.7",
    crossScalaVersions := Seq("2.10.5", "2.11.7"),
      libraryDependencies ++= Seq (
      "com.typesafe.slick" %% "slick-codegen" % "3.0.0"
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
