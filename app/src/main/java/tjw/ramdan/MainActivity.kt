package tjw.ramdan

import android.Manifest
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.MutableLiveData
import pub.devrel.easypermissions.EasyPermissions
import tjw.ramdan.databinding.ActivityMainBinding
import tjw.ramdan.domain.network.Failure
import tjw.ramdan.extentions.delay250
import tjw.ramdan.extentions.delay300
import tjw.ramdan.extentions.mLaunchActivity
import tjw.ramdan.extentions.showToast
import tjw.ramdan.ui.HomeActivity
import javax.inject.Inject

class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {
    companion object{
        const val USERS_COLLECTION="users"
    }

    private val loading = MutableLiveData<Boolean>().apply { this.value = false }
    private val requestCode = 1

    @SuppressLint("InlinedApi")
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bind = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        requestPermission()
    }

    private fun requestPermission() {
        if (EasyPermissions.hasPermissions(this, *permissions))
        {
            continuaFlow()
        } else EasyPermissions.requestPermissions(
            this,
            getString(R.string.location_rationale),
            requestCode,
            *permissions
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        continuaFlow()
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        showToast(Failure.SecurityError.f_causeSt)
        delay250 { finish() }
    }
    private fun continuaFlow()
    {
        delay300 {
            mLaunchActivity<HomeActivity>(contex = this)
            finish()


        }
    }
}
