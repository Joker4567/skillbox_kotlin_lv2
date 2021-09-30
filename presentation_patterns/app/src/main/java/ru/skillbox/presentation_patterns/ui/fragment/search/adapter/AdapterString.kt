package ru.skillbox.presentation_patterns.ui.fragment.search.adapter

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateLayoutContainer
import kotlinx.android.synthetic.main.item_city.view.*
import ru.skillbox.presentation_patterns.R

fun itemSearchCity(openClick: (String) -> Unit) =
        adapterDelegateLayoutContainer<String, Any>(R.layout.item_city) {

            //Открытие карточки
            containerView.setOnClickListener { openClick.invoke(item) }

            bind {
                containerView.item_text_city.text = item
            }
        }