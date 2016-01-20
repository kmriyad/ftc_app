package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.ftccommon.DbgLog;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.GyroSensor;
import com.qualcomm.robotcore.hardware.IrSeekerSensor;
import com.qualcomm.robotcore.hardware.OpticalDistanceSensor;
import com.qualcomm.robotcore.hardware.TouchSensor;
import com.qualcomm.robotcore.util.Range;

//------------------------------------------------------------------------------
//
// PushBotHardwareSensors
//

/**
 * Provides a single sensor access point between custom op-modes and the
 * OpMode class for the Push Bot.  It does this by extending the original Push
 * Bot's hardware and telemetry classes.
 *
 * This class prevents the custom op-mode from throwing an exception at runtime.
 * If any hardware fails to map, a warning will be shown via telemetry data,
 * calls to methods will fail, but will not cause the application to crash.
 *
 * @author SSI Robotics
 * @version 2015-08-13-20-04
 */
public class MxSensors extends MxTelemetry {

    private TouchSensor v_sensor_touch;
    private IrSeekerSensor v_sensor_ir;
    private OpticalDistanceSensor sODS;
    private GyroSensor sGyro;
    private ColorSensor sLine;
    int gyroX, gyroY, gyroZ = 0;
    int heading = 0;


    public MxSensors()

    {
        //
        // Initialize base classes.
        //
        // All via self-construction.

        //
        // Initialize class members.
        //
        // All via self-construction.

    } // PushBotHardwareSensors

    //--------------------------------------------------------------------------
    //
    // init
    //
    /**
     * Perform any actions that are necessary when the OpMode is enabled.
     *
     * The system calls this member once when the OpMode is enabled.
     */
    @Override
    public void init ()

    {
        super.init ();

        try
        {
            v_sensor_touch = hardwareMap.touchSensor.get ("sensor_touch");
        }
        catch (Exception p_exeception)
        {
            m_warning_message ("sensor_touch");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            v_sensor_touch = null;
        }

        try
        {
            v_sensor_ir = hardwareMap.irSeekerSensor.get ("sensor_ir");
        }
        catch (Exception p_exeception)
        {
            m_warning_message ("sensor_ir");
            DbgLog.msg (p_exeception.getLocalizedMessage ());

            v_sensor_ir = null;
        }

        try
        {
            sODS = hardwareMap.opticalDistanceSensor.get ("sODS");
        }
        catch (Exception p_exeception)
        {
            try
            {
                sODS = hardwareMap.opticalDistanceSensor.get
                    ( "sensor_eopd"
                    );
            }
            catch (Exception p_exeception_eopd)
            {
                try
                {
                    sODS = hardwareMap.opticalDistanceSensor.get
                        ( "sensor_EOPD"
                        );
                }
                catch (Exception p_exeception_EOPD)
                {
                    m_warning_message ("sensor_ods/eopd/EOPD");
                    DbgLog.msg
                        ( "Can't map sensor_ods nor sensor_eopd, nor" +
                            " sensor_EOPD ("
                        + p_exeception_EOPD.getLocalizedMessage ()
                        + ").\n"
                        );

                    sODS = null;
                }
            }
        }

        //Gyro Sensor
        try{

            sGyro = hardwareMap.gyroSensor.get("sGyro");

        }catch(Exception ex){

            m_warning_message ("sGyro");
            DbgLog.msg (ex.getLocalizedMessage ());
            sGyro = null;
        }

        //Color Sensors
        try{
            sLine = hardwareMap.colorSensor.get("sLine");
            sLine.enableLed(true);
        }catch(Exception ex){

            m_warning_message ("sLine");
            DbgLog.msg (ex.getLocalizedMessage ());
            sLine = null;
        }

    } // init

    @Override
    public void start(){
        sLine.enableLed(true);
    }

    public void stop(){
        sLine.enableLed(false);
    }

    //Gyro

    void calibrateGyro() throws InterruptedException{

        sGyro.calibrate();
        while (sGyro.isCalibrating()){
            Thread.sleep(50);
        }
        sGyro.resetZAxisIntegrator();
    }

    int getGyroHeading(){

        heading = sGyro.getHeading();
        return heading;
    }

    boolean haveGyroHeadingReached(int heading){

        boolean reached = false;
        if(sGyro != null){
            if(getGyroHeading() <= heading){
                reached = true;
            }
        }

        return reached;
    }

    //ODS

    boolean isWhiteTapeDetected(){

        boolean detection = false;

        if(sODS != null){
            if(sODS.getLightDetected() > 0.8){
                detection = true;
            }
        }
        return detection;
    }

