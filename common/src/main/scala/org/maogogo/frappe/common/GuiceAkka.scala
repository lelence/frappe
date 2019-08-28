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

import com.google.inject.{ Guice, Injector, Module }
import com.typesafe.config.{ Config, ConfigFactory }
import org.maogogo.frappe.common.modules.SysAndConfigModule

final object GuiceAkka {

  def apply(): GuiceAkka = new GuiceAkka(ConfigFactory.load(), Seq.empty)

}

private[common] class GuiceAkka(config: Config, modules: Seq[Module]) {

  def system(c: Config): GuiceAkka = new GuiceAkka(c, modules)

  def system(m: Module*): GuiceAkka = new GuiceAkka(config, modules ++ m)

  def system(cfg: Config, m: Module*): GuiceAkka =
    new GuiceAkka(cfg, modules ++ m)

  //  def cluster(): GuiceAkka = new GuiceAkka(cfg, modules :+ ClusterSystemModule())
  //
  //  def cluster(cfg: Config) = new GuiceAkka(cfg, ms :+ ClusterSystemModule())
  //
  //  def cluster(m: Module*) = new GuiceAkka(cfg, ms ++ m :+ ClusterSystemModule())
  //
  //  def cluster(cfg: Config, m: Module*) = new GuiceAkka(cfg, ms ++ m :+ ClusterSystemModule())

  def build(): Injector =
    Guice.createInjector(modules :+ SysAndConfigModule(config): _*)

}
