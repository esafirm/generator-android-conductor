package <%= appPackage %>.screens

import android.view.View
import android.widget.TextView
import butterknife.BindView
import io.reactivex.android.schedulers.AndroidSchedulers
import <%= appPackage %>.R
import <%= appPackage %>.commons.AbsController
import <%= appPackage %>.network.ApiService
import javax.inject.Inject

class ApiExampleController : AbsController() {

    @BindView(R.id.main_txt_hello) lateinit var txtTitle: TextView

    @Inject lateinit var apiService: ApiService

    override fun onSetupComponent() {
        component.inject(this)
    }

    override fun getLayoutResId(): Int = R.layout.controller_main

    override fun onViewBound(bindingResult: View) {
        txtTitle.setOnClickListener {
            apiService.getUserAgent()
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe { (userAgent), _ ->
                        txtTitle.text = userAgent
                    }
        }
    }
}
