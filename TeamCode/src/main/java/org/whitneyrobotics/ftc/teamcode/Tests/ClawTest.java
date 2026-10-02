package org.whitneyrobotics.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;

import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "clawtest")
public class ClawTest extends OpModeEx {

    public Servo claw;
    @Override
    public void initInternal() {
        claw = hardwareMap.get(Servo.class, "claw");
        claw.setPosition(0.2);
    }

    public double servopos(double degs){
        return degs / 300.0;
    }

    boolean clawopen = false;
    boolean redetprev = false;

    @Override
    protected void loopInternal() {
        boolean redetcurr = gamepad1.A.value();
        if(redetcurr && !redetprev){
            clawopen = !clawopen;
        }
        redetprev = redetcurr;
        if(clawopen){
            claw.setPosition(servopos(40.0));
        }else{
            claw.setPosition(servopos(5.0));
        }
        telemetry.addLine("Servo Pos: ", servopos(claw.getPosition());
        telemetry.update();
    }
}
