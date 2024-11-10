package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;;

@TeleOp(name="Drive", group="a")
public class Drive extends LinearOpMode {

    //---------------Declare Hardware Variables-----------------------//
    private DcMotor leftFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotor lift = null;
    private DcMotor pivot = null;
    private Servo rotate = null;
    private Servo grab = null;

    //---------------Declare Variables-----------------------//
    private int bspeed;
    private double rfspeed;
    private double lfspeed;
    private double rbspeed;
    private double lbspeed;

    //---------------Declare Servo Variables-----------------//
    private final double closedGrab = 0.5;
    private final double openGrab = 0.2;
    private final double rotateGround = 0.5;
    private final double rotateScore = 0.2;

    //---------------Run OpMode-----------------------------//
    @Override
    public void runOpMode() {
        //---------------Init Hardware-----------------------//
        leftFrontDrive = hardwareMap.get(DcMotor.class, "lf");
        leftBackDrive = hardwareMap.get(DcMotor.class, "lb");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rf");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rb");
        lift = hardwareMap.get(DcMotor.class, "lift");
        pivot = hardwareMap.get(DcMotor.class, "pivot");
        rotate = hardwareMap.get(Servo.class, "rotate");
        grab = hardwareMap.get(Servo.class, "grab");

        //---------------Setup Motors-----------------------//
        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);
        lift.setDirection(DcMotor.Direction.REVERSE);
        pivot.setDirection(DcMotor.Direction.FORWARD);

        //---------------Setup Servos-----------------------//
        rotate.setPosition(rotateGround);
        grab.setPosition(closedGrab);
        bspeed = 2;

        //---------------Wait until Play-----------------------//
        telemetry.addData("Status", "Initialized");
        telemetry.update();
        waitForStart();

        //---------------Loop while Active-----------------------//
        while (opModeIsActive()) {

            lift.setPower(gamepad2.left_stick_y);
            pivot.setPower(gamepad2.right_stick_y);

            if (gamepad2.a) {
                rotate.setPosition(rotateScore);
            }

            if (gamepad2.b) {
                rotate.setPosition(rotateGround);
            }

            if (gamepad2.x) {
                grab.setPosition(openGrab);
            }

            if (gamepad2.y) {
                grab.setPosition(closedGrab);
            }

            //--------Joysticks Controls & Wheel Power-----------//

            double max;
            double axial = -gamepad1.left_stick_y;  //Pushing stick forward gives negative value
            double lateral = gamepad1.left_stick_x;
            double yaw = gamepad1.right_stick_x;
            double leftFrontPower = axial + lateral + yaw;
            double rightFrontPower = axial - lateral - yaw;
            double leftBackPower = axial - lateral + yaw;
            double rightBackPower = axial + lateral - yaw;
            // Normalize the values so no wheel power exceeds 100%
            max = Math.max(Math.abs(leftFrontPower), Math.abs(rightFrontPower));
            max = Math.max(max, Math.abs(leftBackPower));
            max = Math.max(max, Math.abs(rightBackPower));

            if (max > 1.0) {
                leftFrontPower /= max;
                rightFrontPower /= max;
                leftBackPower /= max;
                rightBackPower /= max;
            }

            //-----------Speed Control------------//

            if (gamepad1.left_bumper) {
                bspeed = 1;
            }

            if (gamepad1.right_bumper) {
                bspeed = 2;
            }

            if (bspeed == 1) {
                lfspeed = leftFrontPower / 2;
                rfspeed = rightFrontPower / 2;
                lbspeed = leftBackPower / 2;
                rbspeed = rightBackPower / 2;
            }

            if (bspeed == 2) {
                lfspeed = leftFrontPower;
                rfspeed = rightFrontPower;
                lbspeed = leftBackPower;
                rbspeed = rightBackPower;
            }

            leftFrontDrive.setPower(lfspeed);
            rightFrontDrive.setPower(rfspeed);
            leftBackDrive.setPower(lbspeed);
            rightBackDrive.setPower(rbspeed);

            //-------------Display Timer & Wheel Power---------------//
            telemetry.addData("Front left/Right", "%4.2f, %4.2f", leftFrontPower, rightFrontPower);
            telemetry.addData("Back  left/Right", "%4.2f, %4.2f", leftBackPower, rightBackPower);
            telemetry.update();
        }
    }
}