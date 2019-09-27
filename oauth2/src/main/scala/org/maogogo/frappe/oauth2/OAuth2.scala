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

package org.maogogo.frappe.oauth2

import akka.http.scaladsl.server.directives.Credentials

trait OAuth2 {

  def basicAuthAuthenticator[U](
    credentials: Credentials)(implicit dataHandler: DataHandler[U]): Option[U] =
    credentials match {
      case p @ Credentials.Provided(_) =>

        val dd = dataHandler.findUser("", "")

        //        dataHandler.findUser()

        //        println("====>>>>>>>>>>>" + p.identifier)
        //
        //        p.verify("112", s ⇒ {
        //
        //          println("ssss =>>>" + s)
        //          s
        //        })

        //        println("===>>>" + p.verify("111"))

        //        validBasicAuthCredentials.find(
        //          user => user.username == p.identifier && p.verify(user.password)
        //        )

        None
      case _ => None
    }

  def oAuthAuthenticator[U](
    credentials: Credentials)(implicit dataHandler: DataHandler[U]): Option[U] =
    credentials match {
      case p @ Credentials.Provided(_) =>
        // loggedInUsers.find(user => p.verify(user.oAuthToken.access_token))

        None
      case _ => None
    }

}
