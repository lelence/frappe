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

package org.maogogo.frappe.rpc

import akka.actor.{ ActorRef, ActorSystem, Props }
import com.google.inject.name.Named
import com.google.inject.{ Inject, Provides, Singleton }
import org.maogogo.frappe.common.modules.{ AbstractServiceModule, DatabaseModule }
import org.maogogo.frappe.rpc.actors.HelloActor
import org.maogogo.frappe.rpc.services.HelloService

class ServiceModel extends AbstractServiceModule {

  override def configure(): Unit = {

    bind[HelloService]

    install(DatabaseModule())
  }

  @Provides
  @Singleton
  @Named(HelloActor.ActorName)
  def provideHelloActor(
    @Inject() service: HelloService)(implicit system: ActorSystem): ActorRef =
    provideActorRef(
      Props(classOf[HelloActor], service),
      s"actor_${HelloActor.ActorName}")

}

object ServiceModel extends ServiceModel
