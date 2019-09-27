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

package org.maogogo.frappe.common

import akka.actor.ActorRef
import com.google.inject.name.Names
import com.google.inject._
import com.typesafe.config.{ Config, ConfigFactory }
import org.maogogo.frappe.common.modules.{
  SingletonClusterModule,
  SysAndConfigModule
}

final object GuiceAkka {

  def apply(): GuiceAkka = new GuiceAkka(ConfigFactory.load(), Seq.empty)

  implicit class ActorInjector(injector: Injector) {
    def getActorRef(name: String): ActorRef = {
      injector.getInstance(Key.get(classOf[ActorRef], Names.named(name)))
    }
  }

}

private[common] class GuiceAkka(config: Config, modules: Seq[Module]) {

  def system(c: Config): GuiceAkka = new GuiceAkka(c, modules)

  def system(m: Module*): GuiceAkka = new GuiceAkka(config, modules ++ m)

  def system(cfg: Config, m: Module*): GuiceAkka =
    new GuiceAkka(cfg, modules ++ m)

  def cluster(): GuiceAkka =
    new GuiceAkka(config, modules :+ SingletonClusterModule())

  def cluster(cfg: Config): GuiceAkka =
    new GuiceAkka(cfg, modules :+ SingletonClusterModule())

  def cluster(m: Module*): GuiceAkka =
    new GuiceAkka(config, modules ++ m :+ SingletonClusterModule())

  def cluster(cfg: Config, m: Module*): GuiceAkka =
    new GuiceAkka(cfg, modules ++ m :+ SingletonClusterModule())

  def build(): Injector =
    Guice.createInjector(
      Stage.PRODUCTION,
      modules :+ SysAndConfigModule(config): _*)

}
