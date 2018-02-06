package model

class Person(
              val mobile: String,
              val name: String,
              val password: String,
              val category: String,
              val address: String) {

  def compare(that: Person): Boolean = {
    if (this.mobile.equals(that.mobile)) {
      if (this.password.equals(that.password)) {
        true
      }
      else {
        false
      }
    }
    else {
      false
    }
  }

  def updateAttribute(attribute: String, newValue: String): Person = {
    attribute match {
      case "mobile" => new Person(newValue, name, password, category, address)
      case "name" => new Person(mobile, newValue, password, category, address)
      case "password" => new Person(mobile, name, newValue, category, address)
      case "category" => new Person(mobile, name, password, newValue, address)
      case "address" => new Person(mobile, name, password, category, newValue)
      case _ => this
    }
  }
}
