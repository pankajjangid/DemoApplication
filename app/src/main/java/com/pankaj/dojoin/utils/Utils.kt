package com.pankaj.dojoin.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Resources
import android.os.Build
import android.util.DisplayMetrics
import android.util.TypedValue
import android.widget.Toast
import com.pankaj.dojoin.R
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Utils {


    fun dpToPx(dp: Int, activity: Activity): Int {
        val displayMetrics = activity.resources.displayMetrics
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    fun convertDpToPixels(dp: Float): Int {
        return Math.round(
            TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dp, Resources.getSystem().displayMetrics
            )
        )
    }
    fun Show_Alert(
        context: Context?,
        title: String?,
        Message: String?
    ) {
        AlertDialog.Builder(context)
            .setTitle(title)
            .setMessage(Message)
            .setNegativeButton(
                "Ok"
            ) { dialog, which -> dialog.dismiss() }.show()
    }

    fun checkNullOrEmpty(string: String?): String {

        if (string.isNullOrEmpty())
            return ""
        else
            return string
    }

    fun isPackageInstalled(
        packageName: String,
        packageManager: PackageManager
    ): Boolean {
        return try {
            packageManager.getPackageInfo(packageName, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    fun openApp(mActivity: Activity, packageName: String) {
        val launchIntent: Intent =
            mActivity.getPackageManager().getLaunchIntentForPackage(packageName)!!
        launchIntent?.let { mActivity.startActivity(it) }
    }

    val dateTimeStamp: String
        get() {
            val s = SimpleDateFormat("ddMMyyyyhhmmss")
            return s.format(Date())
        }

    fun showToastLong(context: Context?, message: String) {

        Toast.makeText(context, message, Toast.LENGTH_LONG).show()

    }
    fun getPixelFromDips(pixels: Float): Int {
        // Get the screen's density scale
        val scale: Float = MyApplication.getInstance()!!.resources.displayMetrics.density
        // Convert the dps to pixels, based on density scale
        return (pixels * scale + 0.5f).toInt()
    }

    // convert String date to another string date
    fun convertStringDateToAnotherStringDate(
        stringdate: String?,
        returndateformat: String?
    ): String {

        try {
            @SuppressLint("SimpleDateFormat") val date =
                SimpleDateFormat("yyyy-MM-dd").parse(stringdate)
            return SimpleDateFormat(returndateformat).format(date)
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            return ""
        }

    }

    fun convertStringDateToAnotherStringDateForServer(
        stringdate: String?,
        returndateformat: String?
    ): String {

        try {
            @SuppressLint("SimpleDateFormat") val date =
                SimpleDateFormat("dd/MMM/yyyy").parse(stringdate)
            return SimpleDateFormat(returndateformat).format(date)
        } catch (e: ParseException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            return ""
        }

    }


    val tommarowsDate: String
        get() {


            val calendar = Calendar.getInstance()
            val today = calendar.time

            calendar.add(Calendar.DAY_OF_YEAR, 1)
            val tomorrow = calendar.time

            @SuppressLint("SimpleDateFormat")
            val df = SimpleDateFormat("yyyy-MM-dd")

            return df.format(tomorrow)
        }

    val currenTime: String
        get() = SimpleDateFormat("HH:mm").format(Calendar.getInstance().time)


    // To Change String TO Date Object
    fun stringToDate(stringDate: String): Date? {

        var selectedDate: Date? = null
        try {
            selectedDate = SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH)
                .parse(stringDate)


        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return selectedDate
    }

    // To Get Number In Decimal Format
    fun getDecimalFormat(number: Double): String {

        val decimalFormat = DecimalFormat("0.00")

        return decimalFormat.format(number)
    }    // To Get Number In Decimal Format

    fun getDecimalFormat(number: Double, afterDecimalPoint: String): String {

        val decimalFormat = DecimalFormat("0.$afterDecimalPoint")

        return decimalFormat.format(number)
    }


    fun getDateFormate(
        fulldate: String?,
        inputDateFormate: String,
        outputDateFormate: String
    ): String {

        if (!fulldate.isNullOrEmpty()) {
            val oldFormat = inputDateFormate
            val newFormat = outputDateFormate

            var formatedDate = ""
            val dateFormat = SimpleDateFormat(oldFormat)
            var myDate: Date? = null
            try {
                myDate = dateFormat.parse(fulldate)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            val timeFormat = SimpleDateFormat(newFormat)
            formatedDate = timeFormat.format(myDate)

            return formatedDate
        } else {

            return ""
        }

    }


    //ArraryList To Comma Seprated String
    fun toCSV(arrayList: ArrayList<String>): String {
        val sb = StringBuilder()
        for (str in arrayList) {
            sb.append(str).append(",") //separating contents using semi colon
        }

        return sb.toString()

    }


    fun showToast(context: Context, message: String) {

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

    }

    fun dateFormateChange(strDate: String): String {
        val format1: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val format2: SimpleDateFormat = SimpleDateFormat("MMM d, h:mm a")
        val date: Date = format1.parse(strDate)

        return format2.format(date)
    }

    fun checktimings(time: String, endtime: String): Boolean {

        val pattern = "hh:mm a"
        val sdf = SimpleDateFormat(pattern)

        try {
            val date1 = sdf.parse(time)
            val date2 = sdf.parse(endtime)

            return date1.compareTo(date2) < 0
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return false
    }

    val androidVersion: String
        get() = Build.VERSION.RELEASE

    val deviceName: String
        get() {
            val manufacturer = Build.MANUFACTURER
            val model = Build.MODEL
            return if (model.startsWith(manufacturer)) {
                capitalize(model)
            } else {
                capitalize(manufacturer) + " " + model
            }
        }

    private fun capitalize(s: String?): String {
        if (s == null || s.length == 0) {
            return ""
        }
        val first = s[0]
        return if (Character.isUpperCase(first)) {
            s
        } else {
            Character.toUpperCase(first) + s.substring(1)
        }
    }

    /**
     * @param mCurrentActivity
     * @param mTargetActivity
     */
    fun startNewActivity(mCurrentActivity: Activity, mTargetActivity: Class<*>) {

        val mIntent = Intent(mCurrentActivity, mTargetActivity)
        //        mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        mCurrentActivity.startActivity(mIntent)
        mCurrentActivity.overridePendingTransition(R.anim.anim_slide_in, R.anim.anim_slide_out)

    }

    fun startNewActivity(
        mCurrentActivity: Activity,
        mTargetActivity: Class<*>,
        map: MutableMap<String, String>,
        clearTask: Boolean
    ) {

        val mIntent = Intent(mCurrentActivity, mTargetActivity)

        if (clearTask)
            mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK


        for (key: String in map.keys)
            mIntent.putExtra(key, map.get(key))

        mCurrentActivity.startActivity(mIntent)
        mCurrentActivity.overridePendingTransition(R.anim.anim_slide_in, R.anim.anim_slide_out)

    }


    /**
     * @param mCurrentActivity
     * @param mTargetActivity
     */
    fun startNewActivity(
        mCurrentActivity: Activity,
        mTargetActivity: Class<*>,
        clearTask: Boolean
    ) {

        val mIntent = Intent(mCurrentActivity, mTargetActivity)

        if (clearTask)
            mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        mCurrentActivity.startActivity(mIntent)
        mCurrentActivity.overridePendingTransition(R.anim.anim_slide_in, R.anim.anim_slide_out)

    }

    fun convertDpToPixel(dp: Float, context: Context): Float {
        val resources = context.resources
        val metrics = resources.displayMetrics
        return dp * (metrics.densityDpi.toFloat() / DisplayMetrics.DENSITY_DEFAULT)
    }


    /**
     * @param activity
     */
    fun onBackPressedAnimation(
        activity: Activity,
        mTargetActivity: Class<*>,
        clearTask: Boolean
    ) {

        val mIntent = Intent(activity, mTargetActivity)

        if (clearTask)
            mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

        activity.startActivity(mIntent)
        activity.overridePendingTransition(R.anim.animation_enter, R.anim.animation_leave)


    }


    fun startNewActivity(
        mCurrentActivity: Activity,
        mTargetActivity: Class<*>,
        KEY: String,
        string: String,
        clearTask: Boolean
    ) {

        val mIntent = Intent(mCurrentActivity, mTargetActivity)

        if (clearTask)
            mIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK


        mIntent.putExtra(KEY, string)

        mCurrentActivity.startActivity(mIntent)
        mCurrentActivity.overridePendingTransition(R.anim.anim_slide_in, R.anim.anim_slide_out)

    }


}