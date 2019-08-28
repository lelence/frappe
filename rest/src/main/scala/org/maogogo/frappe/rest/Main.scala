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

package org.maogogo.frappe.rest

import akka.http.scaladsl.Http
import org.maogogo.frappe.common.GuiceAkka
import org.maogogo.frappe.rest.httpd.HttpServer

object Main extends App {

  val injector = GuiceAkka().system(ServiceModel).build()
  //
  import net.codingwell.scalaguice.InjectorExtensions._
  //
  injector.instance[HttpServer]

  // val bindingFuture = Http().bindAndHandle(null, "localhost", 8080)
  //
  //  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  //  bindingFuture
  //    .flatMap(_.unbind()) // trigger unbinding from the port
  //    .onComplete(_ => system.terminate())
}
