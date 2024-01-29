import data.HomeRepository
import dev.icerock.moko.mvvm.viewmodel.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import model.Product

class HomeViewModel: ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()
    private val repository = HomeRepository()

    init {
        viewModelScope.launch{
            repository.getProductsFlow().collect{prods ->
                _products.update {
                    it + prods
                }
            }
        }
    }
}