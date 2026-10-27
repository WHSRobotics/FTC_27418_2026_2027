package org.whitneyrobotics.ftc.teamcode.pedro.procedures;

import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.pedro.Constants;

public class Autonomous extends OpModeEx {

    public enum PathChoice {
        BLUE_PARKING,
        //when blue hive is facing up
        RED_PARKING,
        //when red hive is facing up
        BLUE_OPPOSITE,
        RED_OPPOSITE
        // Add more paths here as you create them
    }

    private PathChoice currentPathChoice = PathChoice.BLUE_PARKING;
    //this is if we are starting from the blue parking spot//
    private Follower follower;
    // this is if we are using pose if not this can be changed//
    public final PoseFactory p = PoseFactory.degrees();

//these points need to be changed. These points are for the path from parking space to shooting position to back to parking space.
// so parking space and start pose is the same thing

    // 1. BLUE_PARKING Poses
    public final Pose blueStartPose = p.of(136, 35, 90);
    public final Pose blueScorePose = p.of(57, 20, 0);
    public final Pose blueParkPose = p.of(136, 35, 90);

    // 2. RED_PARKING Poses
    // these need to be changed i didnt actually set it to the right values
    public final Pose redStartPose = p.of(11, 115, 90);
    public final Pose redScorePose = p.of(60, 119, 0);
    public final Pose redParkPose = p.of(11, 115, 90);

    // 3. BLUE_OPPOSITE Poses
    public final Pose blueOppStartPose = p.of(130, 45, 90);
    public final Pose blueOppScorePose = p.of(57, 100, 0);
    public final Pose blueOppParkPose = p.of(130, 45, 90);

    // 4. RED_OPPOSITE Poses
    public final Pose redOppStartPose = p.of(11, 113, 90);
    public final Pose redOppScorePose = p.of(57, 121, 0);
    public final Pose redOppParkPose = p.of(11, 113, 90);

// so far this is linear according to the path made in pedropathing but can be changed

    public Command autoRoutine() {
        switch (currentPathChoice) {
            case BLUE_PARKING:
                return sequential(
                        follow(follower, line(blueStartPose, blueScorePose).linear(blueStartPose, blueScorePose)),
                        // Add mechanism commands here.
                        follow(follower, line(blueScorePose, blueParkPose).linear(blueScorePose, blueParkPose))
                );

            case RED_PARKING:
                return sequential(
                        follow(follower, line(redStartPose, redScorePose).linear(redStartPose, redScorePose)),
                        // Add mechanism commands here.
                        follow(follower, line(redScorePose, redParkPose).linear(redScorePose, redParkPose))
                );
                // this is not linear I need to make it curve

            case BLUE_OPPOSITE:
                return sequential(
                        follow(follower, line(blueOppStartPose, blueOppScorePose).linear(blueOppStartPose, blueOppScorePose)),
                        // Add mechanism commands here.
                        follow(follower, line(blueOppScorePose, blueOppParkPose).linear(blueOppScorePose, blueOppParkPose))
                );

            case RED_OPPOSITE:
                return sequential(
                        follow(follower, line(redOppStartPose, redOppScorePose).linear(redOppStartPose, redOppScorePose)),
                        // Add mechanism commands here.
                        follow(follower, line(redOppScorePose, redOppParkPose).linear(redOppScorePose, redOppParkPose))
                );

            default:
                return sequential(
                        follow(follower, line(blueStartPose, blueScorePose).linear(blueStartPose, blueScorePose))
                );
        }
    }

    @Override
    public void initInternal() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        if (follower != null) {
            follower.setPose(blueStartPose);
            follower.update();
        }
    }

    @Override
    public void initInternalLoop() {
        if (gamepad1.A.value()) {
            currentPathChoice = PathChoice.BLUE_PARKING;
            if (follower != null) follower.setPose(blueStartPose);
        } else if (gamepad1.B.value()) {
            currentPathChoice = PathChoice.RED_PARKING;
            if (follower != null) follower.setPose(redStartPose);
        } else if (gamepad1.X.value()) {
            currentPathChoice = PathChoice.BLUE_OPPOSITE;
            if (follower != null) follower.setPose(blueOppStartPose);
        } else if (gamepad1.Y.value()) {
            currentPathChoice = PathChoice.RED_OPPOSITE;
            if (follower != null) follower.setPose(redOppStartPose);
        }

        if (follower != null) {
            follower.update();
        }

        telemetry.addData("Selected Path", currentPathChoice);
        telemetry.update();
    }

    @Override
    public void startInternal() {
        schedule(autoRoutine());
    }

    @Override
    protected void loopInternal() {
        if (follower != null) {
            follower.update();
        }
        Scheduler.execute();
        // Any other methods can be added here in this loop
        if (follower != null) {
            telemetryPro.addData("X", follower.pose().x());
            telemetryPro.addData("Y", follower.pose().y());
            telemetryPro.addData("Heading", Math.toDegrees(follower.pose().heading()));
            telemetry.addData("Follower Mode", follower.mode());
        }
        telemetry.update();
    }
}