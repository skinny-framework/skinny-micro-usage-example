scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
addSbtPlugin("org.scalariform"      % "sbt-scalariform"         % "1.8.2")
addSbtPlugin("org.skinny-framework" % "sbt-servlet-plugin"      % "3.0.7")
// If you'd like to need Scalate precompilation, enable this settings too
addSbtPlugin("org.scalatra.scalate" % "sbt-scalate-precompiler" % "1.9.1.0")
