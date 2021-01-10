package com.noname.digisapp.presentation.view

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.YAxis.AxisDependency
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.utils.ColorTemplate
import com.noname.digisapp.R
import com.noname.digisapp.models.RandomResponse
import com.noname.digisapp.presentation.viewmodel.MainViewModel
import com.noname.digisapp.presentation.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject
import kotlin.time.ExperimentalTime


@ExperimentalTime
class MainActivity : AppCompatActivity() {


    private lateinit var viewModel: MainViewModel

    private val valuesSet: ArrayList<ILineDataSet> = ArrayList()
    private val valuesSet2: ArrayList<ILineDataSet> = ArrayList()
    private val valuesSet3: ArrayList<ILineDataSet> = ArrayList()
    private val lineData = LineData(valuesSet)
    private val lineData2 = LineData(valuesSet2)
    private val lineData3 = LineData(valuesSet3)


    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModelFactory = ViewModelFactory(baseContext)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
        viewModel.readingsLiveData.observe(this, Observer {
            setUpDataToViews(it)
        })

        viewModel.readingsErrorLiveData.observe(this, Observer {
           showError(it)
        })

        rsrp_graph.data = lineData
        rsrq_graph.data = lineData2
        sinr_graph.data = lineData3

    }

    private fun showError(throwable: Throwable?) {
        Toast.makeText(this,throwable.toString(),Toast.LENGTH_SHORT).show()
    }


    private fun setUpDataToViews(it: RandomResponse) {

        // in texts
        rsrp_value.text = it.RSRP.toString()
        rsrq_value.text = it.RSRQ.toString()
        snr_value.text = it.SINR.toString()

        // in progresses
        rsrp_progress.setBackgroundColor(Color.parseColor(it.RSRPColor))
        rsrq_progress.setBackgroundColor(Color.parseColor(it.RSRQColor))
        snr_progress.setBackgroundColor(Color.parseColor(it.SINRColor))


        addEntry("RSRP",rsrp_graph,it.CurrentTime.toFloat(), it.RSRP.toFloat())
        addEntry("RSRQ",rsrq_graph,it.CurrentTime.toFloat(), it.RSRQ.toFloat())
        addEntry("SINR",sinr_graph,it.CurrentTime.toFloat(), it.SINR.toFloat())


    }

    private fun addEntry(title:String,graph : LineChart, xValue: Float, yValue: Float) {
        val data: LineData = graph.data
        var set = data.getDataSetByIndex(0)
        if (set == null) {
            set = createSet(title)
            data.addDataSet(set)
        }

        data.setDrawValues(true)

        if (data.dataSets.size==10)
            data.clearValues()

        data.addEntry(Entry(xValue, yValue), 0)
        data.notifyDataChanged()

        // let the chart know it's data has changed
        graph.notifyDataSetChanged()

        // limit the number of visible entries
        graph.setMaxVisibleValueCount(10)
        // mChart.setVisibleYRange(30, AxisDependency.LEFT);
        //  if (moveToLastEntry) {
        // move to the latest entry
        graph.moveViewToX(data.entryCount.toFloat())
        //}
    }

    private fun createSet(title: String): LineDataSet {
        val set = LineDataSet(null,title)
        set.axisDependency = AxisDependency.LEFT
        set.color = ColorTemplate.getHoloBlue()
        set.setCircleColor(Color.CYAN)
        set.lineWidth = 2f
        set.circleRadius = 4f
        set.fillAlpha = 65
        set.fillColor = ColorTemplate.getHoloBlue()
        set.highLightColor = Color.rgb(244, 117, 117)
        set.valueTextColor = Color.WHITE
        set.valueTextSize = 9f
        set.setDrawValues(false)
        return set
    }
}
