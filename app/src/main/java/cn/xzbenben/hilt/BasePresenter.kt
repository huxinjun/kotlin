package cn.xzbenben.hilt

import dagger.Component
import dagger.Module
import dagger.Provides

open class BasePresenter<V : UII>(var mUII: V)

@Module
class TestPresenter(var uii: TestUII) : BasePresenter<TestUII>(uii) {

    fun testData() {
        uii.onData()
    }

    @Provides
    fun providePresenter(): TestPresenter = TestPresenter(uii)
}

@Module
class TestPresenter2(var uii: TestUII2) {
    fun testData2() {
        uii.onData("this is test2 data")
    }

    @Provides
    fun providePresenter() = TestPresenter2(uii)
}

//@Component(modules = [TestModule::class, TestModule2::class])
//abstract class MvpComponent {
//    abstract fun injectTo(obj: DaggerTestActivity)
//    abstract fun injectTo(obj: DaggerTest2Activity)
//}

interface BaseComponent<T> {
    fun injectTo(target: T)
}

@Component(modules = [TestPresenter::class])
interface TestComponent : BaseComponent<DaggerTestActivity>

//@Component(modules = [TestPresenter2::class])
//interface TestComponent2 : BaseComponent<DaggerTest2Activity>

@Component(modules = [TestPresenter::class, TestPresenter2::class])
interface Test2Component : BaseComponent<DaggerTest2Activity>


//@Module
//class TestModule(private val uii: TestUII) {
//    @Provides
//    fun providePresenter(): TestPresenter = TestPresenter(uii)
//}

//@Module
//class TestModule2(private val uii: TestUII2) {
//    @Provides
//    fun providePresenter() = TestPresenter2(uii)
//}