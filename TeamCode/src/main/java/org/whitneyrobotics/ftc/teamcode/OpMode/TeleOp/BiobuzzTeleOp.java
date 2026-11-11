package org.whitneyrobotics.ftc.teamcode.OpMode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.Subsystems.Outtake;
import org.whitneyrobotics.ftc.teamcode.Subsystems.Robot;

@TeleOp(name="BiobuzzTeleOp")
public class BiobuzzTeleOp extends OpModeEx {

    private Robot robot;
    private boolean robotCentric = true;

    double[] velocities = {1600, 2000, 2400, 2800};
    double velo1 = 0.0;

    // Edge detection state variables
    boolean transferOn = false;

    @Override
    public void initInternal() {
        robot = new Robot(hardwareMap);
    }

    @Override
    protected void loopInternal() {
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
        } else{
            robot.outtake.stop();
        }

        gamepad1.Y.onPress(() -> {
            velo1 = velocities[0];
        });
        gamepad1.B.onPress(() -> {
            velo1 = velocities[1];
        });
        gamepad1.A.onPress(() -> {
            velo1 = velocities[2];
        });
        gamepad1.X.onPress(() -> {
            velo1 = velocities[3];
        });
        telemetryPro.addData("velocity: ", robot.outtake.getVelocity());
        telemetryPro.addData("target velocity: ", velo1);
        telemetryPro.update();

        telemetryPro.addData("robot centric? ", robotCentric);
        telemetryPro.update();


        gamepad1.X.onPress(()->{
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