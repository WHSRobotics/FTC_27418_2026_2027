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

    //Convert from degrees to normal
    public double servopos(double degs){
        return degs / 300.0;
    }

    //if claw is open
    boolean clawopen = false;
    //rising edge variable
    boolean redetprev = false;

    @Override
    protected void loopInternal() {
        //rising edge detection
        boolean redetcurr = gamepad1.A.value();
        if(redetcurr && !redetprev){
            clawopen = !clawopen;
        }
        redetprev = redetcurr;

        //open/close claw
        if(clawopen){
            claw.setPosition(servopos(40.0)); //convert degrees to normal (0.0 - 300.0 -> 0.0 - 1.0)
        }else{
            claw.setPosition(servopos(5.0));
        }
        telemetryPro.addData("Servo Pos: ", claw.getPosition()); //returns in degrees
        telemetryPro.update();
    }
}