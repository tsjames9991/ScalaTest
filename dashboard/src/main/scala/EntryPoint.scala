import org.apache.log4j.Logger
import scala.io.StdIn

class EntryPoint {
  val Log = Logger.getLogger(this.getClass)
  class Item extends Inventory {
    def customerModule(): Unit = {
      Log.info(s"\nEnter Choice :\n1. Search Item\n2. Get Detailed Description\n3. Buy Items")
      Log.info(s"\nYour Choice : ")
      val choice = StdIn.readInt()
      choice match {
        case 1 => search(database)

        case 2 =>
          Log.info(s"\nCategory : ")
          val category: String = StdIn.readLine()
          Log.info(s"\nVendor : ")
          val vendor: String = StdIn.readLine()
          Log.info(s"\nID : ")
          val id: String = StdIn.readLine()
          val map = Map("category" -> category, "vendor" -> vendor, "id" -> id)
          database.searchItem(map)
      }
    }
  }

  class PersonAccounts extends Accounts
  def inventoryModule(): Unit = {
    Log.info(s"\nEnter Vendor ID : ")
    val id = StdIn.readInt()
    Log.info(s"\nEnter Choice :\n1. Add Items \n2. Delete Item\n3. Update Stocks")
    Log.info(s"\nYour Choice : ")
    val choice = StdIn.readInt()
    choice match {
      case 1 =>

      case 2 =>

      case 3 =>
    }
  }
}

object EntryPoint extends App {
  val Log = Logger.getLogger(this.getClass)
  val entry = new EntryPoint
  Log.info(s"\nEnter Choice :\n1. Customer Module\n2. Inventory Module")
  Log.info(s"\nYour Choice : ")
  val choice: Int = StdIn.readInt()
  choice match {
    case 1 => entry.Person.customerModule()
    case 2 => entry.inventoryModule()
    case _ => Log.info(s"Wrong Choice")
  }
}
