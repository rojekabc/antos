package pl.projewski.game.antos.gameengine.sialg;

import lombok.Getter;

public enum ECreatureSI {
	RANDOM(RandomCreatureSI.class), GUARDIAN(GuardianCreatureSI.class);

	@Getter
	Class<? extends ICreatureSIAlgorithm> implementationClass;

	public ICreatureSIAlgorithm createSIAlgorithm() throws InstantiationException, IllegalAccessException {
		return implementationClass.newInstance();
	}

	private ECreatureSI(final Class<? extends ICreatureSIAlgorithm> clazz) {
		this.implementationClass = clazz;
	}
}
