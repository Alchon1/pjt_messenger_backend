package org.zerock.myapp.util;

public class RoleUtil {

	
	public static String mapPositionToRole(int position) {
		switch (position) {
		case 1:
			return "Employee";
		case 2:
			return "TeamLeader";
		case 3:
			return "DepartmentLeader";
		case 4:
			return "CEO";
		case 5:
			return "HireManager";
		case 9:
			return "SystemManager";
		default:
			return "Employee";
		}
	} // 숫자에 매핑한 역할.
	
    public static String getRedirectUrlByRole(String role) {
        switch (role) {
            case "Employee": return "/chat";
            case "TeamLeader": return "/chat";
            case "DepartmentLeader": return "/chat";
            case "CEO": return "/chat";
            case "HireManager": return "/member/list";
            case "SystemManager": return "/member/list";
            default: return "/chat";
        }
    } // 로그인 이후 해당 역할에 따라 다른 초기 메인 주소.
	
	
	
} // end Util
