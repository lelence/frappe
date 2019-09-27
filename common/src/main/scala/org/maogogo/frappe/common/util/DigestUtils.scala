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

package org.maogogo.frappe.common.util

import java.security.MessageDigest

final object DigestUtils {

  /**
   * Message Digest algorithm 5，信息摘要算法
   */
  private lazy val MD5 = "MD5"

  /**
   * Secure Hash Algorithm，安全散列算法
   */
  private lazy val SHA256 = "SHA256"
  private lazy val SHA512 = "SHA512"

  private lazy val instance = (name: String) ⇒ MessageDigest.getInstance(name)

  private implicit def bytes2String(bytes: Array[Byte]): String =
    bytes map (0xFF & _) map { "%02x".format(_) } mkString

  //  def md5(bytes: Array[Byte]): Array[Byte] = instance(MD5) digest (bytes)
  //
  //  def md5(str: String): Array[Byte] = md5(str.getBytes)
  //
  //  def md5(str: String): String = md5(str.getBytes())

  //  def sha256(bytes: Array[Byte]): Array[Byte] = instance(SHA256) digest (bytes)
  //
  //  def sha256(str: String): Array[Byte] = sha256(str.getBytes)
  //
  //  def sha256(str: String): String = sha256(str.getBytes())
  //
  //  def sha512(bytes: Array[Byte]): Array[Byte] = instance(SHA512) digest (bytes)
  //
  //  def sha512(str: String): Array[Byte] = sha512(str.getBytes)
  //
  //  def sha512(str: String): String = sha512(str.getBytes())

}
