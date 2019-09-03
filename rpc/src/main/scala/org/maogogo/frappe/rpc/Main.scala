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

import com.typesafe.config.ConfigFactory
import org.maogogo.frappe.common.{ AppSetting, GuiceAkka }

object Main extends App {

  //  import org.maogogo.frappe.co

  new scopt.OptionParser[AppSetting]("scopt") {
    head("latte", "1.0")

    opt[Int]('p', "port")
      .action((x, c) ⇒ {
        c.copy(port = x)
      })
      .text("tcp port")

    opt[Seq[String]]('s', "seeds")
      .action((x, c) ⇒ {
        c.copy(seeds = x)
      })
      .text("cluster seeds")

  }.parse(args, AppSetting()) match {
    case Some(settings) ⇒
      //      val seeds = settings.seeds
      //        .map(s ⇒ s""""akka.tcp://${SysAndConfigModule.SystemName}@${s.trim}"""")
      //        .mkString(",")

      //      val config = ConfigFactory
      //        .parseString(s"""
      //             |akka.remote.netty.tcp.port=${settings.port}
      //             |akka.remote.netty.tcp.hostname="127.0.0.1"
      //             |akka.cluster.seed-nodes=[${seeds}]
      //        """.stripMargin)
      //        .withFallback(ConfigFactory.load())

      val injector =
        GuiceAkka().cluster(ConfigFactory.load(), ServiceModel).build()
      //
      //      import net.codingwell.scalaguice.InjectorExtensions._
      //
      //      injector.instance[ActorRef](Names.named("hello_actor"))

      println(logo)

    case _ ⇒
  }

  lazy val logo =
    """
      |    ____
      |   / __ \____  _____
      |  / /_/ / __ \/ ___/
      | / _, _/ /_/ / /__
      |/_/ |_/ .___/\___/
      |     /_/
    """.stripMargin
}
