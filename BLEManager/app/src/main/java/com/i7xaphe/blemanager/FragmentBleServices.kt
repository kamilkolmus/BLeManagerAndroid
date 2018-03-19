/*
 * Copyright (C) 2013 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.i7xaphe.blemanager


import android.annotation.SuppressLint
import android.app.AlertDialog
import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.bluetooth.BluetoothGattService
import android.bluetooth.BluetoothProfile.*
import android.content.*
import android.graphics.Paint
import android.os.Bundle
import android.os.IBinder
import android.support.design.widget.TextInputEditText
import android.support.v4.app.Fragment
import android.util.Log
import android.view.*
import android.view.animation.AnimationUtils
import android.widget.*

import java.util.ArrayList
import android.view.LayoutInflater
import kotlinx.android.synthetic.main.fragment_ble_services.*
import com.i7xaphe.blemanager.MyObject.UIIDFilter
import android.widget.RelativeLayout
import android.widget.RelativeLayout.LayoutParams
import com.i7xaphe.blemanager.MyObject.multiDeviceCharCollection


class FragmentBleServices : Fragment() {

    private var deviceName: String? = null
    private var deviceAddress: String? = null
    private var tabIndex: Int? = null
    private var deviceID: Int? = null
    //   private var elvGattServicesList: ExpandableListView? = null
    private var serviceBle: ServiceBle? = null
    var connected = STATE_DISCONNECTED

    var expanderListAdapter: ExpandableListAdapter? = null


    // Code to manage Service lifecycle.
    private val mServiceConnection = object : ServiceConnection {

        override fun onServiceConnected(componentName: ComponentName, service: IBinder) {
            serviceBle = (service as ServiceBle.LocalBinder).serviceBle
            if (!serviceBle!!.initialize()) {
                Log.e(TAG, "Unable to initialize Bluetooth")
                return
            }
        }

        override fun onServiceDisconnected(componentName: ComponentName) {
            serviceBle = null
        }
    }

    // Handles various events fired by the Service.
    // DEVICE_ID: check if data comes from the device belonging to this fragment
    // ACTION_GATT_CONNECTED: connected to a GATT server.
    // ACTION_GATT_DISCONNECTED: disconnected from a GATT server.
    // ACTION_GATT_SERVICES_DISCOVERED: discovered GATT services.
    // ACTION_DATA_AVAILABLE: received data from the device.  This can be a result of read
    //                        or notification operations.
    private val mGattUpdateReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val action = intent.action
            //check if data comes from the device belonging to this fragment
            if (intent.getIntExtra(ServiceBle.DEVICE_ID, -1) == deviceID) {
                if (ServiceBle.ACTION_GATT_CONNECTED == action) {
                    connected = STATE_CONNECTED
                    //next onClickAction for toolbartextView ill be disconnect
                    updateToolbarAction(R.string.disconnect)
                } else if (ServiceBle.ACTION_GATT_CONNECTING == action) {
                    connected = STATE_CONNECTING
                    //next onClickAction for toolbartextView ill be connect
                    updateToolbarAction(R.string.connecting)
                } else if (ServiceBle.ACTION_GATT_DISCONNECTED == action) {
                    connected = STATE_DISCONNECTED
                    //next onClickAction for toolbartextView ill be connect
                    updateToolbarAction(R.string.connect)
                    clearUI()
                } else if (ServiceBle.ACTION_GATT_DISCONNECTING == action) {
                    connected = STATE_DISCONNECTING
                    //next onClickAction for toolbartextView ill be connect
                    //updateToolbarAction(R.string.disconnecting)

                } else if (ServiceBle.ACTION_GATT_SERVICES_DISCOVERED == action) {
                    // Show all the supported services and characteristics on the user interface.
                    displayGattServices(serviceBle!!.getBluetoothDevice(deviceID!!))
                } else if (ServiceBle.ACTION_DATA_AVAILABLE == action) {
                    Log.i(TAG, "ACTION_DATA_AVAILABLE onReceive")

                    displayData(intent.getStringExtra(ServiceBle.CHARATERISTIC_DATA),
                            //service index informs which service incoming data belongs to
                            intent.getIntExtra(ServiceBle.SERVICE_INDEX, 0),
                            //characteristic index informs which characteristic incoming data belongs to
                            intent.getIntExtra(ServiceBle.CHARATERISTIC_INDEX, 0))
                }
            }

        }
    }


    private fun clearUI() {
        gatt_services_list!!.setAdapter(null as SimpleExpandableListAdapter?)

    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        var v = inflater!!.inflate(R.layout.fragment_ble_services, container, false)
        return v
    }

    override fun onStart() {
        super.onStart()
        val bundle = this.arguments
        if (bundle != null) {
            deviceName = bundle.getString(EXTRAS_DEVICE_NAME)
            deviceAddress = bundle.getString(EXTRAS_DEVICE_ADDRESS)
            tabIndex = bundle.getInt(EXTRAS_TAB_INDEX)
            deviceID = tabIndex!! - 1
        }



        val lp = ib_close.getLayoutParams() as LayoutParams

        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT, RelativeLayout.TRUE);
        ib_close.setLayoutParams(lp)
        ib_close.setOnClickListener({
            ib_close.startAnimation(AnimationUtils.loadAnimation(context, R.anim.propery_click))
//            activity.unregisterReceiver(mGattUpdateReceiver)
//            disconnect()
//            serviceBle!!.close(deviceID!!)
//
//            activity.unbindService(mServiceConnection)
//            (activity as MainActivity).closeTab(tabIndex!!)
            Log.i("sdsd", "sdsdsdsd");
        })

        //Bind service
        val gattServiceIntent = Intent(context, ServiceBle::class.java)
        gattServiceIntent.action = tabIndex.toString()
        if (activity.bindService(gattServiceIntent, mServiceConnection, Context.BIND_AUTO_CREATE)) {
            Log.i(TAG, "ble service ok")
        } else {
            Log.i(TAG, "ble service error")
        }

    }



    override fun onResume() {
        super.onResume()
        activity.registerReceiver(mGattUpdateReceiver, makeGattUpdateIntentFilter())

       // (activity as MainActivity).sendBroadcast()

    }

    //function  to connect to ble device
    //can be use externally  by activity
    fun connect(): Boolean {
        return serviceBle!!.connect(deviceAddress, deviceID!!)
    }

    //function  to disconnect to ble device
    //can be use externally by activity
    fun disconnect() {
        serviceBle!!.disconnect(deviceID!!)
    }


    private fun updateToolbarAction(resourceId: Int) {
        (activity as MainActivity).overwriteToolbarTextView(tabIndex!!, getString(resourceId))
    }

    private fun displayData(data: String?, groupPos: Int, childPos: Int) {

        Log.i(TAG, "displayData $data $groupPos $childPos")

        try {

            (expanderListAdapter as MyExpanderListAdapter).childViews!!.get(Pair(groupPos, childPos))!!.findViewById<TextView>(R.id.tv_characteristic_value).visibility = View.VISIBLE
            (expanderListAdapter as MyExpanderListAdapter).childViews!!.get(Pair(groupPos, childPos))!!.findViewById<TextView>(R.id.tv_characteristic_value).text = data

            //read Tag of
            if ((expanderListAdapter as MyExpanderListAdapter).childViews!!.get(Pair(groupPos, childPos))!!.findViewById<ImageButton>(R.id.ib_graph).getTag() == 1) {
                println("train to broadcast data to GraphActivity")
                Thread(Runnable {
                    var intent = Intent()
                    intent.action = GraphActivity.ACTION_SEND_CHARACTERISTIC_VALUE
                    intent.putExtra(GraphActivity.EXTRA_DEVICE_NAME, deviceName)
                    intent.putExtra(GraphActivity.EXTRA_DEVICE_ADDRESS, deviceAddress)
                    intent.putExtra(GraphActivity.EXTRA_SERVICE_NAME, (expanderListAdapter as MyExpanderListAdapter).groupViews!!.get(groupPos)!!.findViewById<TextView>(R.id.tv_service_name).text.toString())
                    intent.putExtra(GraphActivity.EXTRA_CHARACTERISTIC_NAME, (expanderListAdapter as MyExpanderListAdapter).childViews!!.get(Pair(groupPos, childPos))!!.findViewById<TextView>(R.id.tv_characteristic_name).text.toString())
                    intent.putExtra(GraphActivity.EXTRA_DATA, data)
                    intent.putExtra(GraphActivity.EXTRA_SERVICE_INDEX, groupPos)
                    intent.putExtra(GraphActivity.EXTRA_CHARACTERISTIC_INDEX, childPos)
                    intent.putExtra(GraphActivity.EXTRA_DEVICE_ID, deviceID)
                    (activity as MainActivity).sendBroadcast(intent)
                }).start()

            }
            // (expanderListAdapter as MyExpanderListAdapter).getChildView(groupPos,childPos,false,null,null)
            (expanderListAdapter as MyExpanderListAdapter).notifyDataSetChanged()
        }catch (e:Exception){
            e.printStackTrace()
        }

        // elvGattServicesList!!.no
    }

    // Display gattServices and gattCharacteristics in ExpandedListView
    private fun displayGattServices(bleDevice: BleDevice) {
        expanderListAdapter = MyExpanderListAdapter(context, bleDevice.mListServices!!, bleDevice.mListCharacteristic!!)
        gatt_services_list!!.setAdapter(expanderListAdapter)
    }

    //View holder for GroupView in MyExpanderListAdapter
    internal class GroupViewHolder {
        //serviceName holds service name
        var serviceName: TextView? = null
        //serviceUUID holds service UIID
        var serviceUUID: TextView? = null
        //serviceType holds service Type (primary or secondary)
        var serviceType: TextView? = null
    }


    //View holder for ChildView in MyExpanderListAdapter
    internal class ChildViewHolder {
        //charName holds characteristic name
        var charName: TextView? = null
        //charName holds characteristic UIID
        var charUUID: TextView? = null
        // propertiesLinearlayout holds dynamically added clickable characteristic properties
        var propertiesLinearlayout: LinearLayout? = null
        // tvCharacteristicValue holds characteristic value
        var tvCharacteristicValue:TextView? = null
        // tvCharacteristicValue holds characteristic value
        var ibGraph:ImageButton? = null

    }


    private inner class MyExpanderListAdapter(context: Context,
                                              mLeServices: List<BluetoothGattService>,
                                              mLeCharacteristic: ArrayList<List<BluetoothGattCharacteristic>>) : BaseExpandableListAdapter() {

        private var context: Context = context
        private val mInflator: LayoutInflater = this@FragmentBleServices.layoutInflater

        //List  of available service
        val mLeServices = mLeServices
        // list of ArrayList of available characteristic
        val mLeCharacteristic = mLeCharacteristic
        //charValue holds textViews which store the read values of the characteristic.
        //these values are modified externally by Fragment in mGattUpdateReceiver
        var childViews: HashMap<Pair<Int, Int>, View> = HashMap()
        var groupViews: HashMap<Int, View> = HashMap()

        override fun getGroupCount(): Int {
            return mLeServices.size
        }

        override fun getChildrenCount(i: Int): Int {
            return mLeCharacteristic!!.get(i).size
        }

        override fun getGroup(i: Int): Any {
            return mLeCharacteristic!!.get(i)
        }

        override fun getChild(groupPos: Int, childPos: Int): Any {
            return mLeCharacteristic!!.get(groupPos).get(childPos)
        }

        override fun getGroupId(id: Int): Long {
            return id.toLong()

        }

        override fun getChildId(groupPos: Int, childPos: Int): Long {
            return childPos.toLong()
        }

        override fun hasStableIds(): Boolean {
            return false
        }

        @SuppressLint("SetTextI18n")
        override fun getGroupView(groupPos: Int, p1: Boolean, view: View?, p3: ViewGroup?): View {


            if(groupViews.contains(groupPos)){
                //return view from HashMap childViews
                return groupViews.get(groupPos)!!
            }else{

                var v = mInflator.inflate(R.layout.expandedlistitem, null)
                val groupViewHolder: FragmentBleServices.GroupViewHolder
                groupViewHolder = FragmentBleServices.GroupViewHolder()
                groupViewHolder.serviceName = v!!.findViewById(R.id.tv_service_name)
                groupViewHolder.serviceUUID = v.findViewById(R.id.tv_service_uuid)
                groupViewHolder.serviceType = v.findViewById(R.id.tv_service_type)
                groupViewHolder.serviceName!!.text = MyObject.getServiceName(mLeServices.get(groupPos).uuid.toString())
                groupViewHolder.serviceUUID!!.text = "UUID: " + UIIDFilter(mLeServices.get(groupPos).uuid.toString())
                groupViewHolder.serviceType!!.text = if (mLeServices.get(groupPos).type == 0) getString(R.string.primary_srvice) else getString(R.string.secondary_service)
                groupViews.put(groupPos,v)
                return v

            }


        }

        @SuppressLint("SetTextI18n")
        override fun getChildView(groupPos: Int, childPos: Int, p2: Boolean, view: View?, p4: ViewGroup?): View {

            println("getChildView $groupPos $childPos")

            if(childViews.contains(Pair(groupPos,childPos))){
                //return view from HashMap childViews
                return childViews.get(Pair(groupPos,childPos))!!
            }else{
                //create new view
                val v = mInflator.inflate(R.layout.expandedlistitemchild, null)
                val childViewHolder = ChildViewHolder()

                childViewHolder.charName = v!!.findViewById(R.id.tv_characteristic_name)
                childViewHolder.charUUID = v.findViewById(R.id.tv_characteristic_uuid)
                childViewHolder.propertiesLinearlayout = v.findViewById(R.id.ll_properties)
                childViewHolder.tvCharacteristicValue = v.findViewById(R.id.tv_characteristic_value)
                childViewHolder.ibGraph = v.findViewById(R.id.ib_graph)
                childViewHolder.ibGraph!!.setTag(0)
                childViewHolder.ibGraph!!.setOnClickListener({
                    if(Integer.parseInt(childViewHolder.ibGraph!!.getTag().toString()) == 0){
                        childViewHolder.ibGraph!!.setImageResource(R.drawable.graph_icon_strike)
                        childViewHolder.ibGraph!!.setTag(R.drawable.graph_icon_strike)
                        //use Tag to know if data should be broadcast
                        //broadcast data
                        childViewHolder.ibGraph!!.setTag(1)

                        multiDeviceCharCollection.put(
                                Pair(deviceID!!,Pair(groupPos,childPos)) ,
                                GraphChrateristicInfo(deviceName!!,groupViews!!.get(groupPos)!!.findViewById<TextView>(R.id.tv_service_name).text.toString(),
                                        childViews!!.get(Pair(groupPos, childPos))!!.findViewById<TextView>(R.id.tv_characteristic_name).text.toString()))

                    }else{
                        childViewHolder.ibGraph!!.setImageResource(R.drawable.graph_icon)
                        childViewHolder.ibGraph!!.setTag(R.drawable.graph_icon)
                        //use Tag to know if data should be broadcast
                        childViewHolder.ibGraph!!.setTag(0)

                        multiDeviceCharCollection.remove(Pair(deviceID!!,Pair(groupPos,childPos)))

                    }
                })

                childViewHolder.charName!!.text = MyObject.getCharateristicName(mLeCharacteristic!!.get(groupPos).get(childPos).uuid.toString())
                childViewHolder.charUUID!!.text = """UUID: ${UIIDFilter(mLeCharacteristic!!.get(groupPos).get(childPos).uuid.toString())}"""

                childViewHolder.propertiesLinearlayout = v.findViewById(R.id.ll_properties)
                //dynamically added clickable textViews that correspond to the properties of characteristic
                for (tv in MyObject.getPropertiesTextViews(mLeCharacteristic.get(groupPos).get(childPos).properties, context)) {

                    childViewHolder.propertiesLinearlayout!!.addView(tv)
                    if(tv.text.toString() == "NOTIFY"||tv.text.toString() == "INDICATE"){ childViewHolder.ibGraph!!.visibility= View.VISIBLE}

                    tv.setOnClickListener({ textview ->
                        tv.startAnimation(AnimationUtils.loadAnimation(context, R.anim.propery_click))
                        //read textView text
                        val property = tv.text.toString()
                        //onClickAction  depending on the property
                        when (property) {
                            "BROADCAST" -> {
                                Toast.makeText(context, "PROPERTY NOT IMPLEMENTED YET", Toast.LENGTH_SHORT).show()
                            }
                            "READ" -> {
                                serviceBle!!.readCharacteristic(deviceID!!, groupPos, childPos)

                            }
                            "WRITE NO RESPONSE" -> {
                                val inflater = layoutInflater
                                val dialoglayout = inflater.inflate(R.layout.alert_write_property, null)
                                val builder = AlertDialog.Builder(context)
                                var data = dialoglayout.findViewById<TextInputEditText>(R.id.tiet_data)

                                builder.setView(dialoglayout)
                                builder.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface?, witch: Int) {
                                        dialog!!.dismiss()
                                    }

                                })
                                builder.setPositiveButton("Send", object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface?, witch: Int) {
                                        serviceBle!!.writeCharacteristic(deviceID!!, groupPos, childPos, data.text.toString())
                                        //update text hare because no respond from service
                                        childViewHolder.tvCharacteristicValue!!.text = data.text.toString()
                                        dialog!!.dismiss()
                                    }

                                })
                                builder.show()

                            }
                            "WRITE" -> {
                                val inflater = layoutInflater
                                val dialoglayout = inflater.inflate(R.layout.alert_write_property, null)
                                val builder = AlertDialog.Builder(context)
                                var data = dialoglayout.findViewById<TextInputEditText>(R.id.tiet_data)

                                builder.setView(dialoglayout)
                                builder.setNegativeButton("Cancel", object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface?, witch: Int) {
                                        dialog!!.dismiss()
                                    }

                                })
                                builder.setPositiveButton("Send", object : DialogInterface.OnClickListener {
                                    override fun onClick(dialog: DialogInterface?, witch: Int) {
                                        serviceBle!!.writeCharacteristic(deviceID!!, groupPos, childPos, data.text.toString())
                                        dialog!!.dismiss()
                                    }

                                })
                                builder.show()


                            }
                            "NOTIFY" -> {
                                if(tv.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG>0){
                                    serviceBle!!.setCharacteristicNotification(deviceID!!, groupPos, childPos, false, BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE)
                                    tv.setPaintFlags(tv.getPaintFlags() and Paint.STRIKE_THRU_TEXT_FLAG.inv())


                                }else{
                                    serviceBle!!.setCharacteristicNotification(deviceID!!, groupPos, childPos, true, BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE)
                                    tv.setPaintFlags(tv.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)


                                }

                            }
                            "INDICATE" -> {
                                if(tv.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG>0){
                                    serviceBle!!.setCharacteristicNotification(deviceID!!, groupPos, childPos, false, BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE)
                                    tv.setPaintFlags(tv.getPaintFlags() and Paint.STRIKE_THRU_TEXT_FLAG.inv())

                                }else{
                                    serviceBle!!.setCharacteristicNotification(deviceID!!, groupPos, childPos, true, BluetoothGattDescriptor.ENABLE_INDICATION_VALUE)
                                    tv.setPaintFlags(tv.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

                                }
                            }
                            "SIGNED WRITE" -> {
                                Toast.makeText(context, "PROPERTY NOT IMPLEMENTED YET", Toast.LENGTH_SHORT).show()
                            }
                            "EXTENDED PROPS" -> {
                                Toast.makeText(context, "PROPERTY NOT IMPLEMENTED YET", Toast.LENGTH_SHORT).show()
                            }
                            else -> {
                                Toast.makeText(context, "WRONG PROPERTY", Toast.LENGTH_SHORT).show()
                            }

                        }
                    })

                }
                //store v in HashMap
                childViews.put(Pair(groupPos,childPos),v)
                return v
            }


        }

        override fun isChildSelectable(p0: Int, p1: Int): Boolean {
            return true
        }
    }

    private fun makeGattUpdateIntentFilter(): IntentFilter {
        val intentFilter = IntentFilter()

        intentFilter.addAction(ServiceBle.ACTION_GATT_CONNECTED)
        intentFilter.addAction(ServiceBle.ACTION_GATT_DISCONNECTED)
        intentFilter.addAction(ServiceBle.ACTION_GATT_SERVICES_DISCOVERED)
        intentFilter.addAction(ServiceBle.ACTION_GATT_DISCONNECTING)
        intentFilter.addAction(ServiceBle.ACTION_GATT_CONNECTING)
        intentFilter.addAction(ServiceBle.ACTION_DATA_AVAILABLE)

        return intentFilter
    }


    companion object {
        private val TAG = FragmentBleServices::class.java!!.getSimpleName()
        @JvmField
        var EXTRAS_DEVICE_NAME = "DEVICE_NAME"
        @JvmField
        var EXTRAS_DEVICE_ADDRESS = "DEVICE_ADDRESS"
        @JvmField
        var EXTRAS_TAB_INDEX = "TAB_INDEX"


    }
}
