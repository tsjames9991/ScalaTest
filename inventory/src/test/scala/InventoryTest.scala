import org.scalatest.FunSuite
import model._

class InventoryTest extends FunSuite {
  val price = 10.5
  val quantity = 4
  val database = new Database
  val item = new Items(0, "Test Case", price, quantity, "Test Subject")
  val inventoryObject = new Inventory
  test("addItems Adds an Item to The Shopping Cart") {
    assert(inventoryObject.addItem(database))
  }
  test("addItems Adds an Item to The Shopping Cart") {
    assert(inventoryObject.search(database).isEmpty)
  }
}
