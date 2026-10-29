package org.whitneyrobotics.ftc.teamcode.Subsystems;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.Subsystems.Intake;
import org.whitneyrobotics.ftc.teamcode.Subsystems.Outtake;


/*
This is a push bot teleop.
*/

@RequiresApi(api = Build.VERSION_CODES.N)
@TeleOp(name = "UNTESTED - BACKUP_ROBOT_TELEOP")
public class Robot extends OpModeEx {
    private DcMotor leftFront, rightFront, leftBack, rightBack;
    //public Intake intake;
    //public Outtake outtake;

    @Override
    public void initInternal() {
        //intake = new Intake(hardwareMap);
        //outtake = new Outtake(hardwareMap);

        leftFront  = hardwareMap.get(DcMotor.class, "LF");
        rightFront = hardwareMap.get(DcMotor.class, "RF");
        leftBack   = hardwareMap.get(DcMotor.class, "LB");
        rightBack  = hardwareMap.get(DcMotor.class, "RB");

        //Set motor directions
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        rightFront.setDirection(DcMotor.Direction.FORWARD);
        rightBack.setDirection(DcMotor.Direction.FORWARD);

        leftFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightFront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        leftBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        rightBack.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public double map(double value, double inMin, double inMax, double outMin, double outMax) {
        return outMin + ((value - inMin) * (outMax - outMin)) / (inMax - inMin);
    }

    @Override
    protected void loopInternal() {

        double axial   = -gamepad1.getEncapsulatedGamepad().left_stick_y;  // Forward/Backward
        double lateral =  gamepad1.getEncapsulatedGamepad().left_stick_x;  // Strafing Left/Right
        double yaw     =  gamepad1.getEncapsulatedGamepad().right_stick_x; // Rotation

        double leftFrontPower  = axial + lateral + yaw;
        double rightFrontPower = axial - lateral - yaw;
        double leftBackPower   = axial - lateral + yaw;
        double rightBackPower  = axial + lateral - yaw;

        // Normalize powers so none exceed 1.0
        double max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
        max = Math.max(max, Math.abs(leftBackPower));
        max = Math.max(max, Math.abs(rightBackPower));

        if (max > 1.0) {
            leftFrontPower  /= max;
            rightFrontPower /= max;
            leftBackPower   /= max;
            rightBackPower  /= max;
        }

        // Apply power to drive motor - 0.8 max power for precision
        leftFront.setPower(leftFrontPower * 0.8);
        rightFront.setPower(rightFrontPower * 0.8);
        leftBack.setPower(leftBackPower * 0.8);
        rightBack.setPower(rightBackPower * 0.8);

        /*
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
         */

        //make intake later
        //telemetryPro.addLine("Outtake RPM: " + outtake.getVelocity());
        //telemetryPro.update();
    }

}

