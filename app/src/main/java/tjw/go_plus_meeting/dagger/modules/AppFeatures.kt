package tjw.go_plus_meeting.dagger

import dagger.Module
import tjw.go_plus_meeting.domain.network.NetworkModule

@Module(
    includes = [ViewModelFactoryBindingModule::class
            , NetworkModule::class
    ]
)
class AppFeatures {
    companion object {
        const val UseCaseScopeName = "useCaseScope"
        const val DispatcherUseCaseName = "dispatcherName"
    }
}