    //--------------------------------------------------------------------------
    //
    // is_touch_sensor_pressed
    //
    /**
     * Indicate whether the touch sensor has been pressed.
     */
    boolean is_touch_sensor_pressed ()

    {
        boolean l_return = false;

        if (v_sensor_touch != null)
        {
            l_return = v_sensor_touch.isPressed ();
        }

        return l_return;

    } // is_touch_sensor_pressed

    //--------------------------------------------------------------------------
    //
    // move_arm_upward_until_touch
    //
    /**
     * Apply upward power to the arm motor until the touch sensor is pressed.
     */
    boolean move_arm_upward_until_touch ()

    {
        //
        // If the touch sensor is pressed, halt the motors.
        //
        if (is_touch_sensor_pressed ())
        {
            m_left_arm_power (0.0f);
        }
        //
        // Move the arm upward at full power.
        //
        else
        {
            m_left_arm_power (1.0f);
        }

        //
        // Return whether the sensor has been pressed.
        //
        return is_touch_sensor_pressed ();

    } // move_arm_upward_until_touch

    //--------------------------------------------------------------------------
    //
    // a_ir_angle
    //
    /**
     * Access the IR sensor's estimated angle.
     */
    double a_ir_angle ()

    {
        double l_return = 0.0;

        if (v_sensor_ir != null)
        {
            l_return = v_sensor_ir.getAngle ();
        }

        return l_return;

    } // a_ir_angle

    //--------------------------------------------------------------------------
    //
    // a_ir_strength
    //
    /**
     * Access the IR sensor's estimated strength level.
     */
    double a_ir_strength ()

    {
        double l_return = 0.0;

        if (v_sensor_ir != null)
        {
            l_return = v_sensor_ir.getStrength ();
        }

        return l_return;

    } // a_ir_strength

    //--------------------------------------------------------------------------
    //
    // a_ir_angles_and_strengths
    //
    /**
     * Access the IR sensor's angle and strength levels.
     */
    IrSeekerSensor.IrSeekerIndividualSensor[] a_ir_angles_and_strengths ()

    {
        IrSeekerSensor.IrSeekerIndividualSensor[] l_return =
            { new IrSeekerSensor.IrSeekerIndividualSensor ()
            , new IrSeekerSensor.IrSeekerIndividualSensor ()
            };

        if (v_sensor_ir != null)
        {
            l_return = v_sensor_ir.getIndividualSensors ();
        }

        return l_return;

    } // a_ir_angles_and_strengths

    //--------------------------------------------------------------------------
    //
    // drive_to_ir_beacon
    //
    /**
     * Drive toward the IR beacon.
     */
    final int drive_to_ir_beacon_not_detected = -2;
    final int drive_to_ir_beacon_invalid = -1; // Unknown failure
    final int drive_to_ir_beacon_running = 0;
    final int drive_to_ir_beacon_done = 1;
    int drive_to_ir_beacon ()

    {
        //
        // Assume that the IR beacon is far away.
        //
        int l_return = drive_to_ir_beacon_invalid;

        //
        // Is the IR sensor too far from the beacon?
        //
        double l_strength = a_ir_strength ();
        if ((l_strength > 0.01) && (l_strength < 0.2))
        {
            //
            // Use the estimated angle to determine the direction and power that
            // needs to be applied to the drive motors.
            //
            double l_angle = a_ir_angle () / 240;
            double l_left = Range.clip (0.15 + l_angle, -1, 1);
            double l_right = Range.clip (0.15 - l_angle, -1, 1);

            setDrivePower (l_left, l_right);

            l_return = drive_to_ir_beacon_running;
        }
        //
        // The beacon can't be detected.
        //
        else if (l_strength <= 0.0)
        {
            setDrivePower (0.0, 0.0);

            l_return = drive_to_ir_beacon_not_detected;
        }
        //
        // The beacon is near.
        //
        else
        {
            setDrivePower (0.0, 0.0);

            l_return = drive_to_ir_beacon_done;
        }

        //
        // Return the status.
        //
        return l_return;

    } // drive_to_ir_beacon

    //--------------------------------------------------------------------------
    //
    // a_ods_light_detected
    //
    /**
     * Access the amount of light detected by the Optical Distance Sensor.
     */
    double a_ods_light_detected ()

    {
        double l_return = 0.0;

        if (sODS != null)
        {
            sODS.getLightDetected ();
        }

        return l_return;

    } // a_ods_light_detected






} // MxSensors
