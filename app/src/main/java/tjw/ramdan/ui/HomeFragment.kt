package tjw.ramdan.ui


import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.icu.util.IslamicCalendar
import android.icu.util.ULocale
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.azan.Azan
import com.azan.Method
import com.azan.astrologicalCalc.SimpleDate
import kotlinx.android.synthetic.main.home_fragment.*
import tjw.ramdan.R
import tjw.ramdan.dagger.modules.LocationManger
import tjw.ramdan.databinding.HomeFragmentBinding
import tjw.ramdan.domain.GoPlusApplication
import tjw.ramdan.domain.base.BaseFragment
import tjw.ramdan.domain.network.Failure
import tjw.ramdan.extentions.getHijriDateToday
import java.time.format.DateTimeFormatter
import java.util.*
import javax.inject.Inject
import kotlin.math.floor

class HomeFragment : BaseFragment() {
    companion object{
      fun newInstance()=HomeFragment()
    }
    @Inject
    lateinit var locationManager: LocationManger

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GoPlusApplication.appComponent.inject(this)

    }
    private fun handleLocation(e:Location){
        compute(e)

    }
    private fun handleLocaionFail(failure: Failure){}
    val currentDay = MutableLiveData<String>().apply { this.value="0" }
    val leltAl2dr = MutableLiveData<String>().apply { this.value="0%" }
    lateinit var adapter: PrayTimeAdapter
    override fun layoutId() = R.layout.home_fragment
    override fun view_life_cycle_owner() = viewLifecycleOwner
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        (binding as HomeFragmentBinding).day=currentDay
        (binding as HomeFragmentBinding).rate=leltAl2dr


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        locationManager.getLocation {
                either ->
            either.either(::handleLocaionFail,::handleLocation)
        }
    }

  private  fun azn(l: Location) {
        val today = SimpleDate(GregorianCalendar())
        val azan = Azan(com.azan.astrologicalCalc.Location(30.045411, 31.236735, 2.0, 0),
            Method.EGYPT_SURVEY)
        val pray = azan.getPrayerTimes(today).times
      l_recycler.layoutManager=LinearLayoutManager(context)
        adapter=PrayTimeAdapter(pray)
       l_recycler.adapter=adapter
    }


    @SuppressLint("NewApi")
    fun compute(l:Location) {
        azn(l)
          val day=  getHijriDateToday()
        currentDay.postValue(day.toString())
        if (day%2 == 0) leltAl2dr.postValue("0%")
        else {
            val totalOdds = 15.0f
            val numberOfOddLeft = countOdd(day.toInt(), 30)
            val percentage = (1.0f - (numberOfOddLeft / totalOdds)) * 100.0f
            leltAl2dr.postValue("% ${floor(percentage).toInt()}")
        }
    }
  private  fun countOdd(L: Int, R: Int): Int {
        var ret = (R - L) / 2
        if (R % 2 != 0 || L % 2 != 0) ret++
        return ret
    }

}