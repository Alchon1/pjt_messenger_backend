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
	} // 숫자에 매핑한 역할.
	
    public static String getRedirectUrlByRole(String roles) {
        switch (roles) {
            case "ROLE_Employee": return "/employee/main";
            case "ROLE_TeamLeader": return "/teamleader/main";
            case "ROLE_DepartmentLeader": return "/department/main";
            case "ROLE_CEO": return "/board/Feedback/list";
            case "ROLE_HireManager": return "/member/list";
            case "ROLE_SystemManager": return "/admin/main";
            default: return "/chat";
        }
    } // 로그인 이후 해당 역할에 따라 다른 초기 메인 주소.
	
	
	
} // end Util
