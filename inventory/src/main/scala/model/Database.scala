package model

import org.apache.log4j.Logger
import java.io.{File, PrintWriter}
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization._
import scala.io.Source

class Database {
  val itemList: List[Items] = Database.readFromJSON("items.json")

  def searchItem(query: Map[String, String]): List[Items] = {
    val category: String = query.getOrElse("category", " ")
    val vendor: Int = query.getOrElse("vendor", "0").toInt
    val id: Int = query.getOrElse("id", "0").toInt
    val categoryResult: List[Items] = itemList.filter((entry: Items) => entry.category == category)
    val vendorResult: List[Items] = itemList.filter((entry: Items) => entry.vendorId == vendor)
    val idResult: List[Items] = itemList.filter((entry: Items) => entry.id == id)
    categoryResult ::: vendorResult ::: idResult
  }

  def addToList(commodity: Items): Boolean = {
    if (itemList.contains(commodity)) true else Database.writeToJSON(itemList :+ commodity)
  }

  def updateQuantities(itemsBought: List[Items]): Boolean = {
    val updatedList: List[Items] = for {
      purchased <- itemsBought
      itemInDatabase <- itemList
      if purchased.id == itemInDatabase.id
    } yield itemInDatabase.updateAttribute("quantity", (itemInDatabase.quantity - 1).toString)
    Database.writeToJSON(updatedList)
  }

  def sortBy(attribute: String, itemList: List[Items], direction: String): List[Items] = {
    (attribute, direction) match {
      case ("price", "high to low") => itemList.sortWith((first, second) => first.price > second.price)
      case ("price", "low to high") => itemList.sortWith((first, second) => first.price < second.price)
      case ("name", "high to low") => itemList.sortWith((first, second) => first.name > second.name)
      case ("name", "low to high") => itemList.sortWith((first, second) => first.name < second.name)
      case _ => itemList.sorted
    }
  }
}

object Database {
  val Log = Logger.getLogger(this.getClass)
  val List: List[Items] = readFromJSON("items.json")
  val Obj: Database = new Database()

  def writeToJSON(inventory: List[Items]): Boolean = {
    try {
      implicit def formats: DefaultFormats = DefaultFormats

      val writer = new PrintWriter(new File("items.json"))
      val json = writePretty(inventory)
      writer.write(json)
      writer.close()
      true
    } catch {
      case except: Exception => Log.info(s"\nError: ${except.getMessage}")
        false
    }
  }

  def readFromJSON(fileName: String): List[Items] = {
    try {
      implicit def formats: DefaultFormats = DefaultFormats

      val bufferedSource = Source.fromFile(new File("items.json")).mkString
      read[List[Items]](bufferedSource)
    } catch {
      case except: Exception => Log.info(s"\nError: ${except.getMessage}")
        Nil
    }
  }
}
