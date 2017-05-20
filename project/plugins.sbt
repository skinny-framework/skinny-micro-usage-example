scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
addMavenResolverPlugin
addSbtPlugin("org.scalariform"      % "sbt-scalariform"         % "1.6.0")
addSbtPlugin("org.skinny-framework" % "sbt-servlet-plugin"      % "2.2.3")
// If you'd like to need Scalate precompilation, enable this settings too
addSbtPlugin("org.scalatra.scalate" % "sbt-scalate-precompiler" % "1.8.0.1")
