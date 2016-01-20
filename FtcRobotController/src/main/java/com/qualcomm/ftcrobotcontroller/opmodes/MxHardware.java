package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorController;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.Range;

//------------------------------------------------------------------------------
//
// PushBotHardware
//

/**
 * Provides a single hardware access point between custom op-modes and the
 * OpMode class for the Push Bot.
 *
 * This class prevents the custom op-mode from throwing an exception at runtime.
 * If any hardware fails to map, a warning will be shown via telemetry data,
 * calls to methods will fail, but will not cause the application to crash.
 *
 * @author SSI Robotics
 * @version 2015-08-13-20-04
 */
public class MxHardware extends OpMode

{
    final static double CLIMBER_LEFT_ARM_MIN_RANGE = 0.20;
    final static double CLIMBER_LEFT_ARM_MAX_RANGE = 0.90;

    public MxHardware() {}

    @Override public void init ()

    {

        v_warning_generated = false;
        v_warning_message = "Can't map; ";

        //
        // Connect the drive wheel motors.
        //
        // The direction of the right motor is reversed, so joystick inputs can
        // be more generically applied.
        //
        try
        {
            driveFrontLeft = hardwareMap.dcMotor.get ("dfl");
        }
        catch (Exception p_exeception)
        {
            m_warning_message ("dfl");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            driveFrontLeft = null;
        }

        try
        {
            driveFrontRight = hardwareMap.dcMotor.get ("dfr");
            driveFrontRight.setDirection (DcMotor.Direction.REVERSE);
        }
        catch (Exception p_exeception)
        {
            m_warning_message ("dfr");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            driveFrontRight = null;
        }

        try{

            driveRearLeft = hardwareMap.dcMotor.get("drl");

        }catch (Exception p_exeception)
        {
            m_warning_message ("drl");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            driveRearLeft = null;
        }

        try{

            driveRearRight = hardwareMap.dcMotor.get("drr");
            driveRearRight.setDirection(DcMotor.Direction.REVERSE);

        }catch (Exception p_exeception)
        {
            m_warning_message ("drr");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            driveRearRight = null;
        }

        //Sweeper
        try{
            sweeper = hardwareMap.dcMotor.get("sweeper");
        }catch (Exception ex){
            m_warning_message("sweeper");
            DbgLog.msg((ex.getLocalizedMessage()));
            sweeper = null;
        }

        //hanging
        try{
            hanging1 = hardwareMap.dcMotor.get("hanging1");
        }catch (Exception ex){
            m_warning_message("hanging1");
            DbgLog.msg((ex.getLocalizedMessage()));
            sweeper = null;
        }

        try{
            hanging2 = hardwareMap.dcMotor.get("hanging2");
        }catch (Exception ex){
            m_warning_message("hanging2");
            DbgLog.msg((ex.getLocalizedMessage()));
            sweeper = null;
        }

        //
        // Connect the arm motor.
        //
        try
        {
            v_motor_left_arm = hardwareMap.dcMotor.get ("left_arm");
        }
        catch (Exception p_exeception)
        {
            m_warning_message ("left_arm");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            v_motor_left_arm = null;
        }

        //
        // Connect the servo motors.
        //
        // Indicate the initial position of both the left and right servos.  The
        // hand should be halfway opened/closed.
        //

        try {

            shelterArm = hardwareMap.servo.get ("shelterArm");
            shelterArm.setPosition(1.0);

        }
        catch (Exception p_exeception)
        {
            m_warning_message ("shelterArm");
            DbgLog.msg (p_exeception.getLocalizedMessage ());
            shelterArm = null;
        }

        try{

            climberLeftArm = hardwareMap.servo.get("climberLeftArm");

        }catch (Exception ex) {

            m_warning_message ("climberLeftArm");
            DbgLog.msg (ex.getLocalizedMessage ());
            climberLeftArm = null;
        }

        try{

            climberRightArm = hardwareMap.servo.get("climberRightArm");

        }catch (Exception ex) {

            m_warning_message ("climberRightArm");
            DbgLog.msg (ex.getLocalizedMessage ());
            climberRightArm = null;
        }


        double l_hand_position = 0.5;
        try
        {
            v_servo_left_hand = hardwareMap.servo.get ("left_hand");
            v_servo_left_hand.setPosition (l_hand_position);
        }
        catch (Exception p_exeception)
        {
            m_warning_message ("left_hand");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            v_servo_left_hand = null;
        }

        try
        {
            v_servo_right_hand = hardwareMap.servo.get ("right_hand");
            v_servo_right_hand.setPosition (l_hand_position);
        }
        catch (Exception p_exeception)
        {
            m_warning_message ("right_hand");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            v_servo_right_hand = null;
        }



    } // init

