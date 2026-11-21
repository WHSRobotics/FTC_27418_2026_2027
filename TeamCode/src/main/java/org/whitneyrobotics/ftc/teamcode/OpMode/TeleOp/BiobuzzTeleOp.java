package org.whitneyrobotics.ftc.teamcode.OpMode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.Subsystems.Outtake;
import org.whitneyrobotics.ftc.teamcode.Subsystems.Robot;
import com.pedropathing.api.Paths;
import com.pedropathing.api.PoseFactory;
import com.pedropathing.follower.Follower;
import com.pedropathing.ivy.Command;
import com.pedropathing.ivy.Scheduler;
import com.pedropathing.math.Pose;
import com.pedropathing.paths.Path;
@TeleOp(name="BiobuzzTeleOp")
public class BiobuzzTeleOp extends OpModeEx {
    private Robot robot;
    private boolean robotCentric = true;

    public static boolean redAlliance;
    public static Pose autonEnd;

    double[] velocities = {1600, 2000, 2400, 2800};
    double velo1 = 0.0;

    // Edge detection state variables
    boolean transferOn = false;

    @Override
    public void initInternal() {
        robot = new Robot(hardwareMap);
        if(redAlliance){
            robot.drive.follower.setPose(new Pose(autonEnd.x(), autonEnd.y(),autonEnd.heading()+(Math.PI)/2));
        } else{
            robot.drive.follower.setPose(new Pose(autonEnd.x(), autonEnd.y(),autonEnd.heading()-(Math.PI)/2));
        }
    }

    @Override
    protected void loopInternal() {
        telemetryPro.addData("is red alliance?", redAlliance);
        // Toggle Robot/Field Centric Drive
        gamepad1.BUMPER_RIGHT.onPress(() -> {
            robotCentric = !robotCentric;
        });

        if (robotCentric) {
            robot.drive.robotCentric(gamepad1.LEFT_STICK_Y.value(), gamepad1.LEFT_STICK_X.value(), gamepad1.RIGHT_STICK_X.value());
        } else {
            robot.drive.fieldCentric(gamepad1.LEFT_STICK_Y.value(), gamepad1.LEFT_STICK_X.value(), gamepad1.RIGHT_STICK_X.value());
        }

        // Intake Controls (Using custom OpModeEx onPress)
        gamepad2.BUMPER_RIGHT.onPress(() -> {
            if (robot.intake.getPower() == 0) {
                robot.intake.run(1);
            } else {
                robot.intake.stop();
            }
        });

        gamepad2.BUMPER_LEFT.onPress(() -> {
            if (robot.intake.getPower() == 0) {
                robot.intake.run(-1);
            } else {
                robot.intake.stop();
            }
        });

        //toggle
        if(gamepad2.RIGHT_TRIGGER.value()>0){
            robot.outtake.run(velo1);
            telemetryPro.addLine("shooting!!!!");
            telemetryPro.update();
        } else{
            robot.outtake.stop();
        }

        gamepad2.Y.onPress(() -> {
            velo1 = velocities[1];


        });
        gamepad2.B.onPress(() -> {
            velo1 = velocities[2];
        });
        gamepad2.A.onPress(() -> {
            velo1 = velocities[3];
        });
        //gamepad 1 is selecting path based on autons end pose
        gamepad1.Y.onPress(()->{
            // is path for blue up
            Pose currentPose = new Pose(126.0458, 46.104, 270);
            //change to 0 for heading
            // change heading so it matches drivers view

        });
        gamepad1.X.onPress(()->{
            // is path for blue down
            Pose currentPose = new Pose(128.7066, 23.3719, 90);
            //change heading to be 180


        });
        gamepad1.A.onPress(()->{
            // is path for red up
            Pose currentPose = new Pose(13.8416, 92.9116, 90);
            // change heading to be 0


        });
        gamepad1.B.onPress(()->{
            //is path for red down
            Pose currentPose = new Pose(14.2186, 117.2672, 270);
          // change heading to be 0

        });

        telemetryPro.addData("velocity: ", robot.outtake.getVelocity());
        telemetryPro.addData("target velocity: ", velo1);
        telemetryPro.update();

        telemetryPro.addData("robot centric? ", robotCentric);
        telemetryPro.update();


        gamepad2.X.onPress(()->{
            transferOn =! transferOn;
        });
        if (transferOn){
            robot.transfer.run();
        }
        else{
            robot.transfer.stop();
        }


    }
}