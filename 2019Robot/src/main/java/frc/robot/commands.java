/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.command.Command;
/**
 * An example command.  You can replace me with your own command.
 */
public class level0 extends Command {
    public level0() {
    }

// Called just before this Command runs the first time
@Override
protected void initialize() {
}

// Called repeatedly when this Command is scheduled to run
@Override
protected void execute() {
    if(isCarring()){ //If it's carrying
        
        
        if(encoder != level0+x){ //if its not in the correct place
            //move there
        }
        else { //if it's corect
            //brake
        }


    }
    
    else {
        if(encoder != level0){ //if its not in the correct place
            //move there
        }
        else { //if it's corect
            //brake
        }
    }

}

// Make this return true when this Command no longer needs to run execute()
@Override
protected boolean isFinished() {
    return false;
}
}