package <%= appPackage %>

import android.support.multidex.MultiDexApplication
import <%= appPackage %>.di.components.AppComponent
import timber.log.Timber

class MainApp : MultiDexApplication() {

    companion object {
        var appComponent: AppComponent? = null

        fun setComponent(appComponent: AppComponent) {
            this.appComponent = appComponent
        }
    }

    override fun onCreate() {
        super.onCreate()
        setupComponent()
        setupTimber()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupComponent() {
        appComponent = AppComponent.initialize(this)
    }
}
