package com.example.threading.injection

import com.example.threading.MainActivity
import dagger.Component

@MainScope
@Component(modules = [MainModule::class])
interface MainComponent {
    fun injectMainActivity(mainActivity: MainActivity)
}