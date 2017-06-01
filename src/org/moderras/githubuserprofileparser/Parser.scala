package org.moderras.githubuserprofileparser

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements

import scala.collection.mutable.ListBuffer
import scala.xml.{Elem, XML}

/**
  * Created on 2017/5/29.
  *
  * @author ModerRAS
  */


object Parser {
  def readXML(string: String): Set[String] = XML.load(string).child.map(_.attribute("Name").toString).toSet

  def writeXML(filename: String, nodes: Map[String, List[Contributions]]): Unit = {
    /**
      * 输入数据应该是一个cont的list的map,和一个文件名
      * map的键是用户名,每个用户名对应的是其活跃相关
      * 写的xml格式
      * <person name ="">
      * <year attr="2017" total="1000">
      * <month attr="1" total="100">
      * <day attr="1" contributions="10" />
      * </month>
      * </year>
      * </person>
      * 写一下关于递归调用的版本?
      * 想一下怎么写吧...
      *
      */
    def translate(nodes: Map[String, List[Contributions]]) = {
      val NodesIterator = nodes.iterator

      def conventToTree(map: Map[String, List[Contributions]]) = {

      }

      def addone(list: List[Contributions]): Elem = {

        def addYear(contributions: Contributions) = {

        }

        def addMonth(contributions: Contributions) = {

        }

        def addDay(contributions: Contributions) = {

        }
      }


    }

    def toWrite() = {

    }

  }

  def main(args: Array[String]): Unit = {
    val ret = getLinks("https://github.com/moderras")
    for (i <- ret) println(i.toString)
  }

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
}
