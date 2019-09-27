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

import java.util.UUID

import com.google.common.base.Charsets
import com.google.common.hash.Hashing

final object UUIDs {

  def uuid(): String = {
    val murmur_hasing = (uuid: String) =>
      Hashing.murmur3_32().hashString(uuid, Charsets.UTF_8).toString

    String.format(
      "%s-%s",
      murmur_hasing(UUID.randomUUID().toString),
      murmur_hasing(UUID.randomUUID().toString))
  }

}
