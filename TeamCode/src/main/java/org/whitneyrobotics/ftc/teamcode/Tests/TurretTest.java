package org.whitneyrobotics.ftc.teamcode.Tests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;

@TeleOp(name = "TurretTest")
public class TurretTest extends OpModeEx {
    private DcMotorEx turret312;
    private static final double TICKS_PER_REV = 537.7;
    private static final double MAX_RPM = 312.0;

    public void initInternal() {
        turret312 = hardwareMap.get(DcMotorEx.class, "turret");
        turret312.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        turret312.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    @Override
    protected void loopInternal() {
        double targetRPM = -gamepad1.LEFT_STICK_Y.value() * MAX_RPM;
        double targetTicksPerSecond = (targetRPM * TICKS_PER_REV) / 60.0;
        turret312.setVelocity(targetTicksPerSecond);

        double actualTicksPerSecond = turret312.getVelocity();
        double actualRPM = (actualTicksPerSecond * 60.0) / TICKS_PER_REV;
//        telemetryPro.addData("position in ticks: ",);
    }
}