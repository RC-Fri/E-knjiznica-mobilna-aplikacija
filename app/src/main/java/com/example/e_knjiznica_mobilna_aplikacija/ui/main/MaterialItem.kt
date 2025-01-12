import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.e_knjiznica_mobilna_aplikacija.data.model.Material

@Composable
fun MaterialItem(material: Material, onExtendDate: (Material) -> Unit, onView: (Material) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = material.imageResId),
                    contentDescription = material.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(80.dp).padding(end = 16.dp)
                )

                Text(
                    text = material.name,
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = material.status,
                    fontSize = 14.sp,
                    color = Color.Black, //TODO: Change based on status
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = material.details,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.weight(1f)
                )
            }

            Button(
                onClick = { onExtendDate(material) },
                modifier = Modifier.align(Alignment.End).padding(top = 8.dp)
            ) {
                Text(text = "Extend Date")
            }

            Button(
                onClick = { onView(material) },
                modifier = Modifier.align(Alignment.End).padding(top = 8.dp)
            ) {
                Text(text = "Detail")
            }
        }
    }
}

@Composable
fun MaterialItem2(material: Material, onBorrowMaterial: (Material) -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {

            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(id = material.imageResId),
                    contentDescription = material.name,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(80.dp).padding(end = 16.dp)
                )

                Text(
                    text = material.name,
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = material.status,
                    fontSize = 14.sp,
                    color = Color.Black, //TODO: Change based on status
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = material.details,
                    fontSize = 14.sp,
                    color = Color.Gray,
                    modifier = Modifier.weight(1f)
                )
            }

            Button(
                onClick = { onBorrowMaterial(material) },
                modifier = Modifier.align(Alignment.End).padding(top = 8.dp)
            ) {
                Text(text = "Borrow")
            }
        }
    }
}