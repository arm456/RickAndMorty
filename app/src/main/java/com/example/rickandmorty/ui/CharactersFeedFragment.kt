package com.example.rickandmorty.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.R
import com.example.rickandmorty.dagger2.RickMortyApplication
import com.example.rickandmorty.databinding.FragmentCharactersFeedBinding
import com.example.rickandmorty.repository.RickMortyRepository
import com.example.rickandmorty.viewmodel.CharactersFeedViewModel
import javax.inject.Inject

class CharactersFeedFragment(contentLayoutId: Int) : Fragment(contentLayoutId) {
    private lateinit var charactersFeedViewModel: CharactersFeedViewModel
    private lateinit var charactersAdapter: CharactersFeedAdapter

    private var _binding: FragmentCharactersFeedBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var repository: RickMortyRepository

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharactersFeedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ((activity?.application) as RickMortyApplication).appComponent.inject(this)
        charactersFeedViewModel =
            ViewModelProvider(this, CharactersFeedViewModel.Factory(repository = repository)).get(
                CharactersFeedViewModel::class.java
            )
        charactersFeedViewModel.getCharactersResponse()

        val layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(binding.recyclerView.context, layoutManager.orientation)
        binding.recyclerView.addItemDecoration(dividerItemDecoration)

        charactersAdapter = CharactersFeedAdapter(charactersFeedViewModel)
        binding.recyclerView.adapter = charactersAdapter

        charactersFeedViewModel.charactersLiveData.observe(
            viewLifecycleOwner,
            Observer {
                binding.loadingView.loadingSpinner.visibility = View.GONE
                charactersAdapter.setData(it)
            }
        )

        charactersFeedViewModel.characterItemClickLiveData.observe(
            viewLifecycleOwner,
            Observer { onClickEvent ->
                onClickEvent.getDataIfReceived()?.let {
                    activity?.supportFragmentManager?.beginTransaction()?.add(
                        R.id.container,
                        LocationDetailsFragment.newInstance(
                            it,
                            R.layout.fragment_character_details
                        )
                    )?.addToBackStack(LocationDetailsFragment::class.java.canonicalName)?.commit()
                }
            }
        )

        charactersFeedViewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            binding.loadingView.loadingSpinner.visibility = View.GONE
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}