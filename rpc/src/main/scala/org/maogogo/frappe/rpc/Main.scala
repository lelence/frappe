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

import akka.actor.ActorRef
import com.google.inject.name.Names
import com.typesafe.config.ConfigFactory
import org.maogogo.frappe.common.modules.SysAndConfigModule
import org.maogogo.frappe.common.{ AppSetting, GuiceAkka }
import org.maogogo.frappe.protobuf.data.Hello
import org.maogogo.frappe.rpc.actors.HelloActor

object Main extends App {

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
      val appConfig = ConfigFactory.load()

      val appPort = {
        if (settings.port == 0) {
          if (appConfig.hasPath("akka.remote.netty.tcp.port"))
            appConfig.getInt("akka.remote.netty.tcp.port")
          else 2552
        } else settings.port
      }

      val seeds = (settings.seeds :+ localSeed(appPort)).map { s =>
        val seed = if (s.startsWith(":")) s"0.0.0.0${s.trim}" else s.trim
        s""""akka.tcp://${SysAndConfigModule.AkkaSystemName}@${s.trim}""""
      } mkString (",")

      val config = ConfigFactory
        .parseString(s"""
             |akka.remote.netty.tcp.port=${appPort}
             |akka.remote.netty.tcp.hostname="0.0.0.0"
             |akka.cluster.seed-nodes=[${seeds}]
              """.stripMargin)
        .withFallback(appConfig)

      GuiceAkka().cluster(config, ServiceModel).build()

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

  lazy val localSeed = (port: Int) => s"0.0.0.0:${port}"
}
