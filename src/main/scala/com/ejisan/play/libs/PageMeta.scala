package com.ejisan.play.libs

import play.twirl.api.Html

class PageMeta(val key: String, val title: String, val metas: PageMeta.Metas) {
  def this(key: String, title: String) = this(key, title, Map.empty)
  def this(key: String, title: String, metas: PageMeta.Metas, additional: PageMeta.Metas) = this(key, title, metas ++ additional)
  override def toString = s"PageMeta($key, $title, $metas)"
  /**
   *  Convert to Html
   *
   *  @return Twirl Html object
   */
  def toHtml: Html = Html(s"<title>${title}</title>\n" + metas.map({ case (name, value) => s"""<meta name="${name}" content="${value}">""" }).mkString("\n"))
}

object PageMeta {
  type Metas = Map[String, String]
  /**
   *  PageMeta factory method
   *  @param key    Page key
   *  @param title  Title for title tag
   *  @param metas  A map object of meta name and content pair
   **/
  def apply(key: String, title: String, metas: Metas): PageMeta = new PageMeta(key, title, metas)
  /**
   *  PageMeta factory method
   *  @param key    Page key
   *  @param title  Title for title tag
   **/
  def apply(key: String, title: String): PageMeta = new PageMeta(key, title)
  /**
   *  PageMeta factory method
   *  @param key        Page key
   *  @param title      Title for title tag
   *  @param metas      A map object of meta name and content pair
   *  @param additional A map object of additional meta name and content pair
   **/
  def apply(key: String, title: String, metas: Metas, additional: Metas): PageMeta = new PageMeta(key, title, metas, additional)

  // Utilities

  /** Meta single keyword utility */
  def keywords(keyword: String): (String, String) = keywords(Seq(keyword))
  /** Meta multi keywords utility */
  def keywords(keywords: Seq[String]): (String, String) = ("keywords", keywords.mkString(", "))
  /** Meta description utility */
  def description(description: String): (String, String) = ("description", description)
  /** Meta author utility */
  def author(author: String): (String, String) = ("author", author)
}
