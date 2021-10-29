package com.application.mtxml;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * {@code TagFactory} is a factory-class for creating {@code Tag}-objects from
 * raw MT-format.
 *
 */
public class TagFactory {

	/**
	 * Factory-method for creating a tag. Using preloaded information about the tag
	 * definition
	 * 
	 * @param tag     Tag to create, i.e. "19A"
	 * @param content The contents of the field in the MT-message after the
	 *                ":{Tag}:"-prefix
	 * @return {@code Tag}-object representing the input.
	 * @throws UnknownTagException If the contents of the {@code tag} parameter does
	 *                             not represent a tag in the MT Standard
	 */
	public static Tag createTag(String tag, String content, String tagContentRegex, List<String> fieldNames)
			throws UnknownTagException, MTSyntaxException {
		Objects.requireNonNull(tag, "Tag cannot be null");
		Objects.requireNonNull(content, "Tagcontent cannot be null");
		Objects.requireNonNull(tagContentRegex, "tagContentRegex cannot be null");

		// Map for underlying subfields
		LinkedHashMap<String, MTComponent> subfields = new LinkedHashMap<String, MTComponent>();
		String qualifier = null;

		// Match the different subfields in the tag contents
		Pattern tagContentPattern = Pattern.compile(tagContentRegex, Pattern.MULTILINE);
		Matcher tagContentMatcher = tagContentPattern.matcher(content);

		if (!tagContentMatcher.find()) {
			throw new MTSyntaxException(tagContentRegex, tag);
		}
		if (tagContentMatcher.start() > 0)
			throw new MTSyntaxException(tagContentRegex, tag);

		String fieldValue;
		for (String fieldName : fieldNames) {
			// Retrieve groupname
			String regexGroupName = fieldName.length() <= 32 ? fieldName : fieldName.substring(0, 32);
			fieldValue = tagContentMatcher.group(regexGroupName);

			if (fieldName.equals("Qualifier")) {
				// Qualifier stored in its own variable
				qualifier = fieldValue;
			} else {
				// null variables stored as empty strings
				if (fieldValue == null)
					fieldValue = "";

				// Additional validation is needed for numeric fields
				if (Tag.isNumericField(fieldName)) {
					validateNumericField(fieldValue);
					// Translate MT number format with commas to numeric format with dots
					fieldValue = fieldValue.replace(',', '.');
				}

				// Add the subfield as a MTEndNode
				subfields.put(fieldName, new MTEndNode(fieldName, null, fieldValue));
			}

		}

		// Give a warning if there are characters after the match which cannot be parsed
		// as part of the tag
		int noOverrunChars = content.length() - tagContentMatcher.end();
		if (noOverrunChars > 0) {
			System.out.println(String.format(
					"[Warning] FieldOverrunError: An additional %d characters could not be successfully be treated as part of any subfield in tag %s.",
					noOverrunChars, tag));
		}

		return new Tag(tag, content, qualifier, subfields);

	}

	/**
	 * Performs additional validation on the contents of a numeric field not already
	 * programmed in the regex-components.
	 * 
	 * @param value The matched numeric field content
	 * @throws MTSyntaxException If the {@code value}-parameter is not a propperly
	 *                           formatted numeric format in accordance with the MT
	 *                           standard.
	 */
	private static void validateNumericField(String value) throws MTSyntaxException {
		String error = null;
		int commaIx = value.indexOf(',');
		if (commaIx == -1) {
			error = "Malformed amount field: Lacking comma";
		} else if (commaIx == 0) {
			error = "Malformed amount field: Lacking digits ahead of comma";
		} else if (value.indexOf(',', commaIx + 1) > -1) {
			error = "Malformed amount field: Multiple commas in field";
		}
		if (error != null) {
			throw new MTSyntaxException(error);
		}
	}

}
