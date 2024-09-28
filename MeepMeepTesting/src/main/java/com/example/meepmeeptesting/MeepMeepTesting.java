package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeBlueDark;
import com.noahbres.meepmeep.core.colorscheme.scheme.ColorSchemeRedDark;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        // Declare our first bot
        RoadRunnerBotEntity myFirstBot = new DefaultBotBuilder(meepMeep)
                // We set this bot to be blue
                .setColorScheme(new ColorSchemeBlueDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(0, 0, 0))
                                .forward(30)
                                .turn(Math.toRadians(90))
                                .forward(30)
                                .turn(Math.toRadians(90))
                                .forward(30)
                                .turn(Math.toRadians(90))
                                .forward(30)
                                .turn(Math.toRadians(90))
                                .build()
                );

        // Declare out second bot
        RoadRunnerBotEntity mySecondBot = new DefaultBotBuilder(meepMeep)
                // We set this bot to be red
                .setColorScheme(new ColorSchemeRedDark())
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(new Pose2d(30, 30, Math.toRadians(180)))
                                .UNSTABLE_addTemporalMarkerOffset(15.66, () -> {
                                })
                                .UNSTABLE_addTemporalMarkerOffset(18.53, () -> {
                                })
                                .UNSTABLE_addTemporalMarkerOffset(18.53, () -> {
                                })
                                .splineTo(new Vector2d(-28.02, 67.98), Math.toRadians(254.05))
                                .splineTo(new Vector2d(-29.22, 63.77), Math.toRadians(240.46))
                                .splineTo(new Vector2d(-29.42, 57.34), Math.toRadians(268.21))
                                .splineTo(new Vector2d(-30.23, 54.53), Math.toRadians(254.05))
                                .splineTo(new Vector2d(-31.23, 49.51), Math.toRadians(-26.57))
                                .splineTo(new Vector2d(-27.41, 2.91), Math.toRadians(262.41))
                                .splineTo(new Vector2d(-17.77, 6.93), Math.toRadians(-1.71))
                                .splineTo(new Vector2d(-10.74, 3.31), Math.toRadians(-27.22))
                                .splineTo(new Vector2d(-1.71, 3.31), Math.toRadians(-11.53))
                                .splineTo(new Vector2d(4.72, 5.72), Math.toRadians(-10.99))
                                .splineTo(new Vector2d(14.56, 2.31), Math.toRadians(-33.69))
                                .splineTo(new Vector2d(21.19, -2.71), Math.toRadians(-56.74))
                                .splineTo(new Vector2d(31.03, -5.92), Math.toRadians(-54.16))
                                .splineTo(new Vector2d(34.24, -12.95), Math.toRadians(-59.04))
                                .splineTo(new Vector2d(36.65, -18.18), Math.toRadians(-59.15))
                                .splineTo(new Vector2d(39.06, -25.61), Math.toRadians(-34.88))
                                .splineTo(new Vector2d(47.50, -34.64), Math.toRadians(16.70))
                                .splineTo(new Vector2d(59.55, -31.03), Math.toRadians(36.38))
                                .splineTo(new Vector2d(65.77, -27.41), Math.toRadians(0.00))
                                .splineTo(new Vector2d(67.78, -17.97), Math.toRadians(95.27))
                                .splineTo(new Vector2d(66.18, -13.15), Math.toRadians(86.82))
                                .splineTo(new Vector2d(66.78, -2.91), Math.toRadians(45.00))
                                .splineTo(new Vector2d(60.75, 9.14), Math.toRadians(-26.57))
                                .splineTo(new Vector2d(48.10, 20.79), Math.toRadians(137.37))
                                .splineTo(new Vector2d(42.68, 26.41), Math.toRadians(113.96))
                                .splineTo(new Vector2d(37.05, 40.27), Math.toRadians(112.09))
                                .splineTo(new Vector2d(29.82, 53.12), Math.toRadians(119.36))
                                .build()
                );

        Image img = null;
        try { img = ImageIO.read(new File("C:\\Users\\FRC_Laptop\\Documents\\GitHub\\CenterStage2023-2024\\MeepMeepTesting\\CRI 24 Field.png")); }
        catch (IOException e) {}

        meepMeep.setBackground(img)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)

                // Add both of our declared bot entities
                .addEntity(myFirstBot)
                .addEntity(mySecondBot)
                .start();
    }
}