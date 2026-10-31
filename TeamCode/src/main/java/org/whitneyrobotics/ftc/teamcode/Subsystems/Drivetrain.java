package org.whitneyrobotics.ftc.teamcode.Subsystems;
import com.pedropathing.follower.Follower;

import org.whitneyrobotics.ftc.teamcode.pedropathing.Constants;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.pedropathing.drivetrain.DrivePowers;
import com.pedropathing.follower.ManualDrive;

public class Drivetrain {
        public Follower follower;
        public DrivePowers powers;

        public Drivetrain(HardwareMap hardwareMap){
            follower = Constants.create(hardwareMap);
        }

        public void robotCentric(double ly, double lx, double rx){
            follower.manual(
                ly,
                -lx,
                -rx
            );
            follower.update();
        }
        public void fieldCentric(double ly, double lx, double rx){
            powers = ManualDrive.fieldCentric(
                    ly,
                    -lx,
                    -rx,
                   follower.pose().heading()
            );
            follower.manual(powers);
            follower.update();
        }
}