    //--------------------------------------------------------------------------
    //
    // a_warning_generated
    //
    /**
     * Access whether a warning has been generated.
     */
    boolean a_warning_generated ()

    {
        return v_warning_generated;

    } // a_warning_generated

    //--------------------------------------------------------------------------
    //
    // a_warning_message
    //
    /**
     * Access the warning message.
     */
    String a_warning_message ()

    {
        return v_warning_message;

    } // a_warning_message

    //--------------------------------------------------------------------------
    //
    // m_warning_message
    //
    /**
     * Mutate the warning message by ADDING the specified message to the current
     * message; set the warning indicator to true.
     *
     * A comma will be added before the specified message if the message isn't
     * empty.
     */
    void m_warning_message (String p_exception_message)

    {
        if (v_warning_generated)
        {
            v_warning_message += ", ";
        }
        v_warning_generated = true;
        v_warning_message += p_exception_message;

    } // m_warning_message

    //--------------------------------------------------------------------------
    //
    // start
    //
    /**
     * Perform any actions that are necessary when the OpMode is enabled.
     *
     * The system calls this member once when the OpMode is enabled.
     */
    @Override public void start ()

    {
        //shelterArm.setPosition (shelterArmPosition);

        //climberRightArm.setPosition(climberRightArmPosition);

    } // start

    //--------------------------------------------------------------------------
    //
    // loop
    //
    /**
     * Perform any actions that are necessary while the OpMode is running.
     *
     * The system calls this member repeatedly while the OpMode is running.
     */
    @Override public void loop ()

    {
        //
        // Only actions that are common to all OpModes (i.e. both auto and\
        // manual) should be implemented here.
        //
        // This method is designed to be overridden.
        //

    } // loop

    //--------------------------------------------------------------------------
    //
    // stop
    //
    /**
     * Perform any actions that are necessary when the OpMode is disabled.
     *
     * The system calls this member once when the OpMode is disabled.
     */
    @Override public void stop ()
    {
        shelterArm.setPosition(1.0);

    } // stop

    //--------------------------------------------------------------------------
    //
    // scale_motor_power
    //
    /**
     * Scale the joystick input using a nonlinear algorithm.
     */
    float scale_motor_power (float p_power)
    {
        //
        // Assume no scaling.
        //
        float l_scale = 0.0f;

        //
        // Ensure the values are legal.
        //
        float l_power = Range.clip (p_power, -1, 1);

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

    } // scale_motor_power

    //--------------------------------------------------------------------------
    //
    // a_left_drive_power
    //
    /**
     * Access the left drive motor's power level.
     */
    double a_left_drive_power ()
    {
        double l_return = 0.0;

        if (driveFrontLeft != null)
        {
            l_return = driveFrontLeft.getPower ();
        }

        return l_return;

    } // a_left_drive_power

    //--------------------------------------------------------------------------
    //
    // a_right_drive_power
    //
    /**
     * Access the right drive motor's power level.
     */
    double a_right_drive_power ()
    {
        double l_return = 0.0;

        if (driveFrontRight != null)
        {
            l_return = driveFrontRight.getPower ();
        }

        return l_return;

    } // a_right_drive_power


    double a_left_drive_rear_power ()
    {
        double l_return = 0.0;

        if (driveRearLeft != null)
        {
            l_return = driveRearLeft.getPower ();
        }

        return l_return;

    }

