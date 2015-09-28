lazy val root = (project in file("."))
  .settings(
    organization := "org.skinny-framework",
    name := "skinny-micro-usage-example",
    version := "0.1",
    scalaVersion := "2.11.7",
    resolvers += "sonatype releases" at "https://oss.sonatype.org/content/repositories/releases",
    dependencyOverrides := Set("org.scala-lang" %  "scala-compiler" % scalaVersion.value), // for Scalate
    libraryDependencies ++= Seq(
      "org.skinny-framework" %% "skinny-micro"         % skinnyMicroVersion % Compile,
      "org.skinny-framework" %% "skinny-micro-server"  % skinnyMicroVersion % Compile,
      "org.skinny-framework" %% "skinny-micro-jackson" % skinnyMicroVersion % Compile,
      // "org.skinny-framework" %% "skinny-micro-json4s"  % skinnyMicroVersion % Compile,
      "org.skinny-framework" %% "skinny-micro-scalate" % skinnyMicroVersion % Compile,
      "ch.qos.logback"       %  "logback-classic"      % "1.1.3"            % Compile,
      "org.eclipse.jetty"    %  "jetty-webapp"         % "9.2.13.v20150730" % "container",
      "org.skinny-framework" %% "skinny-micro-test"    % skinnyMicroVersion % Test
    ),
    mainClass in Compile := Some("skinny.standalone.JettyLauncher"),
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
  )
  .settings(servletSettings)
  .settings(scalariformSettings)

lazy val skinnyMicroVersion = "0.9.13"
