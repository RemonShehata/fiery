package com.iti.fiery

import java.lang.Exception

interface UserCallbacks {
    fun onNewUserCreated()
    fun onError(exception: Exception)
}