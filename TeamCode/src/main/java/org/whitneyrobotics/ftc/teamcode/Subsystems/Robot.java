package org.whitneyrobotics.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;
import org.whitneyrobotics.ftc.teamcode.Subsystems.Intake;
import org.whitneyrobotics.ftc.teamcode.Subsystems.Outtake;


public class Robot {
    public Intake intake;
//    public Outtake outtake;
    public Drivetrain drive;

    public Robot(HardwareMap hardwareMap){
        intake = new Intake(hardwareMap);
        drive = new Drivetrain(hardwareMap);
    }

}
