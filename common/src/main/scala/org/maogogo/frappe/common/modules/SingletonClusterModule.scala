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

import akka.actor.ActorSystem
import akka.cluster.Cluster
import com.google.inject.{AbstractModule, Inject, Provider}
import net.codingwell.scalaguice.ScalaModule
import org.maogogo.frappe.common.cluster.SimpleClusterListener

sealed trait SingletonClusterModule {

  def apply(): AbstractModule with ScalaModule =
    new AbstractModule with ScalaModule {
      override def configure(): Unit = {
        bind[Cluster]
          .toProvider[SingletonClusterModule.ClusterProvider]
          .asEagerSingleton()
        bind[SimpleClusterListener]
      }
    }
}

object SingletonClusterModule extends SingletonClusterModule {

  class ClusterProvider @Inject()(system: ActorSystem)
      extends Provider[Cluster] {
    override def get(): Cluster = Cluster(system)
  }

}
