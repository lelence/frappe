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

import scala.concurrent.ExecutionContext.Implicits._
import scala.concurrent.Future

trait Cacher[K, KS, V, VS] {

  val keySerializer: KeySerializer[K, KS]
  val valueSerializer: ValueSerializer[V, VS]
  val accessor: Accessor[KS, VS]

  implicit def toKeySerializer(k: K) = keySerializer.serialize(k)

  implicit def toValueSerializer(v: V) = valueSerializer.serialize(v)

  implicit def toValueDeserializer(vs: VS) = valueSerializer.deserialize(vs)

  implicit def toFutureValueDeserializer(fvs: Future[Option[VS]]) =
    fvs.map(_.map(toValueDeserializer))

  implicit def toSeqValueSerializer(v: Seq[V]) = v.map(toValueSerializer)

  def get(k: K): Future[Option[V]] = accessor.get(k)

  def getOrElse(k: K)(fallback: => Future[Option[V]]) = {

    for {
      vOption ← get(k)
      vMissed = vOption.isEmpty
      fbOption ← if (vMissed) fallback else Future(vOption)
      _ ← if (vMissed && fbOption.isDefined) put(k, fbOption.get)
      else Future.unit
    } yield {
      fbOption
    }

  }

  def put(k: K, v: V): Future[Boolean] = accessor.put(k, v)

  def push(k: K, v: Seq[V]): Future[Long] = accessor.push(k, v)

  def pull(k: K, start: Long = 0, end: Long = -1): Future[Seq[VS]] =
    accessor.pull(k, start, end)

}

trait KeySerializer[K, KS] {
  def serialize(k: K): KS
}

trait ValueSerializer[V, VS] {

  def serialize(v: V): VS

  def deserialize(vs: VS): V
}
