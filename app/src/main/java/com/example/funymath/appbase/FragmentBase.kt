package com.example.funymath.appbase

import android.content.Context
import android.content.res.TypedArray
import android.graphics.drawable.Drawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.addCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavDirections
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.viewbinding.ViewBinding
import com.example.funymath.R
import com.example.funymath.appbase.utils.observeInLifecycle
import com.example.funymath.appbase.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

abstract class FragmentBase<ViewModel : BaseViewModel, ViewBind : ViewBinding> :
    Fragment() {

    abstract val viewModel: ViewModel
    abstract val binding: ViewBind
    val ARGUMENT_INPUT_DATA = "ARGS_INPUT_DATA"

    protected val supportFragmentManager: FragmentManager? by lazy {
        activity?.supportFragmentManager
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onEach()
        onView()
        onViewClick()


        onEach(viewModel.showDefaultError) {
            Toast.makeText(context, it ?: getString(R.string.net_error_default), Toast.LENGTH_SHORT)
                .show()
        }

    }

    protected open fun onView() {}

    protected open fun onViewClick() {}

    protected open fun onEach() {}

    protected inline fun <reified T> onEach(flow: Flow<T>, crossinline action: (T) -> Unit) =
        view?.run {
            if (!this@FragmentBase.isAdded) return@run
            flow.onEach { action(it ?: return@onEach) }.observeInLifecycle(viewLifecycleOwner)
        }

    protected fun toast(text: String?, code: Int? = null) {
        if (text == null && code == null)
            return

        val formattedText = if (text != null && code != null)
            "$code - $text"
        else if (text != null)
            "$text"
        else
            "errorCode = $code"

        Toast.makeText(activity, formattedText, Toast.LENGTH_LONG).show()
    }

    fun getRecyclerDecoration(context: Context): DividerItemDecoration {
        val listDividers = intArrayOf(android.R.attr.listDivider)
        val typedArray: TypedArray = context.obtainStyledAttributes(listDividers)
        val divider: Drawable? = typedArray.getDrawable(0)
        val inset = resources.getDimensionPixelSize(R.dimen.dp_16)
        val insetDivider = InsetDrawable(divider, inset, 0, inset, 0)
        typedArray.recycle()

        val itemDecoration = DividerItemDecoration(context, LinearLayout.VERTICAL)

        itemDecoration.setDrawable(insetDivider)

        return itemDecoration
    }


    protected fun navigateTo(directions: NavDirections) {
        val navHostFragment = Navigation.findNavController(requireActivity(), R.id.content_frame)
        navHostFragment.navigate(directions)
    }

    protected fun navigateTo(directions: NavDirections, navOptions: NavOptions) {
        val navHostFragment = Navigation.findNavController(requireActivity(), R.id.content_frame)
        navHostFragment.navigate(directions, navOptions)
    }

    protected fun fragmentResultListener(requestKey: String, callback: (String, Bundle) -> Unit) {
        supportFragmentManager?.setFragmentResultListener(
            requestKey,
            viewLifecycleOwner
        ) { key, bundle ->
            callback(key, bundle)
        }
    }

    protected fun fragmentResult(requestKey: String, bundle: Bundle = bundleOf()) {
        supportFragmentManager?.setFragmentResult(requestKey, bundle)
    }

    protected fun backStackListener(listener: () -> Unit) {
        activity?.onBackPressedDispatcher?.addCallback(viewLifecycleOwner) {
            listener()
        }
    }
}

