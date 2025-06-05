package com.example.blinkitclone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.blinkitclone.adapters.AdapterCategory
import com.example.blinkitclone.databinding.FragmentHomeBinding
import com.example.blinkitclone.models.CategoryItem

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!  // Safe to use between onCreateView and onDestroyView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setAllCategories()  // ✅ Safe to call here
    }

    private fun setAllCategories() {
        val categoryList = ArrayList<CategoryItem>()
        for (i in Constants.allProductCateImg.indices) {
            categoryList.add(
                CategoryItem(
                    Constants.allProductCateImg[i],
                    Constants.allProductCategory[i]
                )
            )
        }
        binding.rvCategary.adapter = AdapterCategory(categoryList)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null  // Avoid memory leaks
    }
}
