package <%= appPackage %>.screens

import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.controller_main.*
import <%= appPackage %>.R
import <%= appPackage %>.commons.AbsScreen
import <%= appPackage %>.network.ApiService
import javax.inject.Inject

class ApiExampleStatefulScreen : AbsScreen() {

    @Inject lateinit var apiService: ApiService

    init {
        onInit = { component.inject(this) }
    }

    override fun createView() = xml(R.layout.controller_main)

    override fun render() {
        main_txt_hello.setOnClickListener {
            apiService.getUserAgent()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { (userAgent), _ ->
                        main_txt_hello.text = userAgent
                    }
        }
    }
}
