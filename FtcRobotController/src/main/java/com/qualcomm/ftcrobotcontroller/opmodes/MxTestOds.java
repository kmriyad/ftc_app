package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;

/**
 * Created by riyad on 1/18/16.
 */
public class MxTestOds extends OpMode {

    OpticalDistanceSensor ods;

    @Override
    public void init(){
        ods = hardwareMap.opticalDistanceSensor.get("sOds");
    }

    @Override
    public void loop(){
        double reflectance = ods.getLightDetected();
        telemetry.addData("Reflectance", reflectance);
    }


}
