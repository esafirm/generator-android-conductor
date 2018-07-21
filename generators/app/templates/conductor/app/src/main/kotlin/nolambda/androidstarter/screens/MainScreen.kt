package <%= appPackage %>.screens

import com.esafirm.conductorextra.addLifecycleCallback
import kotlinx.android.synthetic.main.controller_main.*
import <%= appPackage %>.R
import <%= appPackage %>.commons.AbsScreen
import <%= appPackage %>.navigator.AppNavigator
import javax.inject.Inject

class MainScreen : AbsScreen() {

    init {
        addLifecycleCallback(onPostCreateView = { _, _, remover ->
            component.inject(this)
            remover()
        })
        screenView = xml(R.layout.controller_main)
    }

    @Inject lateinit var navigator: AppNavigator

    override fun render() {
        main_txt_hello.setOnClickListener {
            navigator.goToDetail()
        }
    }
}
