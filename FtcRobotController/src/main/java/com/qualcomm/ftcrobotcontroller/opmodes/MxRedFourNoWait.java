package com.qualcomm.ftcrobotcontroller.opmodes;

//------------------------------------------------------------------------------
//
// PushBotAuto
//
/**
 * The three indicates the third tile
 */
public class MxRedFourNoWait extends MxSensorsTelemetry

{
    private int v_state = 0;

    public MxRedFourNoWait()

    {
        //
        // Initialize base classes.
        //
        // All via self-construction.

        //
        // Initialize class members.
        //
        // All via self-construction.

    } // PushBotAuto

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
        //
        // Call the PushBotHardware (super/base class) start method.
        //
        super.start ();

        //
        // Reset the motor encoders on the drive wheels.
        //
        resetDriveEncoders();

    } // start

    //--------------------------------------------------------------------------
    //
    // loop
    //
    /**
     * Implement a state machine that controls the robot during auto-operation.
     * The state machine uses a class member and encoder input to transition
     * between states.
     *
     * The system calls this member repeatedly while the OpMode is running.
     */
    @Override public void loop ()

    {
        //----------------------------------------------------------------------
        //
        // State: Initialize (i.e. state_0).
        //
        switch (v_state)
        {
        //
        // Synchronize the state machine and hardware.
        //
        case 0:
            //
            // Reset the encoders to ensure they are at a known good value.
            //
            resetDriveEncoders();

            //
            // Transition to the next state when this method is called again.
            //
            v_state++;

            break;
        //
        // Drive forward until the encoders exceed the specified values.
        //
        case 1:
            //
            // Tell the system that motor encoders will be used.  This call MUST
            // be in this state and NOT the previous or the encoders will not
            // work.  It doesn't need to be in subsequent states.
            //
            driveUsingEncoders();

            //
            // Start the drive wheel motors at full power.
            //
            setDrivePower(-1.0f, -1.0f);
            setSweepPower(-1.0f);

            //
            // Have the motor shafts turned the required amount?
            //
            // If they haven't, then the op-mode remains in this state (i.e this
            // block will be executed the next time this method is called).
            //
            if (haveDriveEncodersReached(4500, 4500))
            {
                //
                // Reset the encoders to ensure they are at a known good value.
                //
                resetDriveEncoders();

                //
                // Stop the motors.
                //
                setDrivePower(0.0f, 0.0f);
                setSweepPower(0.0f);

                //
                // Transition to the next state when this method is called
                // again.
                //
                v_state++;
            }
            break;

        //
        // Wait...
        //
        case 2:
            if (have_drive_encoders_reset ())
            {
                v_state++;
            }
            break;
        //
        // Turn left until the encoders exceed the specified values.
        //
        case 3:
            driveUsingEncoders();
            setDrivePower(1.0f, -1.0f);
            setSweepPower(-1.0f);
            if (haveDriveEncodersReached(1500, 1500))
            {
                resetDriveEncoders();
                setDrivePower(0.0f, 0.0f);
                setSweepPower(0.0f);
                v_state++;
            }
            break;
        //
        // Wait...
        //
        case 4:
            if (have_drive_encoders_reset ())
            {
                v_state++;
            }
            break;
        //
        // Turn right until the encoders exceed the specified values.
        //
        case 5:
            driveUsingEncoders();
            setDrivePower(-1.0f, -1.0f);
            setSweepPower(-1.0f);
            if (haveDriveEncodersReached(9000, 9000))
            {
                resetDriveEncoders();
                setDrivePower(0.0f, 0.0f);
                setSweepPower(0.0f);
                v_state++;
            }
            break;
        //
        // Wait...
        //
        case 6:
            if (have_drive_encoders_reset ())
            {
                v_state++;
            }
            break;

        //
        // Turn right until the encoders exceed the specified values.
        //
        case 7:
            driveUsingEncoders();
            setDrivePower(1.0f, -1.0f);
            setSweepPower(-1.0f);
            if (haveDriveEncodersReached(1600, 1600))
            {
                resetDriveEncoders();
                setDrivePower(0.0f, 0.0f);
                setSweepPower(0.0f);
                v_state++;
            }
            break;

        case 8:
            if (have_drive_encoders_reset ())
            {
                v_state++;
            }
            break;
        case 9:
            driveUsingEncoders();
            setDrivePower(-1.0f, -1.0f);
            setSweepPower(-1.0f);
            if (haveDriveEncodersReached(4000, 4000))
            {
                resetDriveEncoders();
                setDrivePower(0.0f, 0.0f);
                setSweepPower(0.0f);
                v_state++;
            }
            break;

        case 10:
            if (have_drive_encoders_reset ())
            {
                v_state++;
            }
            break;

        //
        // Perform no action - stay in this case until the OpMode is stopped.
        // This method will still be called regardless of the state machine.
        //
        default:
            //
            // The autonomous actions have been accomplished (i.e. the state has
            // transitioned into its final state.
            //
            break;
        }

        //
        // Send telemetry data to the driver station.
        //
        update_telemetry (); // Update common telemetry
        telemetry.addData ("18", "State: " + v_state);

    } // loop



} // MxAuto
