package com.bangkit.cakrawala.ui.home

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bangkit.cakrawala.databinding.ItemRvHomeBinding

data class MenuRvModal(
    val menuTitle: String,
    val menuImage: Int,
    val menuFragment: Fragment
)


class MenuGridAdapter(
    private val courseList: ArrayList<MenuRvModal>,
    private val context: Context,
) : RecyclerView.Adapter<MenuGridAdapter.CourseViewHolder>() {


    private lateinit var onItemClickCallback: OnItemClickCallback

    interface OnItemClickCallback {
        fun onItemClicked(data: MenuRvModal)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CourseViewHolder {
        val itemView = ItemRvHomeBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return CourseViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {

        holder.courseNameTV.text = courseList[position].menuTitle
        holder.courseIV.setImageResource(courseList[position].menuImage)
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(courseList[position])
            Toast.makeText(holder.itemView.context, "Halo ${courseList[position].menuTitle}", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {

        return courseList.size
    }

    inner class CourseViewHolder(itemView: ItemRvHomeBinding) : RecyclerView.ViewHolder(itemView.root) {
        // on below line we are initializing our course name text view and our image view.
        val courseNameTV: TextView = itemView.idTVCourse
        val courseIV: ImageView = itemView.idIVCourse



    }
}