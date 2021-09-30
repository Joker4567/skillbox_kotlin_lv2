package com.example.compose.list

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.compose.GlideImageWithPreview
import com.example.compose.MovieRepository
import com.example.compose.R
import com.example.compose.detail.DetailFragment
import com.example.compose.model.CharacterModel
import com.example.compose.model.Status
import com.example.compose.ui.theme.BlackTheme
import com.example.compose.ui.theme.attr.Black
import com.example.compose.ui.theme.attr.Paddings

class ListFragment : Fragment() {
    private val viewModel by viewModels<ListViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T = ListViewModel(MovieRepository()) as T
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            BlackTheme {
                Surface(modifier = Modifier
                        .fillMaxWidth()
                        .background(Black)) {
                    ListView()
                }
            }
        }
    }

    @Composable
    fun ListView() {
        val items: LazyPagingItems<CharacterModel> = viewModel.movies.collectAsLazyPagingItems()

        LazyColumn {
            items(items) {
                it?.let { model ->
                    when (Status.valueOf(model.status)) {
                        Status.Alive ->
                            CardRickAndMortieView(model = it, R.drawable.ic_alive)
                        Status.Dead ->
                            CardRickAndMortieView(model = it, R.drawable.ic_dead)
                        Status.unknown ->
                            CardRickAndMortieView(model = it, R.drawable.ic_unknown)
                    }
                }
            }

            items.apply {
                when {
                    loadState.refresh is LoadState.Loading -> {
                        item {
                            Box(
                                    modifier = Modifier.fillParentMaxSize(),
                                    contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator()
                            }
                        }
                    }
                    loadState.append is LoadState.Loading -> {
                        item { CircularProgressIndicator() }
                    }
                    loadState.refresh is LoadState.Error -> {
                        val e = items.loadState.refresh as LoadState.Error
                        item {
                            Column(modifier = Modifier.fillParentMaxSize()) {
                                e.error.localizedMessage?.let { Text(text = it) }
                                Button(onClick = { retry() }) {
                                    Text(text = "Retry")
                                }
                            }

                        }
                    }
                    loadState.append is LoadState.Error -> {
                        val e = items.loadState.append as LoadState.Error
                        item {
                            Column(
                                    modifier = Modifier.fillParentMaxSize(),
                                    verticalArrangement = Arrangement.Center
                            ) {
                                e.error.localizedMessage?.let { Text(text = it) }
                                Button(onClick = { retry() }) {
                                    Text(text = "Retry")
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun CardRickAndMortieView(model: CharacterModel, statusId: Int) {
        Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                        .padding(6.dp)
                        .fillMaxWidth()
                        .clickable {
                            openDetail(model)
                        }
        ) {
            GlideImageWithPreview(
                    data = model.image,
                    Modifier
                            .size(60.dp)
            )
            Column(modifier = Modifier.padding(start = 6.dp)) {
                Text(
                        text = model.name,
                        maxLines = 1,
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.White,
                        modifier = Modifier.padding(0.dp, 0.dp, 0.dp, Paddings().smallPadding)
                )
                Row(
                        horizontalArrangement = Arrangement.Start
                ) {
                    ResourcesCompat.getDrawable(
                            LocalContext.current.resources,
                            statusId, LocalContext.current.theme
                    )?.let { drawable ->
                        val bitmap = Bitmap.createBitmap(30, 30,
                                Bitmap.Config.ARGB_8888
                        )
                        val canvas = Canvas(bitmap)
                        drawable.setBounds(0, 0, canvas.width, canvas.height)
                        drawable.draw(canvas)

                        Image(
                                bitmap = bitmap.asImageBitmap(),
                                contentDescription = null,
                                modifier = Modifier.size(15.dp),
                                alignment = Alignment.Center,
                                contentScale = ContentScale.Crop
                        )
                        Text(
                                text = "${model.status} - ${model.species}",
                                maxLines = 1,
                                modifier = Modifier
                                        .padding(Paddings().smallPadding, 0.dp, 0.dp, 0.dp),
                                fontSize = 12.sp,
                                overflow = TextOverflow.Ellipsis,
                                color = Color.White
                        )
                    }
                }
            }
        }
    }

    private fun openDetail(model: CharacterModel) {
        requireActivity().supportFragmentManager.commit {
            replace(R.id.container, DetailFragment.newInstance(model))
            addToBackStack(null)
        }
    }
}