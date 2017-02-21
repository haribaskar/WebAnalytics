package com.hm.routes

import com.mysql.jdbc.interceptors.SessionAssociationInterceptor
import spray.http.{AllOrigins, DateTime, HttpCookie}
import spray.http.MediaTypes.`text/html`
import spray.routing.HttpService
import spray.http.AllOrigins
import spray.http.HttpHeaders.{`Access-Control-Allow-Headers`, `Access-Control-Allow-Origin`}

/**
  * Created by hari on 17/2/17.
  */
trait Routes extends HttpService with CookieHandler with UserHandler {


  def route = {
    respondWithHeaders(List(
      `Access-Control-Allow-Origin`(AllOrigins),
      `Access-Control-Allow-Headers`("Content-Type", "Access-Control-Allow-Headers", "Authorization", "X-Requested-With")
    )) {
      path("test") {
        respondWithHeaders(List(
          `Access-Control-Allow-Origin`(AllOrigins),
          `Access-Control-Allow-Headers`("Content-Type", "Access-Control-Allow-Headers", "Authorization", "X-Requested-With")
        )) {
          headerValueByName("User-Agent") { userAgent =>
            headerValueByName("referer") { referer =>
              optionalCookie("ckName") {
                case Some(cookie) => complete("referer : " + referer + " ua : " + userAgent + " cookie : " + cookie.content)
                case None => complete("referer : " + referer + " ua : " + userAgent)
              }
            }
          }
        }


      }


    }
  }
}