    double a_right_drive_rear_power ()
    {
        double l_return = 0.0;

        if (driveRearRight != null)
        {
            l_return = driveRearRight.getPower ();
        }

        return l_return;

    }

    //--------------------------------------------------------------------------
    //
    // setDrivePower
    //
    /**
     * Scale the joystick input using a nonlinear algorithm.
     */
    void setDrivePower (double p_left_power, double p_right_power)

    {
        if (driveFrontLeft != null)
        {
            driveFrontLeft.setPower (p_left_power);
        }
        if (driveFrontRight != null)
        {
            driveFrontRight.setPower (p_right_power);
        }
        if (driveRearLeft != null){

            driveRearLeft.setPower(p_left_power);
        }
        if (driveRearRight != null){

            driveRearRight.setPower(p_right_power);
        }

    } // setDrivePower

    void setSweepPower(double power){
        if(sweeper != null){
            sweeper.setPower(power);
        }
    }

    //set hanging power
    void setHangingPower(double power){
        if(hanging1 != null){
            hanging1.setPower(power);
        }
        if(hanging2 != null){
            hanging2.setPower(-power);
        }
    }

    //--------------------------------------------------------------------------
    //
    // run_using_left_drive_encoder
    //
    /**
     * Set the left drive wheel encoder to run, if the mode is appropriate.
     */
    public void run_using_left_drive_encoder ()

    {
        if (driveFrontLeft != null)
        {
            driveFrontLeft.setMode
                ( DcMotorController.RunMode.RUN_USING_ENCODERS
                );
        }

    } // run_using_left_drive_encoder

    //--------------------------------------------------------------------------
    //
    // run_using_right_drive_encoder
    //
    /**
     * Set the right drive wheel encoder to run, if the mode is appropriate.
     */
    public void run_using_right_drive_encoder ()

    {
        if (driveFrontRight != null)
        {
            driveFrontRight.setMode
                ( DcMotorController.RunMode.RUN_USING_ENCODERS
                );
        }

    } // run_using_right_drive_encoder

    //--------------------------------------------------------------------------
    //
    // driveUsingEncoders
    //
    /**
     * Set both drive wheel encoders to run, if the mode is appropriate.
     */
    public void driveUsingEncoders ()

    {
        //
        // Call other members to perform the action on both motors.
        //
        run_using_left_drive_encoder ();
        run_using_right_drive_encoder ();

    } // driveUsingEncoders

    //--------------------------------------------------------------------------
    //
    // run_without_left_drive_encoder
    //
    /**
     * Set the left drive wheel encoder to run, if the mode is appropriate.
     */
    public void run_without_left_drive_encoder ()

    {
        if (driveFrontLeft != null)
        {
            if (driveFrontLeft.getMode () ==
                DcMotorController.RunMode.RESET_ENCODERS)
            {
                driveFrontLeft.setMode
                    ( DcMotorController.RunMode.RUN_WITHOUT_ENCODERS
                    );
            }
        }

    } // run_without_left_drive_encoder

    //--------------------------------------------------------------------------
    //
    // run_without_right_drive_encoder
    //
    /**
     * Set the right drive wheel encoder to run, if the mode is appropriate.
     */
    public void run_without_right_drive_encoder ()

    {
        if (driveFrontRight != null)
        {
            if (driveFrontRight.getMode () ==
                DcMotorController.RunMode.RESET_ENCODERS)
            {
                driveFrontRight.setMode
                    ( DcMotorController.RunMode.RUN_WITHOUT_ENCODERS
                    );
            }
        }

    } // run_without_right_drive_encoder

    //--------------------------------------------------------------------------
    //
    // run_without_drive_encoders
    //
    /**
     * Set both drive wheel encoders to run, if the mode is appropriate.
     */
    public void run_without_drive_encoders ()

    {
        //
        // Call other members to perform the action on both motors.
        //
        run_without_left_drive_encoder ();
        run_without_right_drive_encoder ();

    } // run_without_drive_encoders

    //--------------------------------------------------------------------------
    //
    // reset_left_drive_encoder
    //
    /**
     * Reset the left drive wheel encoder.
     */
    public void reset_left_drive_encoder ()

