package com.lachesis.support.auth.common;

import java.io.Serializable;
import java.lang.reflect.Field;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.jdbc.SQL;

public final class SQLBuilder {
	public static final char UNDER_SCORE = '_';

	public String buildSaveOne(final @Param("t") Object t, final @Param("clazz") Class<?> clazz) {
		try {
			return new SQL() {
				{
					INSERT_INTO(buildTableName(clazz));
					Field[] fs = clazz.getDeclaredFields();
					boolean atLeastOneFieldToPresent = false;
					for (Field f : fs) {
						if (needToPresents(t, f)) {
							atLeastOneFieldToPresent = true;
							VALUES(buildValuesLabel(f), buildValuesParamPlaceHolder(f));
						}
					}
					
					if(!atLeastOneFieldToPresent){
						throw new RuntimeException("at least one field should be assigned value.");
					}
				}
			}.toString();
		} catch (IllegalAccessException e) {
			throw new RuntimeException("errors while buildSaveOne", e);
		}
	}

	private boolean needToPresents(Object t, Field f) throws IllegalArgumentException, IllegalAccessException {
		f.setAccessible(true);
		if (f.get(t) == null) {
			return false;
		}
		if (f.getName().equals("id")) {
			return false;
		}

		return true;
	}

	private String buildValuesLabel(Field f) {
		return buildCamelColumnName(f.getName());
	}

	private String buildValuesParamPlaceHolder(Field f) {
		return String.format("#{t.%s}", f.getName());
	}

	public String buildFindOneById(@Param("id") final Serializable id, @Param("clazz") final Class<?> clazz) {
		return new SQL() {
			{
				Field[] fs = clazz.getDeclaredFields();

				for (Field f : fs) {
					SELECT(build(f));
				}

				FROM(buildTableName(clazz));
				WHERE(buildWhere());
			}
		}.toString();
	}

	private String buildWhere() {
		return "ID = #{id}";
	}

	private String buildTableName(Class<?> clazz) {
		return "T_" + clazz.getSimpleName().toUpperCase();
	}

	private String build(Field f) {
		return String.format("%s as %s", buildCamelColumnName(f.getName()), f.getName());
	}

	private String buildCamelColumnName(String fieldName) {
		StringBuilder sb = new StringBuilder();
		int len = fieldName.length();

		for (int index = 0; index < len; index++) {
			char c = fieldName.charAt(index);
			if (Character.isUpperCase(c)) {
				sb.append(UNDER_SCORE).append(Character.toUpperCase(c));
			} else {
				sb.append(Character.toUpperCase(c));
			}
		}

		return sb.toString();
	}

	public SQLBuilder() {
	}
}
