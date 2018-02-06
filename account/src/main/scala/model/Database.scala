package model

import java.io.{File, PrintWriter}

import org.apache.log4j.Logger
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization._

import scala.io.Source


class Database {
  val personList: List[Person] = Database.readFromJSON("person.json")

  def addToList(person: Person): Boolean = {
    if (personList.contains(person)) true else Database.writeToJSON(personList :+ person)
  }

  def updateList(person: Person): Boolean = {
    val personList: List[Person] = personList.filter(_.mobile != person.mobile)
    val updatedList = personList :+ person
    Database.writeToJSON(updatedList)
  }
}

object Database {
  val Log = Logger.getLogger(this.getClass)
  val List: List[Person] = readFromJSON("person.json")
  val Obj: Database = new Database()

  def writeToJSON(inventory: List[Person]): Boolean = {
    try {
      implicit def formats: DefaultFormats = DefaultFormats

      val writer = new PrintWriter(new File("person.json"))
      val json = writePretty(inventory)
      writer.write(json)
      writer.close()
      true
    } catch {
      case except: Exception => Log.info(s"\nError: ${except.getMessage}")
        false
    }
  }

  def readFromJSON(fileName: String): List[Person] = {
    try {
      implicit def formats: DefaultFormats = DefaultFormats

      val bufferedSource = Source.fromFile(new File("person.json")).mkString
      read[List[Person]](bufferedSource)
    } catch {
      case except: Exception => Log.info(s"\nError: ${except.getMessage}")
        Nil
    }
  }
}
