package org.moderras.githubparser

/**
  * Created by ModerRAS on 2017/5/27.
  */
class Node(date:String,count:String) {
  val Date:String = date
  val Count:String = count

  override def toString: String = "Date="+Date+"\t"+"Count="+Count
}
