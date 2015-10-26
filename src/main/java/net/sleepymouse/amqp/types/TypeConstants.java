/**
 * OASIS Advanced Message Queuing Protocol (AMQP) Version 1.0
 * Part 1: Types
 */
package net.sleepymouse.amqp.types;

import java.util.*;

/**
 * @author Alan Smithee
 *
 */
public class TypeConstants
{
	private TypeConstants()
	{}

	public static enum Types
	{
		PRIMITIVE, DESCRIBED, COMPOSITE, RESTRICTED
	}

	public static enum TypeNames
	{

	}

	public static enum CategoryFormatCodes
	{
		FIXED, VARIABLE, COMPOUND, ARRAY
	}

	public static enum SubcategoryFormatCodes
	{
		// Bits 4..7
		EMPTY(0x40), FIXED_ONE(0x50), FIXED_TWO(0x60), FIXED_FOUR(0x70), FIXED_EIGHT(0x80), FIXED_SIXTEEN(0x90), //
		VARIABLE_ONE(0xA0), VARIABLE_FOUR(0xB0), //
		COMPOUND_ONE(0xC0), COMPOUND_FOUR(0xD0), //
		ARRAY_ONE(0xE0), ARRAY_FOUR(0xF0); //

		private int value;

		private SubcategoryFormatCodes(int value)
		{
			this.value = value;
		}

		public int getValue()
		{
			return value;
		}
		private static final Map<Integer, SubcategoryFormatCodes> BY_CODE_MAP = new HashMap<>();

		static
		{
			for (SubcategoryFormatCodes code : SubcategoryFormatCodes.values())
			{
				BY_CODE_MAP.put(code.value, code);
			}
		}

		public static SubcategoryFormatCodes getCodeByValue(Integer key)
		{
			return BY_CODE_MAP.get(key);
		}
	}

}
