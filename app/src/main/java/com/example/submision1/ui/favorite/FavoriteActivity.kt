package com.example.submision1.ui.favorite

import FavoriteAdapter
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submision1.R
import com.example.submision1.database.Favorite
import com.example.submision1.databinding.ActivityFavoriteBinding
import com.example.submision1.helper.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {


    companion object{
        const val ALERT_DIALOG_DELETE = 10
        const val ALERT_DIALOG_CLOSE = 20

    }

    private var _activityFavoriteBinding : ActivityFavoriteBinding ? = null
    private lateinit var favoriteViewModel: FavoriteViewModel
    private val binding get() = _activityFavoriteBinding

    private lateinit var adapter: FavoriteAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

         favoriteViewModel = obtainModel(this@FavoriteActivity)
        favoriteViewModel.getAllFavorite().observe(this){listFavorite->
            if (listFavorite != null){
                adapter.setListNotes(listFavorite)
            }
        }

        adapter = FavoriteAdapter()

        binding?.rvFavorite?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavorite?.setHasFixedSize(true)
        binding?.rvFavorite?.adapter = adapter

//        ketika float hapus di tekan

        adapter.setOnDeleteClickListener(object : FavoriteAdapter.OnDeleteClickListener {
            override fun onDeleteClick(favorite: Favorite) {
                showAlertDialog(ALERT_DIALOG_DELETE , favorite)
            }

        })

    }

    private fun obtainModel(activity: AppCompatActivity):FavoriteViewModel  {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity,factory).get(FavoriteViewModel::class.java)

    }


    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }

//    menampilkan dialog
private fun showAlertDialog(type: Int , favorite: Favorite) {
    val isDialogClose = type == ALERT_DIALOG_CLOSE
    val dialogTitle: String
    val dialogMessage: String
    if (isDialogClose) {
        dialogTitle = getString(R.string.cancel)
        dialogMessage = getString(R.string.message_cancel)
    } else {
        dialogMessage = getString(R.string.message_delete)
        dialogTitle = getString(R.string.delete)
    }
    val alertDialogBuilder = AlertDialog.Builder(this)
    with(alertDialogBuilder) {
        setTitle(dialogTitle)
        setMessage(dialogMessage)
        setCancelable(false)
        setPositiveButton(getString(R.string.yes)) { _, _ ->
            if (!isDialogClose) {
               favoriteViewModel.delete(favorite as Favorite)
            }
            finish()
        }
        setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
    }
    val alertDialog = alertDialogBuilder.create()
    alertDialog.show()
}
}