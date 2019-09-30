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

import java.util

import akka.http.scaladsl.Http
import com.typesafe.config.{ ConfigFactory, ConfigList }
import org.maogogo.frappe.common.modules.SysAndConfigModule
import org.maogogo.frappe.common.{ AppSetting, GuiceAkka }
import org.maogogo.frappe.rest.httpd.HttpServer

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
      val appCfg = ConfigFactory.load()

      val appPort = appCfg.hasPath("akka.remote.netty.tcp.port") match {
        case true =>
          val port = appCfg.getInt("akka.remote.netty.tcp.port")

          port match {
            case 0 => settings.port
            case _ => port
          }
        case _ => settings.port
      }

      val seeds = settings.seeds
        .map { s ⇒
          val seed = if (s.startsWith(":")) s"0.0.0.0${s.trim}" else s.trim
          s""""akka.tcp://${SysAndConfigModule.AkkaSystemName}@${seed}""""
        }
        .mkString(",")

      val config = ConfigFactory
        .parseString(s"""
           |akka.remote.netty.tcp.port=${appPort}
           |akka.remote.netty.tcp.hostname="0.0.0.0"
           |akka.cluster.seed-nodes=[${seeds}]
            """.stripMargin)
        .withFallback(appCfg)

      import scala.collection.JavaConverters._

      //      config.getStringList("akka.cluster.proxies")

      //      println(dd)

      val injector =
        GuiceAkka().cluster(config, ServiceModel).build()
      import net.codingwell.scalaguice.InjectorExtensions._

      injector.instance[HttpServer]

      println(logo)

    case _ ⇒
  }

  // val bindingFuture = Http().bindAndHandle(null, "localhost", 8080)
  //
  //  println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
  //  bindingFuture
  //    .flatMap(_.unbind()) // trigger unbinding from the port
  //    .onComplete(_ => system.terminate())

  lazy val logo =
    """
      |    ____            __
      |   / __ \___  _____/ /_
      |  / /_/ / _ \/ ___/ __/
      | / _, _/  __(__  ) /_
      |/_/ |_|\___/____/\__/ 
      |""".stripMargin
}
