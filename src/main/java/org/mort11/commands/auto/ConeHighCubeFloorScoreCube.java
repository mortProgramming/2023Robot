package org.mort11.commands.auto;

import org.mort11.commands.drivetrain.Balance;
import org.mort11.commands.drivetrain.MoveToPos;
import org.mort11.commands.drivetrain.TimedDrive;
import org.mort11.commands.endeffector.ScoreCone;
import org.mort11.commands.endeffector.armelevator.SetArmAndElevator;
import org.mort11.commands.endeffector.clawwrist.SetClawPiston;
import org.mort11.commands.endeffector.floortake.FloorIntake;
import org.mort11.commands.endeffector.floortake.Spit;
import org.mort11.commands.endeffector.floortake.Stow;
import org.mort11.subsystems.Drivetrain;

import edu.wpi.first.math.util.Units;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.ParallelDeadlineGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;

public class ConeHighCubeFloorScoreCube extends SequentialCommandGroup {
	private Drivetrain drivetrain;

	public ConeHighCubeFloorScoreCube() {
		drivetrain = Drivetrain.getInstance();
		addRequirements(drivetrain);

		addCommands(
				new ParallelDeadlineGroup(
						new SequentialCommandGroup(new SetClawPiston(false), new WaitCommand(0.2), new ScoreCone(),
								new ParallelCommandGroup(SetArmAndElevator.rest(),
										new MoveToPos(Units.inchesToMeters(180), 0, 0, 1.75, 1).withTimeout(7))),
						new Stow()),
				new ParallelDeadlineGroup(new SequentialCommandGroup(
						new MoveToPos(Units.inchesToMeters(40), 0, -160, 3, 2).withTimeout(2),
						new MoveToPos(Units.inchesToMeters(10), 0, 0).withTimeout(1.5)
				// new WaitCommand(0.35)
				), new FloorIntake()),
				new ParallelDeadlineGroup(new SequentialCommandGroup(new MoveToPos(-4, 0, 160, 3, 2)
				// new MoveToPos(-0.6, 0, 90).withTimeout(1.5)
				), new Stow()), new ParallelDeadlineGroup(new TimedDrive(3, -0.5, 0, 0), new Spit()));

	}
}
