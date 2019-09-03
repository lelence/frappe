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
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import com.google.inject.Inject

class HttpServer @Inject()(route: RouteEndpoints)(implicit
                                                  system: ActorSystem,
                                                  mat: ActorMaterializer) {

  val logger = Logging(system, getClass)

  import system.dispatcher

  val port = system.settings.config.getInt("akka.http.port")

  val bind = Http().bindAndHandle(route(), interface = "0.0.0.0", port = 9000)

  bind.onComplete {
    case scala.util.Success(binding) ⇒
      logger.info(s"http server started! {}", binding.localAddress)
    case scala.util.Failure(ex) ⇒ logger.error("http server start failed!", ex)
  }

}
