package org.moderras.githubuserprofileparser

import scala.collection.mutable.ListBuffer

/**
  * Created on 2017/6/1.
  *
  * @author ModerRAS
  */
class TreeNodes(dad: TreeNodes, inname: String, inattr: String) {
  val father: TreeNodes = dad
  val name: String = inname
  val attr: String = inattr
  var total: Int
  private var children: ListBuffer[TreeNodes] = new ListBuffer[TreeNodes]

  def +(treeNodes: TreeNodes): Unit = children += treeNodes

  def child: List[TreeNodes] = children.toList
}
