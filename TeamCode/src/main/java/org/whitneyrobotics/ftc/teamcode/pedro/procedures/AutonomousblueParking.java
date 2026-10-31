package org.whitneyrobotics.ftc.teamcode.pedro.procedures;

import static com.pedropathing.api.Paths.curve;
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

public class AutonomousblueParking extends OpModeEx {

    private Follower follower;
    public final PoseFactory p = PoseFactory.degrees();



    // 1. BLUE_PARKING Poses
    public final Pose blueStartPose = p.of(136, 35, 90);
    public final Pose blueScorePose = p.of(57, 20, 0);
    public final Pose blueParkPose = p.of(136, 35, 90);

   

// So far this is linear according to the path made in pedropathing but can be changed.

    public Command autoRoutine() {
        return sequential(
                follow(follower, line(blueStartPose, blueScorePose).linear(blueStartPose, blueScorePose)),
                // add other commands here
                follow(follower, line(blueScorePose, blueParkPose).linear(blueScorePose, blueParkPose))
        );
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
        if (follower != null) {
            follower.update();
        }
        telemetry.addData("Selected Path", "Red Parking");
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

        if (follower != null) {
            telemetryPro.addData("X", follower.pose().x());
            telemetryPro.addData("Y", follower.pose().y());
            telemetryPro.addData("Heading", Math.toDegrees(follower.pose().heading()));
            telemetry.addData("Follower Mode", follower.mode());
        }
        telemetry.update();
    }
}