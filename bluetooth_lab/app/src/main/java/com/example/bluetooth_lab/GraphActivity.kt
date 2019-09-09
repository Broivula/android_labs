package com.example.bluetooth_lab

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import com.jjoe64.graphview.DefaultLabelFormatter
import com.jjoe64.graphview.GridLabelRenderer
import com.jjoe64.graphview.series.DataPoint
import com.jjoe64.graphview.series.LineGraphSeries
import kotlinx.android.synthetic.main.activity_graph.*

class GraphActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_graph)

        graphSetup()
    }

    private fun graphSetup() {


        val timePoints = IntArray(DataManager.heartRateMeasurements.size){it}
        val datapoints = Array(DataManager.heartRateMeasurements.size, {DataPoint(timePoints[it].toDouble(),DataManager.heartRateMeasurements[it].toDouble())})
        graph_view.addSeries(LineGraphSeries<DataPoint>(datapoints))
        graph_view.gridLabelRenderer.horizontalAxisTitle = "Time"
        graph_view.gridLabelRenderer.verticalAxisTitle = "bpm"
        graph_view.gridLabelRenderer.setHumanRounding(true)
        graph_view.viewport.isXAxisBoundsManual = true
        graph_view.viewport.setMinY(DataManager.getLowest().toDouble() - 5.0)
        graph_view.viewport.setMaxY(DataManager.getHighest().toDouble() + 5.0)
        graph_view.viewport.isYAxisBoundsManual = true
        graph_view.viewport.isXAxisBoundsManual = true
        graph_view.viewport.isScalable = true
        graph_view.viewport.isScrollable = true

    }


}
