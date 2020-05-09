package tjw.ramdan.extentions

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Build
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.azan.Time
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.coroutines.*
import tjw.ramdan.R
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.chrono.Chronology
import java.time.chrono.HijrahChronology
import java.time.chrono.HijrahDate
import java.util.*


fun Activity.cHideSoftKeyboard() {
    val focusedView = currentFocus
    focusedView?.let { view ->
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }
}

@BindingAdapter("loadImageFromUrl", "load_as_cover", requireAll = false)
fun cLoadImageFromUrl(view: ImageView, url: String?, cover: Boolean? = false) {
    val requestOptions = RequestOptions().apply {
        if (cover == true) {
            //placeholder(R.color.carbon_white)
            error(R.color.colorAccent).placeholder(R.color.colorAccent)
        } else {
            //  this.placeholder(R.drawable.user)
            error(R.color.colorPrimary).placeholder(R.color.colorPrimary)
        }
    }
    Glide.with(view.context).applyDefaultRequestOptions(requestOptions).load("$url")
        .into(view)
}

fun View.cVisible(boolean: Boolean?) {
    boolean?.let {
        if (boolean) this.visibility = View.VISIBLE
        else this.visibility = View.INVISIBLE
    }
}

fun View.cEnable(boolean: Boolean?) =
    when (boolean) {
        true -> {
            this.isEnabled = true
        }
        false -> {
            this.isEnabled = false
        }
        null -> {
        }
    }

fun EditText.cTextExtractor(): String {
    return if (this.text.isNullOrEmpty()) ""
    else this.text.toString()
}

fun Spinner.cPopulate(arrayListID: Int) {
    this.context?.let { ctx ->
        val mArray = ctx.resources.getStringArray(arrayListID)
        val adb = ArrayAdapter<String>(ctx, android.R.layout.simple_spinner_dropdown_item, mArray)
        this.adapter = adb
    }
}

@BindingAdapter("lock_view")
fun View.mLock(load: Boolean?) = load?.let { bool -> this.mEnable(!bool) }
fun View.mEnable(boolean: Boolean?) =
    when (boolean) {
        true -> {
            this.isEnabled = true
        }
        false -> {
            this.isEnabled = false
        }
        null -> {
        }
    }

@BindingAdapter("progress_view")
fun View.mProgress(boolean: Boolean?) {
    cVisible(boolean)
}

@BindingAdapter(value = ["safe_text", "decimal_format"], requireAll = false)
fun TextView.mText(any: Any?, boolean: Boolean?) = any?.let { sText ->
    text = if (boolean == true) (sText as Int).safeDecimalFormat() else sText.toString()
}

fun Int?.safeDecimalFormat(): String {
    val df = DecimalFormat("#,###")
    df.roundingMode = RoundingMode.CEILING
    return this?.let { num ->

        val ret = df.format(num)
        ret
    } ?: ""
}

fun delay250(block: (Any) -> Unit = {}) {
    GlobalScope.launch {
        delay(250)
        withContext(Dispatchers.Main)
        { block(Any()) }
    }
}
fun delay300(block: (Any) -> Unit = {}) {
    GlobalScope.launch {
        delay(350)
        withContext(Dispatchers.Main)
        { block(Any()) }
    }
}


@SuppressLint("SimpleDateFormat")
@BindingAdapter("milliSecondData")
fun TextView.msData(msDate: String?) = msDate?.let { date ->
    this.text = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH).format(date.toLong())
}

@BindingAdapter("go_back", "title", requireAll = false)
fun View.handle(data: MutableLiveData<Boolean>?, name: MutableLiveData<String>?) {
    when (name?.value) {
        getString_(R.string.app_name), getString_(R.string.app_name), getString_(R.string.app_name) -> this.cVisible(
            false
        )
        else -> this.cVisible(true)
    }
    this.setOnClickListener { data?.postValue(true) }
}

fun View.getString_(resource_id: Int): String = try {
    this.context.getString(resource_id)
} catch (e: Exception) {
    "-"
}

@BindingAdapter("praytitle")
fun TextView.mSafePray(msDate: PrayTimes?) {
    this.text = when (msDate) {
        PrayTimes.Fagr -> getString_(R.string.fagr)
        PrayTimes.Doha -> getString_(R.string.doha)
        PrayTimes.Zohr -> getString_(R.string.zohr)
        PrayTimes.Asr -> getString_(R.string.asr)
        PrayTimes.Maghrib -> getString_(R.string.maghrib)
        PrayTimes.e4a -> getString_(R.string.e4aa)
        else -> TODO()
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun getHijriDateToday(): Int {
    val date = Date()
    val cl = Calendar.getInstance()
    cl.time = date
    val islamDate: HijrahDate = HijrahChronology.INSTANCE.date(
        LocalDate.of(
            cl[Calendar.YEAR],
            cl[Calendar.MONTH] + 1,
            cl[Calendar.DATE]
        )
    )
    val dateSt= islamDate.toString().split(" ").toTypedArray()[2]
  return   dateSt.split("-").toTypedArray()[2].toInt()
}

@SuppressLint("SetTextI18n")
@BindingAdapter("safetime")
fun TextView.safetime(msDate: Time?) {
    text = msDate?.to24String()
}



enum class PrayTimes {
    Fagr, Doha, Zohr, Asr, Maghrib, e4a
}

@BindingAdapter("emoj")
fun TextView.getEMOJ(d: PrayTimes) {
    this.text = when (d) {
        PrayTimes.Fagr -> "\uD83C\uDF05"
        PrayTimes.Doha -> "\uD83C\uDF1E"
        PrayTimes.Zohr -> "\uD83C\uDF1E"
        PrayTimes.Asr -> "\uD83C\uDF24️"
        PrayTimes.Maghrib -> "\uD83C\uDF25️"
        PrayTimes.e4a -> "\uD83C\uDF11"
        else -> ""
    }
}
private fun Time?.to24String()=this?.let {
    val h=hour%12
     h.toString() + ":" + (if (minute < 10) "0$minute" else minute.toString() + "")  +
             if (hour >12) "  PM  " else "  AM  "

}?:"-:-"