package com.qualcomm.ftcrobotcontroller.opmodes;

/**
 * Created by riyad on 12/8/15.
 */
public class MxAuto extends MxTelemetry{

    private int state = 0;

    public MxAuto(){}

    @Override
    public void start(){

        super.start();
    }

    @Override
    public void loop(){

        //setDrivePower(1.0f, 1.0f);
        switch (state){
            case 0:
                resetEncoders();
                state++;
                break;
            case 1:
                runUsingEncoders();
                setDrivePower(1.0f, 1.0f);
                if(haveBothEncodersReached(1000, 1000)){
                    resetEncoders();
                    setDrivePower(0.0f, 0.0f);
                    state++;
                }
                break;
            case 2:
                if(haveFrontEncodersReset()){
                    state++;
                }
                break;
            case 3:
                runUsingEncoders();
                setDrivePower(-1.0f, 1.0f);
                if(haveBothEncodersReached(1000, 1000)){
                    resetEncoders();
                    setDrivePower(0.0f, 0.0f);
                    state++;
                }
                break;
            case 4:
                if(haveFrontEncodersReset()){
                    state++;
                }
                break;
            default:
                break;
        }
        updateTelemetry();
        telemetry.addData("18", "State: " + state);
    }

}
