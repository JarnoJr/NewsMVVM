package com.example.newsmvvm.framework.presentation.view.fragments

//@AndroidEntryPoint
//class FavouritesFragment :
//    BaseFragment<FavouritesFragmentBinding>(FavouritesFragmentBinding::inflate),
//    FavouritesAdapter.OnItemClickListener {
//
//    private val mViewModel: FavouritesFragmentViewModel by viewModels()
//
//    @Inject
//    lateinit var mAdapter: FavouritesAdapter
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        mAdapter.setOnClickListener(this)
//        initRecycler()
//        swipeToDelete()
//        observeViewModel()
//    }
//
//    private fun initRecycler() {
//        binding.rvFavouriteNews.apply {
//            layoutManager = LinearLayoutManager(requireContext())
//            adapter = mAdapter
//        }
//    }

//    private fun observeViewModel() {
////        mViewModel.getArticles().observe(viewLifecycleOwner) { news ->
////            mAdapter.differ.submitList(news)
////        }
//    }

//    override fun onArticleClick(article: Article) {
//        val action =
//            FavouritesFragmentDirections.actionFavouritesFragment2ToArticleFragment2(article)
//        findNavController().navigate(action)
//
//    private fun swipeToDelete() {
//        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(
//            ItemTouchHelper.UP or ItemTouchHelper.DOWN,
//            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
//        ) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder
//            ): Boolean = true
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val position = viewHolder.bindingAdapterPosition
//                val article = mAdapter.differ.currentList[position]
////                mViewModel.removeArticle(article)
//                Snackbar.make(
//                    requireContext(),
//                    requireView(),
//                    "Removed from  favs.",
//                    Snackbar.LENGTH_LONG
//                ).apply {
//                    setActionTextColor(requireContext().resources.getColor(R.color.white))
//                    setAction("Undo") {
////                        mViewModel.saveArticle(article)
//                    }
//                    setBackgroundTint(requireContext().resources.getColor(R.color.success))
//                }
//                    .show()
//            }
//        }
//
//        ItemTouchHelper(itemTouchHelperCallback).apply {
//            attachToRecyclerView(binding.rvFavouriteNews)
//        }
//
//    }