package com.hm.routes

import com.hm.connector.MysqlClient
import spray.http.DateTime
import spray.http.HttpHeaders.Date
import spray.http.MediaTypes.`text/html`
import spray.json.JsString
import spray.routing.HttpService
import spray.json._

/**
  * Created by hari on 17/2/17.
  */
trait Routes extends HttpService
  {



 val route =
   path("Insert")
   {
      //testing purpose
     //insertData("get","google","jdui","acx","10.22.20.56","chrome")
     complete(" values inserted")
    }~path("") {

      get {
        respondWithMediaType(`text/html`) { // XML is marshalled to `text/xml` by default, so we simply override here
          complete {
            <html>
              <body>
                <h1>welcome to WebAnalytics :)</h1>
              </body>
            </html>
          }
        }
      }
    }





    def insertData(method:String,domain_name:String,path:String,cookie:String,ip:String,user_agent:String)={
      val rs=MysqlClient.executeQuery("insert into request_header(method,domain_name,path,cookie,ip,user_agent,time) values ('"+method+"','"+domain_name+"','"+path+"','"+cookie+"','"+ip+"','"+user_agent+"',NOW())")
      rs
    }






}
