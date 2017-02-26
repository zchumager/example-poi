package net.me.dev.utils;

public enum DATAPOOL_FIELD {
	HOST
	, USERNAME
	, PASSWORD
	, COMMAND
	, EXECUTE_COMMAND_FLAG
	, SOURCE_ENVIRONMENT_ID
	, SOURCE_INPUT_TYPE
	, SOURCE_TABLENAME_OR_QUERY
	, SOURCE_PRIMARY_KEY
	, TARGET_ENVIRONMENT_ID
	, TARGET_INPUT_TYPE
	, TARGET_TABLENAME_OR_QUERY
	, TARGET_PRIMARY_KEY
	, COMPARISON_FLAG;
	
	int value = 0;
	
	int getValue() {
		
		switch(this) {
			case HOST:
				value = 0;
				break;
			case USERNAME:
				value = 1;
				break;
			case PASSWORD:
				value = 2;
				break;
			case COMMAND:
				value = 3;
				break;
			case EXECUTE_COMMAND_FLAG:
				value = 4;
				break;
			case SOURCE_ENVIRONMENT_ID:
				value = 5;
				break;
			case SOURCE_INPUT_TYPE:
				value = 6;
				break;
			case SOURCE_TABLENAME_OR_QUERY:
				value = 7;
				break;
			case SOURCE_PRIMARY_KEY:
				value = 8;
				break;
			case TARGET_ENVIRONMENT_ID:
				value = 9;
				break;
			case TARGET_INPUT_TYPE:
				value = 10;
				break;
			case TARGET_TABLENAME_OR_QUERY:
				value = 11;
				break;
			case TARGET_PRIMARY_KEY:
				value = 12;
				break;
			case COMPARISON_FLAG:
				value = 13;
				break;
		}
		return this.value;
	}
}
