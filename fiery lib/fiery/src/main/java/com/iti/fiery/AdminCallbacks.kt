package com.iti.fiery

import java.lang.Exception

interface AdminCallbacks {
    fun onLoggedin()
    fun  onLoggedout()
    fun onError(exception: Exception)
}