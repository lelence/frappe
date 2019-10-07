/*
 * Copyright 2019 Maogogo Workshop
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.maogogo.frappe.rest.httpd

import akka.actor.ActorSystem
import akka.event.Logging
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.util.Timeout
import com.google.inject.Inject
import com.google.inject.name.Named
import org.maogogo.frappe.oauth2.OAuth2
import org.maogogo.frappe.protobuf.oauth2.UserInfo
import org.maogogo.frappe.rest.oauth2.OAuth2DataHandler

import scala.concurrent.duration._

class RouteEndpoints @Inject() (
  implicit
  system: ActorSystem,
  @Named("endpoints") endpointMap: Map[String, Endpoints]) extends Json4sSupport
  with OAuth2 {

  implicit val timeout = Timeout(5 seconds)

  private val logger = Logging(system, getClass)

  //  def actorService[T: ClassTag](actor: ActorRef, command: Any): Route = {
  //
  //    val f = (actor ? command).mapTo[T].map(WrappedResponse(200, _))
  //
  //    complete(f)
  //  }

  implicit val dataHandler = new OAuth2DataHandler()

  def apply(): Route = {

    endpointMap.foldLeft(rootEndpoints) { (root, endpoint) ⇒
      root ~ {
        val (path, route) = endpoint
        pathPrefix("v1" / path) {
          route()
        }
      }
    }

  }

  private val rootEndpoints: Route = {

    // https://www.jannikarndt.de/blog/2018/10/oauth2-akka-http/
    // handleRejections()

    // https://gitee.com/zifangsky/OAuth2.0Demo/blob/master/rbac_db.sql
    // https://juejin.im/post/5b8cb6586fb9a01a133685c9
    pathEndOrSingleSlash {
      get {
        complete("hello world")
      }
    } ~ path("auth") {
      authenticateBasic(realm = "auth", basicAuthAuthenticator[UserInfo]) {
        user =>
          post {
            println(user)
            //          val loggedInUser = LoggedInUser(user)
            //          loggedInUsers.append(loggedInUser)
            complete("hello")
          }
      }
    }
    //    ~ path("api") {
    //      authenticateOAuth2(realm = "api", oAuthAuthenticator) { validToken =>
    //        complete(s"It worked! user = $validToken")
    //      }
    //    }
  }

}

//case class WrappedResponse[T](code: Int, data: T)
