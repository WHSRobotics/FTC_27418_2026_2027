package org.whitneyrobotics.ftc.teamcode.OpMode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.Subsystems.Outtake;
import org.whitneyrobotics.ftc.teamcode.Subsystems.Robot;

@TeleOp(name="BiobuzzTeleOp")
public class BiobuzzTeleOp extends OpModeEx {

    private Robot robot;
    private boolean robotCentric = true;
    private Outtake outtake;

    double[] velocities = {1000, 2000, 3000, 4000};
    double velo1 = 0.0;

    // Edge detection state variables
    boolean outtakeActive = false;
    boolean prevOuttake = false;

    @Override
    public void initInternal() {
        robot = new Robot(hardwareMap);
        outtake = new Outtake(hardwareMap);
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

        // --- Manual Rising Edge Detection for Outtake Toggle ---
        // 1. Read current raw button state (assuming standard Qualcomm gamepad or OpModeEx button getter)
        boolean currOuttake = gamepad1.BUMPER_LEFT.value();

        // 2. Rising Edge Trigger: True ONLY on the exact frame the button goes from false to true
        if (currOuttake && !prevOuttake) {
            outtakeActive = !outtakeActive;
        }

        // 3. Update previous state for the next loop iteration
        prevOuttake = currOuttake;

        // --- Execute code based on outtakeActive toggle state ---
        if (outtakeActive) {
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
            outtake.run(velo1);
            telemetryPro.addLine("RPM: " + velo1);
            telemetryPro.update();
        }else{
            outtake.stop();
        }

        telemetryPro.addData("robot centric? ", robotCentric);
        telemetryPro.addData("outtake active? ", outtakeActive);
        telemetryPro.update();
    }
}