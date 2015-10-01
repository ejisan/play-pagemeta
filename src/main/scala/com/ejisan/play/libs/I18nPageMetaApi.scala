package ejisan.play.libs

import play.api.mvc.RequestHeader
import play.api.i18n.Messages

class I18nPageMetaApi extends PageMetaApi {
  def default(additional: PageMeta.Metas)(implicit request: RequestHeader, messages: Messages): PageMeta = PageMeta(key, key, additional)
  def get(additional: PageMeta.Metas)(implicit request: RequestHeader, messages: Messages): Option[PageMeta] = Some {
    val k = key
    val metas = Map(
      "keywords" -> messages.translate(k + ".keywords", Nil),
      "description" -> messages.translate(k + ".description", Nil),
      "author" -> messages.translate(k + ".author", Nil)
    ).filter(!_._2.isEmpty).map({case (k, v) => (k, v.get)})

    PageMeta(k, Messages(k + ".title"), metas ++ additional)
  }
}
