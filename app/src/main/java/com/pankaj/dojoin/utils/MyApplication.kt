package com.pankaj.dojoin.utils

import android.content.Context
import android.os.Build
import android.os.StrictMode
import androidx.multidex.MultiDex
import androidx.multidex.MultiDexApplication
import com.pankaj.dojoin.apiclient.MyApi
import com.pankaj.dojoin.apiclient.NetworkConnectionInterceptor
import com.pankaj.dojoin.repository.AppRepository
import com.pankaj.dojoin.ui.category.CategoryFactory
import com.pankaj.dojoin.ui.categroy_selection.SelectionFactory
import com.pankaj.dojoin.ui.video_recording.VideoRecordingFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton


class MyApplication : MultiDexApplication(), KodeinAware {

    override fun onCreate() {
        super.onCreate()

        application = this


        val builder = StrictMode.VmPolicy.Builder()
        StrictMode.setVmPolicy(builder.build())

        StrictMode.setVmPolicy(builder.build())
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure()
        }

    }

    companion object {
        var application: MyApplication? = null
        val TAG = "MyApp"

        fun getInstance(): MyApplication? {
            return application
        }


    }


    override val kodein = Kodein.lazy {
        import(androidXModule(this@MyApplication))

        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { MyApi(instance()) }
        bind() from singleton {
            AppRepository(
                instance()
            )

        }
        bind() from provider { CategoryFactory(instance()) }
        bind() from provider { SelectionFactory() }
        bind() from provider { VideoRecordingFactory() }


    }

    override fun attachBaseContext(base: Context) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }


}