package com.example.funymath.appbase

import android.os.*
import android.util.Log
import android.view.*
import androidx.navigation.*
import androidx.viewbinding.*
import com.example.funymath.appbase.viewmodel.BaseViewModel

abstract class FragmentBaseMVVM<ViewModel : BaseViewModel, ViewBind : ViewBinding> :
    FragmentBase<ViewModel, ViewBind>() {

    protected open lateinit var navController: NavController

    private lateinit var  navOptions: NavOptions

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        try {
            navOptions = NavOptions.Builder()
                .setPopUpTo(navController.graph.startDestinationId, true)
                .setLaunchSingleTop(true)
                .build()
        }
        catch (e: IllegalStateException){
            Log.d("Navigation","Graph is null IllegalStateException")
        }
    }

    protected fun popBackStack() {
        navController.popBackStack()
    }

    protected fun navigateFragment(destinationId: Int, arg: Bundle? = null) {
        navController.navigate(destinationId, arg)
    }

    protected fun navigateRootFragment(destinationId: Int, arg: Bundle? = null, options: NavOptions? = null) {
        navController.navigate(destinationId, arg, options ?: navOptions)
    }

    protected fun navigateFragment(destinations: NavDirections) {
        navigate(destinations)
        //  navController.navigate(destinations)
    }

    private fun navigate(destination: NavDirections) = with(navController) {
        currentDestination?.getAction(destination.actionId)
            ?.let { navigate(destination) }
    }

}