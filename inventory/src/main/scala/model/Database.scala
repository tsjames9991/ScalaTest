package model

import org.apache.log4j.Logger
import java.io.{File, PrintWriter}
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization._
import scala.io.{Source, StdIn}

class Database {
  val itemList: List[Items] = Database.readFromJSON("items.json")
  val Log = Logger.getLogger(this.getClass)

  def searchChoice(choice: Int): List[Items] = {
    Log.info(s"\nEnter Search Parameters : ")
    val input: String = StdIn.readLine()
    Log.info(s"\nSort By :\n1. Name\n2. Price")
    val attribute: Int = StdIn.readInt()
    Log.info(s"\nSort :\n1. Low To High\n2. High To Low")
    val direction: Int = StdIn.readInt()
    (attribute, direction) match {
      case (1, 1) => sortBy("name", getList(choice, input, searchItem), "low to high")
      case (2, 1) => sortBy("price", getList(choice, input, searchItem), "low to high")
      case (1, 2) => sortBy("name", getList(choice, input, searchItem), "high to low")
      case (2, 2) => sortBy("price", getList(choice, input, searchItem), "high to low")
      case _ => itemList
    }
  }

  private def getList(choice: Int, input: String, find: Map[String, String] => List[Items]): List[Items] = {
    choice match {
      case 1 =>
        val map = Map("category" -> input, "id" -> "0")
        find(map)

      case 2 =>
        val map = Map("category" -> "0", "id" -> input)
        find(map)
    }
  }

  def searchItem(query: Map[String, String]): List[Items] = {
    val category: String = query.getOrElse("category", " ")
    val id: Int = query.getOrElse("id", "0").toInt
    val categoryResult: List[Items] = itemList.filter((entry: Items) => entry.category == category)
    val idResult: List[Items] = itemList.filter((entry: Items) => entry.id == id)
    categoryResult ::: idResult
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
