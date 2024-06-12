package yansin.test.shopease.presentation.home.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import yansin.test.shopease.R
import yansin.test.shopease.databinding.ItemProductBinding
import yansin.test.shopease.domain.model.FeaturedProduct
import yansin.test.shopease.domain.model.MostPopularProduct

class FeaturedProductAdapter : RecyclerView.Adapter<FeaturedProductAdapter.ProductViewHolder>() {

    private var productList: List<FeaturedProduct> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.bind(productList[position])
    }

    override fun getItemCount(): Int = productList.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<FeaturedProduct>) {
        productList = list
        notifyDataSetChanged()
    }

    inner class ProductViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(product: FeaturedProduct) {
            binding.apply {
                if(!product.productImage .isNullOrEmpty()){
                    imageProduct.load(product.productImage)
                }else{
                    imageProduct.load(product.productImage) {
                        placeholder(R.drawable.placeholder)
                    }
                }
                productName.text = product.productName
                offerPriceTextView.text = product.offerPrice
                actualPriceTextView.paintFlags =
                    actualPriceTextView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
                actualPriceTextView.text = product.actualPrice
                if (!(product.discount?.split("%")?.get(0)
                        ?.toInt()!! > 0 && product.actualPrice == product.offerPrice)
                ) {
                    offerPercentage.visibility = View.VISIBLE
                    offerPercentage.text = product.discount

                } else {
                    offerPercentage.visibility = View.GONE
                }
                binding.productRatingBar.rating = product.productRating?.toFloat() ?: 0.0f
            }

        }
    }
}