package tjw.ramdan.domain

import android.app.Application
import android.content.Context
import timber.log.Timber
import tjw.ramdan.BuildConfig
import tjw.ramdan.dagger.AppComponent
import tjw.ramdan.dagger.AppModule
import tjw.ramdan.dagger.DaggerAppComponent

class GoPlusApplication:Application()
{
    override fun onCreate() {
        super.onCreate()
        setupTimber()
        initDagger()
    }
    private fun setupTimber(){
        if (BuildConfig.DEBUG)
        {
           Timber.plant(Timber.DebugTree())
        }
    }
    private fun initDagger() {
        appComponent =
            DaggerAppComponent.builder().application(this).appModule(AppModule(this)).build()
        appComponent.inject(this)

    }
    companion object {
        lateinit var appComponent: AppComponent private set
        var context: Context? = null

    }
}