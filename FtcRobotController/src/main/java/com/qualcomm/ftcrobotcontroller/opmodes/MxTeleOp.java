package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by riyad on 12/8/15.
 */
public class MxTeleOp extends MxTelemetry {

    public MxTeleOp(){}

    @Override
    public void loop(){

        float motorLeftPower = scaleMotorPower(-gamepad1.left_stick_y);
        float motorRightPower = scaleMotorPower(-gamepad1.right_stick_y);

        setDrivePower(motorLeftPower, motorRightPower);
    }
}
