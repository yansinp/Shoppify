package yansin.test.shopease.presentation.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import yansin.test.shopease.R
import yansin.test.shopease.databinding.ItemBannerBinding
import yansin.test.shopease.domain.model.Banner

class BannerAdapter : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {

    private var bannerList: List<Banner> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        val binding = ItemBannerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BannerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(bannerList[position])
    }

    override fun getItemCount(): Int = bannerList.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitList(list: List<Banner>) {
        bannerList = list
        notifyDataSetChanged()
    }

    inner class BannerViewHolder(private val binding: ItemBannerBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(banner: Banner) {
            if (!banner.imageUrl.isNullOrEmpty()){
                binding.bannerImage.load(banner.imageUrl)
            }else{
                binding.bannerImage.load(R.drawable.placeholder)
            }



        }
    }
}
