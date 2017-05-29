package org.moderras.githubuserprofileparser

/**
  * Created on 2017/5/29.
  *
  * @author ModerRAS
  */
class Contributions(date:String, count:String) {
  val Date:String = date
  val Count:String = count
  private val DateNums:Array[Int] = date.split("-").map(_.toInt)
  val Year:Int = DateNums(0)
  val Month:Int = DateNums(1)
  val Day:Int = DateNums(2)

  override def toString: String = "Date="+Date+"\t"+"Count="+Count
}
