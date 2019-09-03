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

package org.maogogo.frappe.common.cluster

import akka.actor.{Actor, ActorSystem}
import akka.cluster.Cluster
import akka.cluster.ClusterEvent.{
  InitialStateAsEvents,
  MemberEvent,
  MemberRemoved,
  MemberUp,
  UnreachableMember
}
import akka.event.Logging
import com.google.inject.Inject

class SimpleClusterListener @Inject()(cluster: Cluster) extends Actor {

  private lazy val logger = Logging(context.system, this)

  override def preStart(): Unit = {
    cluster.subscribe(
      self,
      initialStateMode = InitialStateAsEvents,
      classOf[MemberEvent],
      classOf[UnreachableMember]
    )
  }

  override def postStop(): Unit = cluster.unsubscribe(self)

  def receive = {
    case MemberUp(member) ⇒
      logger.info("Member is Up: {}", member.address)
    case UnreachableMember(member) ⇒
      logger.info("Member detected as unreachable: {}", member)
    case MemberRemoved(member, previousStatus) ⇒
      logger.info(
        "Member is Removed: {} after {}",
        member.address,
        previousStatus
      )
    case _: MemberEvent ⇒ // ignore
  }

}
