package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by riyad on 12/5/15.
 */
public class MxHardware extends OpMode {

    final static double MOTOR_POWER = 0.15;

    private DcMotor motorRightFront;
    private DcMotor motorRightBack;
    private DcMotor motorLeftFront;
    private DcMotor motorLeftBack;

    public MxHardware(){}

    @Override
    public void init(){

        motorRightFront = hardwareMap.dcMotor.get("mrf");
        motorRightBack = hardwareMap.dcMotor.get("mrb");
        motorLeftFront = hardwareMap.dcMotor.get("mlf");
        motorLeftBack = hardwareMap.dcMotor.get("mlb");
    }

    @Override
    public void start(){}

    @Override
    public void loop(){
        motorRightFront.setPower(MOTOR_POWER);
        motorRightBack.setPower(MOTOR_POWER);
        motorLeftFront.setPower(MOTOR_POWER);
        motorLeftBack.setPower(MOTOR_POWER);
    }
}
