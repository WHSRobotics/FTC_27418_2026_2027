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

public class AutonomousredParking extends OpModeEx {

    private Follower follower;
    public final PoseFactory p = PoseFactory.degrees();

    // RED_PARKING Poses
    public final Pose redStartPose = p.of(11, 115, 90);
    public final Pose redControlPose = p.of(35, 117, 45); // Control point to create a curved trajectory
    public final Pose redScorePose = p.of(60, 119, 0);
    public final Pose redParkPose = p.of(11, 115, 90);

    public Command autoRoutine() {
        return sequential(
                // Curved path from start to score pose using control point
                follow(follower, curve(redStartPose, redControlPose, redScorePose).linear(redStartPose, redScorePose)),
                // Add mechanism commands here if needed
                follow(follower, line(redScorePose, redParkPose).linear(redScorePose, redParkPose))
        );
    }

    @Override
    public void initInternal() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        if (follower != null) {
            follower.setPose(redStartPose);
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
