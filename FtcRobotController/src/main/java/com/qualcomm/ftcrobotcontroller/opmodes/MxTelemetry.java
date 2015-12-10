package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by riyad on 12/8/15.
 */
public class MxTelemetry extends MxHardware {

    public MxTelemetry(){}

    public void updateTelemetry(){

        if(getWarningGenerated()){
            setWarningMessage(getWarningMessage());
        }

        //telemetry.addData("01", "Motor Left Front: " + getMotorLeftFrontPower() );
        //telemetry.addData("02", "Motor Left Back: " + getMotorLeftBackPower() );
        //telemetry.addData("03", "Motor Right Front: " + getMotorRightFrontPower() );
        //telemetry.addData("02", "Motor Right Back: " + getMotorRightBackPower() );
    }

    public void setFirstMessage(String message){
        telemetry.addData("00", message);
    }

    public void setErrorMessage(String message){
        setFirstMessage(message);
    }
}
