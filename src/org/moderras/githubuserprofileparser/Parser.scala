

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

import scala.collection.mutable.ListBuffer
import scala.xml.XML

/**
  * Created on 2017/5/29.
  *
  * @author ModerRAS
  */


object Parser {
  def getLinks(url:String): List[Contributions] = {
    val doc:Document = Jsoup.connect(url).get()
    val links:Elements = doc.select("rect")
    var ret:ListBuffer[Contributions] = ListBuffer()
    val iterator = links.iterator()
    while (iterator.hasNext) {
      val ne = iterator.next()
      ret += new Contributions(ne.attr("data-date"),ne.attr("data-count"))
    }
    ret.toList
  }

  def readXML(string: String):Set[String] = {
    val children = XML.load(string).child
    
  }
  def writeXML(string: String): Unit = {

  }


  def main(args: Array[String]): Unit = {
    val ret = getLinks("https://github.com/moderras")
    for(i<-ret) println(i.toString)
  }
}
