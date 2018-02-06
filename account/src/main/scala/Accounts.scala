import model._
import org.apache.log4j.Logger

import scala.io.StdIn

object Accounts {
  val Log = Logger.getLogger(this.getClass)

  def addPerson(database: Database): Boolean = {
    Log.info(s"\nEnter Mobile : ")
    val mobile = StdIn.readLine()
    Log.info(s"\nEnter Name : ")
    val name = StdIn.readLine()
    Log.info(s"\nEnter Password : ")
    val password = StdIn.readLine()
    Log.info(s"\nEnter Category : ")
    val category = StdIn.readLine()
    Log.info(s"\nEnter Address : ")
    val address = StdIn.readLine()
    val newPerson: Person = new Person(mobile, name, password, category, address)
    database.addToList(newPerson)
  }

  def updatePerson(person: Person, database: Database): Boolean = {
    Log.info(s"\nEnter Parameter : ")
    val parameter = StdIn.readLine()
    Log.info(s"\nEnter New Value : ")
    val newValue = StdIn.readLine()
    database.updateList(person.updateAttribute(parameter,newValue))
  }
}