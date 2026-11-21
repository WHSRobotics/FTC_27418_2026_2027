package org.whitneyrobotics.ftc.teamcode.OpMode.Autonomous;

import static com.pedropathing.api.Paths.line;
import static com.pedropathing.ivy.Scheduler.schedule;
import static com.pedropathing.ivy.commands.Commands.waitMs;
import static com.pedropathing.ivy.groups.Groups.sequential;
import static com.pedropathing.ivy.pedro.PedroCommands.follow;

import static org.whitneyrobotics.ftc.teamcode.OpMode.TeleOp.BiobuzzTeleOp.autonEnd;
import static org.whitneyrobotics.ftc.teamcode.OpMode.TeleOp.BiobuzzTeleOp.redAlliance;

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

@Autonomous(name = "red down")
public class redDown extends OpModeEx {

    private Follower follower;
    private final PoseFactory poseFactory = PoseFactory.degrees();

    private final Pose start = poseFactory.of(13.9644, 136.8696, 90);
    private final Pose path1 = poseFactory.of(14.1618, 117.9003, 90);
    private final Pose point2Start = poseFactory.of(14.1618, 117.9003, 0);
    private final Pose point2 = poseFactory.of(57.8042, 117.9679, 270);
    private final Pose point3Start = poseFactory.of(57.8042, 117.9679, 270);
    private final Pose point3 = poseFactory.of(14.2186, 117.2672, 270);

    public Path path1() {
        return Paths.line(start, path1).linear(start, path1);
    }

    public Path path2() {
        return Paths.line(point2Start, point2).linear(point2Start, point2);
    }

    public Path path3() {
        return Paths.line(point3Start, point3).linear(point3Start, point3);
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
        follower.setPose(start);
        follower.update();
        redAlliance = true;
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
        autonEnd = follower.pose();
    }
}
