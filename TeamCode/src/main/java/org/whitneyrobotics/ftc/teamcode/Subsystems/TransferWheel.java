package org.whitneyrobotics.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class TransferWheel {

    public DcMotorEx transfer;

    public TransferWheel(HardwareMap hardwaremap){
        transfer = hardwaremap.get(DcMotorEx.class, "transfer");
    }

    public void run(){
        transfer.setPower(-1);
    }

    public void stop(){
        transfer.setPower(0);
    }
}
