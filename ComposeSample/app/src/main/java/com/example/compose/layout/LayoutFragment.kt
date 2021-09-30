package com.example.compose.layout

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import com.example.compose.GlideImageWithPreview
import com.example.compose.R
import com.example.compose.ui.theme.ComposeSampleTheme

class LayoutFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            Body(  url = "https://picsum.photos/300/300",
                title = stringResource(id = R.string.lorem_ipsum),
                price = 300.00,
                comments = (0..5).map {
                    Comment(
                        image = "https://picsum.photos/300/300",
                        name = "Hello",
                        text = stringResource(id = R.string.lorem_ipsum)
                    )
                })
        }
    }
}

class Comment(val image: String?, val name: String, val text: String)

@Composable
fun Body(
    url: String?,
    title: String,
    price: Double,
    comments: List<Comment>
) {
    ComposeSampleTheme {
        Column(
            Modifier
                .fillMaxHeight()
                .verticalScroll(rememberScrollState())
        ) {

            GlideImageWithPreview(
                data = url,
                Modifier
                    .height(120.dp)
                    .fillMaxWidth(),
                contentScale = ContentScale.FillBounds
            )
            Row(Modifier.fillMaxWidth().padding20) {
                Text(
                    text = title,
                    modifier = Modifier
                        .weight(5f)
                        .align(Alignment.CenterVertically),
                    fontSize = 18.sp,
                    maxLines = 1,
                    textAlign = TextAlign.Start,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "$price $",
                    fontSize = 20.sp,
                    modifier = Modifier.weight(2f),
                    textAlign = TextAlign.End,
                    fontWeight = FontWeight.Bold,
                )
            }
            Text((0..10).joinToString { title }, Modifier.padding20)

            comments.forEach { CommentView(comment = it) }

            Button(
                onClick = { print("oops") },
                modifier = Modifier
                    .padding(20.dp)
                    .fillMaxWidth(),
            ) {
                Text(
                    modifier = Modifier.padding(10.dp),
                    text = "BUY",
                    color = Color(0xFF000000),
                )
            }
        }
    }
}


//@Preview(showBackground = true, backgroundColor = 0xFFFFFF, name = "LayoutInteractive")
@Composable
fun PreviewTest() {
    Body(
        url = null,
        title = stringResource(id = R.string.lorem_ipsum),
        price = 300.00,
        comments = emptyList()
    )
}


@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun Preview() {
    Body(
        url = "https://picsum.photos/300/300",
        title = stringResource(id = R.string.lorem_ipsum),
        price = 300.00,
        comments = (0..5).map {
            Comment(
                image = "https://picsum.photos/300/300",
                name = "Hello",
                text = stringResource(id = R.string.lorem_ipsum)
            )
        }
    )
}

val Modifier.padding20 get() = padding(20.dp)

@Composable
fun CommentView(comment: Comment) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(6.dp)
    ) {
        GlideImageWithPreview(data = comment.image, Modifier.size(60.dp))
        Column(modifier = Modifier.padding(start = 6.dp)) {
            Text(
                text = comment.name,
                maxLines = 1,
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp,
            )
            Text(
                text = comment.text,
                maxLines = 2,
                fontSize = 12.sp,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}