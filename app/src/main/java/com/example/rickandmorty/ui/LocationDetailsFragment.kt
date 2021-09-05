package com.example.rickandmorty.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.rickandmorty.dagger2.RickMortyApplication
import com.example.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.example.rickandmorty.network.domain.ResultX
import com.example.rickandmorty.repository.RickMortyRepository
import com.example.rickandmorty.viewmodel.LocationDetailsViewModel
import javax.inject.Inject

class LocationDetailsFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {
    private lateinit var detailsViewModel: LocationDetailsViewModel
    private var _binding: FragmentCharacterDetailsBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var repository: RickMortyRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ((activity?.application) as RickMortyApplication).appComponent.inject(this)
        detailsViewModel = ViewModelProvider(
            this, LocationDetailsViewModel.Factory(repository = repository)
        ).get(LocationDetailsViewModel::class.java)

        arguments?.getInt(ID)?.let { detailsViewModel.getItemDetails(it) }

        detailsViewModel.detailsLiveData.observe(viewLifecycleOwner, Observer { item ->
            binding.loadingView.loadingSpinner.visibility = View.GONE
            binding.itemTitle.text = item.name
            binding.itemDesc.text = item.type
            binding.itemRating.text = item.dimension
            binding.itemAddress.text = item.residents.size.toString()
            arguments?.getString(IMAGE)?.let {
                Glide.with(view.context).load(it).into(binding.itemDetailImageView)
            }
        })

        detailsViewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            binding.loadingView.loadingSpinner.visibility = View.GONE
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        private const val ID = "ID"
        private const val IMAGE = "IMAGE"

        @JvmStatic
        fun newInstance(id: Int, contentLayoutId: Int): LocationDetailsFragment {
            return LocationDetailsFragment(contentLayoutId).apply {
                arguments = Bundle().apply {
                    putInt(ID, id)
//                    putString(IMAGE, image)
                }
            }
        }
    }
}