package org.whitneyrobotics.ftc.teamcode.OpMode.Autonomous;

import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.pedropathing.Constants;

@Autonomous(name = "red down")
public class redDown extends OpModeEx {

    private Follower follower;
    public final PoseFactory p = PoseFactory.degrees();

    // RED_PARKING Poses
    public final Pose redStartPose = p.of(127, 13, 90);
    public final Pose redPathPose = p.of(127, 22, 90);
    public final Pose redPathPose2 = p.of(83, 22, 90);
    public final Pose redParkPose = p.of(127, 22, 90);

    public Path path1() {
        return line(redStartPose, redPathPose).linear(redStartPose, redPathPose);
    }

    public Path path2() {
        return line(redPathPose, redPathPose2).linear(redPathPose, redPathPose2);
    }

    public Path path3() {
        return line(redPathPose2, redParkPose).linear(redPathPose2, redParkPose);
    }

    public Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                follow(follower, path2()),
                waitMs(2000), // Waits 2000 ms before parking
                follow(follower, path3())
        );
    }

    @Override
    public void initInternal() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(redStartPose);
        follower.update();
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
            telemetryPro.addData("Follower Mode", follower.mode());
        }
        telemetryPro.update();
    }
}
