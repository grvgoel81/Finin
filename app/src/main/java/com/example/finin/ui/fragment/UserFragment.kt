package com.example.finin.ui.fragment

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.ProgressDialog.show
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.ViewCompat.animate
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.finin.R
import com.example.finin.base.BaseFragment
import com.example.finin.base.CustomViewModelFactory
import com.example.finin.base.EventObserver
import com.example.finin.model.Data
import com.example.finin.ui.adapter.UserAdapter
import com.example.finin.utils.NetworkUtils
import com.example.finin.utils.hide
import com.example.finin.utils.show
import com.example.finin.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_user.*
import javax.inject.Inject

class UserFragment : BaseFragment() {

    companion object {
        val TAG = UserFragment::class.java.name
        const val ANIMATION_DURATION = 1000.toLong()
        fun newInstance(): UserFragment {
            return UserFragment()
        }
    }

    @Inject
    lateinit var viewModelFactory: CustomViewModelFactory

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    private val usersData by lazy { ArrayList<Data>() }
    private lateinit var userAdapter: UserAdapter
    private var currentPage: Int = 1
    private var total: Int? = 0

    override fun getLayoutRes(): Int = R.layout.fragment_user

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handleNetworkChanges()
        viewModel.fetchUsersData(currentPage, 3)
    }

    override fun viewInitialization(view: View) {
        initAdapter()
        observeDataChange()
    }

    private fun initAdapter() {
        userAdapter = UserAdapter(usersData)
        with(rvUsers) {
            this.visibility = android.view.View.VISIBLE
            layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                context,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL,
                false
            )
            adapter = userAdapter
        }

        rvUsers.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            private var actionExecuted: Boolean = false
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)

                when (newState) {
                    RecyclerView.SCROLL_STATE_DRAGGING -> actionExecuted = false
                    RecyclerView.SCROLL_STATE_SETTLING -> actionExecuted = false
                    RecyclerView.SCROLL_STATE_IDLE -> {

                        val scrolledPosition =
                            (recyclerView.layoutManager as? LinearLayoutManager)?.findFirstVisibleItemPosition()
                                ?: return

                        if (scrolledPosition != RecyclerView.NO_POSITION && !actionExecuted) {
                            if (total!! > usersData.size) {
                                progressBar.visibility = View.VISIBLE
                                currentPage += 1
                                viewModel.fetchUsersData(currentPage, 3)
                                actionExecuted = true
                            }
                        }

                    }
                }
            }
        })

    }

    private fun observeDataChange() {
        viewModel.loadingState.observe(viewLifecycleOwner, Observer { showLoadingState(it) })
        viewModel.apiError.observe(viewLifecycleOwner, EventObserver { handleError(it) })
        viewModel.userLiveData.observe(viewLifecycleOwner, EventObserver { it ->
            progressBar.visibility = View.GONE
            total = it.total
            if (it.data?.isNotEmpty()!!) {
                it.data?.let { it1 -> setUsersData(it1) }
            }
        })
    }

    private fun setUsersData(list: List<Data>) {
        usersData.addAll(list)
        with(userAdapter) {
            notifyDataSetChanged()
        }
    }

    private fun handleNetworkChanges() {
        context?.let {
            NetworkUtils.getNetworkLiveData(it).observe(this, Observer { isConnected ->
                if (!isConnected) {
                    textViewNetworkStatus.text = getString(R.string.text_no_connectivity)
                    networkStatusLayout.apply {
                        show()
                        setBackgroundColor(context.resources.getColor(R.color.colorStatusNotConnected))
                    }
                } else {
                    textViewNetworkStatus.text = getString(R.string.text_connectivity)
                    networkStatusLayout.apply {
                        setBackgroundColor(context.resources.getColor(R.color.colorStatusConnected))

                        animate()
                            .alpha(1f)
                            .setStartDelay(ANIMATION_DURATION)
                            .setDuration(ANIMATION_DURATION)
                            .setListener(object : AnimatorListenerAdapter() {
                                override fun onAnimationEnd(animation: Animator) {
                                    hide()
                                }
                            })
                    }
                }
            })
        }
    }

    override fun showLoadingState(loading: Boolean) {
        if (loading) {
            shimmer_view_container.visibility = View.VISIBLE
            shimmer_view_container.startShimmerAnimation()
        } else {
            shimmer_view_container.stopShimmerAnimation()
            shimmer_view_container.visibility = View.GONE
        }
    }

    override fun onError(message: String) {
        showToast(message)
    }
}