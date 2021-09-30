package com.example.animationsamles.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.animationsamles.ViewBindingFragment
import com.example.animationsamles.clock.TimeState
import com.example.animationsamles.databinding.FragmentStarBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.sql.Time

class StarFragment : ViewBindingFragment<FragmentStarBinding>(FragmentStarBinding::inflate) {

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.btReset.setOnClickListener {
            binding.starView.reset()
            binding.btStart.setText("Start")
        }
        binding.btStart.setText("Start")
        binding.btStart.setOnClickListener {
            if(binding.btStart.text == "Start") {
                binding.starView.start(0L)
                binding.btStart.setText("Stop")
            } else {
                binding.starView.stop()
                binding.btStart.setText("Start")
            }
        }
        binding.starView.addListener { timeState ->
            setTime(timeState)
        }

        setTime(binding.starView.currentTime())

        Log.d("LGT", "onActivityCreated")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        savedInstanceState?.let { saveState ->
            val time = saveState.getLong(TIME)
            binding.starView.start(time)
            binding.btStart.setText("Stop")
        }
        Log.d("LGT", "onViewStateRestored")

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.starView.stop()
        outState.putLong(TIME, binding.starView.currentTime())

        Log.d("LGT", "save time ${binding.starView.currentTime()}")
        Log.d("LGT", "onSaveInstanceState")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d("LGT", "onDetach")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("LGT", "onDestroy")
    }

    private fun setTime(timeState: TimeState) {
        val time = Time(timeState.time)
        binding.tvTime.text = "${time.hours} : ${time.minutes} : ${time.seconds}"
    }

    private fun setTime(timeLong: Long) {
        val time = Time(timeLong)
        binding.tvTime.text = "${time.hours} : ${time.minutes} : ${time.seconds}"
    }

    override fun onDestroyView() {
        binding.starView.removeListener {  }
        super.onDestroyView()
        Log.d("LGT", "onDestroyView")
    }

    companion object {
        const val TIME = "time"
    }
}