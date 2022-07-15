package com.qwuiteam.project.fragment

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.transition.AutoTransition
import androidx.transition.TransitionManager
import com.blankj.utilcode.util.*
import com.qwuiteam.project.R
import com.qwuiteam.project.gone
import com.qwuiteam.project.utils.AutoExpandTransition
import com.qwuiteam.project.view.TouchRootLayout
import com.qwuiteam.project.visible
import kotlinx.android.synthetic.main.fragment_layout.*
import kotlinx.android.synthetic.main.fragment_string.*

/**
 * id
 */
class ExpandLayoutFragment : BaseFragment() {

    override fun getLayoutId(): Int = R.layout.fragment_expand


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val cardView = view.findViewById<ViewGroup>(R.id.base_cardview);
        val arrow = view.findViewById<ImageView>(R.id.arrow_button);
        val hiddenView = view.findViewById<View>(R.id.hidden_view);
        val imageBigBg = view.findViewById<View>(R.id.imageBigBg);
        val imageExpandBg = view.findViewById<View>(R.id.imageExpandBg);
        imageExpandBg.gone()
        imageBigBg.visible()
        arrow.setOnClickListener {

            // If the CardView is already expanded, set its visibility
            // to gone and change the expand less icon to expand more.
            if (hiddenView.getVisibility() == View.VISIBLE) {

                // The transition of the hiddenView is carried out
                // by the TransitionManager class.
                // Here we use an object of the AutoTransition
                // Class to create a default transition.
                TransitionManager.beginDelayedTransition(cardView, AutoTransition());
                hiddenView.setVisibility(View.GONE);
//                imageExpandBg.setVisibility(View.VISIBLE);
//                imageBigBg.setVisibility(View.GONE);
                arrow.setImageResource(R.drawable.ic_baseline_expand_more_24);
            }

            // If the CardView is not expanded, set its visibility
            // to visible and change the expand more icon to expand less.
            else {

                TransitionManager.beginDelayedTransition(cardView, AutoTransition());
//                imageExpandBg.setVisibility(View.GONE);
//                imageBigBg.setVisibility(View.VISIBLE);
                hiddenView.setVisibility(View.VISIBLE);
                arrow.setImageResource(R.drawable.ic_baseline_expand_less_24);
            }
        }
    }

}