    {
        if (driveFrontLeft != null)
        {
            driveFrontLeft.setMode
                ( DcMotorController.RunMode.RESET_ENCODERS
                );
        }

    } // reset_left_drive_encoder

    //--------------------------------------------------------------------------
    //
    // reset_right_drive_encoder
    //
    /**
     * Reset the right drive wheel encoder.
     */
    public void reset_right_drive_encoder ()

    {
        if (driveFrontRight != null)
        {
            driveFrontRight.setMode
                ( DcMotorController.RunMode.RESET_ENCODERS
                );
        }

    } // reset_right_drive_encoder

    //--------------------------------------------------------------------------
    //
    // resetDriveEncoders
    //
    /**
     * Reset both drive wheel encoders.
     */
    public void resetDriveEncoders ()

    {
        //
        // Reset the motor encoders on the drive wheels.
        //
        reset_left_drive_encoder ();
        reset_right_drive_encoder ();

    } // resetDriveEncoders

    //--------------------------------------------------------------------------
    //
    // a_left_encoder_count
    //
    /**
     * Access the left encoder's count.
     */
    int a_left_encoder_count ()
    {
        int l_return = 0;

        if (driveFrontLeft != null)
        {
            l_return = driveFrontLeft.getCurrentPosition ();
        }

        return l_return;

    } // a_left_encoder_count

    //--------------------------------------------------------------------------
    //
    // a_right_encoder_count
    //
    /**
     * Access the right encoder's count.
     */
    int a_right_encoder_count ()

    {
        int l_return = 0;

        if (driveFrontRight != null)
        {
            l_return = driveFrontRight.getCurrentPosition ();
        }

        return l_return;

    } // a_right_encoder_count

    //--------------------------------------------------------------------------
    //
    // has_left_drive_encoder_reached
    //
    /**
     * Indicate whether the left drive motor's encoder has reached a value.
     */
    boolean has_left_drive_encoder_reached (double p_count)

    {
        //
        // Assume failure.
        //
        boolean l_return = false;

        if (driveFrontLeft != null)
        {
            //
            // Has the encoder reached the specified values?
            //
            // TODO Implement stall code using these variables.
            //
            if (Math.abs (driveFrontLeft.getCurrentPosition ()) > p_count)
            {
                //
                // Set the status to a positive indication.
                //
                l_return = true;
            }
        }

        //
        // Return the status.
        //
        return l_return;

    } // has_left_drive_encoder_reached

    //--------------------------------------------------------------------------
    //
    // has_right_drive_encoder_reached
    //
    /**
     * Indicate whether the right drive motor's encoder has reached a value.
     */
    boolean has_right_drive_encoder_reached (double p_count)

    {
        //
        // Assume failure.
        //
        boolean l_return = false;

        if (driveFrontRight != null)
        {
            //
            // Have the encoders reached the specified values?
            //
            // TODO Implement stall code using these variables.
            //
            if (Math.abs (driveFrontRight.getCurrentPosition ()) > p_count)
            {
                //
                // Set the status to a positive indication.
                //
                l_return = true;
            }
        }

        //
        // Return the status.
        //
        return l_return;

    } // has_right_drive_encoder_reached

    //--------------------------------------------------------------------------
    //
    // haveDriveEncodersReached
    //
    /**
     * Indicate whether the drive motors' encoders have reached a value.
     */
    boolean haveDriveEncodersReached
        ( double p_left_count
        , double p_right_count
        )

    {
        //
        // Assume failure.
        //
        boolean l_return = false;

        //
        // Have the encoders reached the specified values?
        //
        if (has_left_drive_encoder_reached (p_left_count) &&
            has_right_drive_encoder_reached (p_right_count))
        {
            //
            // Set the status to a positive indication.
            //
            l_return = true;
        }

        //
        // Return the status.
        //
        return l_return;

    } // have_encoders_reached

    //--------------------------------------------------------------------------
    //
    // drive_using_encoders
    //
    /**
     * Indicate whether the drive motors' encoders have reached a value.
     */
    boolean drive_using_encoders
        ( double p_left_power
        , double p_right_power
        , double p_left_count
        , double p_right_count
        )

