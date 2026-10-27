package org.whitneyrobotics.ftc.teamcode.Subsystems;
import com.pedropathing.follower.Follower;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import org.whitneyrobotics.ftc.teamcode.pedro.Constants;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.whitneyrobotics.ftc.teamcode.Extensions.GamepadEx.GamepadEx;

public class Drivetrain {
        private Follower follower;
        public Drivetrain(HardwareMap hardwareMap){
            follower = Constants.create(hardwareMap);
        }

        public void run(double ly, double lx, double rx){
            follower.manual(
                -ly,
                lx,
                rx
            );
            follower.update();
        }
}

