import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.submision1.database.Favorite
import com.example.submision1.databinding.ItemFavoriteBinding
import com.example.submision1.helper.NoteDiffCallback

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private val listFavorite = ArrayList<Favorite>()
    private var onDeleteClickListener: OnDeleteClickListener? = null

    fun setListNotes(listNotes: List<Favorite>) {
        val diffCallback = NoteDiffCallback(this.listFavorite, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavorite.clear()
        this.listFavorite.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = ItemFavoriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    inner class FavoriteViewHolder(private val binding: ItemFavoriteBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favorite: Favorite) {
            with(binding) {
                user.text = favorite.login
                imageUser.load(favorite.avatarUrl) {
                    transformations(CircleCropTransformation())
                }
                fabbDelet.setOnClickListener {
                    onDeleteClickListener?.onDeleteClick(favorite)
                }
            }
        }
    }

//    hendler untuk click
    fun setOnDeleteClickListener(listener: OnDeleteClickListener) {
        onDeleteClickListener = listener
    }

//    supaya click bisa di akses dari activity
    interface OnDeleteClickListener {
        fun onDeleteClick(favorite: Favorite)
    }


}
