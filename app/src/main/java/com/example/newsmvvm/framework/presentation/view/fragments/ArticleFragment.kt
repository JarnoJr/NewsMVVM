package com.example.newsmvvm.framework.presentation.view.fragments

import android.graphics.Bitmap
import android.view.animation.Animation
import android.view.animation.BounceInterpolator
import android.view.animation.ScaleAnimation
import android.webkit.WebView
import android.widget.CompoundButton
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.example.newsmvvm.R
import com.example.newsmvvm.databinding.ArticleFragmentBinding
import com.example.newsmvvm.framework.presentation.base.BaseFragment
import com.example.newsmvvm.framework.presentation.viewmodel.ArticleFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

private const val TAG = "ArticleFragment"

@AndroidEntryPoint
class ArticleFragment : BaseFragment<ArticleFragmentBinding, ArticleFragmentViewModel>(),
    CompoundButton.OnCheckedChangeListener {


    private val mViewModel: ArticleFragmentViewModel by viewModels()

    private val args: ArticleFragmentArgs by navArgs()

    override val layoutId: Int
        get() = R.layout.article_fragment


    override fun getVM(): ArticleFragmentViewModel = mViewModel

    override fun init() {
        val article = args.article
        mViewModel.getArticle(article.url)
        subscribeObservers()
        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }
    }

    private fun subscribeObservers() {
        mViewModel.article.observe(viewLifecycleOwner) {
            binding.fab.setOnCheckedChangeListener(null)
            binding.fab.isChecked = it != null
            binding.fab.setOnCheckedChangeListener(this)
        }
    }

    inner class WebViewClient() : android.webkit.WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            if (isAdded) showProgress(true)
        }

        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view?.loadUrl(url!!)
            return true
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            if (isAdded) showProgress(false)
        }
    }

    override fun onCheckedChanged(p0: CompoundButton?, isChecked: Boolean) {
        val scaleAnimation = ScaleAnimation(
            0.7f,
            1.0f,
            0.7f,
            1.0f,
            Animation.RELATIVE_TO_SELF,
            0.7f,
            Animation.RELATIVE_TO_SELF,
            0.7f
        )
        scaleAnimation.duration = 500
        val bounceInterpolator = BounceInterpolator()
        scaleAnimation.interpolator = bounceInterpolator
        if (isChecked) {
            mViewModel.insertArticle(args.article)
        } else {
            mViewModel.removeArticle(args.article)
        }
        p0?.startAnimation(scaleAnimation)
    }
}