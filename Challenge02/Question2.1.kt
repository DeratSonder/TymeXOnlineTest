import java.text.DecimalFormat

data class Product(
    val name: String = "",
    val price: Double = 0.0,
    val quantity: Int = 0
)

class Products(products: ArrayList<Product>) {
    private val products: ArrayList<Product> = ArrayList()

    init {
        this.products.addAll(products)
    }

    fun initProducts(
        products: ArrayList<Product>
    ) {
        products.addAll(products)
    }

    fun addProduct(product: Product) {
        products.add(product)
    }

    fun getProducts(): ArrayList<Product> {
        return products
    }

    fun calculateTotalInventory(): String {
        var totalInventory = 0.0
        for (product in products) {
            totalInventory += product.price * product.quantity
        }
        return DecimalFormat("#,##0.00").format(totalInventory)
    }

    fun findMostExpensiveProduct(): String {
        val mostExpensiveProduct = products.maxByOrNull { it.price }
        return mostExpensiveProduct?.name.toString()
    }

    fun checkExistingProductByName(name: String): Boolean =
        products.any { it.name == name && it.quantity > 0 }

    fun sortProductsByPrice(order: String = "ASC"): List<Product> =
        when (order.uppercase()) {
            "ASC" -> products.sortedBy { it.price }
            "DESC" -> products.sortedByDescending { it.price }
            else -> products
        }

    fun sortProductsByQuantity(order: String = "ASC"): List<Product> =
        when (order.uppercase()) {
            "ASC" -> products.sortedBy { it.quantity }
            "DESC" -> products.sortedByDescending { it.quantity }
            else -> products
        }

    fun printProducts(products: List<Product>) {
        if (products.isEmpty()) {
            println("No products available.")
            return
        }

        println("List of Products:")
        println("-----------------------------------------------------")
        println("Name                 Price      Quantity")
        println("-----------------------------------------------------")
        for (product in products) {
            val name = product.name.padEnd(20)
            val price = "%.2f".format(product.price).padEnd(10)
            val quantity = product.quantity.toString().padEnd(10)
            println("$name$price$quantity")
        }
        println("-----------------------------------------------------")
    }

}

val products = arrayListOf(
    Product("Laptop", 999.99, 5),
    Product("Smartphone", 499.99, 10),
    Product("Tablet", 299.99, 0),
    Product("Smartwatch", 199.99, 3)
)

fun main() {

    val products = Products(products)

    // Calculate the total inventory value
    val totalInventory = products.calculateTotalInventory()
    println("Total Inventory Value: $totalInventory")

    // Find the most expensive product
    val mostExpensiveProduct = products.findMostExpensiveProduct()
    println("Most Expensive Product: $mostExpensiveProduct")

    // Check if a product named "Headphones" is in stock
    val hasHeadphonesInStock = products.checkExistingProductByName("Headphones")
    println("Headphones in Stock: $hasHeadphonesInStock")

    // Sort ascendingly by price
    val sortedByPriceAsc = products.sortProductsByPrice("ASC")
    println("Sorted by Price (Ascending):")
    products.printProducts(sortedByPriceAsc)

    // Sort descendingly by price
    val sortedByPriceDesc = products.sortProductsByPrice("DESC")
    println("Sorted by Price (Descending):")
    products.printProducts(sortedByPriceDesc)

    // Sort descendingly by quantity
    val sortedByQuantityAsc = products.sortProductsByQuantity("ASC")
    println("Sorted by Quantity (Ascending):")
    products.printProducts(sortedByQuantityAsc)

    // Sort descendingly by quantity
    val sortedByQuantityDesc = products.sortProductsByQuantity("DESC")
    println("Sorted by Quantity (Descending):")
    products.printProducts(sortedByQuantityDesc)


}