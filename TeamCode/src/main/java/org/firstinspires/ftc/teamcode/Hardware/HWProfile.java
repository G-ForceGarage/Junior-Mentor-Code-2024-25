package org.firstinspires.ftc.teamcode.Hardware;

import com.arcrobotics.ftclib.drivebase.MecanumDrive;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoImplEx;

public class HWProfile {
    final public int LIFT_BOTTOM=0;
    final public int LIFT_LOW=-1800;
    final public int LIFT_HIGH=-3700;
    final public int liftAdjust=50;
    final public int MAX_LIFT_VALUE = -3900;

    /* Public OpMode members. */
    public MotorEx motorLF = null;
    //
    public MotorEx motorLR = null;
    //
    public MotorEx motorRF = null;
    //1
    public MotorEx motorRR = null;

    public DcMotorEx motorLift =null;

    public DcMotorEx light = null;

    public Servo servoIntakeLeft = null;
    public Servo servoIntakeRight = null;
    //port0CH
    public CRServo servoIntake = null;
    public Servo servoIntakeExtend = null;
    public Servo servoDepositAngle = null;
    public Servo servoPincher = null;
    public Servo servoWrist = null;
    public Servo servoTwist = null;
    //port1CH

    public IMU imu = null;
    //

    public MecanumDrive mecanum = null;
    // public MotorEx autoLight = null;

    public Boolean opModeTeleop = null;

    IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
            RevHubOrientationOnRobot.LogoFacingDirection.UP,
            RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));

    /* local OpMode members. */
    HardwareMap hwMap =  null;

    /* Constructor */
    public HWProfile(){
    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap, boolean teleOp) {
        // Save reference to Hardware map
        hwMap = ahwMap;
        this.opModeTeleop = teleOp;

            //drive motor init
            motorLF = new MotorEx(ahwMap, "motorLF", Motor.GoBILDA.RPM_1150);
            motorLF.setRunMode(Motor.RunMode.RawPower);
            motorLF.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
            motorLF.resetEncoder();

            motorLR = new MotorEx(ahwMap, "motorLR", Motor.GoBILDA.RPM_1150);
            motorLR.setRunMode(Motor.RunMode.RawPower);
            motorLR.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
            motorLR.resetEncoder();

            motorRF = new MotorEx(ahwMap, "motorRF", Motor.GoBILDA.RPM_1150);
            motorRF.setRunMode(Motor.RunMode.RawPower);
            motorRF.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
            motorRF.resetEncoder();

            motorRR = new MotorEx(ahwMap, "motorRR", Motor.GoBILDA.RPM_1150);
            motorRR.setRunMode(Motor.RunMode.RawPower);
            motorRR.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
            motorRR.resetEncoder();

            motorLift = hwMap.get(DcMotorEx.class, "motorLift");
            motorLift.setDirection(DcMotorSimple.Direction.FORWARD);
            motorLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motorLift.setPower(1);
            motorLift.setTargetPosition(0);
            motorLift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            light = hwMap.get(DcMotorEx.class, "light");
            light.setPower(0);

            servoIntake = hwMap.get(CRServo.class, "servoIntake");
            servoIntake.setPower(0);
            servoIntakeExtend = hwMap.get(ServoImplEx.class, "servoIntakeExtend");
            servoIntakeExtend.setPosition(0.35);
            servoDepositAngle = hwMap.get(ServoImplEx.class, "servoDepositAngle");
            servoDepositAngle.setPosition(0.5);
            servoPincher = hwMap.get(ServoImplEx.class, "servoDeposit");
            servoPincher.setPosition(1);
            servoWrist = hwMap.get(ServoImplEx.class, "servoWrist");
            servoWrist.setPosition(0.75);
            servoTwist = hwMap.get(ServoImplEx.class, "servoTwist");
            servoTwist.setPosition(0.5);
            //init imu
        imu = hwMap.get(IMU.class, "imu");

        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.FORWARD));
        imu.initialize(parameters);

            //drivebase init
            mecanum = new com.arcrobotics.ftclib.drivebase.MecanumDrive(motorLF, motorRR, motorLR, motorRF);


    }   // end of init() method

}       // end of the HardwareProfile class