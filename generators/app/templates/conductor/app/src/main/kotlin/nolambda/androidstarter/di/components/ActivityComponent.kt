package <%= appPackage %>.di.components

import dagger.Subcomponent
import <%= appPackage %>.di.modules.ActivityModule
import <%= appPackage %>.di.modules.NavigatorModule
import <%= appPackage %>.di.scopes.ActivityScope
import <%= appPackage %>.navigator.AppNavigator
import <%= appPackage %>.navigator.IntentNavigator

@ActivityScope
@Subcomponent(modules = [ActivityModule::class, NavigatorModule::class])
interface ActivityComponent {

    fun appNavigator(): AppNavigator
    fun intentNavigator(): IntentNavigator

    /* --------------------------------------------------- */
    /* > Subcomponent */
    /* --------------------------------------------------- */

    fun controllerComponent(): ControllerComponent.Builder

    /* --------------------------------------------------- */
    /* > Builder */
    /* --------------------------------------------------- */

    @Subcomponent.Builder
    interface Builder {
        fun activityModule(activityModule: ActivityModule): ActivityComponent.Builder
        fun navigatorModule(navigatorModule: NavigatorModule): ActivityComponent.Builder
        fun build(): ActivityComponent
    }
}
