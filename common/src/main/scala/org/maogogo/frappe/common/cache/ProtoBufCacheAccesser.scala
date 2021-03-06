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

package org.maogogo.frappe.common.cache

import akka.util.ByteString
import org.maogogo.frappe.common.ProtoBuf

class ProtoBufCacheAccesser[T <: ProtoBuf[T]](
  implicit
  val accessor: ReidsByteStringAccessor,
  c: scalapb.GeneratedMessageCompanion[T]) extends Cacher[String, String, T, ByteString] {

  override val keySerializer: KeySerializer[String, String] =
    new StringSerializer

  override val valueSerializer: ValueSerializer[T, ByteString] =
    new ProtoBufByteStringDeserializer[T]

}
