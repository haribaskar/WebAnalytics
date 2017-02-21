package com.hm.routes

import com.hm.connector.MysqlClient
import spray.routing.HttpService
import spray.json.{JsArray, JsNumber, JsObject, JsString,_}

import collection.JavaConversions._
import scala.collection.mutable.ArrayBuffer

/**
  * Created by rahul on 21/2/17.
  */
trait UserGroup extends HttpService{

  def count = post {
    entity(as[String])
    {
      body =>{
        val json = body.parseJson.asJsObject
        val domain = json.getFields("domain").head.asInstanceOf[JsString].value
        val path = json.getFields("path").head.asInstanceOf[JsString].value
        val r=countOfUser(domain,path)
        complete(r.prettyPrint)

      }
    }
  }




  def countOfUser(domain:String, path:String):JsArray={
    val query = "select cookie,count(*) as count_user from request_header where domain_name = '"+domain+"' and path= '"+path+"' group by cookie"
    println(query)
    val rs = MysqlClient.getResultSet(query)
    val result=new collection.mutable.ArrayBuffer[(String,Int)]

    while(rs.next()) {

      result.add((rs.getString("cookie"),rs.getInt("count_user")))

    }



    JsArray(
      result.toVector.map(i=>JsObject("user"->JsString(i._1),"count_access"->JsNumber(i._2)))
    )
  }

}
