package com.tvo.common;

import com.tvo.common.AppConstant.Status;
import com.tvo.enums.StatusActivate;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.Conditions;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.spi.MappingContext;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


public class ModelMapperUtils {

	private static ModelMapper modelMapper = new ModelMapper();

	/**
	 * Model mapper property setting are specified in the following block. Default
	 * property matching strategy is set to Strict see {@link MatchingStrategies}
	 * Custom mappings are added using {@link ModelMapper#addMappings(PropertyMap)}
	 */
	static {
		modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setPropertyCondition(Conditions.isNotNull());
		modelMapper.getConfiguration().setSkipNullEnabled(true);
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
		

		// convert String -> LocalDate
		Converter<String, LocalDate> localDateConverter = new Converter<String, LocalDate>() {
			@Override
			public LocalDate convert(final MappingContext<String, LocalDate> context) {
				String source = context.getSource();
				if (CheckUtil.isEmpty(source) || !CheckUtil.isDateFormat(source, "yyyy/MM/dd")) {
					return null;
				}
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
				return LocalDate.parse(source, formatter);
			}
		};
		modelMapper.addConverter(localDateConverter);

		// convert String -> LocalDateTime
		Converter<String, LocalDateTime> localDateTimeConverter = new Converter<String, LocalDateTime>() {
			@Override
			public LocalDateTime convert(final MappingContext<String, LocalDateTime> context) {
				String source = context.getSource();
				if (CheckUtil.isEmpty(source) || !CheckUtil.isDateFormat(source, "yyyy/MM/dd HH:mm")) {
					return null;
				}
				DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
				return LocalDateTime.parse(source, formatter);
			}

		};
		modelMapper.addConverter(localDateTimeConverter);

		// convert LocalDate -> String
		Converter<LocalDate, String> stringConverter = new Converter<LocalDate, String>() {
			@Override
			public String convert(final MappingContext<LocalDate, String> context) {
				LocalDate source = context.getSource();
				if (source == null) {
					return null;
				}
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
				Date date = Date.from(source.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
				String reportDate = df.format(date);
				return reportDate;
			}
		};
		modelMapper.addConverter(stringConverter);

		// convert LocalDateTime -> String
		Converter<LocalDateTime, String> stringTimeConverter = new Converter<LocalDateTime, String>() {
			@Override
			public String convert(final MappingContext<LocalDateTime, String> context) {
				LocalDateTime source = context.getSource();
				if (source == null) {
					return null;
				}
				DateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
				Date date = Date.from(source.atZone(ZoneId.systemDefault()).toInstant());
				String reportDate = df.format(date);
				return reportDate;
			}
		};

		modelMapper.addConverter(stringTimeConverter);

		// convert String -> Boolean
		Converter<String, Boolean> stringBooleanConverter = new Converter<String, Boolean>() {
			@Override
			public Boolean convert(final MappingContext<String, Boolean> context) {
				String source = context.getSource();
				if (StringUtils.isBlank(source)) {
					return Boolean.FALSE;
				} else if (StatusActivate.STATUS_ACTIVATED.getStatus().equals(source)) {
					return Boolean.TRUE;
				}
				return Boolean.TRUE;
			}
		};
//		modelMapper.addConverter(stringBooleanConverter);

		Converter<Boolean, String> booleanToString = new Converter<Boolean, String>() {

			@Override
			public String convert(MappingContext<Boolean, String> context) {
				Boolean source = context.getSource();
				if(source) {
					return StatusActivate.STATUS_ACTIVATED.getStatus();
				}
				return StatusActivate.STATUS_DEACTIVATED.getStatus();
			}
			
		};
//		modelMapper.addConverter(booleanToString);
		
		// convert String -> EnumValue
				Converter<String, AppConstant.Status> stringEnumValueConverter = new Converter<String, AppConstant.Status>() {
					@Override
					public Status convert(final MappingContext<String, Status> context) {
						String source = context.getSource();
						switch (source) {
						case "ACTIVATED":
							return Status.ACTIVATED;
						case "DEACTIVATED":
							return Status.DEACTIVATED;
						default:
							return null;
						}
					}
				};
				modelMapper.addConverter(stringEnumValueConverter);

				// convert EnumValue -> String
				Converter<AppConstant.Status, String> enumValueStatusStringConverter = new Converter<AppConstant.Status, String>() {
					@Override
					public String convert(final MappingContext<Status, String> context) {
						Status source = context.getSource();
						return source.getValue();
					}
				};
				modelMapper.addConverter(enumValueStatusStringConverter);
	}
	/**
	 * Hide from public usage.
	 */
	private ModelMapperUtils() {
	}

	/**
	 * <p>
	 * Note: outClass object must have default constructor with no arguments
	 * </p>
	 *
	 * @param <D>      type of result object.
	 * @param <T>      type of source object to map from.
	 * @param entity   entity that needs to be mapped.
	 * @param outClass class of result object.
	 * @return new object of <code>outClass</code> type.
	 */
	public static <D, T> D map(final T entity, final Class<D> outClass) {
		return modelMapper.map(entity, outClass);
	}

	/**
	 * <p>
	 * Note: outClass object must have default constructor with no arguments
	 * </p>
	 *
	 * @param entityList list of entities that needs to be mapped
	 * @param outCLass   class of result list element
	 * @param <D>        type of objects in result list
	 * @param <T>        type of entity in <code>entityList</code>
	 * @return list of mapped object with <code><D></code> type.
	 */
	public static <D, T> List<D> mapAll(final Collection<T> entityList, final Class<D> outCLass) {
		return entityList.stream().map(entity -> map(entity, outCLass)).collect(Collectors.toList());
	}

	/**
	 * Maps {@code source} to {@code destination}.
	 *
	 * @param source      object to map from
	 * @param destination object to map to
	 */
	public static <S, D> D map(final S source, final D destination) {
		modelMapper.map(source, destination);
		return destination;
	}
}