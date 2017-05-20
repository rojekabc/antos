package pl.projewski.game.antos.util;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

public class GsonEnumAdapterFactory implements TypeAdapterFactory {

	@Override
	public <T> TypeAdapter<T> create(final Gson gson, final TypeToken<T> type) {
		final Class<? super T> rawType = type.getRawType();
		if (rawType.isEnum()) {
			return new EnumTypeAdapter<T>(rawType);
		}
		return null;
	}

	public class EnumTypeAdapter<T> extends TypeAdapter<T> {
		public Class<? super T> rawType;

		public EnumTypeAdapter(final Class<? super T> type) {
			rawType = type;
		}

		@Override
		public void write(final JsonWriter out, final T value) throws IOException {
			if (value == null || !value.getClass().isEnum()) {
				out.nullValue();
				return;
			}

			try {
				out.beginObject();
				out.name(value.toString());
				out.beginObject();
				Arrays.stream(Introspector.getBeanInfo(value.getClass()).getPropertyDescriptors())
						.filter(new Predicate<PropertyDescriptor>() {
							@Override
							public boolean test(final PropertyDescriptor pd) {
								return pd.getReadMethod() != null && !"class".equals(pd.getName())
										&& !"declaringClass".equals(pd.getName());
							}

						}).forEach(new Consumer<PropertyDescriptor>() {

							@Override
							public void accept(final PropertyDescriptor pd) {
								try {
									out.name(pd.getName());
									out.value(String.valueOf(pd.getReadMethod().invoke(value)));
								} catch (IllegalAccessException | InvocationTargetException | IOException e) {
									e.printStackTrace();
								}
							}

						});
				out.endObject();
				out.endObject();
			} catch (final IntrospectionException e) {
				e.printStackTrace();
			}
		}

		@Override
		public T read(final JsonReader in) throws IOException {
			// FIXME: BETTER EXCEPTION AND MORE FAILL CASES CATCH
			T result = null;
			try {
				final PropertyDescriptor[] propertyDescriptors = Introspector.getBeanInfo(rawType)
						.getPropertyDescriptors();
				final Method method = rawType.getMethod("valueOf", String.class);
				in.beginObject();
				final String enumName = in.nextName();
				result = (T) method.invoke(null, enumName);
				in.beginObject();
				while (in.hasNext()) {
					final String propName = in.nextName();
					for (final PropertyDescriptor propertyDescriptor : propertyDescriptors) {
						if (propertyDescriptor.getName().equals(propName)) {
							final JsonToken check = in.peek();
							if (check == JsonToken.NULL) {
								propertyDescriptor.getWriteMethod().invoke(result, null);
							} else {
								final Class<?> propertyClass = propertyDescriptor.getPropertyType();
								if (propertyClass == int.class || propertyClass == Integer.class) {
									propertyDescriptor.getWriteMethod().invoke(result, in.nextInt());
								} else if (propertyClass == String.class) {
									propertyDescriptor.getWriteMethod().invoke(result, in.nextString());
								} else if (propertyClass == long.class || propertyClass == Long.class) {
									propertyDescriptor.getWriteMethod().invoke(result, in.nextLong());
								} else if (propertyClass == boolean.class || propertyClass == Boolean.class) {
									propertyDescriptor.getWriteMethod().invoke(result, in.nextBoolean());
								}
							}
							break;
						}
					}
				}
				in.endObject();
				in.endObject();
				// }
				// Properly deserialize the input (if you use deserialization)
			} catch (final NoSuchMethodException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final InvocationTargetException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final IntrospectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return result;
		}
	}
}