package com.example.compose.detail

import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.GlideImageWithPreview
import com.example.compose.MovieRepository
import com.example.compose.R
import com.example.compose.list.ListFragment
import com.example.compose.model.CharacterModel
import com.example.compose.model.Episode
import com.example.compose.model.Status
import com.example.compose.ui.theme.BlackTheme
import com.example.compose.ui.theme.attr.Black
import com.example.compose.ui.theme.attr.LightBlack
import com.example.compose.ui.theme.attr.Paddings

class DetailFragment : Fragment() {

    private val viewModel by viewModels<DetailViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T = DetailViewModel(MovieRepository()) as T
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val model = arguments?.getSerializable(MODEL)
        model?.let {
            Log.d("param", it.toString())
            viewModel.setActor(model as CharacterModel)
        }
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            BlackTheme {
                val scrollState = rememberScrollState()
                Column(Modifier
                        .fillMaxSize()
                        .background(Black)
                        .scrollable(
                                state = scrollState,
                                orientation = Orientation.Vertical
                        )) {
                    menuNavigation()
                    viewModel.getList()
                    val list: State<List<Episode>?> = viewModel.itemList.observeAsState()
                    list.value.let { it: List<Episode>? ->
                        it?.let {
                            ListView(it)
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun menuNavigation() {
        Row(
                verticalAlignment = Alignment.Top,
                modifier = Modifier
                        .height(60.dp)
                        .fillMaxWidth()
                        .padding(Paddings().smallPadding)
        ) {
            Image(
                    painter = painterResource(id = R.drawable.ic_back),
                    contentDescription = null,
                    modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                            .clickable {
                                close()
                            },
                    alignment = Alignment.Center
            )
            Text(
                    text = "Person detail",
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(Paddings().defaultPadding, 0.dp, 0.dp, 0.dp),
                    color = Color.White,
                    textAlign = TextAlign.Start
            )
        }
    }

    @Composable
    fun infoActor(model: CharacterModel) {
        Column(modifier = Modifier.fillMaxWidth()) {
            //Image photo
            GlideImageWithPreview(
                    data = model.image,
                    Modifier
                            .height(240.dp)
                            .fillMaxWidth()
                            .padding(Paddings().smallPadding, 0.dp, Paddings().smallPadding, Paddings().tinyPadding)
            )

            //Name
            Text(
                    text = model.name,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp,
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(Paddings().largePadding, 0.dp, 0.dp, 0.dp),
                    color = Color.White,
                    textAlign = TextAlign.Start
            )

            //Status
            Text(
                    text = "Live status",
                    maxLines = 1,
                    fontWeight = FontWeight.Normal,
                    fontSize = 16.sp,
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(Paddings().largePadding, 0.dp, 0.dp, Paddings().tinyPadding),
                    color = Color.LightGray,
                    textAlign = TextAlign.Start
            )
            when (Status.valueOf(model.status)) {
                Status.Alive ->
                    statusAndName(model, R.drawable.ic_alive, false)
                Status.Dead ->
                    statusAndName(model, R.drawable.ic_dead, false)
                Status.unknown ->
                    statusAndName(model, R.drawable.ic_unknown, false)
            }
            //Species
            setInfoActor("Species and gender:", model.species)
            //Location
            setInfoActor("Last know location:", model.location.name)
            setInfoActor("First seen in:", model.origin.name)
            Text(
                    text = "Episodes:",
                    maxLines = 1,
                    modifier = Modifier
                            .fillMaxWidth()
                            .padding(Paddings().largePadding, 0.dp, 0.dp, Paddings().smallPadding),
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.Start
            )
        }
    }

    @Composable
    fun setInfoActor(header: String, value: String = "") {
        Text(
                text = header,
                maxLines = 1,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(Paddings().largePadding, 0.dp, 0.dp, Paddings().tinyPadding),
                color = Color.LightGray,
                textAlign = TextAlign.Start
        )
        Text(
                text = value,
                maxLines = 1,
                fontWeight = FontWeight.Normal,
                fontSize = 16.sp,
                modifier = Modifier
                        .fillMaxWidth()
                        .padding(Paddings().largePadding, 0.dp, 0.dp, Paddings().smallPadding),
                color = Color.White,
                textAlign = TextAlign.Start
        )
    }

    @Composable
    fun ListView(itemsEpisode: List<Episode>) {
        LazyColumn {
            items(itemsEpisode) { model ->
                CardEpisode(model as Episode)
            }
        }
    }

    @Composable
    fun CardEpisode(model: Episode) {
        if (model.id == 0) {
            Row(
                    horizontalArrangement = Arrangement.Center
            ) {
                viewModel.getActor()?.let { modelCharacter ->
                    val model = (modelCharacter as CharacterModel)
                    infoActor(model)
                }
            }
        } else {
            Card(
                    shape = RoundedCornerShape(4.dp),
                    backgroundColor = Black,
                    modifier = Modifier
                            .padding(6.dp)
                            .fillMaxWidth()
            ) {
                Column(modifier = Modifier
                        .background(LightBlack)
                        .fillMaxWidth()) {
                    Text(
                            text = model.name,
                            maxLines = 1,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            color = Color.White,
                            modifier = Modifier.padding(Paddings().defaultPadding, 0.dp, 0.dp, Paddings().tinyPadding)
                    )
                    Text(
                            text = model.air_date,
                            maxLines = 1,
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp,
                            color = Color.White,
                            modifier = Modifier.padding(Paddings().defaultPadding, 0.dp, 0.dp, Paddings().smallPadding)
                    )
                }
            }
        }
    }

    @Composable
    private fun statusAndName(model: CharacterModel, statusId: Int, isFullName: Boolean = true) {
        Row(
                horizontalArrangement = Arrangement.Start,
                modifier = Modifier.padding(Paddings().largePadding, 0.dp, 0.dp, 0.dp).fillMaxWidth(1F)
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
                        modifier = Modifier
                                .size(10.dp),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop
                )
                Text(
                        text = if (isFullName)
                            "${model.status} - ${model.species}"
                        else
                            "${model.status}",
                        maxLines = 1,
                        modifier = Modifier
                                .padding(Paddings().smallPadding, 0.dp, 0.dp, 0.dp),
                        fontSize = 16.sp,
                        color = Color.White
                )
            }
        }
    }

    private fun close() {
        requireActivity().supportFragmentManager.commit {
            replace(R.id.container, ListFragment())
            addToBackStack(null)
        }
    }

    companion object {
        fun newInstance(model: CharacterModel): DetailFragment {
            val args = Bundle().apply {
                putSerializable(MODEL, model)
            }
            val fragment = DetailFragment()
            fragment.arguments = args
            return fragment
        }

        const val MODEL = "model"
    }
}