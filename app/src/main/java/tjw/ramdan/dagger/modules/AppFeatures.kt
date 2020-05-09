package tjw.ramdan.dagger.modules

import dagger.Module
import tjw.ramdan.dagger.ViewModelFactoryBindingModule

@Module(
    includes = [ViewModelFactoryBindingModule::class
            , NetworkModule::class,Location_Module::class
    ]
)
class AppFeatures {
    companion object {
        const val UseCaseScopeName = "useCaseScope"
        const val DispatcherUseCaseName = "dispatcherName"
    }
}