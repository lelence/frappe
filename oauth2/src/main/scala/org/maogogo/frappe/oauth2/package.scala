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

package org.maogogo.frappe

import java.util.Date

package object oauth2 {

  final case class AuthInfo[U](user: U,
                               clientId: String,
                               scope: Option[String],
                               redirectUri: Option[String])

  final case class AccessToken(token: String,
                               refreshToken: Option[String],
                               scope: Option[String],
                               expiresIn: Option[Long],
                               createdAt: Date)

  final case class ClientCredential(clientId: String, clientSecret: String)

  final case class GrantResult(tokenType: String,
                               accessToken: String,
                               expiresIn: Option[Long],
                               refreshToken: Option[String],
                               scope: Option[String])

}
