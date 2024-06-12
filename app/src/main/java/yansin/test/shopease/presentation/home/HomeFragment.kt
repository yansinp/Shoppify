package yansin.test.shopease.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import coil.api.load
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import yansin.test.shopease.R
import yansin.test.shopease.databinding.FragmentHomeBinding
import yansin.test.shopease.presentation.home.adapter.BannerAdapter
import yansin.test.shopease.presentation.home.adapter.CategoryAdapter
import yansin.test.shopease.presentation.home.adapter.FeaturedProductAdapter
import yansin.test.shopease.presentation.home.adapter.ProductAdapter

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val homeViewModel by viewModels<HomeViewModel>()
    private val bannerAdapter by lazy { BannerAdapter() }
    private val categoryAdapter by lazy { CategoryAdapter() }
    private val popularProductsAdapter by lazy { ProductAdapter() }
    private val featuredProductsAdapter by lazy { FeaturedProductAdapter() }


    private lateinit var binding: FragmentHomeBinding
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        binding.recyclerViewBanner.adapter = bannerAdapter
        binding.recyclerViewCategory.adapter = categoryAdapter
        binding.recyclerViewMostPopularProducts.adapter = popularProductsAdapter
        binding.recyclerViewFeaturedProducts.adapter = featuredProductsAdapter

        observeBanner()
        observeHomeState()
        observeMostPopularProduct()
        observeFeaturedProduct()
        observeAdvBanner()
        observeCategory()

    }


    private fun observeHomeState() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.homeState.collectLatest {
                    Log.d("response", "product -- > $it")

                    when (it) {
                        HomeUiState.Empty -> {}
                        is HomeUiState.Error -> {}
                        HomeUiState.Loading -> {}
                        is HomeUiState.Success -> {}
                    }
                }
            }
        }

    }

    private fun observeMostPopularProduct() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.getMostPopularProducts.collectLatest {
                    popularProductsAdapter.submitList(it)
                    Log.d("response", "featured products -- > $it")
                }
            }
        }
    }

    private fun observeFeaturedProduct() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.getFeaturedProducts.collectLatest {
                    featuredProductsAdapter.submitList(it)
                    Log.d("response", "featured products -- > $it")
                }
            }
        }
    }

    private fun observeCategory() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.getCategory.collectLatest {
                    categoryAdapter.submitList(it)
                    Log.d("response", "category -- > $it")
                }
            }
        }
    }

    private fun observeAdvBanner() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.getAdvBanners.collectLatest {
                    Log.d("response", "advBanner -- > ${it.map { it.imageUrl }.firstOrNull()}")
                    if (!it.map { it.imageUrl }.firstOrNull().isNullOrEmpty()) {
                        binding.bannerImage.load(it.map { it.imageUrl }.firstOrNull())
                    } else {
                        binding.bannerImage.load(R.drawable.placeholder)
                    }

                }
            }
        }
    }


    private fun observeBanner() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeViewModel.getBanners.collectLatest {
                    bannerAdapter.submitList(it)
                    Log.d("response", "banner -- > $it")
                }
            }
        }
    }
}
