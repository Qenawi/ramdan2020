package tjw.ramdan.dagger.modules

import android.content.Context
import tjw.ramdan.extentions.networkInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHandler @Inject constructor(private val context: Context)
{ val isConnected get() = context.networkInfo?.isConnected }