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

package org.maogogo.frappe.common.modules

import akka.actor.{ ActorRef, ActorSystem }
import akka.cluster.Cluster
import akka.cluster.singleton.{
  ClusterSingletonProxy,
  ClusterSingletonProxySettings
}
import com.google.inject.{ AbstractModule, Inject, Provider }
import net.codingwell.scalaguice.ScalaModule
import org.maogogo.frappe.common.cluster.SimpleClusterListener

trait SingletonClusterProxyModule {

  def apply(): AbstractModule with ScalaModule =
    new AbstractModule with ScalaModule {
      override def configure(): Unit = {
        bind[Map[String, ActorRef]]
          .annotatedWithName("ActorRefMap")
          .toProvider[SingletonClusterProxyModule.ClusterProxyProvider]
          .asEagerSingleton()
        //        bind[SimpleClusterListener]
      }
    }

}

object SingletonClusterProxyModule extends SingletonClusterProxyModule {

  class ClusterProxyProvider @Inject() (system: ActorSystem)
    extends Provider[Map[String, ActorRef]] {

    def provideActorRefProxy(path: String) = {
      system.actorOf(
        ClusterSingletonProxy.props(
          singletonManagerPath = s"/user/actor_${path}",
          settings = ClusterSingletonProxySettings(system) // .withRole("worker")
        ),
        name = s"proxy_${path}")
    }

    override def get(): Map[String, ActorRef] = {

      val config = system.settings.config

      config.hasPath("akka.cluster.proxies") match {
        case true ⇒
          import scala.collection.JavaConverters._
          config.getStringList("akka.cluster.proxies").asScala.map { x ⇒
            x → provideActorRefProxy(x)
          } toMap
        case _ ⇒ Map.empty
      }

    }

  }

}
