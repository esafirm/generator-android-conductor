package <%= appPackage %>.di.modules

import android.app.Activity
import android.content.Context
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import <%= appPackage %>.di.qualifiers.ActivityContext

@Module
class ActivityModule(private val activity: AppCompatActivity) {

    @Provides
    @ActivityContext
    fun provideContext(): Context = activity

    @Provides
    fun provideActivity(): Activity = activity

    @Provides
    fun provideFragmentManager(): FragmentManager = activity.supportFragmentManager
}
