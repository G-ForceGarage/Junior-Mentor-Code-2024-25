package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Hardware.HWProfile;

@Config
@TeleOp(name = "World's Best Teleop - WORLDS", group = "Competition")
public class WorldsBestTeleopWorlds extends OpMode {

    // Hardware control
    private HWProfile robot;
    private GamepadEx gp1;

    public static int target=0, liftMinOffset=0;

    //private final double ticks_in_degrees = 8192/360;
    private double strafePower, forwardPower;

    private boolean intakeToggle=false, toggleReadyUp, toggleReadyDown, pincherToggle=false, pincherReady=false, armReady=false, armToggle=false, twistReady=false, twistToggle=false;
    private ElapsedTime intakeTime = new ElapsedTime();
    private boolean intakeReady = true;

    // lift control variables
    private int bumpCount=0,offset=0, liftPos=0;

    @Override
    public void init(){
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        telemetry.addData("initializing ", "Robot");
        telemetry.update();

        robot = new HWProfile();
        OpMode opMode = this;
        robot.init(hardwareMap, true);

        telemetry.addData("Robot ", "Initialized");
        telemetry.addData("Initializing ", "mechOps");
        telemetry.update();

        telemetry.addData("mechOps ", "Initialized");
        telemetry.addData("Initializing ", "telemetry");
        telemetry.update();

        telemetry.addData("mechOps ", "Initialized");
        telemetry.addData("Initializing ", "intakeControl");
        telemetry.update();

        gp1 = new GamepadEx(gamepad1);
        telemetry.addData("Ready to Run: ", "GOOD LUCK");
        telemetry.update();

    }

    @Override
    public void loop(){
//DRIVE CONTROL SECTION//
        //drive power input from analog sticks
        forwardPower=-gp1.getLeftY();
        strafePower=-gp1.getLeftX();

        //FTCLib drive code, instantiated in HWProfile
        robot.mecanum.driveFieldCentric(strafePower,forwardPower,-gp1.getRightX()*0.75,robot.imu.getRobotYawPitchRollAngles().getYaw(AngleUnit.RADIANS), true);

        if(gp1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)>0.05){
            robot.servoIntakeExtend.setPosition(gp1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER));
        }

//INTAKE CONTROL SECTION//
        //run intake forward
        if(gp1.getButton(GamepadKeys.Button.A)){
            robot.servoIntake.setPower(-1);
        }else if(gp1.getButton(GamepadKeys.Button.Y)){
            robot.servoIntake.setPower(1);
        }else{
            robot.servoIntake.setPower(0);
        }


        //extend intake (toggle)
        if(gp1.getButton(GamepadKeys.Button.B)&&intakeReady){
            intakeToggle=!intakeToggle;
        }
        if(!gp1.getButton(GamepadKeys.Button.B)) {
            intakeReady = true;
        }else{
            intakeReady=false;
        }

        if(intakeToggle){
            //extended state
            robot.servoIntakeExtend.setPosition(0);
            robot.servoWrist.setPosition(0.1);
        }else{
            //retracted state
            robot.servoIntakeExtend.setPosition(0.275);
            robot.servoWrist.setPosition(0.925);
        }
//TRANSFER CONTROL SECTION//
        //extend intake (toggle)
        if(gp1.getButton(GamepadKeys.Button.X)&&pincherReady){
            pincherToggle=!pincherToggle;
        }
        if(!gp1.getButton(GamepadKeys.Button.X)) {
            pincherReady = true;
        }else{
            pincherReady=false;
        }

        if(pincherToggle){
            robot.servoPincher.setPosition(0.9);
        }else{
            robot.servoPincher.setPosition(0.1);
        }

//ARM CONTROL SECTION//
        if(gp1.getButton(GamepadKeys.Button.LEFT_STICK_BUTTON)&&armReady){
            armToggle=!armToggle;
        }
        if(!gp1.getButton(GamepadKeys.Button.LEFT_STICK_BUTTON)) {
            armReady = true;
        }else{
            armReady=false;
        }

        if(armToggle){
            robot.servoDepositAngle.setPosition(1);
        }else{
            robot.servoDepositAngle.setPosition(0.5);
        }

//TWIST CONTROL SECTION//
        if(gp1.getButton(GamepadKeys.Button.DPAD_LEFT)&&twistReady){
            twistToggle=!twistToggle;
        }
        if(!gp1.getButton(GamepadKeys.Button.X)) {
            twistReady = true;
        }else{
            twistReady=false;
        }

        if(twistToggle){
            robot.servoTwist.setPosition(1);
        }else{
            robot.servoTwist.setPosition(0.5);
        }

//LIGHT CONTROL SECTION//
        if(gp1.getButton(GamepadKeys.Button.DPAD_RIGHT)){
            robot.light.setPower(1);
        }else{
            robot.light.setPower(0);
        }

//LIFT CONTROL SECTION//
        //lift ready toggles
        if(!gp1.isDown(GamepadKeys.Button.RIGHT_BUMPER)){
            toggleReadyUp=true;
        }
        if(!gp1.isDown(GamepadKeys.Button.LEFT_BUMPER)){
            toggleReadyDown=true;
        }

        //lift reset
        if(gp1.isDown(GamepadKeys.Button.RIGHT_STICK_BUTTON)){
            bumpCount=0;
            offset=0;
            liftPos= robot.LIFT_BOTTOM;
            target= robot.LIFT_BOTTOM;
        }

        //increase lift position
        if (gp1.getButton(GamepadKeys.Button.RIGHT_BUMPER)&&toggleReadyUp){
            offset=0;
            toggleReadyUp=false;
            if(bumpCount<2){
                bumpCount++;
            }

        }else if(gp1.getButton(GamepadKeys.Button.LEFT_BUMPER)&&toggleReadyDown){
            offset=0;
            toggleReadyDown=false;
            if(bumpCount>0){
                bumpCount--;
            }
        }

        //keep track of lift button clicks
        if (bumpCount == 0) {
            target = robot.LIFT_BOTTOM+offset;
        } else if (bumpCount == 1) {
            target = robot.LIFT_LOW+offset;
        } else if (bumpCount == 2) {
            target = robot.LIFT_HIGH+offset;
        }

        //adjust lift position
        if(gp1.getTrigger(GamepadKeys.Trigger.RIGHT_TRIGGER)>0.5){
            offset-= robot.liftAdjust;
        }else if(gp1.getTrigger(GamepadKeys.Trigger.LEFT_TRIGGER)>0.5){
            offset += robot.liftAdjust;
        }
        Range.clip(target, robot.MAX_LIFT_VALUE,robot.LIFT_BOTTOM);
        //apply lift target
        robot.motorLift.setTargetPosition(target);
        telemetry.addData("Lift position:",robot.motorLift.getCurrentPosition());
    }

    @Override
    public void stop(){

    }

}