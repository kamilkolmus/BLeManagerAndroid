package com.i7xaphe.blemanager

import android.annotation.SuppressLint
import android.os.Bundle
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothManager
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Handler
import android.support.v4.app.ListFragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import java.util.ArrayList


class FragmentBleDevices : ListFragment() {

    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var mScanning: Boolean = false
    private var mHandler: Handler? = null

    private var bleDevices= mutableListOf<BluetoothDevice>()

    private var mLeDeviceListAdapter: LeDeviceListAdapter? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
       var v=inflater!!.inflate(R.layout.fragment_ble_devices, container, false)


        mHandler = Handler()


        // Initializes a Bluetooth adapter.  For API level 18 and above, get a reference to
        // BluetoothAdapter through BluetoothManager.
        val bluetoothManager = activity.getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        mBluetoothAdapter = bluetoothManager.adapter

        // Checks if Bluetooth is supported on the device.
        if (mBluetoothAdapter == null) {
            Toast.makeText(context, R.string.err_ble_not_supported, Toast.LENGTH_SHORT).show()
            activity.finish()
            return null
        }

        mLeDeviceListAdapter = LeDeviceListAdapter()
        listAdapter = mLeDeviceListAdapter
        bleDevices.clear()
        mLeDeviceListAdapter!!.clear()
        scanLeDevice(true)

        scanLeDevice(true)

        Log.i("onCreate","onCreate")
        return v
    }

    override fun onResume() {
        super.onResume()

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
    }

     override fun onListItemClick(l: ListView, v: View, position: Int, id: Long) {

        super.onListItemClick(l, v, position, id)

         val device = mLeDeviceListAdapter!!.getDevice(position)

        (activity as MainActivity).addNewTab(device!!.name,device!!.address)
    }

    fun scanLeDevice(enable: Boolean) {
        if (enable) {
            // Stops scanning after a pre-defined scan period.
            mHandler!!.postDelayed({
                mScanning = false
                mBluetoothAdapter!!.stopLeScan(mLeScanCallback)

            }, SCAN_PERIOD)

            mScanning = true
            mBluetoothAdapter!!.startLeScan(mLeScanCallback)
        } else {
            mScanning = false
            mBluetoothAdapter!!.stopLeScan(mLeScanCallback)
        }

    }

    // Device scan callback.
    private val mLeScanCallback = BluetoothAdapter.LeScanCallback { device, rssi, scanRecord ->
        activity.runOnUiThread {
                mLeDeviceListAdapter!!.addDevice(device,rssi)
                mLeDeviceListAdapter!!.notifyDataSetChanged()
        }
    }

    internal class ViewHolder {
        var deviceName: TextView? = null
        var deviceAddress: TextView? = null
        var deviceRSSI: TextView? = null
    }
    // Adapter for holding devices found through scanning.
    private inner class LeDeviceListAdapter : BaseAdapter() {
        private val mLeDevices: ArrayList<BluetoothDevice> = ArrayList<BluetoothDevice>()
        private val mLeDevicesRSSI: ArrayList<Int> = ArrayList<Int>()
        private val mInflator: LayoutInflater = this@FragmentBleDevices.layoutInflater

        fun addDevice(device: BluetoothDevice,rssi:Int) {
            if (!mLeDevices.contains(device)) {
                mLeDevices.add(device)
                mLeDevicesRSSI.add(rssi)
            }else{
                //signal strength update
                Log.i(TAG," mLeDevicesRSSI[mLeDevices.indexOf(device)] = rssi")
                mLeDevicesRSSI[mLeDevices.indexOf(device)] = rssi
            }
        }

        fun getDevice(position: Int): BluetoothDevice? {
            return mLeDevices[position]
        }

        fun clear() {
            mLeDevices.clear()
        }

        override fun getCount(): Int {
            return mLeDevices.size
        }

        override fun getItem(i: Int): Any {
            return mLeDevices[i]
        }

        override fun getItemId(i: Int): Long {
            return i.toLong()
        }

        @SuppressLint("SetTextI18n")
        override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View {
            var view = view
            val viewHolder: ViewHolder
            // General ListView optimization code.
            if (view == null) {
                view = mInflator.inflate(R.layout.listitem_device, null)
                viewHolder = ViewHolder()
                viewHolder.deviceAddress = view!!.findViewById(R.id.device_address)
                viewHolder.deviceName = view.findViewById(R.id.device_name)
                viewHolder.deviceRSSI = view.findViewById(R.id.device_rssi)
                view.tag = viewHolder
            } else {
                viewHolder = view.tag as ViewHolder
            }

            val device = mLeDevices[i]
            val rssi = mLeDevicesRSSI[i]
            val deviceName = device.name
            if (deviceName != null && deviceName.length > 0)
                viewHolder.deviceName!!.text = deviceName
            else
                viewHolder.deviceName!!.setText(R.string.unknown_device)
            viewHolder.deviceAddress!!.text = device.address
            viewHolder.deviceRSSI!!.setText(R.string.rssi)
            viewHolder.deviceRSSI!!.append("  $rssi"+"dbm")
            return view
        }
    }



    companion object {

        private val REQUEST_ENABLE_BT = 1
        // Stops scanning after 10 seconds.
        private val SCAN_PERIOD: Long = 6000
        private val PERMISSION_REQUEST_COARSE_LOCATION = 1
    }
}
