package example

import scala.concurrent._
import skinny.micro._
// contrib.json4s.JSONSupport is also available
import skinny.micro.contrib.jackson.JSONSupport
import skinny.micro.contrib.jackson.JSONParamsAutoBinderSupport
import skinny.micro.contrib.ScalateSupport

/**
 * Demo app to show Skinny Micro examples.
 *
 * If the web app doesn't have Future operation at all, using WebApp is straightforward because it's a normal Servlet application.
 * If the web app deals with Future values on it, using AsyncWebApp is preferred because it makes safer than WebApp.
 */
object App
  extends AsyncWebApp
  with JSONSupport
  with JSONParamsAutoBinderSupport
  with ScalateSupport {

  // ---------------------------------------------
  // before filter
  before("/future".r) { implicit ctx =>
    response.headers += "X-Operation-Type" -> "async"
  }
  // after filter
  after("\\.json$".r) { implicit ctx =>
    contentType = "application/json"
  }

  // ---------------------------------------------
  // Error filter
  // Do something here when exceptions are thrown from this web app
  error {
    case e =>
      logger.error(e.getMessage, e)
      throw e // re-throwing the exception keeps the response as status 500
  }

  // ---------------------------------------------
  // HTTP methods

  get("/method") { implicit ctx => "get" }
  post("/method") { implicit ctx => "post" }
  put("/method") { implicit ctx => "put" }
  patch("/method") { implicit ctx => "patch" }
  head("/method") { implicit ctx => }
  options("/method") { implicit ctx => "options" }
  delete("/method") { implicit ctx => "delete" }

  // ---------------------------------------------
  // Simple HTML rendering example with Scalate
  // http://scalate.github.io/scalate/
  //
  // see also:
  //  - src/main/webapp/WEB-INF/layouts/default.ssp
  //  - src/main/webapp/WEB-INF/views/index.ssp
  get("/") { implicit ctx =>
    // NOTE: content-type is "text/plain" with charset by default
    contentType = "text/html"
    ssp("/index.ssp") // ssp,jade,scaml,mustache
  }

  // ---------------------------------------------
  // Simple text response example
  get("/text") { implicit ctx =>
    "Hello, world!"
  }

  // ---------------------------------------------
  // Returning any reponse status
  //
  // Basically returning ActionResult value as below is more functional programming style.
  // If you prefer forcing this style, TypedWebApp/TypedAsyncWebApp is better because they lead non-ActionResult value to compilation error.
  //
  // See also:
  //  - https://github.com/skinny-framework/skinny-micro/blob/master/micro/src/main/scala/skinny/micro/response/ActionResults.scala
  get("/status/200") { implicit ctx =>
    Ok("ok")
  }
  get("/status/201") { implicit ctx =>
    // setter for response status
    status = 201
    // no response body
  }

  // ---------------------------------------------
  // Response headers example
  //
  // ActionResult can hold response headers
  get("/headers") { implicit ctx =>
    Ok(body = "ok", headers = Map("X-Response-Id" -> "12345"))
  }
  // "response.headers" is mutable
  get("/headers-2") { implicit ctx =>
    response.headers += "X-Response-Id" -> "12345"
    "ok"
  }

  // ---------------------------------------------
  // Set-Cookie example
  //
  val exampleCookies = Seq(
    Cookie("foo" -> "bar"),
    Cookie("foo" -> "bar").withOptions(domain = "localhost"))
  // AtionResult can hold cookies to be set
  get("/cookies") { implicit ctx =>
    Ok(body = "ok", cookies = exampleCookies)
  }
  // "cookies" is mutable
  get("/cookies-2") { implicit ctx =>
    cookies ++= exampleCookies
    "ok"
  }

  // ---------------------------------------------
  // JSON response example
  // see also: after filter defined above
  get("/sample.json") { implicit ctx =>
    toJSONString(Map("name" -> "Martin", "id" -> 123))
  }

  // ---------------------------------------------
  // Params example
  //
  // all the parameters including query string and form params
  post("/params.json") { implicit ctx =>
    toJSONString(params)
  }
  // params only from query string
  get("/query-params.json") { implicit ctx =>
    toJSONString(queryParams)
  }
  // params only from form params on the request body
  post("/form-params.json") { implicit ctx =>
    toJSONString(formParams)
  }
  // JSON request body will be merged into params only when JSONParamsAutoBinderSupport is activated.
  //   curl -XPOST 'http://localhost:8080/json-params' -H'Content-Type: application/json' -d'{"name":"Alice"}'
  post("/json-params") { implicit ctx =>
    params
  }
  // path params
  // path params are given priority over query params/form params
  get("/path-params/:name") { implicit ctx =>
    params.getAs[String]("name").getOrElse("path param is absent")
  }
  // wildcard
  get("/say/*/to/*") { implicit ctx =>
    // Matches "GET /say/hello/to/world"
    multiParams("splat") // == Seq("hello", "world")
  }
  get("/download/*.*") { implicit ctx =>
    // Matches "GET /download/path/to/file.xml"
    multiParams("splat") // == Seq("path/to/file", "xml")
  }
  // regexp
  get("""^\/f(.*)/b(.*)""".r) { implicit ctx =>
    // Matches "GET /foo/bar"
    multiParams("captures") // == Seq("oo", "ar")
  }

  // ---------------------------------------------
  // Future operation example

  // It's safer to make Future[Any] or Future[ActionResult] operations on AsyncWebApp.
  // Since non-async WebApp provides thread-local request/response everywhere, you should be aware not to access them in Future operations.
  get("/future") { implicit ctx =>
    Future {
      // on a different thread
      Thread.sleep(100)
      "ok after 100ms"
    }.map { body => Ok(body) }
  }
  post("/future/params.json") { implicit ctx =>
    Future {
      contentType = "application/json"
      toJSONString(params)
    }
  }

  // ---------------------------------------------
  // Sending 500 error

  get("/error") { implicit ctx =>
    throw new RuntimeException("Oops!")
  }

}
