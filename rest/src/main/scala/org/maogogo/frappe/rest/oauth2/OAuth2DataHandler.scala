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

package org.maogogo.frappe.rest.oauth2

import org.maogogo.frappe.oauth2
import org.maogogo.frappe.oauth2.DataHandler
import org.maogogo.frappe.protobuf.oauth2.UserInfo

import scala.concurrent.Future

class OAuth2DataHandler extends DataHandler[UserInfo] {

  /**
    * Verify proper client with parameters for issue an access token.
    *
    * @param clientId     Client send this value which is registered by application.
    * @param clientSecret Client send this value which is registered by application.
    * @param grantType    Client send this value which is registered by application.
    * @return true if request is a regular client, false if request is a illegal client.
    */
  override def validateClient(clientId: String,
                              clientSecret: String,
                              grantType: String): Future[Boolean] = ???

  /**
    * Find userId with username and password these are used on your system.
    * If you don't support Resource Owner Password Credentials Grant then doesn't need implementing.
    *
    * @param username Client send this value which is used on your system.
    * @param password Client send this value which is used on your system.
    * @return Including UserId to Option if could find the user, None if couldn't find.
    */
  override def findUser(username: String,
                        password: String): Future[Option[UserInfo]] = ???

  /**
    * Creates a new access token by authorized information.
    *
    * @param authInfo This value is already authorized by system.
    * @return Access token returns to client.
    */
  override def createAccessToken(
    authInfo: oauth2.AuthInfo[UserInfo]
  ): Future[oauth2.AccessToken] = ???

  /**
    * Returns stored access token by authorized information.
    *
    * If want to create new access token then have to return None
    *
    * @param authInfo This value is already authorized by system.
    * @return Access token returns to client.
    */
  override def getStoredAccessToken(
    authInfo: oauth2.AuthInfo[UserInfo]
  ): Future[Option[oauth2.AccessToken]] = ???

  /**
    * Creates a new access token by refreshToken.
    *
    * @param authInfo This value is already authorized by system.
    * @return Access token returns to client.
    */
  override def refreshAccessToken(
    authInfo: oauth2.AuthInfo[UserInfo],
    refreshToken: String
  ): Future[oauth2.AccessToken] = ???

  /**
    * Find authorized information by authorization code.
    *
    * If you don't support Authorization Code Grant then doesn't need implementing.
    *
    * @param code Client send authorization code which is registered by system.
    * @return Return authorized information that matched the code.
    */
  override def findAuthInfoByCode(
    code: String
  ): Future[Option[oauth2.AuthInfo[UserInfo]]] = ???

  /**
    * Find authorized information by refresh token.
    *
    * If you don't support Refresh Token Grant then doesn't need implementing.
    *
    * @param refreshToken Client send refresh token which is created by system.
    * @return Return authorized information that matched the refresh token.
    */
  override def findAuthInfoByRefreshToken(
    refreshToken: String
  ): Future[Option[oauth2.AuthInfo[UserInfo]]] = ???

  /**
    * Find userId by clientId and clientSecret.
    *
    * If you don't support Client Credentials Grant then doesn't need implementing.
    *
    * @param clientId     Client send this value which is registered by application.
    * @param clientSecret Client send this value which is registered by application.
    * @return Return user that matched both values.
    */
  override def findClientUser(clientId: String,
                              clientSecret: String,
                              scope: Option[String]): Future[Option[UserInfo]] =
    ???

  /**
    * Find AccessToken object by access token code.
    *
    * @param token Client send access token which is created by system.
    * @return Return access token that matched the token.
    */
  override def findAccessToken(
    token: String
  ): Future[Option[oauth2.AccessToken]] = ???

  /**
    * Find authorized information by access token.
    *
    * @param accessToken This value is AccessToken.
    * @return Return authorized information if the parameter is available.
    */
  override def findAuthInfoByAccessToken(
    accessToken: oauth2.AccessToken
  ): Future[Option[oauth2.AuthInfo[UserInfo]]] = ???
}
