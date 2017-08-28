package <%= appPackage %>

import io.kotlintest.Spec
import io.kotlintest.mock.mock
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import <%= appPackage %>.di.components.AppComponent
import org.mockito.Answers
import org.mockito.Mockito

/* --------------------------------------------------- */
/* > Mock */
/* --------------------------------------------------- */

fun <T> Spec.whenever(it: T) = Mockito.`when`(it)
fun <T> Spec.verify(mock: T) = Mockito.verify(mock)
fun <T> Spec.verifyFor(mock: T, times: Int) = Mockito.verify(mock, Mockito.times(times))
fun <T> Spec.verifyNever(mock: T) = Mockito.verify(mock, Mockito.never())
fun <T> Spec.any() = Mockito.any<T>()

inline fun <reified T> mockDeep(): T {
    val klass = T::class
    return Mockito.mock(Class.forName(klass.qualifiedName), Answers.RETURNS_DEEP_STUBS) as T
}

/* --------------------------------------------------- */
/* > Helper */
/* --------------------------------------------------- */

fun Spec.mockAppComponent(body: AppComponent.() -> Unit) {
    MainApp.setComponent(mock<AppComponent>().apply {
        body.invoke(this)
    })
}

fun Spec.initTest() {
    val scheduler = Schedulers.trampoline()

    RxJavaPlugins.reset()
    RxJavaPlugins.setIoSchedulerHandler { scheduler }
    RxJavaPlugins.setComputationSchedulerHandler { scheduler }

    RxAndroidPlugins.reset()
    RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
}
