package <%= appPackage %>.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import <%= appPackage %>.navigator.AppNavigator
import <%= appPackage %>.navigator.IntentNavigator

@Module
class NavigatorModule(private val navigator: AppNavigator) {

    @Provides
    fun provideAppNavigator(): AppNavigator = navigator

    @Provides
    fun provideIntentNavigator(context: Context): IntentNavigator = IntentNavigator(context)
}
