import skinny.servlet._, ServletPlugin._, ServletKeys._
lazy val jettyVersion = "9.4.15.v20190215"

lazy val root = (project in file("."))
  .settings(
    organization := "org.skinny-framework",
    name := "skinny-micro-usage-example",
    version := "0.1",
    scalaVersion := "2.12.8",
    resolvers += "sonatype staging" at "https://oss.sonatype.org/content/repositories/staging",
    dependencyOverrides := Seq("org.scala-lang" %  "scala-compiler" % scalaVersion.value), // for Scalate
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
    mainClass in Compile := Some("skinny.standalone.JettyLauncher"),
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
  )
  .settings(servletSettings)
  // If you'd like to need Scalate precompilation, enable this settings too
  //.settings(scalateSettings)

lazy val skinnyMicroVersion = "2.0.+"
