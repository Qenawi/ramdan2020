package tjw.go_plus_meeting.dagger

import android.content.Context
import tjw.go_plus_meeting.extentions.networkInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkHandler @Inject constructor(private val context: Context)
{ val isConnected get() = context.networkInfo?.isConnected }