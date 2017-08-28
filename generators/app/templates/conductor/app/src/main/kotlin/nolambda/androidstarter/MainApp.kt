package <%= appPackage %>

import android.support.multidex.MultiDexApplication
import <%= appPackage %>.di.components.AppComponent

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
    }

    private fun setupComponent() {
        appComponent = AppComponent.initialize(this)
    }
}
