package org.whitneyrobotics.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.Subsystems.Intake;
import org.whitneyrobotics.ftc.teamcode.Subsystems.Outtake;


@TeleOp(name = "TEST_INANDOUT_V1")
public class Robot extends OpModeEx {
    public Intake intake;
    public Outtake outtake;

    @Override
    public void initInternal() {
        //intake = new Intake(hardwareMap);
        outtake = new Outtake(hardwareMap);

    }

    public double map(double value, double inMin, double inMax, double outMin, double outMax) {
        return outMin + ((value - inMin) * (outMax - outMin)) / (inMax - inMin);
    }

    @Override
    protected void loopInternal() {
        if(gamepad1.getEncapsulatedGamepad().b){
            double triggerValue = gamepad1.getEncapsulatedGamepad().right_trigger;
            outtake.run(map(triggerValue,0.0,1.0,0.0,-5000.0));
        }else{
            outtake.stop();
        }


        if(gamepad1.getEncapsulatedGamepad().left_bumper){
            intake.run(0.9);
        }else{
            intake.stop();
        }

        //make intake later
        telemetryPro.addLine("Outtake RPM: " + outtake.getVelocity());
        telemetryPro.update();
    }

}
