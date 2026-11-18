package org.whitneyrobotics.ftc.teamcode.OpMode.Autonomous;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.pedropathing.Constants;

@Autonomous(name = "redUp")
public class redUp extends OpModeEx {
    private Follower follower;

    public final PoseFactory poseFactory = PoseFactory.degrees();

    public final Pose start = poseFactory.of(58.75, 8.75, 90);
    public final Pose point1 = poseFactory.of(58.75, 21.75, 90);
    public final Pose point2 = poseFactory.of(9.75, 66.75, 90);
    public final Pose point3 = poseFactory.of(9.75, 92.75, -90);

    // Autonomous routine
    public Command autoRoutine() {
        return sequential(
                follow(follower, path1()),
                follow(follower, path2()),
                follow(follower, path3())
        );
    }

    @Override
    public void initInternal() {
        Scheduler.reset();
        follower = Constants.create(hardwareMap);
        follower.setPose(start);
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
            telemetryPro.addData("x", follower.pose().x());
            telemetryPro.addData("y", follower.pose().y());
            telemetryPro.addData("heading", follower.pose().heading());

            if (follower.currentPath() != null) {
                telemetryPro.addData("Current path distance remaining", follower.distanceToEndpoint());
                telemetryPro.addData("Path number", follower.pathIndex());
            }
        }
        telemetryPro.update();
    }

    public Path path1() {
        return Paths.line(start, point1).constant(point1);
    }

    public Path path2() {
        return Paths.line(point1, point2).constant(point2);
    }

    public Path path3() {
        return Paths.line(point2, point3).reverseTangent();
    }
}
