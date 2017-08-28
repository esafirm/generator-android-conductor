package <%= appPackage %>.di.helpers

interface HasComponent<T> {
    fun getComponent(): T
}
