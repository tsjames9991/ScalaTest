package model

class Items(
             val id: Int,
             val name: String,
             val price: Double,
             val quantity: Int,
             val category: String
           ) extends Ordered[Items] {

  def updateAttribute(attribute: String, newValue: String): Items = {
    attribute match {
      case "id" => new Items(newValue.toInt, name, price, quantity, category)
      case "name" => new Items(id, newValue, price, quantity, category)
      case "price" => new Items(id, name, newValue.toDouble, quantity, category)
      case "quantity" => new Items(id, name, price, newValue.toInt, category)
      case "category" => new Items(id, name, price, quantity, newValue)
      case _ => this
    }
  }

  def compare(that: Items): Int = (this.price - that.price) intValue

  override def toString: String = s"  $id  |  $name  |  $price  |  $quantity  | $category"
}
