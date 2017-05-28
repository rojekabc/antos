package pl.projewski.game.antos.configuration;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MazeConfiguration {
	/** The name of the maze. */
	private String name;
	/** The type of the maze. */
	private String type;
	/**
	 * The list of names of a creature amount system to generate on the maze.
	 */
	private List<String> mobs;
}
