package yansin.test.shopease.presentation.offer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class OfferViewModel: ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Offer Fragment"
    }
    val text: LiveData<String> = _text
}