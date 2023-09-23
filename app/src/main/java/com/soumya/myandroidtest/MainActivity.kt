package com.soumya.myandroidtest

import android.app.Dialog
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import coil.load
import com.soumya.myandroidtest.adapter.ImageShowAdapter
import com.soumya.myandroidtest.adapter.LoaderStateAdapter
import com.soumya.myandroidtest.databinding.ActivityMainBinding
import com.soumya.myandroidtest.databinding.DialogViewLayoutBinding
import com.soumya.myandroidtest.model.ImageShowData
import com.soumya.myandroidtest.viewModel.ImageShowViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageShowAdapter: ImageShowAdapter
    private val viewModel: ImageShowViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*Use Splash Api In this Project*/
        installSplashScreen()
        /*Use ViewBinding For This Project */
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()

        lifecycleScope.launchWhenStarted {

            viewModel.getAllPagingImages.collectLatest { response ->
                binding.apply {
                    delay(500)
                    progressBar.isVisible = false
                    recyclerView.isVisible = true
                }
                imageShowAdapter.submitData(response)
            }
        }
    }

    private fun setUpRecyclerView() {
        /*Set Recyclerview and show Popup With Clickable Data*/
        imageShowAdapter = ImageShowAdapter(onItemClick = {

            showTitleAndDescInPopup(it)
        })
        binding.recyclerView.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            setHasFixedSize(true)
            adapter = imageShowAdapter.withLoadStateHeaderAndFooter(
                header = LoaderStateAdapter { imageShowAdapter.retry() },
                footer = LoaderStateAdapter { imageShowAdapter.retry() }
            )
        }
    }


    private fun showTitleAndDescInPopup(data: ImageShowData) {
        val dialog = Dialog(this@MainActivity)
        val binding = DialogViewLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        binding.apply {
            val imageLink = data.download_url
            imgView.load(imageLink) {
                placeholder(R.drawable.male)
                crossfade(true)
                crossfade(1000)
            }
            tvTitle.text = data.author
            tvDesc.text = getString(R.string.no_data)


            btnClose.setOnClickListener {
                dialog.dismiss()
            }

        }


        dialog.show()


    }


}