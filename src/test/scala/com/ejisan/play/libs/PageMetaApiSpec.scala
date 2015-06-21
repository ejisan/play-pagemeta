package com.ejisan.play.libs

import java.io.File
import scala.concurrent.{ Await, Future }
import scala.concurrent.duration._
import org.specs2.mutable._
import play.api.mvc.{ Headers, RequestHeader }
import play.api.i18n.{ Messages, Lang, DefaultMessagesApi, DefaultLangs }
import play.api.routing.Router.Tags
import play.api.{ PlayException, Mode, Environment, Configuration }

class PageMetaApiSpec extends Specification {

  case class DummyRequestHeader(
    requestMethod: String = "GET",
    requestUri: String = "/",
    headers: Headers = Headers()
  ) extends RequestHeader {
    def id = 1
    def tags = Map(Tags.RouteController -> "controllers.Test.index")
    def uri = requestUri
    def path = "/"
    def method = requestMethod
    def version = ""
    def queryString = Map()
    def remoteAddress = ""
    def secure = false
  }

  val request = DummyRequestHeader()

  val messagesApi = new DefaultMessagesApi(
    new Environment(new File("."), this.getClass.getClassLoader, Mode.Dev),
    Configuration.reference,
    new DefaultLangs(Configuration.reference ++ Configuration.from(Map("play.i18n.langs" -> Seq("en"))))
  ) {
    override protected def loadAllMessages = Map("en" -> Map(
      "test.index.title" -> "Title",
      "test.index.keywords" -> "keyword1, keyword2",
      "test.index.description" -> "description description")
    )
  }

  val messages = Messages(Lang("en"), messagesApi)

  val pageMetaApi: PageMetaApi = new I18nPageMetaApi

  val asyncPageMetaApi: AsyncPageMetaApi = new AsyncPageMetaApi {
    def default(additional: PageMeta.Metas)(implicit request: RequestHeader, messages: Messages): PageMeta = PageMeta(key, "Title", Map(PageMeta.keywords(Seq("keyword1", "keyword2")), PageMeta.description("description description")), additional)
    def get(additional: PageMeta.Metas)(implicit request: RequestHeader, messages: Messages): Future[Option[PageMeta]] = Future.successful(Some(default(additional)))
  }

  "Get PageMeta from I18nPageMetaApi" in {
    val pm = pageMetaApi()(request, messages)
    pm.key mustEqual "test.index"
    pm.title mustEqual "Title"
    pm.metas("description") mustEqual "description description"
    pm.metas("keywords") mustEqual "keyword1, keyword2"
    pm.toHtml.toString mustEqual "<title>Title</title>\n<meta name=\"keywords\" content=\"keyword1, keyword2\">\n<meta name=\"description\" content=\"description description\">"
  }

  "Get PageMeta from AsyncPageMetaApi" in {
    val pm = Await.result(asyncPageMetaApi(PageMeta.author("Mr. X"))(request, messages), 500 millisecond)
    println(pm)
    pm.key mustEqual "test.index"
    pm.title mustEqual "Title"
    pm.metas("description") mustEqual "description description"
    pm.metas("keywords") mustEqual "keyword1, keyword2"
    pm.toHtml.toString mustEqual "<title>Title</title>\n<meta name=\"keywords\" content=\"keyword1, keyword2\">\n<meta name=\"description\" content=\"description description\">\n<meta name=\"author\" content=\"Mr. X\">"
  }

}
