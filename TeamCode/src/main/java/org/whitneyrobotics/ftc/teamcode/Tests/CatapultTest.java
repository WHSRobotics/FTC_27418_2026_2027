package org.whitneyrobotics.ftc.teamcode.Tests;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.RUN_TO_POSITION;
import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
@TeleOp (name = "catapulttest")
public class CatapultTest extends OpMode {
    DcMotor motor;
    // DcMotor motor2;
    public enum Ticks{
        ONE(383.6),
        HALF(191.8),
        NEG(-191.8);
        double ticks;
        Ticks(double v){
            ticks = v;
        }
    }
    double target = Ticks.HALF.ticks;
    double target2 = Ticks.NEG.ticks;
    boolean lastAState = false;
    @Override
    public void init() {

        motor = hardwareMap.get(DcMotor.class, "motor");
        //motor2 = hardwareMap.get(DcMotor.class, "motor2");
        motor.setMode(STOP_AND_RESET_ENCODER);
        motor.setTargetPosition(0);
        motor.setMode(RUN_TO_POSITION);
        motor.setPower(0.0);
    }
    @Override
    public void loop() {
        if (gamepad1.a && !lastAState){
            motor.setTargetPosition((int)target);
            motor.setMode(RUN_TO_POSITION);
            motor.setPower(1.0);
            motor.setTargetPosition(0);
            motor.setMode(RUN_TO_POSITION);
            motor.setPower(-1.0);

            //target += 383.6;
        }

        lastAState = gamepad1.a;
    }
}