package tjw.ramdan.dagger

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import tjw.ramdan.dagger.modules.AppFeatures
import tjw.ramdan.domain.GoPlusApplication
import tjw.ramdan.ui.HomeFragment
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        AppFeatures::class]
)
interface AppComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun appModule(module: AppModule): Builder
        fun build(): AppComponent
    }
    fun inject(app: GoPlusApplication)
    fun inject(app: HomeFragment)
}