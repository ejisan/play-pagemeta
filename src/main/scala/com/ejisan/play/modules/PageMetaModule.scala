package com.ejisan.play.modules

import com.ejisan.play.libs.{ PageMetaApi, I18nPageMetaApi }
import play.api.{ Configuration, Environment }
import play.api.inject.Module

class I18nPageMetaModule extends Module {
  def bindings(environment: Environment, configuration: Configuration) = Seq(
    bind[PageMetaApi].to[I18nPageMetaApi]
  )
}
