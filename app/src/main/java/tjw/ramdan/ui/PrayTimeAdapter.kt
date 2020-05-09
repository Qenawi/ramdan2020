package tjw.ramdan.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.azan.Time
import tjw.ramdan.R
import tjw.ramdan.databinding.PrayTimeItemBinding
import tjw.ramdan.extentions.PrayTimes

class PrayTimeAdapter(val date: Array<Time>) :
    RecyclerView.Adapter<PrayTimeAdapter.PlaceHolder>() {
    inner class PlaceHolder(val binding: PrayTimeItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Time?, type: PrayTimes) {
            item?.let { case ->
                binding.data = case
                binding.type = type
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaceHolder {
       val binding = DataBindingUtil.inflate<PrayTimeItemBinding>(
            LayoutInflater.from(parent.context), R.layout.pray_time_item,
            parent, false)

        return PlaceHolder(binding)
    }
    override fun getItemCount() = 6
    override fun onBindViewHolder(holder: PlaceHolder, position: Int) = holder.bind(date[position],
        posToType(position)
        )
    private fun posToType(i: Int) :PrayTimes{
      return  when (i) {
                 0 ->PrayTimes.Fagr
                 1 ->PrayTimes.Doha
                 2 ->PrayTimes.Zohr
                 3 ->PrayTimes.Asr
                 4 ->PrayTimes.Maghrib
                 5->PrayTimes.e4a
                 else->PrayTimes.Fagr
        }
    }
}