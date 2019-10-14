package com.hezhihu89.kt

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import java.lang.NullPointerException

/**
 * @author hezhihu89
 * 创建时间 2019 年 10 月 14 日 16:56
 * 功能描述:
 */
data class Type<T>(val layoutId:Int, val action: BaseViewHolder.(T)-> Unit)

/**
 * Adapter 使用的ViewHolder
 */
class BaseViewHolder(itemVIew: View): RecyclerView.ViewHolder(itemVIew){

    val idMap: SparseArray<View> = SparseArray()

    fun setText(id: Int,text: String){
        val view = if(null != idMap[id]){
            idMap[id]
        }else{
            val view = itemView.findViewById<TextView>(id)
            idMap.put(id,view)
            view
        } as TextView

        view.text = text
    }
}

abstract class BaseAdapter<R: TypeBean,T: BaseViewHolder>: RecyclerView.Adapter<T>(){
    protected var data = mutableListOf<R>()

    fun setNewData(data: MutableList<R>){
        this.data = data
        notifyDataSetChanged()
    }

}


/**
 * 类型的英文
 */
interface TypeBean{
    fun type():Int
}

/**
 * 创建RecyclerView 相关参数数据
 */
class RecyclerViewCreate<T>{

    private val itemTypes = hashMapOf<Int,Type<T>>()

    lateinit var layoutManager: RecyclerView.LayoutManager

    fun itemType(type: Int,@LayoutRes layoutId: Int,action: BaseViewHolder.(T)-> Unit){
        itemTypes[type] = Type(layoutId,action)
    }

    fun itemType(type: Int):Type<T>?{
        return itemTypes[type]
    }

}

/**
 * 构建一个ReyclerView
 */
fun <T: TypeBean,R: BaseViewHolder> RecyclerView.apply(create: RecyclerViewCreate<T>.()->Unit): BaseAdapter<T,R>{
    val recyclerViewCreate = RecyclerViewCreate<T>()
    create(recyclerViewCreate)

    layoutManager = recyclerViewCreate.layoutManager
    val adapter:BaseAdapter<T,R> = object: BaseAdapter<T,R>() {

        private val factory = LayoutInflater.from(context)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): R {
            val type = recyclerViewCreate.itemType(viewType) ?: throw NullPointerException("没有找到支持的类型")
            return BaseViewHolder(factory.inflate(type.layoutId,parent,false)) as R
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun getItemViewType(position: Int): Int {
            return data[position].type()
        }

        /**
         * 获取viewHolder bind 方式
         */
        fun getBindViewHolderAction(position: Int): BaseViewHolder.(T) -> Unit {
            return recyclerViewCreate.itemType(getItemViewType(position))!!.action
        }

        override fun onBindViewHolder(holder: R, position: Int) {
            getBindViewHolderAction(position).invoke(holder,data[position])
        }
    }
    setAdapter(adapter)
    return adapter
}