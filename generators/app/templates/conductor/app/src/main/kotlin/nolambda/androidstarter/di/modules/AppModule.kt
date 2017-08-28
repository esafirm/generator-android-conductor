package <%= appPackage %>.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides

@Module
class AppModule(val context: Context) {
    @Provides fun provideContext(): Context = context
}
