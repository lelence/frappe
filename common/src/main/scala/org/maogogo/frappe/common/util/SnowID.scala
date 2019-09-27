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

class SnowID(datacenterId: Long = 1L, workerId: Long = 1L) {

  // 2019/4/19 20:51:9
  private[this] lazy val startTimestamp = 1555678269797L

  private[this] lazy val sequenceBits = 12L
  private[this] lazy val workerIdBits = 5L
  private[this] lazy val datacenterIdBits = 5L

  private[this] lazy val maxWorkerId = ~(-1L << workerIdBits)
  private[this] lazy val maxDatacenterId = ~(-1L << datacenterIdBits)
  private[this] lazy val maxSequence = ~(-1L << sequenceBits)

  private[this] lazy val workerIdShift = sequenceBits
  private[this] lazy val datacenterIdShift = sequenceBits + workerIdBits
  private[this] lazy val timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits

  private[this] var sequence = 0L
  private[this] var lastTimestamp = -1L

  if (workerId > maxWorkerId || workerId < 0)
    throw new IllegalArgumentException(
      "worker Id can't be greater than %d or less than 0".format(maxWorkerId))

  if (datacenterId > maxDatacenterId || datacenterId < 0)
    throw new IllegalArgumentException(
      "datacenter Id can't be greater than %d or less than 0"
        .format(maxDatacenterId))

  def next(): Long = synchronized {

    var timestamp = timeGen()

    if (timestamp < lastTimestamp) {
      throw new IllegalStateException(
        "Clock moved backwards. Refusing to generate id for %d milliseconds"
          .format(lastTimestamp - timestamp))
    }

    if (lastTimestamp == timestamp) {
      sequence = (sequence + 1) & maxSequence

      if (sequence == 0)
        timestamp = tilNextMillis(lastTimestamp)

    } else {
      sequence = 0
    }

    lastTimestamp = timestamp

    ((timestamp - startTimestamp) << timestampLeftShift) |
      (datacenterId << datacenterIdShift) |
      (workerId << workerIdShift) |
      sequence
  }

  protected def tilNextMillis(lastTimestamp: Long): Long = {
    val timestamp = timeGen()
    (timestamp <= lastTimestamp) match {
      case true => tilNextMillis(lastTimestamp)
      case _ => timestamp
    }
  }

  protected def timeGen(): Long = System.currentTimeMillis()

}
