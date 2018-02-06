package model

import java.io.{File, PrintWriter}
import org.apache.log4j.Logger
import org.json4s.DefaultFormats
import org.json4s.jackson.Serialization._
import scala.io.Source


class Database {
  val personList: List[Person] = Database.readFromJSON("person.json")

}

object Database {
  val Log = Logger.getLogger(this.getClass)
  val List: List[Person] = readFromJSON("person.json")
  val Obj: Database = new Database()

  def writeToJSON(inventory: List[Person], fileName: String): Boolean = {
    try {
      implicit def formats: DefaultFormats = DefaultFormats

      val writer = new PrintWriter(new File(fileName))
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
