package ejisan.play.libs

import play.twirl.api.Html
import play.api.libs.json.{ Format, Json, JsValue }

case class PageMeta(key: String, title: String, metas: PageMeta.Metas = Map()) {
  import PageMeta.jsonFormat

  def this(key: String, title: String) = this(key, title, Map.empty)
  def this(key: String, title: String, metas: PageMeta.Metas, additional: PageMeta.Metas) = this(key, title, metas ++ additional)
  override def toString = s"PageMeta($key, $title, $metas)"
  /**
   *  Convert to Html
   *
   *  @return Twirl Html object
   */
  def toHtml: Html = Html(s"<title>${title}</title>\n" + metas.map({ case (name, value) => s"""<meta name="${name}" content="${value}">""" }).mkString("\n"))

  def toJson: JsValue = Json.toJson(this)
  def toStringedJson: String = Json.stringify(Json.toJson(this))
}

object PageMeta {
  type Metas = Map[String, String]

  // Utilities

  /** Meta single keyword utility */
  def keywords(keyword: String): (String, String) = keywords(Seq(keyword))
  /** Meta multi keywords utility */
  def keywords(keywords: Seq[String]): (String, String) = ("keywords", keywords.mkString(", "))
  /** Meta description utility */
  def description(description: String): (String, String) = ("description", description)
  /** Meta author utility */
  def author(author: String): (String, String) = ("author", author)

  implicit val jsonFormat: Format[PageMeta] = Json.format[PageMeta]
}
