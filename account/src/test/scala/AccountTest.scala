import org.scalatest.FunSuite
import model._

class AccountTest extends FunSuite{
  val database = new Database
  val accounts = new Accounts
  test("addPerson returns true for success") {
    assert(accounts.addPerson(database))
  }
  test("updatePerson returns true for success and false if no data is found") {
    val person = new Person("","","","","")
    assert(!accounts.updatePerson(person, database))
  }
}
