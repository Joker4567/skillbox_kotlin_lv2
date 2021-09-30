package com.example.compose.counter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.compose.ui.theme.ComposeSampleTheme

class CounterFragment : Fragment() {
    private val viewModel by viewModels<CounterViewModel> {
        object : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T = CounterViewModel() as T
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setContent {
            val counter = viewModel.count.collectAsState()
            val step = viewModel.step.collectAsState()

            ComposeSampleTheme {
                Body(
                    counter = counter.value,
                    changeCounter = { viewModel.count.value = it },
                    step = step.value,
                    changeStep = { viewModel.step.value = it }
                )
            }
        }
    }
}

@Composable
fun Body(counter: Int, changeCounter: (Int) -> Unit, step: Int, changeStep: (Int) -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Row {
            Button(
                modifier = Modifier.align(Alignment.CenterVertically),
                onClick = { changeCounter(counter + step) }) {
                Text(text = "-$step")
            }
            TextField(
                modifier = Modifier.weight(3f).padding(6.dp),
                singleLine = true,
                value = counter.toString(),
                onValueChange = { changeCounter(it.toInt()) },
            )
            Button(modifier = Modifier.align(Alignment.CenterVertically),
                onClick = { changeCounter(counter + step) }) {
                Text(text = "+$step")
            }
        }

        Row {
            Button(
                modifier = Modifier.align(Alignment.CenterVertically),
                onClick = { if (step > 1) changeStep(step - 1) }) {
                Text(text = "-1")
            }
            TextField(
                modifier = Modifier.weight(3f).padding(6.dp),
                singleLine = true,
                value = step.toString(),
                onValueChange = { changeStep(it.toInt()) },
            )
            Button(modifier = Modifier.align(Alignment.CenterVertically),
                onClick = { changeStep(step + 1) }) {
                Text(text = "+1")
            }
        }
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFF)
@Composable
fun Preview() {
    val counter = remember { mutableStateOf(0) }
    val step = remember { mutableStateOf(1) }
    Body(
        counter = counter.value,
        changeCounter = { counter.value = it },
        step = step.value,
        changeStep = { step.value = it },
    )
}