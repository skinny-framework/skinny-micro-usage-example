import javax.servlet._
import skinny.micro._

class Bootstrap extends LifeCycle {
  override def init(ctx: ServletContext) {
    example.App.mount(ctx)
  }
}
