package com.example.recyclerview.paging

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.recyclerview.R
import com.example.recyclerview.api.Character
import com.example.recyclerview.model.State
import com.squareup.picasso.Picasso
import jp.wasabeef.picasso.transformations.RoundedCornersTransformation

class CharacterAdapter : PagedListAdapter<Character, ViewHolder>(DiffUtilCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_character, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }
}

class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    fun bind(character: Character) {
        val tvName = (itemView as? ViewGroup)?.findViewById<TextView>(R.id.tv_name)
        val tvLastKnown = (itemView as? ViewGroup)?.findViewById<TextView>(R.id.tv_last_known_value)
        val tvStatus = (itemView as? ViewGroup)?.findViewById<TextView>(R.id.tv_status)
        val tvFirstSeen = (itemView as? ViewGroup)?.findViewById<TextView>(R.id.tv_first_seen_value)
        val status = (itemView as? ViewGroup)?.findViewById<View>(R.id.status)
        val ivImage = (itemView as? ViewGroup)?.findViewById<ImageView>(R.id.iv_icon)
        tvName?.text = "${character.name}"
        tvLastKnown?.text = "${character.location.name}"
        tvFirstSeen?.text = "${character.origin.name}"

        ivImage?.let {
            val transformation = RoundedCornersTransformation(20, 0, RoundedCornersTransformation.CornerType.LEFT)
            Picasso.get().load(character.image)
                    .transform(transformation)
                    .into(ivImage)
        }

        tvStatus?.text = "${character.status} - ${character.species}"

        when(State.valueOf(character.status)) {
            State.Alive -> {
                status?.setBackgroundDrawable(itemView.context.getDrawable(R.drawable.ic_alive))
            }
            State.Dead -> {
                status?.setBackgroundDrawable(itemView.context.getDrawable(R.drawable.ic_dead))
            }
            State.unknown -> {
                status?.setBackgroundDrawable(itemView.context.getDrawable(R.drawable.ic_unknown))
            }
        }
    }
}

class DiffUtilCallback : DiffUtil.ItemCallback<Character>() {
    override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean =
        oldItem.name == newItem.name && oldItem.species == newItem.species
}