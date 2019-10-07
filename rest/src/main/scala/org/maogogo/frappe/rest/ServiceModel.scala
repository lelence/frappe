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

import com.google.inject.name.Names
import net.codingwell.scalaguice.ScalaMapBinder
import org.maogogo.frappe.common.modules.AbstractServiceModule
import org.maogogo.frappe.rest.endpoints.HelloEndpoint
import org.maogogo.frappe.rest.httpd.{ Endpoints, HttpServer, RouteEndpoints }

class ServiceModel extends AbstractServiceModule {

  def endpointsMap(mBinder: ScalaMapBinder[String, Endpoints] ⇒ Unit): Unit = {
    val mBinder: ScalaMapBinder[String, Endpoints] = ScalaMapBinder
      .newMapBinder[String, Endpoints](binder(), Names.named("endpoints"))
    // mBinder.addBinding("hello").to[HelloEndpoint]
  }

  override def configure(): Unit = {

    endpointsMap { mBinder ⇒

      mBinder.addBinding("hello").to[HelloEndpoint]

    }

    bind[RouteEndpoints]
    bind[HttpServer]

  }

  //  def provideActorRefProxy(
  //    path: String)(implicit system: ActorSystem): ActorRef = {
  //    system.actorOf(
  //      ClusterSingletonProxy.props(
  //        singletonManagerPath = s"/user/actor_${path}",
  //        settings = ClusterSingletonProxySettings(system)),
  //      name = s"proxy_${path}")
  //  }

  //  @Provides
  //  @Singleton
  //  @Named("hello_actor")
  //  def provideHelloProxy(system: ActorSystem): ActorRef = {
  //    // provideProxy("hello_actor")
  //
  //    system.actorOf(
  //      ClusterSingletonProxy.props(
  //        singletonManagerPath = "/user/hello",
  //        settings = ClusterSingletonProxySettings(system) // .withRole("worker")
  //      ),
  //      name = "consumerProxy")
  //
  //  }

}

object ServiceModel extends ServiceModel
