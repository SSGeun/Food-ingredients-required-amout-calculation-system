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
	public String logInCheck(String id, String pw) throws SQLException {   //LogIn üũ.
		String check="";

		if(id.equals("student")) {
			check = "Student";
			return check;
		}
      
      		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
      		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
      		Statement stmt = cone.createStatement();
      
     	 	ResultSet rs = stmt.executeQuery("SELECT * FROM ����");
      		ArrayList<Teacher> teacher = new ArrayList<Teacher>();

	      	try {
      			while (rs.next()) {
   				String teacherID = rs.getString("����id");
   				String teacherName = rs.getString("�����");
   				String accNum = rs.getString("���¹�ȣ");

   				teacher.add(new Teacher(teacherID, teacherName, accNum));
  			}
  	    	} catch (SQLException e) {
     			e.printStackTrace();
      		}
     
     	 	ResultSet rs2 = stmt.executeQuery("SELECT * FROM ������");
      		ArrayList<Managers> manager = new ArrayList<Managers>();
      	
   		try {
   			while (rs2.next()) {
   				String managerID = rs2.getString("������id");
      				String managerPW = rs2.getString("������pw");

      				manager.add(new Managers(managerID, managerPW));
      			}
	     	} catch (SQLException e) {
      			e.printStackTrace();
      		}
      		
      		if (id.charAt(0)=='F') {		//ID�� ù ���ڰ� 'F'�Ͻ� ����.
      			for (int i = 0; i < teacher.size(); i++) {
      				if (teacher.get(i).getTeacherID().equals(id)) {	//ID �˻�
               				//check = "Teacher";
          					//return check;

					if(teacher.get(i).getAccNum().equals(pw)) {	//PW �˻�
          						check = "Teacher";
          						return check;
               				}
            				}
         			}
       			check = "Default";	//��ġ�ϴ� ���簡 ����.
           			 return check;
      		}
      		
      		else if (id.charAt(0)=='M') {		//ID�� ù���ڰ� 'M'�Ͻ� ������.
        			for (int j = 0; j < manager.size(); j++) {
        				if (manager.get(j).getManagerID().equals(id)) {		//ID�˻�
       					//check = "Manager";
       					//return check;

					if (manager.get(j).getManagerPW().equals(pw)) {	//PW�˻�
       						check = "Manager";
       						return check;
               				}
           			 	}	
       		  	}
         			check = "Default";	//��ġ�ϴ� �����ڰ� ����.
          			  return check;
      		}
      
      		check = "Default";	//���絵 �����ڵ� �ƴϴ�.
      		return check;
   	}
	
	public ArrayList<MenuList> getMenuList() throws SQLException { // �Ĵ�
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM �Ĵ�");
		ArrayList<MenuList> menuList = new ArrayList<>();
		
		try {
			while (rs.next()) {
				String menuID = rs.getString("�Ĵ�id");
				String menuName = rs.getString("�Ĵܸ�");
				double price = rs.getInt("�ǽ�����");

				menuList.add(new MenuList(menuID, menuName, price));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
		return menuList;
	}

	public void addMenuList(ArrayList<MenuList> menuList) throws SQLException {	//�Ĵ� �߰�
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		System.out.println("DB���� ����");
		Statement stmt = cone.createStatement();

		ArrayList<MenuList> ml = getMenuList();
		
		try {
			for (int i = 0; i < menuList.size(); i++) {
				String menuID = menuList.get(i).getMenuID();
				String menuName = menuList.get(i).getMenuName();
				double price = menuList.get(i).getPrice();
				stmt.executeUpdate("INSERT INTO �Ĵ�(�Ĵ�id, �Ĵܸ�, �ǽ�����) VALUES('" + menuID + "', '" + menuName + "', " + price + ");");	//�Է¹��� ����Ʈ ������ �Ĵ� �߰�.
				//for(int j = 0; j < ml.size(); j++) {
				//	if (menuID.equals(ml.get(j).getMenuID()))		//�Է��� MenuID�� �̹� ����.
				//		break;
				//	else if (menuName.equals(ml.get(j).getMenuName()))	//�Է��� MenuName�� �̹� ����.
				//		break;
				//	else if (price <= 0)		//�ǽ����� 0������ �۰ų� ����.
				//		break;
				//	else {	//���ο� Menu�Ͻ� �߰�.
				//		stmt.executeUpdate("INSERT INTO �Ĵ�(�Ĵ�id, �Ĵܸ�, �ǽ�����) VALUES('" + menuID + "', '" + menuName + "', " + price + ");");	//�Է¹��� ����Ʈ ������ �Ĵ� �߰�.
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

	public ArrayList<Reserve> getReserve() throws SQLException { // �����
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM �����");
		ArrayList<Reserve> reserve = new ArrayList<>();
		
		try {
			while (rs.next()) {
				String reserveID = rs.getString("�����id");
				String reserveName = rs.getString("������");
				String unit = rs.getString("����");

				reserve.add(new Reserve(reserveID, reserveName, unit));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
		return reserve;
	}

	public void addReserve(ArrayList<Reserve> reserve) throws SQLException {	//����� �߰�
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
				//	if (reserveID.equals(re.get(j).getReserveID()))	//�Է��� ReserveID�� �̹� ����.
				//		break;
				//	else if (reserveName.equals(re.get(j).getReserveName()))	//�Է��� ReserveName�� �̹� ����.
				//		break;
				//	else {	//���ο� Reserve�Ͻ� �߰�.
				//		stmt.executeUpdate(  "INSERT INTO �����(�����id, ������, ����) VALUES('" + reserveID + "', '" + reserveName + "', '" + unit + "');");	//�Է¹��� ����� �߰�.
				//		break;
				//	}
				//}
				stmt.executeUpdate( "INSERT INTO �����(�����id, ������, ����) VALUES('" + reserveID + "', '" + reserveName + "', '" + unit + "');");	//�Է¹��� ����� �߰�.
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
				String menuID = rs.getString("�Ĵ�id");
				String reserveID = rs.getString("�����id");
				double requires = rs.getDouble("�ҿ䷮");

				recipe.add(new Recipe(menuID, reserveID, requires));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
		return recipe;
	}

	public void addRecipe(ArrayList<Recipe> recipe) throws SQLException {	//recipe �߰�
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

				stmt.executeUpdate( "INSERT INTO recipe(�Ĵ�id, �����id, �ҿ䷮) VALUES('" + menuID + "', '" + reserveID + "', " + requires + ");");	//�Է¹��� recipe �߰�
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}
	
	public void updateRecipe(ArrayList<Recipe> recipe) throws SQLException {	//recipe ������Ʈ
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();
	
		try {
			for (int i = 0; i < recipe.size(); i++) {
				String menuID = recipe.get(i).getMenuID();
				String reserveID = recipe.get(i).getReserveID();
				double requires = recipe.get(i).getRequires();

				stmt.executeUpdate( "UPDATE recipe SET �ҿ䷮=" + requires + " WHERE �Ĵ�id='" + menuID + "' AND �����id='" + reserveID +"';");	//recipe ������Ʈ
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}

	public void removeRecipe(ArrayList<Recipe> recipe2) throws SQLException {		//recipe ����
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM recipe");
		ArrayList<Recipe> recipe = new ArrayList<>();

		//ArrayList<Recipe> recipe = getRecipe();	

		try {
			while (rs.next()) {
				String menuID = rs.getString("�Ĵ�id");
				String reserveID = rs.getString("�����id");
				double requires = rs.getDouble("�ҿ䷮");

				recipe.add(new Recipe(menuID, reserveID, requires));
			}

			for(int i = 0; i < recipe2.size(); i++) {
				for(int j = 0; j < recipe.size(); j++) {
					if(recipe2.get(i).getMenuID().equals(recipe.get(j).getMenuID()) && recipe2.get(i).getReserveID().equals(recipe.get(j).getReserveID())) {
						String menuID = recipe2.get(i).getMenuID();
						String reserveID = recipe2.get(i).getReserveID();
						System.out.println(menuID);
						System.out.println(reserveID);
						stmt.executeUpdate("DELETE FROM recipe WHERE �Ĵ�id='" + menuID + "' AND �����id='" + reserveID + "';");
					}
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}			

	public ArrayList<Menu> getMenu() throws SQLException { // �������ں�_�ǽ��޴�
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";;
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM �������ں�_�ǽ��޴�");
		ArrayList<Menu> menu = new ArrayList<Menu>();
		
		try {
			while (rs.next()) {
				String branch = rs.getString("�������");
				int date = rs.getInt("������");
				String menuID = rs.getString("�Ĵ�id");

				menu.add(new Menu(branch, date, menuID));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
		return menu;
	}

	public void addMenu(ArrayList<Menu> menu) throws SQLException {	//�ǽ��޴� �߰�
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		System.out.println("DB���� ����");
		Statement stmt = cone.createStatement();		

		try {
			for (int i = 0; i < menu.size(); i++) {
				String branch = menu.get(i).getBranch();
				int date = menu.get(i).getDate();
				String menuID = menu.get(i).getMenuID();

				stmt.executeUpdate("INSERT INTO �������ں�_�ǽ��޴�(�������, ������, �Ĵ�id) VALUES('" + branch + "', " + date + ", '" + menuID + "');");	//�ǽ��޴� �߰�.
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}

	public ArrayList<Applications> getApplications() throws SQLException { // �����_��û
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM �����_��û");
		ArrayList<Applications> application = new ArrayList<Applications>();
		
		ArrayList<ApplicationsList> al = getApplicationsList();

		Date dates=new Date();
		int nowDate=(1900+dates.getYear())*10000+(1+dates.getMonth())*100+(dates.getDay()+23);
		
		try {
			while (rs.next()) {
				int appNum = rs.getInt("��û��ȣ");
				String branch = rs.getString("�������");
				int date = rs.getInt("������");
				String studentName = rs.getString("��û�ڸ�");
				String phoneNum = rs.getString("��û����ȭ��ȣ");
				int depositDay = rs.getInt("�Ա���");
				int depositMoney = rs.getInt("�Աݾ�");
				int cancleDay = rs.getInt("�����");
				int refundDay = rs.getInt("ȯ����");
				int refundMoney = rs.getInt("ȯ�ұݾ�");

				if((date - 1) == nowDate) {
					if (depositMoney == 0) {
						stmt.executeUpdate("UPDATE �����_��û SET �����=" + nowDate + " WHERE ��û��ȣ=" + appNum + ";");	//(�Ա� x)������ �Ϸ� �� '�����_��û' �࿡ ����� �߰�.

						for(int i = 0; i < al.size(); i++) {
							if(al.get(i).getAppNum() == appNum) {
								stmt.executeUpdate("DELETE FROM �����_��û���� WHERE ��û��ȣ=" + appNum + ";");	//(�Ա� x)������ �Ϸ� �� '�����_��û����' �� ����.
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

	public ArrayList<Applications> getApplications2() throws SQLException { // �����_��û == �����_��û���� �����κи� ��ȯ.
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();
		Statement stmt2 = cone.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM �����_��û");
		ArrayList<Applications> application = new ArrayList<Applications>();	
		
		ResultSet rs2 = stmt2.executeQuery("SELECT * FROM �����_��û����");
		ArrayList<ApplicationsList> applicationList = new ArrayList<ApplicationsList>();

		ArrayList<Applications> application2 = new ArrayList<Applications>();	
		
		try {
			while (rs.next()) {
				int appNum = rs.getInt("��û��ȣ");
				String branch = rs.getString("�������");
				int date = rs.getInt("������");
				String studentName = rs.getString("��û�ڸ�");
				String phoneNum = rs.getString("��û����ȭ��ȣ");
				int depositDay = rs.getInt("�Ա���");
				int depositMoney = rs.getInt("�Աݾ�");
				int cancleDay = rs.getInt("�����");
				int refundDay = rs.getInt("ȯ����");
				int refundMoney = rs.getInt("ȯ�ұݾ�");

				application.add(new Applications(appNum, branch, date, studentName, phoneNum, depositDay, depositMoney, cancleDay, refundDay, refundMoney));
			}
			
			while (rs2.next()) {
				int appNum = rs2.getInt("��û��ȣ");
				String menuID = rs2.getString("�Ĵ�id");
				double amount = rs2.getDouble("��û����");
				double totalPrice = rs2.getDouble("������");

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
	
	public void addApplications(Applications application) throws SQLException {	//�����_��û �߰�.
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		System.out.println("DB���� ����");
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

			//for (int j = 0; j <m.size(); j++) {		//���������� �ִ� ������ ��û ����.
			//		if (m.get(j).getBranch().equals(branch) && m.get(j).getDate() == date)
			//			stmt.executeUpdate("INSERT INTO �����_��û(�������, ������, ��û�ڸ�, ��û����ȭ��ȣ) VALUES('" + branch + "', " + date + ", '" + studentName + "', '" + phoneNum + "');");
			//}
			stmt.executeUpdate("INSERT INTO �����_��û(�������, ������, ��û�ڸ�, ��û����ȭ��ȣ) VALUES('" + branch + "', " + date + ", '" + studentName + "', '" + phoneNum + "');");
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}

	public void cancelApplications(int[] cancel) throws SQLException {	//�����_��û ����� �� ȯ����, ȯ�Ҿ� ���.
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		System.out.println("DB���� ����");
		Statement stmt = cone.createStatement();

		ResultSet rs = stmt.executeQuery("SELECT * FROM �����_��û");
		ArrayList<Applications> application = new ArrayList<Applications>();

		try {
			while (rs.next()) {
				int appNum = rs.getInt("��û��ȣ");
				String branch = rs.getString("�������");
				int date = rs.getInt("������");
				String studentName = rs.getString("��û�ڸ�");
				String phoneNum = rs.getString("��û����ȭ��ȣ");
				int depositDay = rs.getInt("�Ա���");
				int depositMoney = rs.getInt("�Աݾ�");
				int cancleDay = rs.getInt("�����");
				int refundDay = rs.getInt("ȯ����");
				int refundMoney = rs.getInt("ȯ�ұݾ�");

				if ((cancel[1] + 1) == date)	//������ �Ϸ� �� �Ա��� �Ǿ� ���� �� ��� �Ұ�.
					continue;	

				application.add(new Applications(appNum, branch, date, studentName, phoneNum, depositDay, depositMoney, cancleDay, refundDay, refundMoney));
			}
			
			int depositMoney2 = 0;

			for (int i = 0; i < application.size(); i++) {
				if (application.get(i).getAppNum() == cancel[0])
					depositMoney2 = application.get(i).getDepositMoney();
			}

			if (depositMoney2 == 0)
				stmt.executeUpdate("UPDATE �����_��û SET �����=" + cancel[1] + " WHERE ��û��ȣ=" + cancel[0] + ";");
			else if (depositMoney2 != 0)
				stmt.executeUpdate("UPDATE �����_��û SET �����=" + cancel[1] + ", ȯ����=" + cancel[1] + ", ȯ�ұݾ�=" + depositMoney2 + " WHERE ��û��ȣ=" + cancel[0] + ";");
		} catch(SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}
	
	public void depositApplications(int appNum, int depositMoney, int depositDay) throws SQLException {	//�����_��û�� �Աݾ� ������Ʈ
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();

		stmt.executeUpdate("UPDATE �����_��û SET �Ա���=" + depositDay + ", �Աݾ�=" + depositMoney + " WHERE ��û��ȣ='" + appNum + "';");

		stmt.close();
	}

	public ArrayList<ApplicationsList> getApplicationsList() throws SQLException { // �����_��û����
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM �����_��û����");
		ArrayList<ApplicationsList> applicationList = new ArrayList<ApplicationsList>();
		
		try {
			while (rs.next()) {
				int appNum = rs.getInt("��û��ȣ");
				String menuID = rs.getString("�Ĵ�id");
				double amount = rs.getDouble("��û����");
				double totalPrice = rs.getDouble("������");

				applicationList.add(new ApplicationsList(appNum, menuID, amount, totalPrice));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
		return applicationList;
	}
	
	public void removeApplicationsList(int appNum) throws SQLException {	//�����_��û���� ���� (appNum)
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		System.out.println("DB���� ����");
		Statement stmt = cone.createStatement();
		ArrayList<ApplicationsList> applicationList = getApplicationsList();
		
		try {
			for(int i = 0; i < applicationList.size(); i++) {
				if(applicationList.get(i).getAppNum() == appNum)			
					stmt.executeUpdate("DELETE FROM �����_��û���� WHERE ��û��ȣ='" + appNum + "';");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}
	
	public void addApplicationsList(ApplicationsList applicationList) throws SQLException {	//�����_��û���� �߰�
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();

		ArrayList<Menu> m = getMenu();
		
		ResultSet rs=stmt.executeQuery("SELECT * FROM �����_��û");
		
		try {
			String menuID = applicationList.getMenuID();
			double amount = applicationList.getAmount();
			double totalPrice = applicationList.getTotalPrice();
			while(rs.next()) {
				if(rs.last() == true) {
					String branch =rs.getString("�������");
					int appNum = rs.getInt("��û��ȣ");
					int date = rs.getInt("������");

					for (int i = 0; i < m.size(); i++) {
						if(m.get(i).getBranch().equals(branch) && m.get(i).getDate() == date && m.get(i).getMenuID().equals(menuID)) {	//���������� �����ϴ� menuID�� �߰� ����.
							stmt.executeUpdate("INSERT INTO �����_��û����(��û��ȣ, �Ĵ�id, ��û����, ������) VALUES('" + appNum + "', '" + menuID + "', " + amount + ", " + totalPrice + ");");
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

	public ArrayList<Teacher> getTeacher() throws SQLException { // ����
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM ����");
		ArrayList<Teacher> teacher = new ArrayList<Teacher>();
		
		try {
			while (rs.next()) {
				String teacherID = rs.getString("����id");
				String teacherName = rs.getString("�����");
				String accNum = rs.getString("���¹�ȣ");

				teacher.add(new Teacher(teacherID, teacherName, accNum));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
		return teacher;
	}

	public ArrayList<Schedule> getSchedule() throws SQLException { // ��������
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();
		ResultSet rs = stmt.executeQuery("SELECT * FROM ��������");
		ArrayList<Schedule> schedule = new ArrayList<Schedule>();
		
		try {
			while (rs.next()) {
				String branch = rs.getString("�������");
				int year = rs.getInt("�⵵");
				String teacherID = rs.getString("����id");
				String day = rs.getString("����");
				int from = rs.getInt("���۽ð�");
				int to = rs.getInt("���ð�");

				schedule.add(new Schedule(branch, year, teacherID, day, from, to));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
		return schedule;
	}

	public void addSchedule(ArrayList<Schedule> schedule) throws SQLException {	//�������� �߰�
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

				stmt.executeUpdate("INSERT INTO ��������(�������, �⵵, ����id, ����, ���۽ð�, ���ð�) VALUES('" + branch + "', '" + year + "', '" + teacherID + "', '" + day + "', " + from + ", " + to + ");");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}
	
	public void updateSchedule(ArrayList<Schedule> schedule) throws SQLException {	//�������� ����
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

				stmt.executeUpdate("UPDATE �������� SET ����='" + day + "', ���۽ð�=" + from + ", ���ð�=" + to + " WHERE �������='" + branch + "' AND �⵵='" + year + "' AND ����id='" + teacherID + "';");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			stmt.close();
		}
	}

	public void removeSchedule(ArrayList<Schedule> schedule2) throws SQLException {		//�������� ����
		String url = "jdbc:mysql://localhost:3306/project?serverTimezone=Asia/Seoul";
		Connection cone = DriverManager.getConnection(url, "root", "l643545h!!");
		Statement stmt = cone.createStatement();
		
		ResultSet rs = stmt.executeQuery("SELECT * FROM ��������");
		ArrayList<Schedule> schedule = new ArrayList<Schedule>();

		try {
			while (rs.next()) {
				String branch = rs.getString("�������");
				int year = rs.getInt("�⵵");
				String teacherID = rs.getString("����id");
				String day = rs.getString("����");
				int from = rs.getInt("���۽ð�");
				int to = rs.getInt("���ð�");

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

						stmt.executeUpdate("DELETE FROM �������� WHERE �������='" + branch + "' AND �⵵=" + year + " AND ����id='" + teacherID + "';");
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