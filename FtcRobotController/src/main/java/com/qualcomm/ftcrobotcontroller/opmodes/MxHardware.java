package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.util.Range;

/**
 * Created by riyad on 12/5/15.
 */
public class MxHardware extends OpMode {

    private boolean warningGenerated = false;
    private String warningMessage;

    final static double MOTOR_POWER = 0.15;

    private DcMotor motorRightFront;
    private DcMotor motorRightBack;
    private DcMotor motorLeftFront;
    private DcMotor motorLeftBack;

    private DcMotorController motorRightController;
    private DcMotorController motorLeftController;
    private DcMotorController.DeviceMode devMode;

    public MxHardware(){}

    @Override
    public void init(){

        warningGenerated = false;
        warningMessage = "Can't map; ";

        try{
            motorLeftFront = hardwareMap.dcMotor.get("mlf");
        }catch(Exception ex){
            setWarningMessage("mlf");
            DbgLog.msg(ex.getLocalizedMessage());
            motorLeftFront = null;
        }

        try{
            motorLeftBack = hardwareMap.dcMotor.get("mlb");
        }catch(Exception ex){
            setWarningMessage("mlb");
            DbgLog.msg(ex.getLocalizedMessage());
            motorLeftBack = null;
        }

        try{
            motorRightFront = hardwareMap.dcMotor.get("mrf");
            motorRightFront.setDirection(DcMotor.Direction.REVERSE);
        }catch(Exception ex){
            setWarningMessage("mrf");
            DbgLog.msg(ex.getLocalizedMessage());
            motorRightFront = null;
        }

        try{
            motorRightBack = hardwareMap.dcMotor.get("mrb");
            motorRightBack.setDirection(DcMotor.Direction.REVERSE);
        }catch(Exception ex){
            setWarningMessage("mrb");
            DbgLog.msg(ex.getLocalizedMessage());
            motorRightBack = null;
        }

        try{
            motorLeftController = hardwareMap.dcMotorController.get("mcl");
        }catch (Exception ex){
            setWarningMessage("mcl");
            DbgLog.msg(ex.getLocalizedMessage());
            motorLeftController = null;
        }

        try{
            motorRightController = hardwareMap.dcMotorController.get("mcr");
        }catch (Exception ex){
            setWarningMessage("mcr");
            DbgLog.msg(ex.getLocalizedMessage());
            motorRightController = null;
        }
    }

    boolean getWarningGenerated (){
        return warningGenerated;

    }

    String getWarningMessage(){
        return warningMessage;
    }

    void setWarningMessage (String ex) {
        if (warningGenerated)
        {
            warningMessage += ", ";
        }
        warningGenerated = true;
        warningMessage += ex;

    }



    @Override
    public void start(){}

    @Override
    public void loop(){}

    @Override
    public void stop(){}

    float scaleMotorPower (float p_power)
    {
        float l_scale = 0.0f;

        //
        // Ensure the values are legal.
        //
        float l_power = Range.clip(p_power, -1, 1);

        float[] l_array =
                { 0.00f, 0.05f, 0.09f, 0.10f, 0.12f
                        , 0.15f, 0.18f, 0.24f, 0.30f, 0.36f
                        , 0.43f, 0.50f, 0.60f, 0.72f, 0.85f
                        , 1.00f, 1.00f
                };

        //
        // Get the corresponding index for the specified argument/parameter.
        //
        int l_index = (int)(l_power * 16.0);
        if (l_index < 0)
        {
            l_index = -l_index;
        }
        else if (l_index > 16)
        {
            l_index = 16;
        }

        if (l_power < 0)
        {
            l_scale = -l_array[l_index];
        }
        else
        {
            l_scale = l_array[l_index];
        }

        return l_scale;

    }

    /**
     * Get the power from left front motor
     */
    double getMotorLeftFrontPower(){
        double power = 0.0;
        if(motorLeftFront != null){
            power = motorLeftFront.getPower();
        }
        return power;
    }

    /**
     * Get the power from left back motor
     */
    double getMotorLeftBackPower(){
        double power = 0.0;
        if(motorLeftBack != null){
            power = motorLeftBack.getPower();
        }
        return power;
    }

    /**
     * Get the power from right front motor
     */
    double getMotorRightFrontPower(){
        double power = 0.0;
        if(motorRightFront != null){
            power = motorRightFront.getPower();
        }
        return power;
    }

    /**
     * Get the power from right back motor
     */
    double getMotorRightBackPower(){
        double power = 0.0;
        if(motorRightBack != null){
            power = motorRightBack.getPower();
        }
        return power;
    }

