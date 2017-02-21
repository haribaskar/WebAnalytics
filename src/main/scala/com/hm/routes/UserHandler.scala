package com.hm.routes

import com.hm.connector.MysqlClient
import spray.json.{JsArray, JsNumber, JsObject, JsString}
import spray.routing.HttpService

import collection.JavaConversions._
/**
  * Created by hari on 21/2/17.
  */
trait UserHandler extends  HttpService{


  //random value generator
  //
  //r.nextInt
  def listOfUsersApi={

    val r=listUsers
    complete(JsArray(r.map(i=>JsObject("id"->JsNumber(i))).toVector).prettyPrint)
  }
  def listUsers:Array[String]={

    val rs = MysqlClient.getResultSet("select cookie from request_header group by cookie")
    val result=new collection.mutable.ArrayBuffer[String]
    while (rs.next()) {
      result.add(rs.getString("cookie"))
    }
     result.toArray
  }

  def insertData(method:String,domain_name:String,path:String,persistent_cookie:String,session_cookie:String,ip:String,user_agent:String)={
    val rs=MysqlClient.executeQuery("insert into request_header(method,domain_name,path,persistent_cookie,session_cookie,ip,user_agent,time) values ('"+method+"','"+domain_name+"','"+path+"','"+persistent_cookie+"' '"+session_cookie+"','"+ip+"','"+user_agent+"',NOW())")

    rs
  }

}
