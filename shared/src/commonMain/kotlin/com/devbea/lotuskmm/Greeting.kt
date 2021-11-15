package com.devbea.lotuskmm

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}