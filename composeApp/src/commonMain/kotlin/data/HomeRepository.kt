package data

import io.ktor.client.call.body
import io.ktor.client.request.get
import kotlinx.coroutines.flow.flow
import model.Product

class HomeRepository {

    suspend fun getProducts(): List<Product>{
        val resp = httpClient.get("https://fakestoreapi.com/products")
        return resp.body()
    }

    fun getProductsFlow() = flow{
        emit(getProducts())
    }
}