package org.zerock.myapp.util;

public class RoleUtil {

	public static String mapPositionToRole(int position) {
		switch (position) {
		case 1:
			return "ROLE_Employee";
		case 2:
			return "ROLE_TeamLeader";
		case 3:
			return "ROLE_DepartmentLeader";
		case 4:
			return "ROLE_CEO";
		case 5:
			return "ROLE_HireManager";
		case 9:
			return "ROLE_SystemManager";
		default:
			return "ROLE_Employee";
		}
	}
}
