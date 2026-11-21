package org.whitneyrobotics.ftc.teamcode.OpMode.Autonomous;

import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import static org.whitneyrobotics.ftc.teamcode.OpMode.TeleOp.BiobuzzTeleOp.autonEnd;
import static org.whitneyrobotics.ftc.teamcode.OpMode.TeleOp.BiobuzzTeleOp.redAlliance;
import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.pedropathing.follower.Follower;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;
import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.pedropathing.Constants;

@Autonomous(name = "blueUp", group = "Autonomous")
public class blueUp extends OpModeEx {
    private Follower follower;
    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(81.6555, 132.9226, 270);
    private final Pose path1 = poseFactory.of(82.0502, 119.0844, 270);
    private final Pose point2Start = poseFactory.of(82.0502, 119.0844, -90);
    private final Pose point2 = poseFactory.of(123.1539, 89.4703, 270);
    private final Pose point3Start = poseFactory.of(123.1539, 89.4703, 270);
    private final Pose point3 = poseFactory.of(126.0458, 46.104, 270);

    public Path path1() {
        return Paths.line(start, path1).linear(start, path1);
    }

    public Path path2() {
        return Paths.line(point2Start, point2).linear(point2Start, point2);
    }

    public Path path3() {
        return Paths.line(point3Start, point3).linear(point3Start, point3);
    }

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
        redAlliance = false;
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
        autonEnd = follower.pose();
    }
}
    