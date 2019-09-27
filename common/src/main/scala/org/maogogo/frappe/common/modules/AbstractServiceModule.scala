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

import akka.actor.{ ActorRef, ActorSystem, PoisonPill, Props }
import akka.cluster.singleton.{
  ClusterSingletonManager,
  ClusterSingletonManagerSettings
}
import com.google.inject.AbstractModule
import net.codingwell.scalaguice.ScalaModule

trait AbstractServiceModule extends AbstractModule with ScalaModule {

  def provideActorRef(
    props: Props,
    name: String)(implicit system: ActorSystem): ActorRef = {
    system.actorOf(
      ClusterSingletonManager.props(
        singletonProps = props,
        terminationMessage = PoisonPill,
        settings = ClusterSingletonManagerSettings(system) // .withRole("worker")
      ),
      name = name)
  }

}
