

/* Copyright (c) 2023 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="Auto")

public class Auto extends LinearOpMode{

    private DcMotor leftFrontDrive = null;
    private DcMotor rightFrontDrive = null;
    private DcMotor leftBackDrive = null;
    private DcMotor rightBackDrive = null;
    private DcMotor lift = null;
    private DcMotor pivot = null;
    private Servo rotate = null;
    private Servo grab = null;
    private final double closedGrab = 0.5;
    private final double openGrab = 0.2;
    private final int liftHighBucket = 2000;
    private final int liftZero = 10;
    private final int pivotZero = 10;
    private final int pivotHighBucket = 1000;
    private final double rotateGround = 0.5;
    private final double rotateScore = 0.2;

    @Override public void runOpMode() {
        leftFrontDrive = hardwareMap.get(DcMotor.class, "lf");
        rightFrontDrive = hardwareMap.get(DcMotor.class, "rf");
        leftBackDrive = hardwareMap.get(DcMotor.class, "lb");
        rightBackDrive = hardwareMap.get(DcMotor.class, "rb");
        lift = hardwareMap.get(DcMotor.class, "lift");
        pivot = hardwareMap.get(DcMotor.class, "pivot");
        rotate = hardwareMap.get(Servo.class, "rotate");
        grab = hardwareMap.get(Servo.class, "grab");

        leftFrontDrive.setDirection(DcMotor.Direction.REVERSE);
        leftBackDrive.setDirection(DcMotor.Direction.REVERSE);
        rightFrontDrive.setDirection(DcMotor.Direction.FORWARD);
        rightBackDrive.setDirection(DcMotor.Direction.FORWARD);

        //TODO: Figure out the direction of your lift and pivot
        lift.setDirection(DcMotor.Direction.REVERSE);
        pivot.setDirection(DcMotor.Direction.FORWARD);

        //TODO: Make positions for your rotate and grab
        rotate.setPosition(rotateGround);
        grab.setPosition(closedGrab);

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pivot.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        pivot.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        telemetry.addData(">", "Touch Play to start OpMode");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            /* TODO: Put your code here - This is the auto section.
            * This will push a sample into the net zone and then park.
            * You need to add a section for the pivot to go back */

            left(1000);
            sleep(1000);
            right(1000);
            sleep(1000);
            forward(2000);
            sleep(1000);
            turnLeft(1000);
            sleep(1000);
            back(1000);
            sleep(100000);

            telemetry.addData("liftPos", lift.getCurrentPosition());
            telemetry.addData("pivotPos", pivot.getCurrentPosition());
            telemetry.addData("rotatePos", rotate.getPosition());
            telemetry.addData("grabPos", grab.getPosition());
            telemetry.update();

        }
    }


    public void lift(int pos) {
        lift.setTargetPosition(pos);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lift.setPower(1);

        while (lift.isBusy()) {
            sleep(25);
        }
        lift.setPower(0);
    }

    public void pivot(int pos) {
        pivot.setTargetPosition(pos);
        pivot.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pivot.setPower(1);

        while (pivot.isBusy()){
            sleep(25);
        }

        pivot.setPower(0);
    }

    public void turnLeft(int ticks) {
        move(-ticks,-ticks,ticks,ticks);
    }

    public void turnRight(int ticks) {
        move(ticks,ticks,-ticks,-ticks);
    }

    public void forward(int ticks) {
        move(ticks,ticks,ticks,ticks);
    }

    public void back(int ticks) {
        move(-ticks,-ticks,-ticks,-ticks);
    }

    public void left(int ticks) {
        move(-ticks,ticks,ticks,-ticks);
    }

    public void right(int ticks) {
        move(ticks,-ticks,-ticks,ticks);
    }

    public void move(int lf, int lb, int rf, int rb) {
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightFrontDrive.setTargetPosition(rf);
        rightBackDrive.setTargetPosition(rb);
        leftFrontDrive.setTargetPosition(lf);
        leftBackDrive.setTargetPosition(lb);

        rightFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftFrontDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        rightBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        leftBackDrive.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        rightBackDrive.setPower(0.75);
        rightFrontDrive.setPower(0.75);
        leftFrontDrive.setPower(0.75);
        leftBackDrive.setPower(0.75);

        while (leftFrontDrive.isBusy() && leftBackDrive.isBusy() && rightFrontDrive.isBusy() && rightBackDrive.isBusy()) {
            sleep(25);

        }
        rightFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftFrontDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        rightBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        leftBackDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        rightBackDrive.setPower(0);
        leftFrontDrive.setPower(0);
        rightFrontDrive.setPower(0);
        leftBackDrive.setPower(0);
    }

}