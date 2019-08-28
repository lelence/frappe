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
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.server.{ RejectionHandler, Route }
import com.google.inject.Inject
import akka.http.scaladsl.server.Directives._

class RouteEndpoints @Inject() (implicit system: ActorSystem) {

  private val logger = Logging(system, getClass)

  //  val dd = RejectionHandler.newBuilder().handle {
  ////    case
  //  }

  def wrappedResponse(response: HttpResponse): HttpResponse = {
    println(response.entity)
    response.copy(entity = "hello")
  }

  def apply(): Route = {

    mapResponse(wrappedResponse)(
      // handleRejections()
      pathEndOrSingleSlash {
        get {
          complete("ddd => heeheheh")
        }
      })
  }

}
