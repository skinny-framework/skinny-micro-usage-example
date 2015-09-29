package example

import skinny.jackson.JSONStringOps
import skinny.test.SkinnyFunSpec
import scala.util._

class AppSpec extends SkinnyFunSpec with JSONStringOps {
  addFilter(App, "/*")

  describe("Usage example app") {

    it("renders HTML body with Scalate") {
      get("/") {
        status should equal(200)
        body.startsWith("<html>") should equal(true)
        header("Content-Type") should equal("text/html; charset=UTF-8")
      }
    }

    it("supports various HTTP methods") {
      get("/method") { status should equal(200) }
      post("/method") { status should equal(200) }
      put("/method") { status should equal(200) }
      patch("/method") { status should equal(200) }
      delete("/method") { status should equal(200) }
      head("/method") { status should equal(200) }
      options("/method") { status should equal(200) }
    }

    it("renders text/plain body by default") {
      get("/text") {
        status should equal(200)
        body should equal("Hello, world!")
      }
    }

    it("returns any HTTP response status code") {
      get("/status/200") { status should equal(200) }
      get("/status/201") { status should equal(201) }
    }

    it("returns Set-Cookie headers") {
      get("/cookies") {
        status should equal(200)
        response.headers("Set-Cookie") should equal(Seq("foo=bar", "foo=bar;Domain=localhost"))
      }
      get("/cookies-2") {
        status should equal(200)
        response.headers("Set-Cookie") should equal(Seq("foo=bar", "foo=bar;Domain=localhost"))
      }
    }

    it("renders JSON body") {
      get("/sample.json") {
        status should equal(200)
        body should equal("""{"name":"Martin","id":123}""")
      }
    }

    it("accepts params") {
      post("/params.json?baz=123", Map("foo" -> "bar")) {
        status should equal(200)
        body should equal("""{"baz":"123","foo":"bar"}""")
      }
      get("/query-params.json", Map("foo" -> "bar")) {
        status should equal(200)
        body should equal("""{"foo":"bar"}""")
      }
      post("/form-params.json?baz=123", Map("foo" -> "bar")) {
        status should equal(200)
        body should equal("""{"foo":"bar"}""")
      }
    }

    it("accepts json body params") {
      post("/json-params", """{"name":"Alice","age":"18"}""".getBytes, Map("Content-Type" -> "application/json")) {
        status should equal(200)
        body should equal("""Map(age -> 18, name -> Alice)""")
      }
    }

    it("accepts path params") {
      get("/path-params/foo-bar") {
        status should equal(200)
        body should equal("foo-bar")
      }
    }

    it("accepts splat params") {
      get("/say/hello/to/world") {
        status should equal(200)
        body should equal("""Vector(hello, world)""")
      }
      get("/download/path/to/file.xml") {
        status should equal(200)
        body should equal("""Vector(path/to/file, xml)""")
      }
    }

    it("accepts captured path params") {
      get("/foo/bar") {
        status should equal(200)
        body should equal("""List(oo, ar)""")
      }
    }

    it("Future values") {
      get("/future") {
        status should equal(200)
        body should equal("ok after 100ms")
      }
      post("/future/params.json?baz=123", Map("foo" -> "bar")) {
        status should equal(200)
        body should equal("""{"baz":"123","foo":"bar"}""")
      }
    }

    it("returns 500 error") {
      get("/error") {
        status should equal(500)
      }
    }

  }

}

