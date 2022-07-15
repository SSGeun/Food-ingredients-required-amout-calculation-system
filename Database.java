package CookingClass.Server;

import CookingClass.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;

public class Database implements Serializable {
	public String logInCheck(String id, String pw) throws SQLException {   //LogIn 체크.
		String check="";

		if(id.equals("student")) {
			check = "Student";
			return check;
		}
      
      		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
      		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
      		Statement stmt = cone.createStatement();
      
     	 	ResultSet rs = stmt.executeQuery("SELECT * FROM 강사");
      		ArrayList<Teacher> teacher = new ArrayList<Teacher>();

	      	try {
      			while (rs.next()) {
   				String teacherID = rs.getString("강사id");
   				String teacherName = rs.getString("강사명");
   				String accNum = rs.getString("계좌번호");

   				teacher.add(new Teacher(teacherID, teacherName, accNum));
  			}
  	    	} catch (SQLException e) {
     			e.printStackTrace();
      		}
     
     	 	ResultSet rs2 = stmt.executeQuery("SELECT * FROM 관리자");
      		ArrayList<Managers> manager = new ArrayList<Managers>();
      	
   		try {
   			while (rs2.next()) {
   				String managerID = rs2.getString("관리자id");
      				String managerPW = rs2.getString("관리자pw");

      				manager.add(new Managers(managerID, managerPW));
      			}
	     	} catch (SQLException e) {
      			e.printStackTrace();
      		}
      		
      		if (id.charAt(0)=='F') {		//ID의 첫 글자가 'F'일시 강사.
      			for (int i = 0; i < teacher.size(); i++) {
      				if (teacher.get(i).getTeacherID().equals(id)) {	//ID 검사
               				//check = "Teacher";
          					//return check;

					if(teacher.get(i).getAccNum().equals(pw)) {	//PW 검사
          						check = "Teacher";
          						return check;
               				}
            				}
         			}
       			check = "Default";	//일치하는 강사가 없다.
           			 return check;
      		}
      		
      		else if (id.charAt(0)=='M') {		//ID의 첫글자가 'M'일시 관리자.
        			for (int j = 0; j < manager.size(); j++) {
        				if (manager.get(j).getManagerID().equals(id)) {		//ID검사
       					//check = "Manager";
       					//return check;

					if (manager.get(j).getManagerPW().equals(pw)) {	//PW검사
       						check = "Manager";
       						return check;
               				}
           			 	}	
       		  	}
         			check = "Default";	//일치하는 관리자가 없다.
          			  return check;
      		}
      
      		check = "Default";	//강사도 관리자도 아니다.
      		return check;
   	}
	