    {
        //
        // Assume the encoders have not reached the limit.
        //
        boolean l_return = false;

        //
        // Tell the system that motor encoders will be used.
        //
        driveUsingEncoders ();

        //
        // Start the drive wheel motors at full power.
        //
        setDrivePower (p_left_power, p_right_power);

        //
        // Have the motor shafts turned the required amount?
        //
        // If they haven't, then the op-mode remains in this state (i.e this
        // block will be executed the next time this method is called).
        //
        if (haveDriveEncodersReached (p_left_count, p_right_count))
        {
            //
            // Reset the encoders to ensure they are at a known good value.
            //
            resetDriveEncoders ();

            //
            // Stop the motors.
            //
            setDrivePower (0.0f, 0.0f);

            //
            // Transition to the next state when this method is called
            // again.
            //
            l_return = true;
        }

        //
        // Return the status.
        //
        return l_return;

    } // drive_using_encoders

    //--------------------------------------------------------------------------
    //
    // has_left_drive_encoder_reset
    //
    /**
     * Indicate whether the left drive encoder has been completely reset.
     */
    boolean has_left_drive_encoder_reset ()
    {
        //
        // Assume failure.
        //
        boolean l_return = false;

        //
        // Has the left encoder reached zero?
        //
        if (a_left_encoder_count () == 0)
        {
            //
            // Set the status to a positive indication.
            //
            l_return = true;
        }

        //
        // Return the status.
        //
        return l_return;

    } // has_left_drive_encoder_reset

    //--------------------------------------------------------------------------
    //
    // has_right_drive_encoder_reset
    //
    /**
     * Indicate whether the left drive encoder has been completely reset.
     */
    boolean has_right_drive_encoder_reset ()
    {
        //
        // Assume failure.
        //
        boolean l_return = false;

        //
        // Has the right encoder reached zero?
        //
        if (a_right_encoder_count () == 0)
        {
            //
            // Set the status to a positive indication.
            //
            l_return = true;
        }

        //
        // Return the status.
        //
        return l_return;

    } // has_right_drive_encoder_reset

    //--------------------------------------------------------------------------
    //
    // have_drive_encoders_reset
    //
    /**
     * Indicate whether the encoders have been completely reset.
     */
    boolean have_drive_encoders_reset ()
    {
        //
        // Assume failure.
        //
        boolean l_return = false;

        //
        // Have the encoders reached zero?
        //
        if (has_left_drive_encoder_reset () && has_right_drive_encoder_reset ())
        {
            //
            // Set the status to a positive indication.
            //
            l_return = true;
        }

        //
        // Return the status.
        //
        return l_return;

    } // have_drive_encoders_reset


    /**
     * Access the shelterArm position.
     */
    double getShelterArmPosition ()
    {
        double l_return = 0.0;

        if (shelterArm != null)
        {
            l_return = shelterArm.getPosition ();
        }

        return l_return;

    } //

    void setShelterArmPosition(double position){

        //ensure the position is legal
        double legalPosition = Range.clip(position, Servo.MIN_POSITION, Servo.MAX_POSITION);
        if(shelterArm != null){
            shelterArm.setPosition(legalPosition);
        }
    }

    double getClimberLeftArmPosition () {

        double position = 0.0;

        if (climberLeftArm != null)
        {
            position = climberLeftArm.getPosition ();
        }

        return position;

    } //

    void setClimberLeftArmPosition(double position){

        //ensure the position is legal
        double legalPosition = Range.clip(position, Servo.MIN_POSITION, Servo.MAX_POSITION);
        if(climberLeftArm != null){
            climberLeftArm.setPosition(legalPosition);
        }
    }

    double getClimberRightArmPosition () {

        double position = 0.0;

        if (climberRightArm != null)
        {
            position = climberRightArm.getPosition ();
        }

        return position;

    } //

    void setClimberRightArmPosition(double position){

        //ensure the position is legal
        double legalPosition = Range.clip(position, Servo.MIN_POSITION, Servo.MAX_POSITION);
        if(climberRightArm != null){
            climberRightArm.setPosition(legalPosition);
        }
    }

