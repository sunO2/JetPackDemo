package com.hezhihu89.fragment.room


import android.os.Bundle
import android.os.Debug
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.hezhihu89.dao.AppDataBase
import com.hezhihu89.dao.UserDao
import com.hezhihu89.entity.Address
import com.hezhihu89.entity.User
import com.hezhihu89.jetpackdemo.R
import com.hezhihu89.kt.*
import kotlinx.android.synthetic.main.fragment_room.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

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
        val userDao = AppDataBase.instance.getUserDao()

        initRecyclerView(userDao)

        room_insert.setOnClickListener{
            insertUser()
        }
        room_delete.click {
            checkEmpt(room_user_id.value())
                    .isFalse {
                        GlobalScope.launch(Dispatchers.Main) {
                            Log.d("当前线程 launch","线程 ---- ${Thread.currentThread()}")
                            val text = async(Dispatchers.IO) {
                                Log.d("当前线程 async","线程 ---- ${Thread.currentThread()}")
                                userDao.delete(room_user_id.value().toLong())
                                return@async "100000"
                            }.await()
                            Log.d("当前线程 launch return","线程 $text ---- ${Thread.currentThread()}")
                        }
                        Log.d("当前线程 launch","线程 是否等待 ---- ${Thread.currentThread()}")
                    }
        }
    }

    /**
     * 初始化RecyclerView
     */
    private fun initRecyclerView(userDao: UserDao) {
        val adapter = room_recycler.apply <User,BaseViewHolder>{
            layoutManager = LinearLayoutManager(context)

            itemType(0,R.layout.room_item_layout){
                setText(R.id.room_item_id,it.userID.toString())
                setText(R.id.room_item_name,it.userName)
                setText(R.id.room_item_first_name,it.firstName)
                setText(R.id.room_item_last_name,it.lastName)
                setText(R.id.room_item_address,it.address.city)
                setText(R.id.room_item_post_number,it.address.postNumber.toString())
            }

            itemType(1,R.layout.room_item_header_layout){

            }
        }
        userDao.getAllByDateAsc().observe(this, Observer {
            it.add(0,User.type(1))
            adapter.setNewData(it)
        })

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
                    GlobalScope.launch(Dispatchers.IO) {
                        userDao.insert(User(userId,userName, firstName, lastName,Address("北京",10090)))
                    }
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
