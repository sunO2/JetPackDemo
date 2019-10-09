package com.hezhihu89.jetpackdemo


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.hezhihu89.fragment.dao.AppDataBase
import com.hezhihu89.fragment.dao.UserDao
import com.hezhihu89.fragment.entity.User
import com.hezhihu89.fragment.kt.checkEmpt
import com.hezhihu89.fragment.kt.isFalse
import com.hezhihu89.fragment.kt.isTrue
import com.hezhihu89.fragment.kt.value
import kotlinx.android.synthetic.main.fragment_room.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RoomFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RoomFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        return inflater.inflate(R.layout.fragment_room, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        room_insert.setOnClickListener{
            insertUser()
        }
        room_insert.addTextChangedListener {  }
    }

    /**
     * 存储用户
     */
    private fun insertUser(){
        var userId = 0L
        val userName = room_user_name.value()
        val firstName = room_first_name.value()
        val lastName = room_last_name.value()

        checkEmpt(room_user_id.value())
                .isTrue {
                    userId = 0L
                }
                .isFalse {
                    userId = room_user_id.value().toLong()
                }


        checkEmpt(userName,firstName,lastName)
                .isFalse {
                    Toast.makeText(context,"插入数据库",Toast.LENGTH_SHORT).show()
                    val userDao = AppDataBase.instance.getUserDao()
                    userDao.insert(User(userId,userName, firstName, lastName))

                    val sb = StringBuilder()
                    userDao.getAllByDateDesc().forEach{
                        sb.appendln(it.toString())
                    }
                    query_all_tv.text = sb.toString()
                }
                .isTrue {
                    Toast.makeText(context,"输入为空",Toast.LENGTH_SHORT).show()
                }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RoomFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                RoomFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