    void setDrivePower(double motorLeftPower, double motorRightPower){
        if(motorLeftFront != null){
            motorLeftFront.setPower(motorLeftPower);
        }
        if(motorLeftBack != null){
            motorLeftBack.setPower(motorLeftPower);
        }
        if(motorRightFront != null){
            motorRightFront.setPower(motorRightPower);
        }
        if(motorRightBack != null){
            motorRightBack.setPower(motorRightPower);
        }
    }

    public void runMotorLeftFrontUsingEncoder(){
        //motorLeftController.setMotorChannelMode(1, DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorLeftController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
        if(motorLeftFront != null){
            motorLeftFront.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        }
    }

    public void runMotorRightFrontUsingEncoder(){
        //motorRightController.setMotorChannelMode(1, DcMotorController.RunMode.RUN_USING_ENCODERS);
        motorRightController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
        if(motorRightFront != null){
            motorRightFront.setMode(DcMotorController.RunMode.RUN_USING_ENCODERS);
        }
    }

    public void runUsingEncoders(){
        runMotorLeftFrontUsingEncoder();
        runMotorRightFrontUsingEncoder();
    }

    public void runMotorLeftFrontWithoutEncoder(){
        if(motorLeftFront != null){
            if(motorLeftFront.getMode() == DcMotorController.RunMode.RESET_ENCODERS){
                motorLeftFront.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
            }
        }
    }

    public void runMotorRightFrontWithoutEncoder(){
        if(motorRightFront != null){
            if(motorRightFront.getMode() == DcMotorController.RunMode.RESET_ENCODERS){
                motorRightFront.setMode(DcMotorController.RunMode.RUN_WITHOUT_ENCODERS);
            }
        }
    }

    public void runWithoutEncoders(){
        runMotorLeftFrontWithoutEncoder();
        runMotorRightFrontWithoutEncoder();
    }

    public void resetMotorLeftFrontEncoder(){
        motorLeftController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
        if(motorLeftFront != null){
            motorLeftFront.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        }
    }

    public void resetMotorRightFrontEncoder(){
        motorRightController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.WRITE_ONLY);
        if(motorRightFront != null){
            motorRightFront.setMode(DcMotorController.RunMode.RESET_ENCODERS);
        }
    }

    public void resetEncoders(){
        resetMotorLeftFrontEncoder();
        resetMotorRightFrontEncoder();
    }

    int getMotorLeftFrontEncoderCount(){
        int count = 0;
        motorLeftController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
        if(motorLeftFront != null){
            count = motorLeftFront.getCurrentPosition();
        }
        return count;
    }

    int getMotorRightFrontEncoderCount(){
        int count = 0;
        motorRightController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
        if(motorRightFront != null){
            count = motorRightFront.getCurrentPosition();
        }
        return count;
    }

    boolean hasMotorLeftFrontEncoderReached(double count){
        boolean reached = false;
        motorLeftController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);

        if(motorLeftFront != null){
            if(Math.abs(motorLeftFront.getCurrentPosition()) > count){
                reached = true;
            }
        }
        return reached;
    }

    boolean hasMotorRightFrontEncoderReached(double count){
        boolean reached = false;
        motorRightController.setMotorControllerDeviceMode(DcMotorController.DeviceMode.READ_ONLY);
        if(motorRightFront != null){
            if(Math.abs(motorRightFront.getCurrentPosition()) > count){
                reached = true;
            }
        }
        return reached;
    }

    /**
     *
     * Indicate whether both motors' encoders in the front have reached a valiue
     */
    boolean haveBothEncodersReached(double leftCount, double rightCount){
        boolean reached = false;

        if(hasMotorLeftFrontEncoderReached(leftCount) && hasMotorRightFrontEncoderReached(rightCount)){
            reached = true;
        }
        return reached;
    }

    boolean runUsingEncoders(double motorLeftPower, double motorRightPower, double leftCount, double rightCount){
        boolean reached = false;

        runUsingEncoders();

        setDrivePower(motorLeftPower, motorRightPower);

        if(haveBothEncodersReached(leftCount, rightCount)){
            resetEncoders();

            setDrivePower(0.0f, 0.0f);

            reached = true;
        }

        return reached;
    }

    boolean hasMotorLeftFrontEncoderReset(){

        boolean reset = false;

        if(getMotorLeftFrontEncoderCount() == 0){
            reset = true;
        }
        return reset;
    }

    boolean hasMotorRightFrontEncoderReset(){

        boolean reset = false;

        if(getMotorRightFrontEncoderCount() == 0){
            reset = true;
        }
        return reset;
    }

    boolean haveFrontEncodersReset(){

        boolean reset = false;

        if(hasMotorLeftFrontEncoderReset() && hasMotorRightFrontEncoderReset()){
            reset = true;
        }
        return reset;
    }

}
