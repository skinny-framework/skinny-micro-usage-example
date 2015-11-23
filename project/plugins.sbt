scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
addMavenResolverPlugin

addSbtPlugin("com.typesafe.sbt"     % "sbt-scalariform"         % "1.3.0")
addSbtPlugin("org.skinny-framework" % "sbt-servlet-plugin"      % "2.0.2")
// If you'd like to need Scalate precompilation, enable this settings too
addSbtPlugin("org.skinny-framework" % "sbt-scalate-precompiler" % "1.7.1.0")

addSbtPlugin("com.github.mpeltonen" % "sbt-idea"                % "1.6.0")
