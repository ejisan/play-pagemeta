package ejisan.play.libs

import scala.concurrent.Future
import scala.concurrent.ExecutionContext

import play.api.mvc.RequestHeader
import play.api.i18n.Messages
import play.api.routing.Router.Tags

/**
 * Utility and default PageMeta factory methods.
 *
 * @tparam P the parameter type which is PageMeta
 */
trait PageMetaFunctions[P <: PageMeta] {
  /** Returns page key from request tags */
  def key(implicit request: RequestHeader): String = (request.tags.getOrElse(Tags.RouteController, "").replace("controllers.", "") + "." + request.tags.getOrElse(Tags.RouteActionMethod, "")).toLowerCase
  /** Return default page meta */
  def default(additional: PageMeta.Metas)(implicit request: RequestHeader, messages: Messages): P
}

/**
 * Synchronized page meta API
 */
trait PageMetaApi extends PageMetaFunctions[PageMeta] {
  /** Get page meta */
  def get(additional: PageMeta.Metas)(implicit request: RequestHeader, messages: Messages): Option[PageMeta]
  def apply(additional: (String, String)*)(implicit request: RequestHeader, messages: Messages): PageMeta = get(additional.toMap) match {
    case Some(pm) => pm
    case None => default(additional.toMap)
  }
}

/**
 * Asynchronized page meta API
 */
trait AsyncPageMetaApi extends PageMetaFunctions[PageMeta] {
  /** The execution context to map future */
  val executionContext: ExecutionContext = play.api.libs.concurrent.Execution.defaultContext
  private implicit val ec: ExecutionContext = executionContext
  /** Get page meta */
  def get(additional: PageMeta.Metas)(implicit request: RequestHeader, messages: Messages): Future[Option[PageMeta]]
  def apply(additional: (String, String)*)(implicit request: RequestHeader, messages: Messages): Future[PageMeta] = get(additional.toMap) map {
    case Some(pm) => pm
    case None => default(additional.toMap)
  }
}
