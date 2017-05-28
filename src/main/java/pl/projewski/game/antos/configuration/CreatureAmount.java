package pl.projewski.game.antos.configuration;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatureAmount {
	private String name;
	private String creature;
	private int minAmount;
	private int randAmount;
}
