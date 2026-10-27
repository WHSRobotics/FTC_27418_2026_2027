package org.whitneyrobotics.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Outtake {
    public DcMotorEx outtakeMotor;

    public Outtake(HardwareMap hardwareMap){
        outtakeMotor = hardwareMap.get(DcMotorEx.class, "out");
    }

    double tps6000 = 28.0;

    public void run(double targetRPM) {
       outtakeMotor.setVelocity((targetRPM / 60.0) * tps6000);
    }

    public void stop(){
        outtakeMotor.setVelocity(0.0);
    }

    public double getVelocity(){
        return outtakeMotor.getVelocity();
    }
}