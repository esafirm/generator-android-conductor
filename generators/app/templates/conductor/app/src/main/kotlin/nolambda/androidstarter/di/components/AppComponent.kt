package <%= appPackage %>.di.components

import android.app.Application
import dagger.Component
import <%= appPackage %>.di.modules.AppModule
import <%= appPackage %>.di.modules.ContextServiceModule
import <%= appPackage %>.di.modules.NetworkModule
import <%= appPackage %>.network.ApiService
import javax.inject.Singleton

@Singleton
@Component(modules = arrayOf(
        AppModule::class,
        ContextServiceModule::class,
        NetworkModule::class
))
interface AppComponent {

    /* --------------------------------------------------- */
    /* > Singletons */
    /* --------------------------------------------------- */

    fun apiService(): ApiService

    /* --------------------------------------------------- */
    /* > Subcomponent */
    /* --------------------------------------------------- */

    fun activityComponent(): ActivityComponent.Builder

    companion object {
        fun initialize(app: Application): AppComponent =
                DaggerAppComponent.builder()
                        .appModule(AppModule((app)))
                        .build()
    }
}
