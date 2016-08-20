lazy val root = (project in file("."))
  .settings(
    organization := "org.skinny-framework",
    name := "skinny-micro-usage-example",
    version := "0.1",
    scalaVersion := "2.11.8",
    resolvers += "sonatype releases" at "https://oss.sonatype.org/content/repositories/releases",
    dependencyOverrides := Set("org.scala-lang" %  "scala-compiler" % scalaVersion.value), // for Scalate
    libraryDependencies ++= Seq(
      "org.skinny-framework" %% "skinny-micro"         % skinnyMicroVersion % Compile,
      "org.skinny-framework" %% "skinny-micro-jackson" % skinnyMicroVersion % Compile,
      "org.skinny-framework" %% "skinny-micro-scalate" % skinnyMicroVersion % Compile,
      "javax.servlet"        %  "javax.servlet-api"    % "3.1.0"            % "container;provided;test",
      "ch.qos.logback"       %  "logback-classic"      % "1.1.7"            % Compile,
      "org.eclipse.jetty"    %  "jetty-webapp"         % "9.2.18.v20160721" % "container",
      "org.skinny-framework" %% "skinny-micro-test"    % skinnyMicroVersion % Test
    ),
    transitiveClassifiers in Global := Seq(Artifact.SourceClassifier),
    incOptions := incOptions.value.withNameHashing(true),
    mainClass in Compile := Some("skinny.standalone.JettyLauncher"),
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
  )
  .settings(servletSettings)
  .settings(scalariformSettings)
  // If you'd like to need Scalate precompilation, enable this settings too
  //.settings(scalateSettings)

lazy val skinnyMicroVersion = "1.1.0"
