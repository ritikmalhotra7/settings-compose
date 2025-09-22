package com.orane.setting_compose.feat_core.domain.controllers

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

class SnackBarController @Inject constructor(){
    private val mEvent = Channel<SnackBarEvent>()
    val event = mEvent.receiveAsFlow()

    suspend fun sendEvent(event: SnackBarEvent){
        mEvent.send(event)
    }
}

data class SnackBarEvent(
    val success:Boolean? = false,
    val message:String,
    val action: SnackBarAction?
)

data class SnackBarAction(
    val label:String,
    val action:(suspend ()->Unit)?
)