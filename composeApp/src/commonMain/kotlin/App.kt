import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import org.jetbrains.compose.resources.ExperimentalResourceApi

@OptIn(ExperimentalResourceApi::class)
@Composable
fun App() {
    MaterialTheme {
        Content(viewModel = HomeViewModel())
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Content(viewModel: HomeViewModel) {
    val products = viewModel.products.collectAsState()
    BoxWithConstraints {
        val scope = this
        val maxWidth = scope.maxWidth
        var cols = 2
        var modifier = Modifier.fillMaxWidth()
        if (maxWidth > 840.dp) {
            cols = 3
            modifier = Modifier.widthIn(max = 1080.dp)
        }

        val scrollState = rememberLazyGridState()

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(cols),
                state = scrollState,
                contentPadding = PaddingValues(16.dp)
            ) {

                item(span = { GridItemSpan(cols) }) {
                    SearchBar(
                        modifier = Modifier.fillMaxWidth(),
                        query = "",
                        active = false,
                        onActiveChange = {},
                        onQueryChange = {},
                        onSearch = {},
                        placeholder = { Text("Search Products") },
                        leadingIcon = {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = null
                            )
                        }
                    ) {}

                    Spacer(Modifier.height(16.dp))
                }


                items(items = products.value, key = {
                    it.id.toString()
                }) { product ->
                    Card(
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier.padding(8.dp).fillMaxWidth(),
                        elevation = 2.dp
                    ) {
                        Column(
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            val painter = rememberImagePainter(product.image.toString())
                            Image(
                                painter,
                                modifier = Modifier.height(130.dp).padding(8.dp),
                                contentDescription = product.title
                            )
                            Text(
                                product.title.toString(),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 2,
                                modifier = Modifier.padding(8.dp).heightIn(min = 40.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}