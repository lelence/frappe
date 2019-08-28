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

import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule
import org.maogogo.frappe.rest.httpd.{ HttpServer, RouteEndpoints }

class ServiceModel extends AbstractModule with ScalaModule {

  override def configure(): Unit = {

    //    val mBinder = ScalaMapBinder.newMapBinder[String, Endpoints](binder(), Names.named("endpoints"))
    //    mBinder.addBinding("hello").to[HelloEndpoint]

    bind[RouteEndpoints]
    bind[HttpServer]
    //
    //    install(TimeoutModule())

  }

  //  @Provides
  //  @Singleton
  //  @Named("hello_actor")
  //  def provideHelloProxy(implicit system: ActorSystem): ActorRef = {
  //    provideProxy("hello_actor")
  //  }

}

object ServiceModel extends ServiceModel
