package tjw.ramdan.ui

import android.os.Bundle
import tjw.ramdan.R
import tjw.ramdan.databinding.HomeActivityBinding
import tjw.ramdan.domain.base.BaseActivity

class HomeActivity:BaseActivity<HomeActivityBinding>()
{
    override fun layoutId()= R.layout.home_activity
    override fun fragment()=HomeFragment.newInstance()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addFragment(fragment())
    }
}