package org.whitneyrobotics.ftc.teamcode.Subsystems;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.whitneyrobotics.ftc.teamcode.Extensions.OpModeEx.OpModeEx;

import java.util.List;

@TeleOp(name = "--- LIMELIGHT DISTANCE XY HYPO TEST V1 ---", group = "Sensor")
public class limelight extends OpModeEx {
    private Limelight3A limelight;

    @Override
    public void initInternal() {
        limelight = hardwareMap.get(Limelight3A.class, "limelight");
        limelight.pipelineSwitch(0); // Switch to desired AprilTag pipeline
        limelight.start(); // Starts background polling thread

        telemetryPro.addLine("Successful Start");
        telemetryPro.update();
    }

    @Override
    protected void loopInternal() {
        LLResult result = limelight.getLatestResult();
        if (result != null && result.isValid()) {
            List<LLResultTypes.FiducialResult> fiducials = result.getFiducialResults();

            if (!fiducials.isEmpty()) {
                // Gets the first detected tag result
                LLResultTypes.FiducialResult firstTag = fiducials.get(0);

                // Get robot pose relative to the target AprilTag 3D coordinate frame (in meters)
                Pose3D targetPose = firstTag.getRobotPoseTargetSpace();

                if (targetPose != null && targetPose.getPosition() != null) {
                    double xMeters = targetPose.getPosition().x;
                    double yMeters = targetPose.getPosition().y;
                    double zMeters = targetPose.getPosition().z;

                    // Standard conversions from meters to centimeters
                    double xdistcm = xMeters * 100.0; // Lateral distance (+ = right of tag face)
                    double ydistcm = yMeters * 100.0; // Vertical distance (+ = above tag center)
                    double zdistcm = zMeters * 100.0; // Depth distance (+ = outward along target normal)

                    // 3D Euclidean distance (straight-line) to AprilTag in cm
                    double hypotdistcm = Math.sqrt(
                            xMeters * xMeters +
                                    yMeters * yMeters +
                                    zMeters * zMeters
                    ) * 100.0;

                    double txAngle = result.getTx(); // Horizontal angle offset relative to crosshair

                    telemetryPro.addData("Eucl. Dist. in CM: ", hypotdistcm);
                    telemetryPro.addData("X Dist in CM: ", xdistcm);
                    telemetryPro.addData("Y Dist in CM: ", ydistcm);
                    telemetryPro.addData("Z Dist in CM: ", zdistcm);
                    telemetryPro.addData("tx crosshair offset in degrees: ", txAngle);
                } else {
                    telemetryPro.addLine("Target pose position null");
                }
            } else {
                telemetryPro.addLine("Empty fiducials");
            }
        } else {
            telemetryPro.addLine("Limelight result not valid");
        }

        telemetryPro.update();
    }
}