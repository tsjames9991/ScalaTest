import model._
import org.apache.log4j.Logger

import scala.io.StdIn

class Inventory {
  val Log = Logger.getLogger(this.getClass)
  val database: Database = new Database()

  def addItem(database: Database): Boolean = {
    Log.info(s"\nEnter Name : ")
    val name = StdIn.readLine()
    Log.info(s"\nEnter Vendor ID : ")
    val vendorId = StdIn.readInt()
    Log.info(s"\nEnter Price : ")
    val price = StdIn.readDouble()
    Log.info(s"\nEnter Quantity : ")
    val quantity = StdIn.readInt()
    Log.info(s"\nEnter Category : ")
    val category = StdIn.readLine()
    val initialList = database.itemList
    val sortedIds = initialList
      .sortWith((first, second) => first.id > second.id)
      .map { (item) => item.id }
    val id = sortedIds match {
      case head :: _ => head
      case _ => 0
    }
    val newItem: Items = new Items(id + 1, vendorId, name, price, quantity, category)
    database.addToList(newItem)
  }

  def search(database: Database): Unit = {
    Log.info(s"\nEnter Choice :\n1. Search By Category\n2. Search By Vendor\n3. Search By ID")
    Log.info(s"\nYour Choice : ")
    searchChoice(StdIn.readInt, database)
  }
}