    //--------------------------------------------------------------------------
    //
    // a_left_arm_power
    //
    /**
     * Access the left arm motor's power level.
     */
    double a_left_arm_power ()
    {
        double l_return = 0.0;

        if (v_motor_left_arm != null)
        {
            l_return = v_motor_left_arm.getPower ();
        }

        return l_return;

    } // a_left_arm_power

    //--------------------------------------------------------------------------
    //
    // m_left_arm_power
    //
    /**
     * Access the left arm motor's power level.
     */
    void m_left_arm_power (double p_level)
    {
        if (v_motor_left_arm != null)
        {
            v_motor_left_arm.setPower (p_level);
        }

    } // m_left_arm_power

    //--------------------------------------------------------------------------
    //
    // a_hand_position
    //
    /**
     * Access the hand position.
     */
    double a_hand_position ()
    {
        double l_return = 0.0;

        if (v_servo_left_hand != null)
        {
            l_return = v_servo_left_hand.getPosition ();
        }

        return l_return;

    } // a_hand_position

    //--------------------------------------------------------------------------
    //
    // m_handm_hand_position
    //
    /**
     * Mutate the hand position.
     */
    void m_hand_position (double p_position)
    {
        //
        // Ensure the specific value is legal.
        //
        double l_position = Range.clip
            ( p_position
            , Servo.MIN_POSITION
            , Servo.MAX_POSITION
            );

        //
        // Set the value.  The right hand value must be opposite of the left
        // value.
        //
        if (v_servo_left_hand != null)
        {
            v_servo_left_hand.setPosition (l_position);
        }
        if (v_servo_right_hand != null)
        {
            v_servo_right_hand.setPosition (1.0 - l_position);
        }

    } // m_hand_position

    //--------------------------------------------------------------------------
    //
    // open_hand
    //
    /**
     * Open the hand to its fullest.
     */
    void open_hand ()

    {
        //
        // Set the value.  The right hand value must be opposite of the left
        // value.
        //
        if (v_servo_left_hand != null)
        {
            v_servo_left_hand.setPosition (Servo.MAX_POSITION);
        }
        if (v_servo_right_hand != null)
        {
            v_servo_right_hand.setPosition (Servo.MIN_POSITION);
        }

    } // open_hand

    //--------------------------------------------------------------------------
    //
    // v_warning_generated
    //
    /**
     * Indicate whether a message is a available to the class user.
     */
    private boolean v_warning_generated = false;

    //--------------------------------------------------------------------------
    //
    // v_warning_message
    //
    /**
     * Store a message to the user if one has been generated.
     */
    private String v_warning_message;

    //--------------------------------------------------------------------------
    //
    // driveFrontLeft
    //
    /**
     * Manage the aspects of the left drive motor.
     */
    private DcMotor driveFrontLeft;

    //--------------------------------------------------------------------------
    //
    // driveFrontRight
    //
    /**
     * Manage the aspects of the right drive motor.
     */
    private DcMotor driveFrontRight;

    private DcMotor driveRearLeft;

    private DcMotor driveRearRight;

    /**
     * Manage the sweeper motor
     */
    private DcMotor sweeper;

    private DcMotor hanging1;
    private DcMotor hanging2;

    private Servo shelterArm;
    private Servo climberLeftArm;
    private Servo climberRightArm;
    //--------------------------------------------------------------------------
    //
    // v_motor_left_arm
    //
    /**
     * Manage the aspects of the left arm motor.
     */
    private DcMotor v_motor_left_arm;

    //--------------------------------------------------------------------------
    //
    // v_servo_left_hand
    //
    /**
     * Manage the aspects of the left hand servo.
     */
    private Servo v_servo_left_hand;

    //--------------------------------------------------------------------------
    //
    // v_servo_right_hand
    //
    /**
     * Manage the aspects of the right hand servo.
     */
    private Servo v_servo_right_hand;

    /**
     * Manage the aspects of the shelter arm
     */
    private Servo vShelterArm;


} // PushBotHardware
