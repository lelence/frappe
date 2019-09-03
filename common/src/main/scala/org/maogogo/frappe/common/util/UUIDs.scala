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
      murmur_hasing(UUID.randomUUID().toString)
    )
  }

}
