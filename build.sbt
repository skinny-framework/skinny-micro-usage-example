import skinny.servlet._, ServletPlugin._, ServletKeys._
lazy val jettyVersion = "9.4.9.v20180320"

lazy val root = (project in file("."))
  .settings(
    organization := "org.skinny-framework",
    name := "skinny-micro-usage-example",
    version := "0.1",
    scalaVersion := "2.12.4",
    resolvers += "sonatype releases" at "https://oss.sonatype.org/content/repositories/releases",
    dependencyOverrides := Set("org.scala-lang" %  "scala-compiler" % scalaVersion.value), // for Scalate
    libraryDependencies ++= Seq(
      "org.skinny-framework" %% "skinny-micro"         % skinnyMicroVersion % Compile,
      "org.skinny-framework" %% "skinny-micro-server"  % skinnyMicroVersion % Compile,
      "org.skinny-framework" %% "skinny-micro-jackson" % skinnyMicroVersion % Compile,
      "org.skinny-framework" %% "skinny-micro-scalate" % skinnyMicroVersion % Compile,
      "javax.servlet"        %  "javax.servlet-api"    % "3.1.0"            % "container;provided;test",
      "ch.qos.logback"       %  "logback-classic"      % "1.2.3"            % Compile,
      "org.eclipse.jetty"    %  "jetty-webapp"         % jettyVersion       % "container",
      "org.eclipse.jetty"    %  "jetty-plus"           % jettyVersion       % "container",
      "org.skinny-framework" %% "skinny-micro-test"    % skinnyMicroVersion % Test
    ),
    transitiveClassifiers in Global := Seq(Artifact.SourceClassifier),
    incOptions := incOptions.value.withNameHashing(true),
    mainClass in Compile := Some("skinny.standalone.JettyLauncher"),
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
  )
  .settings(servletSettings)
  // If you'd like to need Scalate precompilation, enable this settings too
  //.settings(scalateSettings)

lazy val skinnyMicroVersion = "1.3.+"
