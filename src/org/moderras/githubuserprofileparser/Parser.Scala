package org.moderras.githubparser

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

import scala.collection.mutable.ListBuffer
import scala.sys.process.processInternal.File

/**
  * Created by ModerRAS on 2017/5/27.
  */
object Parser {
  def getLinks(url:String): List[Node] = {
    val doc:Document = Jsoup.connect(url).get()
    val links:Elements = doc.select("rect")
    var ret:ListBuffer[Node] = ListBuffer()
    val iterator = links.iterator()
    while (iterator.hasNext) {
      val ne = iterator.next()
      ret += new Node(ne.attr("data-date"),ne.attr("data-count"))
    }
    ret.toList
  }
  /*
  def readXML(file: File):Set[String] = {
    Set[String]
  }
  def writeXML(file: File): Unit = {

  }
  */

  def main(args: Array[String]): Unit = {
    val ret = getLinks("https://github.com/moderras")
    for(i<-ret) println(i.toString)
  }
}
