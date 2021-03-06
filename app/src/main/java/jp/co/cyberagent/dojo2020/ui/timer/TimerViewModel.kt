package jp.co.cyberagent.dojo2020.ui.timer

import android.app.Application
import androidx.lifecycle.*
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class TimerViewModel(application: Application) : AndroidViewModel(application) {

    private val mutableTimeCountTextViewLiveData: MutableLiveData<String> = MutableLiveData()
    val timeCountTextViewLiveData: LiveData<String> =  mutableTimeCountTextViewLiveData

    var startTimeMills: Int = 0
    var pauseTimeStartMills: Int = 0
    private var sumPauseTimeMills: Int = 0

    fun init(){
        startTimeMills = 0
        pauseTimeStartMills = 0
        sumPauseTimeMills = 0
    }

    fun getCurrentDate(): String? {
        val df: DateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
        val date = Date(System.currentTimeMillis())
        return df.format(date)
    }

    fun getCurrentTimeMills(): Int{
        return System.currentTimeMillis().toInt()
    }

    private fun getStringCurrentTimeMills(): String{
        return getCurrentTimeMills().toString()
    }

    fun setStartTimeMills(){
        startTimeMills = getCurrentTimeMills()
    }

    fun setPauseTimeStartMills(){
        pauseTimeStartMills = getCurrentTimeMills()
    }

    private fun getElapsedTimeMills(): Int{
        return getCurrentTimeMills() - startTimeMills
    }

    private fun getStringElapsedTimeMills(): String{
        return getElapsedTimeMills().toString()
    }

    fun timeToTimeString(time: Int = 0): String? {
        return if (time < 0) {
            null // if time smaller than 0 to null
        } else if (time == 0) {
            "00:00:00:000" // if time is 0 "00:00:00:000"
        } else {
            val h = time / 3600000
            val m = time % 3600000 / 60000
            val s = time % 60000 / 1000
            val ms = time % 1000
            "%1$02d:%2$02d:%3$02d:%4$03d".format(h, m, s, ms) // Formatting
        }
    }

    private fun getFormattedElapsedTime(): String{
        return timeToTimeString(getElapsedTimeMills()) ?: "ElapsedTime cannot refer." // try to use Elvis operator.
    }

    private fun getFormattedCurrentTime(): String{
        return ""
    }

    fun getPauseTimeMills(): Int{
        return getCurrentTimeMills() - pauseTimeStartMills
    }

    fun getTimeMills(): Int{
        return getElapsedTimeMills() - getSumPauseTimeMills()
    }

    fun addPauseTimeMills(){
        sumPauseTimeMills += getPauseTimeMills()
    }

    fun setSumPauseTimeMills(time: Int){
        sumPauseTimeMills = time
    }

    fun getSumPauseTimeMills(): Int{
        return sumPauseTimeMills
    }

    private fun getFormattedTime(): String{
        return timeToTimeString(getTimeMills()) ?: "Time cannot refer."
    }

    fun applyMutableTimeCountTextViewLiveData() {
        mutableTimeCountTextViewLiveData.value = getFormattedTime()
    }

}