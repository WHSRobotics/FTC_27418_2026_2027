package org.whitneyrobotics.ftc.teamcode.OpMode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.Subsystems.Robot;

@TeleOp(name="BiobuzzTeleOp")
public class BiobuzzTeleOp extends OpModeEx {

    private Robot robot;
    private boolean robotCentric = true;

    @Override
    public void initInternal() {
        robot = new Robot(hardwareMap);
    }

    @Override
    protected void loopInternal() {
        gamepad1.BUMPER_RIGHT.onPress(() -> {
            robotCentric = !robotCentric;
        });
        if(robotCentric){
            robot.drive.robotCentric(gamepad1.LEFT_STICK_Y.value(),gamepad1.LEFT_STICK_X.value(),gamepad1.RIGHT_STICK_X.value());
        } else{
            robot.drive.fieldCentric(gamepad1.LEFT_STICK_Y.value(), gamepad1.LEFT_STICK_X.value(), gamepad1.RIGHT_STICK_X.value());
        }

        gamepad2.BUMPER_RIGHT.onPress(() -> {
            if(robot.intake.getPower()==0){
                robot.intake.run(1);
            } else{
                robot.intake.stop();
            }
        });

        gamepad2.BUMPER_LEFT.onPress(() -> {
            if(robot.intake.getPower()==0){
                robot.intake.run(-1);
            } else{
                robot.intake.stop();
            }
        });


        telemetryPro.addData("robot centric? ", robotCentric);
    }
}