	public ArrayList<MenuList> getMenuList() throws SQLException { // 식단
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM 식단");
		ArrayList<MenuList> menuList = new ArrayList<>();
		
		try {
			while (rs.next()) {
				String menuID = rs.getString("식단id");
				String menuName = rs.getString("식단명");
				double price = rs.getInt("실습재료비");

				menuList.add(new MenuList(menuID, menuName, price));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
		return menuList;
	}

	public void addMenuList(ArrayList<MenuList> menuList) throws SQLException {	//식단 추가
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		System.out.println("DB연결 성공");
		Statement stmt = cone.createStatement();

		ArrayList<MenuList> ml = getMenuList();
		
		try {
			for (int i = 0; i < menuList.size(); i++) {
				String menuID = menuList.get(i).getMenuID();
				String menuName = menuList.get(i).getMenuName();
				double price = menuList.get(i).getPrice();
				stmt.executeUpdate("INSERT INTO 식단(식단id, 식단명, 실습재료비) VALUES('" + menuID + "', '" + menuName + "', " + price + ");");	//입력받은 리스트 순으로 식단 추가.
				//for(int j = 0; j < ml.size(); j++) {
				//	if (menuID.equals(ml.get(j).getMenuID()))		//입력한 MenuID가 이미 존재.
				//		break;
				//	else if (menuName.equals(ml.get(j).getMenuName()))	//입력한 MenuName이 이미 존재.
				//		break;
				//	else if (price <= 0)		//실습재료비가 0원보다 작거나 같음.
				//		break;
				//	else {	//새로운 Menu일시 추가.
				//		stmt.executeUpdate("INSERT INTO 식단(식단id, 식단명, 실습재료비) VALUES('" + menuID + "', '" + menuName + "', " + price + ");");	//입력받은 리스트 순으로 식단 추가.
				//		break;
				//	}
				//}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}

	public ArrayList<Reserve> getReserve() throws SQLException { // 식재료
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM 식재료");
		ArrayList<Reserve> reserve = new ArrayList<>();
		
		try {
			while (rs.next()) {
				String reserveID = rs.getString("식재료id");
				String reserveName = rs.getString("식재료명");
				String unit = rs.getString("단위");

				reserve.add(new Reserve(reserveID, reserveName, unit));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
		return reserve;
	}

	public void addReserve(ArrayList<Reserve> reserve) throws SQLException {	//식재료 추가
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();

		ArrayList<Reserve> re = getReserve();

		try {
			for (int i = 0; i < reserve.size(); i++) {
				String reserveID = reserve.get(i).getReserveID();
				String reserveName = reserve.get(i).getReserveName();
				String unit = reserve.get(i).getUnit();
				System.out.println(reserveID);
				System.out.println(reserveName);
				System.out.println(unit);

				//for(int j = 0; j < re.size(); j++) {
				//	if (reserveID.equals(re.get(j).getReserveID()))	//입력한 ReserveID가 이미 존재.
				//		break;
				//	else if (reserveName.equals(re.get(j).getReserveName()))	//입력한 ReserveName이 이미 존재.
				//		break;
				//	else {	//새로운 Reserve일시 추가.
				//		stmt.executeUpdate(  "INSERT INTO 식재료(식재료id, 식재료명, 단위) VALUES('" + reserveID + "', '" + reserveName + "', '" + unit + "');");	//입력받은 식재료 추가.
				//		break;
				//	}
				//}
				stmt.executeUpdate( "INSERT INTO 식재료(식재료id, 식재료명, 단위) VALUES('" + reserveID + "', '" + reserveName + "', '" + unit + "');");	//입력받은 식재료 추가.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}

	public ArrayList<Recipe> getRecipe() throws SQLException { // recipe
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM recipe");
		ArrayList<Recipe> recipe = new ArrayList<>();
		
		try {
			while (rs.next()) {
				String menuID = rs.getString("식단id");
				String reserveID = rs.getString("식재료id");
				double requires = rs.getDouble("소요량");

				recipe.add(new Recipe(menuID, reserveID, requires));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
		return recipe;
	}

	public void addRecipe(ArrayList<Recipe> recipe) throws SQLException {	//recipe 추가
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();
		
		ArrayList<Recipe> r = getRecipe();
		ArrayList<Reserve> re = getReserve();
		ArrayList<MenuList> m = getMenuList();

		try {
			for (int i = 0; i < recipe.size(); i++) {
				String menuID = recipe.get(i).getMenuID();
				String reserveID = recipe.get(i).getReserveID();
				double requires = recipe.get(i).getRequires();

				stmt.executeUpdate( "INSERT INTO recipe(식단id, 식재료id, 소요량) VALUES('" + menuID + "', '" + reserveID + "', " + requires + ");");	//입력받은 recipe 추가
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}
	
	public void updateRecipe(ArrayList<Recipe> recipe) throws SQLException {	//recipe 업데이트
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();
	
		try {
			for (int i = 0; i < recipe.size(); i++) {
				String menuID = recipe.get(i).getMenuID();
				String reserveID = recipe.get(i).getReserveID();
				double requires = recipe.get(i).getRequires();

				stmt.executeUpdate( "UPDATE recipe SET 소요량=" + requires + " WHERE 식단id='" + menuID + "' AND 식재료id='" + reserveID +"';");	//recipe 업데이트
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}

	public void removeRecipe(ArrayList<Recipe> recipe2) throws SQLException {		//recipe 삭제
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM recipe");
		ArrayList<Recipe> recipe = new ArrayList<>();

		//ArrayList<Recipe> recipe = getRecipe();	

		try {
			while (rs.next()) {
				String menuID = rs.getString("식단id");
				String reserveID = rs.getString("식재료id");
				double requires = rs.getDouble("소요량");

				recipe.add(new Recipe(menuID, reserveID, requires));
			}

			for(int i = 0; i < recipe2.size(); i++) {
				for(int j = 0; j < recipe.size(); j++) {
					if(recipe2.get(i).getMenuID().equals(recipe.get(j).getMenuID()) && recipe2.get(i).getReserveID().equals(recipe.get(j).getReserveID())) {
						String menuID = recipe2.get(i).getMenuID();
						String reserveID = recipe2.get(i).getReserveID();
						System.out.println(menuID);
						System.out.println(reserveID);
						stmt.executeUpdate("DELETE FROM recipe WHERE 식단id='" + menuID + "' AND 식재료id='" + reserveID + "';");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}			

	public ArrayList<Menu> getMenu() throws SQLException { // 강의일자별_실습메뉴
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";;
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM 강의일자별_실습메뉴");
		ArrayList<Menu> menu = new ArrayList<Menu>();
		
		try {
			while (rs.next()) {
				String branch = rs.getString("강의장소");
				int date = rs.getInt("수업일");
				String menuID = rs.getString("식단id");

				menu.add(new Menu(branch, date, menuID));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
		return menu;
	}

	public void addMenu(ArrayList<Menu> menu) throws SQLException {	//실습메뉴 추가
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		System.out.println("DB연결 성공");
		Statement stmt = cone.createStatement();		

		try {
			for (int i = 0; i < menu.size(); i++) {
				String branch = menu.get(i).getBranch();
				int date = menu.get(i).getDate();
				String menuID = menu.get(i).getMenuID();

				stmt.executeUpdate("INSERT INTO 강의일자별_실습메뉴(강의장소, 수업일, 식단id) VALUES('" + branch + "', " + date + ", '" + menuID + "');");	//실습메뉴 추가.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}

	public ArrayList<Applications> getApplications() throws SQLException { // 식재료_신청
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM 식재료_신청");
		ArrayList<Applications> application = new ArrayList<Applications>();
		
		ArrayList<ApplicationsList> al = getApplicationsList();

		Date dates=new Date();
		int nowDate=(1900+dates.getYear())*10000+(1+dates.getMonth())*100+(dates.getDay()+23);
		
		try {
			while (rs.next()) {
				int appNum = rs.getInt("신청번호");
				String branch = rs.getString("강의장소");
				int date = rs.getInt("수업일");
				String studentName = rs.getString("신청자명");
				String phoneNum = rs.getString("신청자전화번호");
				int depositDay = rs.getInt("입금일");
				int depositMoney = rs.getInt("입금액");
				int cancleDay = rs.getInt("취소일");
				int refundDay = rs.getInt("환불일");
				int refundMoney = rs.getInt("환불금액");

				if((date - 1) == nowDate) {
					if (depositMoney == 0) {
						stmt.executeUpdate("UPDATE 식재료_신청 SET 취소일=" + nowDate + " WHERE 신청번호=" + appNum + ";");	//(입금 x)수업일 하루 전 '식재료_신청' 행에 취소일 추가.

						for(int i = 0; i < al.size(); i++) {
							if(al.get(i).getAppNum() == appNum) {
								stmt.executeUpdate("DELETE FROM 식재료_신청내역 WHERE 신청번호=" + appNum + ";");	//(입금 x)수업일 하루 전 '식재료_신청내역' 행 삭제.
								break;
							}	
						}
						continue;
					}
					else
						continue;
				}
					
				application.add(new Applications(appNum, branch, date, studentName, phoneNum, depositDay, depositMoney, cancleDay, refundDay, refundMoney));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
		return application;
	}

	public ArrayList<Applications> getApplications2() throws SQLException { // 식재료_신청 == 식재료_신청내역 같은부분만 반환.
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();
		Statement stmt2 = cone.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM 식재료_신청");
		ArrayList<Applications> application = new ArrayList<Applications>();	
		
		ResultSet rs2 = stmt2.executeQuery("SELECT * FROM 식재료_신청내역");
		ArrayList<ApplicationsList> applicationList = new ArrayList<ApplicationsList>();

		ArrayList<Applications> application2 = new ArrayList<Applications>();	
		
		try {
			while (rs.next()) {
				int appNum = rs.getInt("신청번호");
				String branch = rs.getString("강의장소");
				int date = rs.getInt("수업일");
				String studentName = rs.getString("신청자명");
				String phoneNum = rs.getString("신청자전화번호");
				int depositDay = rs.getInt("입금일");
				int depositMoney = rs.getInt("입금액");
				int cancleDay = rs.getInt("취소일");
				int refundDay = rs.getInt("환불일");
				int refundMoney = rs.getInt("환불금액");

				application.add(new Applications(appNum, branch, date, studentName, phoneNum, depositDay, depositMoney, cancleDay, refundDay, refundMoney));
			}
			
			while (rs2.next()) {
				int appNum = rs2.getInt("신청번호");
				String menuID = rs2.getString("식단id");
				double amount = rs2.getDouble("신청수량");
				double totalPrice = rs2.getDouble("식재료비");

				applicationList.add(new ApplicationsList(appNum, menuID, amount, totalPrice));
			}

			for (int i = 0; i < application.size(); i++) {
				for(int j = 0; j < applicationList.size(); j++) {
					if(application.get(i).getAppNum() == applicationList.get(j).getAppNum()) {
						int appNum = application.get(i).getAppNum();
						String branch = application.get(i).getBranch();
						int date = application.get(i).getDate();
						String studentName = application.get(i).getStudentName();
						String phoneNum = application.get(i).getPhoneNum();
						int depositDay = application.get(i).getDepositDay();
						int depositMoney = application.get(i).getDepositMoney();
						int cancleDay = application.get(i).getCancleDay();
						int refundDay = application.get(i).getRefundDay();
						int refundMoney = application.get(i).getRefundMoney();
						application2.add(new Applications(appNum, branch, date, studentName, phoneNum, depositDay, depositMoney, cancleDay, refundDay, refundMoney));
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
			stmt2.close();
		}
		return application2;
	}
	
	public void addApplications(Applications application) throws SQLException {	//식재료_신청 추가.
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		System.out.println("DB연결 성공");
		Statement stmt = cone.createStatement();

		ArrayList<Menu> m = getMenu();

		try {
			String studentName = application.getStudentName();
			String phoneNum = application.getPhoneNum();
			int date = application.getDate();
			String branch = application.getBranch();
			System.out.println(studentName);
			System.out.println(phoneNum);
			System.out.println(date);
			System.out.println(branch);

			//for (int j = 0; j <m.size(); j++) {		//강의일정에 있는 정보만 신청 가능.
			//		if (m.get(j).getBranch().equals(branch) && m.get(j).getDate() == date)
			//			stmt.executeUpdate("INSERT INTO 식재료_신청(강의장소, 수업일, 신청자명, 신청자전화번호) VALUES('" + branch + "', " + date + ", '" + studentName + "', '" + phoneNum + "');");
			//}
			stmt.executeUpdate("INSERT INTO 식재료_신청(강의장소, 수업일, 신청자명, 신청자전화번호) VALUES('" + branch + "', " + date + ", '" + studentName + "', '" + phoneNum + "');");
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}

	public void cancelApplications(int[] cancel) throws SQLException {	//식재료_신청 취소일 및 환불일, 환불액 등록.
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		System.out.println("DB연결 성공");
		Statement stmt = cone.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM 식재료_신청");
		ArrayList<Applications> application = new ArrayList<Applications>();

		try {
			while (rs.next()) {
				int appNum = rs.getInt("신청번호");
				String branch = rs.getString("강의장소");
				int date = rs.getInt("수업일");
				String studentName = rs.getString("신청자명");
				String phoneNum = rs.getString("신청자전화번호");
				int depositDay = rs.getInt("입금일");
				int depositMoney = rs.getInt("입금액");
				int cancleDay = rs.getInt("취소일");
				int refundDay = rs.getInt("환불일");
				int refundMoney = rs.getInt("환불금액");

				if ((cancel[1] + 1) == date)	//수업일 하루 전 입금이 되어 있을 시 취소 불가.
					continue;	

				application.add(new Applications(appNum, branch, date, studentName, phoneNum, depositDay, depositMoney, cancleDay, refundDay, refundMoney));
			}
			
			int depositMoney2 = 0;

			for (int i = 0; i < application.size(); i++) {
				if (application.get(i).getAppNum() == cancel[0])
					depositMoney2 = application.get(i).getDepositMoney();
			}

			if (depositMoney2 == 0)
				stmt.executeUpdate("UPDATE 식재료_신청 SET 취소일=" + cancel[1] + " WHERE 신청번호=" + cancel[0] + ";");
			else if (depositMoney2 != 0)
				stmt.executeUpdate("UPDATE 식재료_신청 SET 취소일=" + cancel[1] + ", 환불일=" + cancel[1] + ", 환불금액=" + depositMoney2 + " WHERE 신청번호=" + cancel[0] + ";");
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}
	
	public void depositApplications(int appNum, int depositMoney, int depositDay) throws SQLException {	//식재료_신청자 입금액 업데이트
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();

		stmt.executeUpdate("UPDATE 식재료_신청 SET 입금일=" + depositDay + ", 입금액=" + depositMoney + " WHERE 신청번호='" + appNum + "';");

		stmt.close();
	}

	public ArrayList<ApplicationsList> getApplicationsList() throws SQLException { // 식재료_신청내역
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM 식재료_신청내역");
		ArrayList<ApplicationsList> applicationList = new ArrayList<ApplicationsList>();
		
		try {
			while (rs.next()) {
				int appNum = rs.getInt("신청번호");
				String menuID = rs.getString("식단id");
				double amount = rs.getDouble("신청수량");
				double totalPrice = rs.getDouble("식재료비");

				applicationList.add(new ApplicationsList(appNum, menuID, amount, totalPrice));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
		return applicationList;
	}
	
	public void removeApplicationsList(int appNum) throws SQLException {	//식재료_신청내역 삭제 (appNum)
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		System.out.println("DB연결 성공");
		Statement stmt = cone.createStatement();
		ArrayList<ApplicationsList> applicationList = getApplicationsList();
		
		try {
			for(int i = 0; i < applicationList.size(); i++) {
				if(applicationList.get(i).getAppNum() == appNum)			
					stmt.executeUpdate("DELETE FROM 식재료_신청내역 WHERE 신청번호='" + appNum + "';");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}
	
	public void addApplicationsList(ApplicationsList applicationList) throws SQLException {	//식재료_신청내역 추가
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();

		ArrayList<Menu> m = getMenu();
		
		ResultSet rs=stmt.executeQuery("SELECT * FROM 식재료_신청");
		
		try {
			String menuID = applicationList.getMenuID();
			double amount = applicationList.getAmount();
			double totalPrice = applicationList.getTotalPrice();
			while(rs.next()) {
				if(rs.last() == true) {
					String branch =rs.getString("강의장소");
					int appNum = rs.getInt("신청번호");
					int date = rs.getInt("수업일");

					for (int i = 0; i < m.size(); i++) {
						if(m.get(i).getBranch().equals(branch) && m.get(i).getDate() == date && m.get(i).getMenuID().equals(menuID)) {	//강의일정에 존재하는 menuID만 추가 가능.
							stmt.executeUpdate("INSERT INTO 식재료_신청내역(신청번호, 식단id, 신청수량, 식재료비) VALUES('" + appNum + "', '" + menuID + "', " + amount + ", " + totalPrice + ");");
							break;
						}
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}

	public ArrayList<Teacher> getTeacher() throws SQLException { // 강사
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM 강사");
		ArrayList<Teacher> teacher = new ArrayList<Teacher>();
		
		try {
			while (rs.next()) {
				String teacherID = rs.getString("강사id");
				String teacherName = rs.getString("강사명");
				String accNum = rs.getString("계좌번호");

				teacher.add(new Teacher(teacherID, teacherName, accNum));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
		return teacher;
	}

	public ArrayList<Schedule> getSchedule() throws SQLException { // 강의일정
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM 강의일정");
		ArrayList<Schedule> schedule = new ArrayList<Schedule>();
		
		try {
			while (rs.next()) {
				String branch = rs.getString("강의장소");
				int year = rs.getInt("년도");
				String teacherID = rs.getString("강사id");
				String day = rs.getString("요일");
				int from = rs.getInt("시작시간");
				int to = rs.getInt("끝시간");

				schedule.add(new Schedule(branch, year, teacherID, day, from, to));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
		return schedule;
	}

	public void addSchedule(ArrayList<Schedule> schedule) throws SQLException {	//강의일정 추가
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();

		try {
			for (int i = 0; i < schedule.size(); i++) {
				String branch = schedule.get(i).getBranch();
				int year = schedule.get(i).getYear();
				String teacherID = schedule.get(i).getTeacherID();
				String day = schedule.get(i).getDay();
				int from = schedule.get(i).getFrom();
				int to = schedule.get(i).getTo();

				stmt.executeUpdate("INSERT INTO 강의일정(강의장소, 년도, 강사id, 요일, 시작시간, 끝시간) VALUES('" + branch + "', '" + year + "', '" + teacherID + "', '" + day + "', " + from + ", " + to + ");");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}
	
	public void updateSchedule(ArrayList<Schedule> schedule) throws SQLException {	//강의일정 수정
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();

		try {
			for (int i = 0; i < schedule.size(); i++) {
				String branch = schedule.get(i).getBranch();
				int year = schedule.get(i).getYear();
				String teacherID = schedule.get(i).getTeacherID();
				String day = schedule.get(i).getDay();
				int from = schedule.get(i).getFrom();
				int to = schedule.get(i).getTo();

				stmt.executeUpdate("UPDATE 강의일정 SET 요일='" + day + "', 시작시간=" + from + ", 끝시간=" + to + " WHERE 강의장소='" + branch + "' AND 년도='" + year + "' AND 강사id='" + teacherID + "';");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}

	public void removeSchedule(ArrayList<Schedule> schedule2) throws SQLException {		//강의일정 삭제
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();
		
		ResultSet rs = stmt.executeQuery("SELECT * FROM 강의일정");
		ArrayList<Schedule> schedule = new ArrayList<Schedule>();

		try {
			while (rs.next()) {
				String branch = rs.getString("강의장소");
				int year = rs.getInt("년도");
				String teacherID = rs.getString("강사id");
				String day = rs.getString("요일");
				int from = rs.getInt("시작시간");
				int to = rs.getInt("끝시간");

				schedule.add(new Schedule(branch, year, teacherID, day, from, to));
			}
			//ArrayList<Schedule> schedule = getSchedule();

			for (int i = 0; i < schedule2.size(); i++) {
				for (int j = 0; j < schedule.size(); j++) {
					if(schedule2.get(i).getBranch().equals(schedule.get(j).getBranch()) && schedule2.get(i).getYear() == schedule.get(j).getYear() && schedule2.get(i).getTeacherID().equals(schedule.get(j).getTeacherID())) {
						String branch = schedule2.get(i).getBranch();
						int year = schedule2.get(i).getYear();
						String teacherID = schedule2.get(i).getTeacherID();
						System.out.println(branch);
						System.out.println(year);
						System.out.println(teacherID);

						stmt.executeUpdate("DELETE FROM 강의일정 WHERE 강의장소='" + branch + "' AND 년도=" + year + " AND 강사id='" + teacherID + "';");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}		
}