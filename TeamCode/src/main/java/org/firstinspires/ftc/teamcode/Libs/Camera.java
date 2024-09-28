package org.firstinspires.ftc.teamcode.Libs;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.acmerobotics.roadrunner.Pose2d;

import org.firstinspires.ftc.robotcore.external.hardware.camera.BuiltinCameraDirection;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.vision.VisionPortal;
import org.firstinspires.ftc.vision.apriltag.AprilTagDetection;
import org.firstinspires.ftc.vision.apriltag.AprilTagProcessor;

import java.util.List;

public class Camera {
    private static final boolean USE_WEBCAM = true;  // true for webcam, false for phone camera

    /**
     * The variable to store our instance of the AprilTag processor.
     */
    private AprilTagProcessor aprilTag;

    /**
     * The variable to store our instance of the vision portal.
     */
    private VisionPortal visionPortal;

    private int filterId;
    private final boolean X = true;
    private final boolean Y = false;
    private boolean isRobotLocalized = true;

    public Camera(){
        filterId = 0;
    }

    /**
     * Position 1 -> Left
     * Position 2 -> Middle
     * Position 3 -> Right
     * Toggle True -> X
     * Toggle False -> Y
     */
    public double getPosition(boolean xytoggle){
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        double positionValue = 0;

        for(AprilTagDetection detection : currentDetections) {
            if(detection.metadata != null){
                if(detection.id == filterId || detection.id == filterId + 3){
                    if(xytoggle) {
                        positionValue = calcBackDropPositionX(detection);
                    } else {
                        positionValue = calcBackDropPositionY(detection);
                    }
                }
            }
        }

        return positionValue;
    }

    public double getRotation(){
        List<AprilTagDetection> currentDetections = aprilTag.getDetections();

        double rotationValue = 0;

        for(AprilTagDetection detection : currentDetections) {
            if(detection.metadata != null){
                if(detection.id == filterId || detection.id == filterId + 3){
                    rotationValue = calcPositionRotation(detection);
                }
            }
        }

        return rotationValue;
    }

    public Pose2d getRelativePosition(){
        Pose2d pose = new Pose2d(
                getPosition(true),
                getPosition(false),
                getRotation()
        );
        pose = modifyPosition(pose);
        return pose;
    }

    public void initAprilTag() {

        // Create the AprilTag processor the easy way.
        aprilTag = AprilTagProcessor.easyCreateWithDefaults();

        // Create the vision portal the easy way.
        if (USE_WEBCAM) {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    hardwareMap.get(WebcamName.class, "Webcam 1"), aprilTag);
        } else {
            visionPortal = VisionPortal.easyCreateWithDefaults(
                    BuiltinCameraDirection.BACK, aprilTag);
        }

        startStreaming();
    }   // end method initAprilTag()

    private double calcBackDropPositionX(AprilTagDetection xDetection){
        return xDetection.ftcPose.x;
    }

    private double calcBackDropPositionY(AprilTagDetection yDetection){
        return yDetection.ftcPose.y;
    }

    private double calcPositionRotation(AprilTagDetection rotationDetection){
        return rotationDetection.ftcPose.bearing;
    }

    private void startStreaming(){
        visionPortal.resumeStreaming();
    }

    public void stopCamera() { stopSteaming(); }

    private void stopSteaming(){
        visionPortal.stopStreaming();
    }

    public void setPositionId(int Id){
        filterId = Id;
    }

    public Pose2d modifyPosition(Pose2d position){
        return new Pose2d(
                position.position.x,
                position.position.y,
                position.heading.toDouble());
    }
}