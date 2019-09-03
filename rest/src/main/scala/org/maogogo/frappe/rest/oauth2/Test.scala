package org.maogogo.frappe.rest.oauth2

import java.util.Base64

object Test extends App {

  println(Base64.getMimeDecoder.decode("amFubmlrOnA0c3N3MHJk"))

  val dd = Base64.getMimeDecoder.decode("amFubmlrOnA0c3N3MHJk")

  println(new String(dd))

  println(Base64.getEncoder.encodeToString(dd.toArray))
}
