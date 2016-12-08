package pl.projewski.game.antos.configuration;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import pl.projewski.game.antos.AntosUtil;
import pl.projewski.game.antos.util.GsonEnumAdapterFactory;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class GameConfiguration {
	@Getter
	@Setter
	String language = "pl";

	@Getter
	@Setter
	String playerCreatureName = ECreature.PLAYER.name();

	@Getter
	@Setter
	boolean paintGridLines = false;

	@Getter
	@Setter
	boolean moveSlide = true;

	@Getter
	@Setter
	String backgroundImage = null;

	@Getter
	@Setter
	private List<ECreature> creatures = Arrays.asList(ECreature.values());

	@Getter
	@Setter
	private List<EBlock> blocks = Arrays.asList(EBlock.values());

	private static GameConfiguration instance;

	public static GameConfiguration getInstance() {
		if (instance == null) {
			try {
				instance = read("game.conf");
			} catch (final JsonIOException e) {
				log.info("Cannot read game.conf", e);
			} catch (final IOException e) {
				log.info("Cannot read game.conf", e);
			}
		}
		return instance;
	}

	public static void main(final String[] args) throws JsonIOException, IOException {
		// write empty initialization of configuration
		final GameConfiguration gameConfiguration = new GameConfiguration();
		write(gameConfiguration, "game.conf.empty");
	}

	public static void write(final GameConfiguration conf, final String filename) throws JsonIOException, IOException {
		final GsonBuilder builder = new GsonBuilder();
		builder.setPrettyPrinting().create();
		builder.serializeNulls().create();
		builder.registerTypeAdapterFactory(new GsonEnumAdapterFactory());
		final Gson gson = builder.create();
		FileWriter writer = null;
		try {
			writer = new FileWriter(filename);
			gson.toJson(conf, writer);
			writer.flush();
		} finally {
			IOUtils.closeQuietly(writer);
		}
	}

	public static GameConfiguration read(final String filename) throws JsonIOException, IOException {
		final GsonBuilder builder = new GsonBuilder();
		builder.registerTypeAdapterFactory(new GsonEnumAdapterFactory());
		final Gson gson = builder.create();
		InputStream inputStream = null;
		InputStreamReader reader = null;
		try {
			inputStream = AntosUtil.getStreamToResource(filename);
			reader = new InputStreamReader(inputStream);
			return gson.fromJson(reader, GameConfiguration.class);
		} finally {
			IOUtils.closeQuietly(reader);
			IOUtils.closeQuietly(inputStream);
		}
	}
}
