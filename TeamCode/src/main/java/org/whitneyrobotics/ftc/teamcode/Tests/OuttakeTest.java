package org.whitneyrobotics.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.Subsystems.Outtake;

@TeleOp(name="ouutake test")
public class OuttakeTest extends OpModeEx {

    public Outtake outtake;
    private boolean isOn = false;
    private double targetrpm = 2800;

    @Override
    public void initInternal(){
        outtake = new Outtake(hardwareMap);
    }

    @Override
    public void loopInternal(){
        gamepad1.BUMPER_RIGHT.onPress(() -> {
            isOn = !isOn;
        });

        if(isOn){
            outtake.run(targetrpm);
        } else{
            outtake.stop();
        }

        gamepad1.A.onPress(() -> {targetrpm=2800;});
        gamepad1.B.onPress(() -> {targetrpm=2400;});
        gamepad1.X.onPress(() -> {targetrpm=2000;});
        gamepad1.Y.onPress(() -> {targetrpm=1600;});

        telemetryPro.addData("target velocity: ", targetrpm);
        telemetryPro.addData("outtake velocity: ", outtake.getVelocity());
    }
}
