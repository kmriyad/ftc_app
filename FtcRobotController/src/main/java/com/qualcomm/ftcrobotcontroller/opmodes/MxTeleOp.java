package com.qualcomm.ftcrobotcontroller.opmodes;

//------------------------------------------------------------------------------
//
// PushBotManual
//
/**
 * Provide a basic manual operational mode that uses the left and right
 * drive motors, left arm motor, servo motors and gamepad input from two
 * gamepads for the Push Bot.
 *
 * @author SSI Robotics
 * @version 2015-08-01-06-01
 */
public class MxTeleOp extends MxTelemetry {



    double climberLeftArmPosition;

    double climberLeftArmDelta = 0.1;

    public MxTeleOp() {}

    @Override
    public void init(){

        super.init();
        climberLeftArmPosition = 0.2;
    }

    @Override
    public void start(){

        super.start();
        setClimberLeftArmPosition(climberLeftArmPosition);
    }

    @Override
    public void loop ()

    {
        float l_left_drive_power = scale_motor_power (-gamepad1.left_stick_y);
        float l_right_drive_power = scale_motor_power (-gamepad1.right_stick_y);

        setDrivePower(l_left_drive_power, l_right_drive_power);

        // Manage the hanging motor
        float l_hanging_power = scale_motor_power(-gamepad2.left_stick_y);
        setHangingPower(l_hanging_power);

        //Manage the sweeping power
        float l_sweeping_power = scale_motor_power(-gamepad2.right_stick_y);
        setSweepPower(l_sweeping_power);


        if(gamepad2.x){
            setShelterArmPosition(getShelterArmPosition() - 0.20);
        }

        if(gamepad2.left_bumper){
            setClimberLeftArmPosition(getClimberLeftArmPosition() - 0.20);
        }

        if(gamepad2.dpad_left){
            climberLeftArmPosition += climberLeftArmDelta;
        }

        if(gamepad2.dpad_right){
            climberLeftArmPosition -= climberLeftArmDelta;
        }

        setClimberLeftArmPosition(climberLeftArmPosition);

        /**
        if (gamepad2.x)
        {
            m_hand_position (a_hand_position () + 0.05);
        }
        else if (gamepad2.b)
        {
            m_hand_position (a_hand_position () - 0.05);
        }
        **/
        //
        // Send telemetry data to the driver station.
        //
        update_telemetry (); // Update common telemetry
        update_gamepad_telemetry ();

    } // loop

} // PushBotManual
