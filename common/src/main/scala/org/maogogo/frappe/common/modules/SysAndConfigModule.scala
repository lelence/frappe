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
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.google.inject.{ AbstractModule, Inject, Provider }
import com.typesafe.config.Config
import net.codingwell.scalaguice.ScalaModule
import scala.concurrent.duration._

sealed trait SysAndConfigModule {

  def apply(config: Config): AbstractModule with ScalaModule =
    new AbstractModule with ScalaModule {
      override def configure(): Unit = {

        import org.maogogo.frappe.common.modules.SysAndConfigModule._

        val system = ActorSystem(AkkaSystemName, config)
        bind[ActorSystem].toInstance(system)
        bind[Config].toInstance(config)

        bind[ActorMaterializer]
          .toProvider[ActorMaterializerProvider]
          .asEagerSingleton()

        val timeout = config.hasPath("akka.timeout") match {
          case true ⇒ config.getInt("akka.timeout")
          case _ ⇒ 5
        }

        bind[Timeout].toInstance(new Timeout(timeout seconds))
      }
    }
}

object SysAndConfigModule extends SysAndConfigModule {

  lazy val AkkaSystemName = "MyClusterSystem"

  class ActorMaterializerProvider @Inject() (system: ActorSystem)
    extends Provider[ActorMaterializer] {
    override def get(): ActorMaterializer = ActorMaterializer()(system)
  }

}
