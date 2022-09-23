package droid.telemed.mts.ru.telemed.refactor.appbase

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.example.funymath.appbase.utils.observeInLifecycle
import com.example.funymath.appbase.viewmodel.BaseViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

abstract class DialogFragmentBase<ViewModel : BaseViewModel, ViewBind : ViewBinding> :
    DialogFragment() {

    abstract val viewModel: ViewModel
    abstract val binding: ViewBind

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onView()
        onEach()
        onEach(viewModel.showDefaultError) {
            toast(it)
        }
    }

    protected open fun onView() {}

    protected open fun onEach() {}

    protected fun toast(text: String?) {
        text?.let { Toast.makeText(activity, it, Toast.LENGTH_LONG).show() }
    }

    protected inline fun <reified T> onEach(
        flow: Flow<T>,
        crossinline action: (T) -> Unit
    ) = flow
        .takeIf { isAdded }
        ?.onEach { action(it) }
        ?.observeInLifecycle(viewLifecycleOwner)
}