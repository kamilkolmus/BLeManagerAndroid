package com.izaphe.ble.utils


import com.izaphe.ble.charateristics.*
import com.izaphe.ble.charateristics.abstractcharateristic.AbstractBleCharacteristic



/**
 * Created by Kamil on 2018-03-29.
 */
object BleCharacteristicInitializer{

    fun getCharacteristicObject(UUID:String): AbstractBleCharacteristic?{
        return when (UUID) {
            "00002a00-0000-1000-8000-00805f9b34fb" -> DeviceName()
            "00002a01-0000-1000-8000-00805f9b34fb" -> Appearance()
            "00002a02-0000-1000-8000-00805f9b34fb" -> PeripheralPrivacyFlag()
            "00002a03-0000-1000-8000-00805f9b34fb" -> ReconnectionAddress()
            "00002a04-0000-1000-8000-00805f9b34fb" -> PeripheralPreferredConnectionParameters()
            "00002a05-0000-1000-8000-00805f9b34fb" -> ServiceChanged()
            "00002a06-0000-1000-8000-00805f9b34fb" -> null
            "00002a07-0000-1000-8000-00805f9b34fb" -> null
            "00002a08-0000-1000-8000-00805f9b34fb" -> DateTime()
            "00002a09-0000-1000-8000-00805f9b34fb" -> null
            "00002a0a-0000-1000-8000-00805f9b34fb" -> null
            "00002a0b-0000-1000-8000-00805f9b34fb" -> null
            "00002a0c-0000-1000-8000-00805f9b34fb" -> null
            "00002a0d-0000-1000-8000-00805f9b34fb" -> null
            "00002a0e-0000-1000-8000-00805f9b34fb" -> null
            "00002a0f-0000-1000-8000-00805f9b34fb" -> null
            "00002a10-0000-1000-8000-00805f9b34fb" -> null
            "00002a11-0000-1000-8000-00805f9b34fb" -> null
            "00002a12-0000-1000-8000-00805f9b34fb" -> null
            "00002a13-0000-1000-8000-00805f9b34fb" -> null
            "00002a14-0000-1000-8000-00805f9b34fb" -> null
            "00002a15-0000-1000-8000-00805f9b34fb" -> null
            "00002a16-0000-1000-8000-00805f9b34fb" -> null
            "00002a17-0000-1000-8000-00805f9b34fb" -> null
            "00002a18-0000-1000-8000-00805f9b34fb" -> null
            "00002a19-0000-1000-8000-00805f9b34fb" -> null
            "00002a1a-0000-1000-8000-00805f9b34fb" -> null
            "00002a1b-0000-1000-8000-00805f9b34fb" -> null
            "00002a1c-0000-1000-8000-00805f9b34fb" -> TemperatureMeasurement()
            "00002a1d-0000-1000-8000-00805f9b34fb" -> TemperatureType()
            "00002a1e-0000-1000-8000-00805f9b34fb" -> IntermediateTemperature()
            "00002a1f-0000-1000-8000-00805f9b34fb" -> null
            "00002a20-0000-1000-8000-00805f9b34fb" -> null
            "00002a21-0000-1000-8000-00805f9b34fb" -> MeasurementInterval()
            "00002a22-0000-1000-8000-00805f9b34fb" -> null
            "00002a23-0000-1000-8000-00805f9b34fb" -> null
            "00002a24-0000-1000-8000-00805f9b34fb" -> null
            "00002a25-0000-1000-8000-00805f9b34fb" -> null
            "00002a26-0000-1000-8000-00805f9b34fb" -> null
            "00002a27-0000-1000-8000-00805f9b34fb" -> null
            "00002a28-0000-1000-8000-00805f9b34fb" -> null
            "00002a29-0000-1000-8000-00805f9b34fb" -> null
            "00002a2a-0000-1000-8000-00805f9b34fb" -> null
            "00002a2b-0000-1000-8000-00805f9b34fb" -> null
            "00002a2c-0000-1000-8000-00805f9b34fb" -> null
            "00002a2f-0000-1000-8000-00805f9b34fb" -> null
            "00002a30-0000-1000-8000-00805f9b34fb" -> null
            "00002a31-0000-1000-8000-00805f9b34fb" -> null
            "00002a32-0000-1000-8000-00805f9b34fb" -> null
            "00002a33-0000-1000-8000-00805f9b34fb" -> null
            "00002a34-0000-1000-8000-00805f9b34fb" -> null
            "00002a35-0000-1000-8000-00805f9b34fb" -> null
            "00002a36-0000-1000-8000-00805f9b34fb" -> null
            "00002a37-0000-1000-8000-00805f9b34fb" -> HeartRateMeasurement()
            "00002a38-0000-1000-8000-00805f9b34fb" -> BodySensorLocation()
            "00002a39-0000-1000-8000-00805f9b34fb" -> HeartRateControlPoint()
            "00002a3a-0000-1000-8000-00805f9b34fb" -> null
            "00002a3b-0000-1000-8000-00805f9b34fb" -> null
            "00002a3c-0000-1000-8000-00805f9b34fb" -> null
            "00002a3d-0000-1000-8000-00805f9b34fb" -> null
            "00002a3e-0000-1000-8000-00805f9b34fb" -> null
            "00002a3f-0000-1000-8000-00805f9b34fb" -> null
            "00002a40-0000-1000-8000-00805f9b34fb" -> null
            "00002a41-0000-1000-8000-00805f9b34fb" -> null
            "00002a42-0000-1000-8000-00805f9b34fb" -> null
            "00002a43-0000-1000-8000-00805f9b34fb" -> null
            "00002a44-0000-1000-8000-00805f9b34fb" -> null
            "00002a45-0000-1000-8000-00805f9b34fb" -> null
            "00002a46-0000-1000-8000-00805f9b34fb" -> null
            "00002a47-0000-1000-8000-00805f9b34fb" -> null
            "00002a48-0000-1000-8000-00805f9b34fb" -> null
            "00002a49-0000-1000-8000-00805f9b34fb" -> null
            "00002a4a-0000-1000-8000-00805f9b34fb" -> null
            "00002a4b-0000-1000-8000-00805f9b34fb" -> null
            "00002a4c-0000-1000-8000-00805f9b34fb" -> null
            "00002a4d-0000-1000-8000-00805f9b34fb" -> null
            "00002a4e-0000-1000-8000-00805f9b34fb" -> null
            "00002a4f-0000-1000-8000-00805f9b34fb" -> null
            "00002a50-0000-1000-8000-00805f9b34fb" -> null
            "00002a51-0000-1000-8000-00805f9b34fb" -> null
            "00002a52-0000-1000-8000-00805f9b34fb" -> null
            "00002a53-0000-1000-8000-00805f9b34fb" -> null
            "00002a54-0000-1000-8000-00805f9b34fb" -> null
            "00002a55-0000-1000-8000-00805f9b34fb" -> null
            "00002a56-0000-1000-8000-00805f9b34fb" -> null
            "00002a57-0000-1000-8000-00805f9b34fb" -> null
            "00002a58-0000-1000-8000-00805f9b34fb" -> null
            "00002a59-0000-1000-8000-00805f9b34fb" -> null
            "00002a5a-0000-1000-8000-00805f9b34fb" -> null
            "00002a5b-0000-1000-8000-00805f9b34fb" -> null
            "00002a5c-0000-1000-8000-00805f9b34fb" -> null
            "00002a5d-0000-1000-8000-00805f9b34fb" -> null
            "00002a5e-0000-1000-8000-00805f9b34fb" -> null
            "00002a5f-0000-1000-8000-00805f9b34fb" -> null
            "00002a60-0000-1000-8000-00805f9b34fb" -> null
            "00002a62-0000-1000-8000-00805f9b34fb" -> null
            "00002a63-0000-1000-8000-00805f9b34fb" -> null
            "00002a64-0000-1000-8000-00805f9b34fb" -> null
            "00002a65-0000-1000-8000-00805f9b34fb" -> null
            "00002a66-0000-1000-8000-00805f9b34fb" -> null
            "00002a67-0000-1000-8000-00805f9b34fb" -> null
            "00002a68-0000-1000-8000-00805f9b34fb" -> null
            "00002a69-0000-1000-8000-00805f9b34fb" -> null
            "00002a6a-0000-1000-8000-00805f9b34fb" -> null
            "00002a6b-0000-1000-8000-00805f9b34fb" -> null
            "00002a6c-0000-1000-8000-00805f9b34fb" -> null
            "00002a6d-0000-1000-8000-00805f9b34fb" -> null
            "00002a6e-0000-1000-8000-00805f9b34fb" -> null
            "00002a6f-0000-1000-8000-00805f9b34fb" -> null
            "00002a70-0000-1000-8000-00805f9b34fb" -> null
            "00002a71-0000-1000-8000-00805f9b34fb" -> null
            "00002a72-0000-1000-8000-00805f9b34fb" -> null
            "00002a73-0000-1000-8000-00805f9b34fb" -> null
            "00002a74-0000-1000-8000-00805f9b34fb" -> null
            "00002a75-0000-1000-8000-00805f9b34fb" -> null
            "00002a76-0000-1000-8000-00805f9b34fb" -> null
            "00002a77-0000-1000-8000-00805f9b34fb" -> null
            "00002a78-0000-1000-8000-00805f9b34fb" -> null
            "00002a79-0000-1000-8000-00805f9b34fb" -> null
            "00002a7a-0000-1000-8000-00805f9b34fb" -> null
            "00002a7b-0000-1000-8000-00805f9b34fb" -> null
            "00002a7d-0000-1000-8000-00805f9b34fb" -> null
            "00002a7e-0000-1000-8000-00805f9b34fb" -> null
            "00002a7f-0000-1000-8000-00805f9b34fb" -> null
            "00002a80-0000-1000-8000-00805f9b34fb" -> null
            "00002a81-0000-1000-8000-00805f9b34fb" -> null
            "00002a82-0000-1000-8000-00805f9b34fb" -> null
            "00002a83-0000-1000-8000-00805f9b34fb" -> null
            "00002a84-0000-1000-8000-00805f9b34fb" -> null
            "00002a85-0000-1000-8000-00805f9b34fb" -> null
            "00002a86-0000-1000-8000-00805f9b34fb" -> null
            "00002a87-0000-1000-8000-00805f9b34fb" -> null
            "00002a88-0000-1000-8000-00805f9b34fb" -> null
            "00002a89-0000-1000-8000-00805f9b34fb" -> null
            "00002a8a-0000-1000-8000-00805f9b34fb" -> null
            "00002a8b-0000-1000-8000-00805f9b34fb" -> null
            "00002a8c-0000-1000-8000-00805f9b34fb" -> null
            "00002a8d-0000-1000-8000-00805f9b34fb" -> null
            "00002a8e-0000-1000-8000-00805f9b34fb" -> null
            "00002a8f-0000-1000-8000-00805f9b34fb" -> null
            "00002a90-0000-1000-8000-00805f9b34fb" -> null
            "00002a91-0000-1000-8000-00805f9b34fb" -> null
            "00002a92-0000-1000-8000-00805f9b34fb" -> null
            "00002a93-0000-1000-8000-00805f9b34fb" -> null
            "00002a94-0000-1000-8000-00805f9b34fb" -> null
            "00002a95-0000-1000-8000-00805f9b34fb" -> null
            "00002a96-0000-1000-8000-00805f9b34fb" -> null
            "00002a97-0000-1000-8000-00805f9b34fb" -> null
            "00002a98-0000-1000-8000-00805f9b34fb" -> null
            "00002a99-0000-1000-8000-00805f9b34fb" -> null
            "00002a9a-0000-1000-8000-00805f9b34fb" -> null
            "00002a9b-0000-1000-8000-00805f9b34fb" -> null
            "00002a9c-0000-1000-8000-00805f9b34fb" -> null
            "00002a9d-0000-1000-8000-00805f9b34fb" -> null
            "00002a9e-0000-1000-8000-00805f9b34fb" -> null
            "00002a9f-0000-1000-8000-00805f9b34fb" -> null
            "00002aa0-0000-1000-8000-00805f9b34fb" -> null
            "00002aa1-0000-1000-8000-00805f9b34fb" -> null
            "00002aa2-0000-1000-8000-00805f9b34fb" -> null
            "00002aa3-0000-1000-8000-00805f9b34fb" -> null
            "00002aa4-0000-1000-8000-00805f9b34fb" -> null
            "00002aa5-0000-1000-8000-00805f9b34fb" -> null
            "00002aa6-0000-1000-8000-00805f9b34fb" -> null
            "00002aa7-0000-1000-8000-00805f9b34fb" -> null
            "00002aa8-0000-1000-8000-00805f9b34fb" -> null
            "00002aa9-0000-1000-8000-00805f9b34fb" -> null
            "00002aaa-0000-1000-8000-00805f9b34fb" -> null
            "00002aab-0000-1000-8000-00805f9b34fb" -> null
            "00002aac-0000-1000-8000-00805f9b34fb" -> null
            "00002aad-0000-1000-8000-00805f9b34fb" -> null
            "00002aae-0000-1000-8000-00805f9b34fb" -> null
            "00002aaf-0000-1000-8000-00805f9b34fb" -> null
            "00002ab0-0000-1000-8000-00805f9b34fb" -> null
            "00002ab1-0000-1000-8000-00805f9b34fb" -> null
            "00002ab2-0000-1000-8000-00805f9b34fb" -> null
            "00002ab3-0000-1000-8000-00805f9b34fb" -> null
            "00002ab4-0000-1000-8000-00805f9b34fb" -> null
            "00002ab5-0000-1000-8000-00805f9b34fb" -> null
            "00002ab6-0000-1000-8000-00805f9b34fb" -> null
            "00002ab7-0000-1000-8000-00805f9b34fb" -> null
            "00002ab8-0000-1000-8000-00805f9b34fb" -> null
            "00002ab9-0000-1000-8000-00805f9b34fb" -> null
            "00002aba-0000-1000-8000-00805f9b34fb" -> null
            "00002abb-0000-1000-8000-00805f9b34fb" -> null
            "00002abc-0000-1000-8000-00805f9b34fb" -> null
            "00002abd-0000-1000-8000-00805f9b34fb" -> null
            "00002abe-0000-1000-8000-00805f9b34fb" -> null
            "00002abf-0000-1000-8000-00805f9b34fb" -> null
            "00002ac0-0000-1000-8000-00805f9b34fb" -> null
            "00002ac1-0000-1000-8000-00805f9b34fb" -> null
            "00002ac2-0000-1000-8000-00805f9b34fb" -> null
            "00002ac3-0000-1000-8000-00805f9b34fb" -> null
            "00002ac4-0000-1000-8000-00805f9b34fb" -> null
            "00002ac5-0000-1000-8000-00805f9b34fb" -> null
            "00002ac6-0000-1000-8000-00805f9b34fb" -> null
            "00002ac7-0000-1000-8000-00805f9b34fb" -> null
            "00002ac8-0000-1000-8000-00805f9b34fb" -> null
            "00002ac9-0000-1000-8000-00805f9b34fb" -> null
            "00002acc-0000-1000-8000-00805f9b34fb" -> null
            "00002acd-0000-1000-8000-00805f9b34fb" -> null
            "00002ace-0000-1000-8000-00805f9b34fb" -> null
            "00002acf-0000-1000-8000-00805f9b34fb" -> null
            "00002ad0-0000-1000-8000-00805f9b34fb" -> null
            "00002ad1-0000-1000-8000-00805f9b34fb" -> null
            "00002ad2-0000-1000-8000-00805f9b34fb" -> null
            "00002ad3-0000-1000-8000-00805f9b34fb" -> null
            "00002ad4-0000-1000-8000-00805f9b34fb" -> null
            "00002ad5-0000-1000-8000-00805f9b34fb" -> null
            "00002ad6-0000-1000-8000-00805f9b34fb" -> null
            "00002ad7-0000-1000-8000-00805f9b34fb" -> null
            "00002ad8-0000-1000-8000-00805f9b34fb" -> null
            "00002ad9-0000-1000-8000-00805f9b34fb" -> null
            "00002ada-0000-1000-8000-00805f9b34fb" -> null
            "00002b1d-0000-1000-8000-00805f9b34fb" -> null
            "00002b1e-0000-1000-8000-00805f9b34fb" -> null
            "00002b1f-0000-1000-8000-00805f9b34fb" -> null
            else -> null

        }

    }


}