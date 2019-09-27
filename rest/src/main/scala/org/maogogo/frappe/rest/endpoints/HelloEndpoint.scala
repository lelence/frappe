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

package org.maogogo.frappe.rest.endpoints

import akka.actor.ActorRef
import akka.http.scaladsl.server.Route
import com.google.inject.Inject
import com.google.inject.name.Named
import org.maogogo.frappe.rest.httpd.Endpoints
import akka.http.scaladsl.server.Directives._
import org.maogogo.frappe.protobuf.data.Hello
import akka.pattern.ask
import akka.util.Timeout
import org.maogogo.frappe.common.modules.SingletonProxyLookup

import scala.concurrent.duration._

class HelloEndpoint @Inject() (lookup: SingletonProxyLookup)
  extends Endpoints {

  implicit val timeout = Timeout(5 seconds)

  override def apply(): Route = {

    val actor = lookup.get("hello")

    path("list") {
      get {

        val f = (actor ? Hello("Toan")).mapTo[String]

        complete(f)
      }
    }
  }
}
