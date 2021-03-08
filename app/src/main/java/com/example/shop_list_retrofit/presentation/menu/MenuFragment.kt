package com.example.shop_list_retrofit.presentation.menu

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.shop_list_retrofit.R
import com.example.shop_list_retrofit.databinding.FragmentMenuBinding

class MenuFragment : Fragment() {
    lateinit var binding: FragmentMenuBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        val viewListShopping = ViewListShoppingFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMenuBinding.inflate(layoutInflater)
        binding.apply {
            menuAddItem.setOnClickListener{
                findNavController().navigate(R.id.action_menuFragment_to_formFragment)
            }
            menuItemList.setOnClickListener {
                findNavController().navigate(R.id.action_menuFragment_to_listFragment)
            }
        }
        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = MenuFragment()
    }
}