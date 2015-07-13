# scalabase

Modified Slick MySQL driver for correct handling of Timestamp and Date columns with default values like '0000-00-00 00:00:00' respectively '0000-00-00' while slick source code generation.
It's useful for MySQL databases with Timestamp or Date columns having one of the mentioned default values and are not marked as nullable. 
The modified driver will return None as default value and true for nullable for such columns. 
So these columns will be generated as e.g. Rep[Option[java.sql.Timestamp]] instead of Rep[java.sql.Timestamp].
One can use the generated slick data model with jdbc driver option zeroDateTimeBehavior=convertToNull.

To use it you need to add the following line to your build.sbt

<pre>resolvers += ("tomdom-mvn snapshots" at "https://github.com/tomdom/tomdom-mvn/raw/master/snapshots")</pre>

and add this to your library dependencies

<pre>"com.github.tomdom" %% "slick-mysql-driver" % "0.1-SNAPSHOT"</pre>

In your database config use

<pre>slickDriver = "com.github.tomdom.slick.driver.MySQLDriver"</pre>
