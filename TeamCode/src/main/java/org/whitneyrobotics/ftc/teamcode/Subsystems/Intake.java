package org.whitneyrobotics.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Intake {
    public DcMotorEx intakeMotor;

    public Intake(HardwareMap hardwareMap){
        intakeMotor = hardwareMap.get(DcMotorEx.class, "intakeMotor");
    }

    public void run(double scalar) {
        intakeMotor.setPower(1 * scalar);
    }

    public void stop(){
        intakeMotor.setPower(0);
    }

    public double getPower(){
        return intakeMotor.getPower();
    }
}