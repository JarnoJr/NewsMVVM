package com.example.newsmvvm.framework.presentation.view.fragments

//import androidx.navigation.fragment.navArgs
import androidx.fragment.app.Fragment
import com.example.newsmvvm.R

private const val TAG = "ArticleFragment"


class ArticleFragment : Fragment(R.layout.article_fragment) {

//    private var _binding: ArticleFragmentBinding? = null
//
//    private val mViewModel: ArticleFragmentViewModel by viewModels()
//
//    private val binding get() = _binding!!
//
//    private val args: ArticleFragmentArgs by navArgs()
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        _binding = ArticleFragmentBinding.bind(view)
//        val article = args.article
//        binding.fab.isChecked = args.article.isChecked
//        initFavButton()
//        binding.webView.apply {
//            webViewClient = WebViewClient()
//            loadUrl(article.url.toString())
//        }
//    }
//
//    private fun initFavButton() {
//        val scaleAnimation = ScaleAnimation(
//            0.7f,
//            1.0f,
//            0.7f,
//            1.0f,
//            Animation.RELATIVE_TO_SELF,
//            0.7f,
//            Animation.RELATIVE_TO_SELF,
//            0.7f
//        )
//        scaleAnimation.duration = 500
//        val bounceInterpolator = BounceInterpolator()
//        scaleAnimation.interpolator = bounceInterpolator
//        binding.fab.setOnCheckedChangeListener { p0, isChecked ->
//            args.article.isChecked = isChecked
//            if (isChecked) {
//                mViewModel.insertArticle(args.article)
//                Snackbar.make(
//                    requireContext(),
//                    requireView(),
//                    "Added to favs",
//                    Snackbar.LENGTH_LONG
//                )
//                    .setBackgroundTint(requireContext().resources.getColor(R.color.success))
//                    .show()
//            } else {
//                mViewModel.removeArticle(args.article)
//                Snackbar.make(
//                    requireContext(),
//                    requireView(),
//                    "Removed from  favs.",
//                    Snackbar.LENGTH_LONG
//                )
//                    .setBackgroundTint(requireContext().resources.getColor(R.color.success))
//                    .show()
//            }
//            p0?.startAnimation(scaleAnimation)
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    inner class WebViewClient() : android.webkit.WebViewClient() {
//        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
//            super.onPageStarted(view, url, favicon)
//            binding.progressBar.visibility = View.VISIBLE
//        }
//
//        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
//            view?.loadUrl(url!!)
//            return true
//        }
//
//        override fun onPageFinished(view: WebView?, url: String?) {
//            super.onPageFinished(view, url)
//            binding.progressBar.visibility = View.GONE
//        }
//    }
}