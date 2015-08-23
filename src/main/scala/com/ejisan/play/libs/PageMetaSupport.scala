package ejisan.play.libs

import scala.concurrent.Future

import play.api.mvc.{ Controller, RequestHeader }
import play.api.i18n.Messages

trait PageMetaSupport { self: Controller =>
  /** Page Meta API */
  val pageMetaApi: PageMetaApi
  /** Retrieves the page meta implicitly from the request. */
  @inline implicit final def request2pagemeta(implicit request: RequestHeader, messages: Messages): PageMeta = pageMetaApi()
  @inline final def pageMeta(additional: (String, String)*)(implicit request: RequestHeader, messages: Messages): PageMeta = pageMetaApi(additional:_*)
}

trait PageMetaAsyncSupport { self: Controller =>
  /** Page Meta API */
  val pageMetaApi: AsyncPageMetaApi
  /** Retrieves the page meta from the request. */
  @inline final def pageMeta(implicit request: RequestHeader, messages: Messages): Future[PageMeta] = pageMetaApi()
  @inline final def pageMeta(additional: (String, String)*)(implicit request: RequestHeader, messages: Messages): Future[PageMeta] = pageMetaApi(additional:_*)
}
