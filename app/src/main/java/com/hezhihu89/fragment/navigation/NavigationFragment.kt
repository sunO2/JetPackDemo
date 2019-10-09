package com.hezhihu89.fragment.navigation

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.navOptions
import com.hezhihu89.fragment.OnFragmentInteractionListener
import com.hezhihu89.fragment.animationNavOptions
import com.hezhihu89.jetpackdemo.R
import kotlinx.android.synthetic.main.fragment_navigation.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [NavigationFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [NavigationFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NavigationFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_navigation, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        jet_pack_navigation_a.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.navigationFragmentA,null,  navOptions {
                anim {
                    enter = R.anim.app_nav_default_enter_anim
                    exit = R.anim.app_nav_default_exit_anim
                    popEnter = R.anim.app_nav_default_pop_enter_anim
                    popExit = R.anim.app_nav_default_pop_exit_anim
                }
            })
        }
        jet_pack_navigation_b.setOnClickListener(){
            Navigation.findNavController(it).navigate(R.id.navigationFragmentB, null,navOptions {
                anim {
                    enter = R.anim.app_nav_default_enter_anim
                    exit = R.anim.app_nav_default_exit_anim
                    popEnter = R.anim.app_nav_default_pop_enter_anim
                    popExit = R.anim.app_nav_default_pop_exit_anim
                }
            })
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NavigationFragmentA.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                NavigationFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
