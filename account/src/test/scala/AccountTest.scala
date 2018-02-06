import org.scalatest.FunSuite
import model._

class AccountTest extends FunSuite{
  val database = new Database
  test("addPerson returns true for success") {
    assert(Accounts.addPerson(database))
  }
  test("updatePerson returns true for success and false if no data is found") {
    val person = new Person("","","","","")
    assert(!Accounts.updatePerson(person, database))
  }
}
