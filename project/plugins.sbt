scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature")
addMavenResolverPlugin

addSbtPlugin("org.skinny-framework" % "sbt-servlet-plugin" % "2.0.1")
addSbtPlugin("com.typesafe.sbt"     % "sbt-scalariform"    % "1.3.0")
