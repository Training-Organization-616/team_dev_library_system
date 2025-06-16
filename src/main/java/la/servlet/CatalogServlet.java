//package la.servlet;
//
//import java.io.IOException;
//import java.sql.Date;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import la.bean.CategoryBean;
//
//
//@WebServlet("/CatalogServlet")
//public class CatalogServlet extends HttpServlet {
//
//    protected void doGet(HttpServletRequest request,
//            HttpServletResponse response) throws ServletException, IOException {
//    	
//    	request.setCharacterEncoding("UTF-8");
//    	
//        try {
//        	//actionのリクエストパラメータを取得
//            String action = request.getParameter("action");
//            
//            
//            if(action.equals("top") || action == null) {
//            	//actionが "top" か なしの時、top画面に遷移
//                gotoPage(request, response, "/top.jsp");
//                
//                
//            }else if (action.equals("returnAdd")) {                
//            	//actionの値が「returnAdd」の場合
//            	//新規資料登録画面から、資料メニューに戻る
//                String category = request.getParameter("category");
//                String strName = request.getParameter("name");
//                String sort = request.getParameter("sort");
//                String progress = request.getParameter("progress");
//                int name = 0;
//                //int page = Integer.parseInt(request.getParameter("page"));
//                
//                if(strName == null || strName.length() == 0) {
//                	
//                	request.setAttribute("message" , "ログインしてください");
//                	gotoPage(request, response, "/login.jsp");
//                	return;
//                	
//                }else {
//                	name = Integer.parseInt(strName);
//                }
//                
//                //DAOを使用するためにDAOオブジェクトの生成
//                TaskDAO dao = new TaskDAO();
//                List<TaskBean> list = new ArrayList<TaskBean>();
//                int count = 0;
//                
//                if(category.equals("all")) {
//                	
//                    list = dao.findAllTask(name , sort , Integer.parseInt(progress));
//                    count = dao.countByCategory(name , progress);
//                    
//                }else {
//                	//生成したDAOオブジェクト使用して「findByCategory」メソッドを実行
//                    //引数にはパラメータのカテゴリコードを使用
//                    //List<TaskBean> list = dao.findByCategory(categoryCode , page);
//                    list = dao.findByCategory(Integer.parseInt(category) , name , sort , Integer.parseInt(progress));
//                    
//                  //生成したDAOオブジェクト使用して「countByCategory」メソッドを実行
//                    //引数にはパラメータのカテゴリコードを使用
//                	count = dao.countByCategory(Integer.parseInt(category) , name , progress);
//                }
//                
//                request.setAttribute("category", category);
//            	
//                // 実行結果のListをリクエストスコープに入れて
//                request.setAttribute("tasks", list);
//                
//                // 実行結果のintをリクエストスコープに入れて
//            	request.setAttribute("count", count);
//                
//                //JSPへフォーワードする
//                gotoPage(request, response, "/list.jsp");
//                
//            }else if(action.equals("add")) {
//            	
//            	
//            	//actionの値が「add」の場合
//            	String user = request.getParameter("name");
//            	String category = request.getParameter("category");
//            	String title = request.getParameter("title");
//            	String strDate = request.getParameter("date");
//            	String progress = request.getParameter("progress");
//            	String memo = request.getParameter("memo");
//            	
//            	request.setAttribute("category" , category);
//            	request.setAttribute("progress" , progress);
//            	request.setAttribute("memo" , memo);
//            	
//            	//DAOを使用するためにDAOオブジェクトの生成
//            	TaskDAO dao = new TaskDAO();
//            	
//				if (user == null || user.length() == 0) {
//
//					request.setAttribute("message", "ログインしてください");
//					gotoPage(request, response, "/login.jsp");
//					return;
//
//				}
//            	
//            	if((category != null && category.length() != 0)
//            			&&(user != null && user.length() != 0)
//            			&&(title != null && title.length() != 0)
//            			&&(strDate != null && strDate.length() != 0)
//            			&&(progress != null && progress.length() != 0)) {
//            		
//            		boolean titleFlag = isCheckTitle(title);
//            		
//            		if(!titleFlag) {
//            			
//            			request.setAttribute("message" , "タイトルは10文字以下で入力して下さい");
//            			gotoPage(request, response, "/addTask.jsp");
//						return;
//            		}
//            		
//            		request.setAttribute("title" , title);
//            		
//					String newStrDate = isCheckDate(strDate);
//					String newDate = checkDate(strDate);
//					
//					Date date = null;
//					
//					if(newStrDate == null && newDate == null) {
//						
//						request.setAttribute("message", "有効な期日を入力して下さい");
//						gotoPage(request, response, "/addTask.jsp");
//						return;
//						
//					}else if(newDate == null) {
//						
//						date = setDate(newStrDate);
//						
//					}else if(newStrDate == null) {
//						
//						date = setDate(newDate);
//					}
//            		
//            		dao.addTask(Integer.parseInt(category) , Integer.parseInt(user) , 
//            				title , date , Integer.parseInt(progress) , memo);
//            		
//            		List<TaskBean> list = dao.findAllTask(Integer.parseInt(user));
//            		int count = dao.countByCategory(Integer.parseInt(user));
//            		
//            		// 実行結果のbeanをリクエストスコープに入れて
//                	request.setAttribute("tasks", list);
//                	request.setAttribute("count" , count);
//                	
//                	//JSPへフォーワードする
//                	gotoPage(request , response , "/list.jsp");
//                	
//            	}else if(user == null || user.length() == 0){
//            		request.setAttribute("message" , "ログインしてください");
//            		//JSPへフォーワードする
//                	gotoPage(request , response , "/login.jsp");
//                	
//            	}else if(category == null || category.length() == 0){
//            		request.setAttribute("message" , "カテゴリーを選択してください");
//            		request.setAttribute("title" , title);
//            		request.setAttribute("date" , strDate);
//            		//JSPへフォーワードする
//                	gotoPage(request , response , "/addTask.jsp");
//                	
//            	}else if(title == null || title.length() == 0){
//            		request.setAttribute("message" , "タイトルを入力して下さい");
//            		request.setAttribute("date" , strDate);
//            		//JSPへフォーワードする
//                	gotoPage(request , response , "/addTask.jsp");
//                	
//            	}else if(strDate == null || strDate.length() == 0){
//            		request.setAttribute("message" , "期限を入力して下さい");
//            		request.setAttribute("title" , title);
//            		//JSPへフォーワードする
//                	gotoPage(request , response , "/addTask.jsp");
//                	
//            	}else if(progress == null || progress.length() == 0){
//            		request.setAttribute("message" , "進捗状況を選択してください");
//            		request.setAttribute("title" , title);
//            		request.setAttribute("date" , strDate);
//            		//JSPへフォーワードする
//                	gotoPage(request , response , "/addTask.jsp");
//                	
//            	}
//            	
//            	            	
//            }else if(action.equals("editpage")) {
//            	
//            	//actionの値が「editpage」の場合
//            	String taskId = request.getParameter("task");
//            	String categoryId = request.getParameter("categoryId");
//            	String title = request.getParameter("title");
//            	String date = request.getParameter("date");
//            	String progress = request.getParameter("progress");
//            	String memo = request.getParameter("memo");
//            	
//            	request.setAttribute("taskId" , taskId);
//            	request.setAttribute("categoryId" , categoryId);
//            	request.setAttribute("title" , title);
//            	request.setAttribute("date" , date);
//            	request.setAttribute("progress" , progress);
//            	request.setAttribute("memo" , memo);
//            	
//            	gotoPage(request , response , "/editTask.jsp");
//            	
//            }else if(action.equals("edit")) {
//            	
//            	
//            	//actionの値が「edit」の場合
//            	int task = Integer.parseInt(request.getParameter("task"));
//            	String user = request.getParameter("name");
//            	String category = request.getParameter("category");
//            	String title = request.getParameter("title");
//            	String strDate = request.getParameter("date");
//            	String progress = request.getParameter("progress");
//            	String memo = request.getParameter("memo");
//            	
//            	request.setAttribute("taskId" , task);
//            	request.setAttribute("categoryId" , category);
//            	request.setAttribute("progress" , progress);
//            	request.setAttribute("memo" , memo);
//            	
//            	//DAOを使用するためにDAOオブジェクトの生成
//            	TaskDAO dao = new TaskDAO();
//            	
//            	if (user == null || user.length() == 0) {
//
//					request.setAttribute("message", "ログインしてください");
//					gotoPage(request, response, "/login.jsp");
//					return;
//
//				}
//            	
//            	if((category != null && category.length() != 0)
//            			&&(user != null && user.length() != 0)
//            			&&(title != null && title.length() != 0)
//            			&&(strDate != null && strDate.length() != 0)
//            			&&(progress != null && progress.length() != 0)) {
//            		
//            		boolean titleFlag = isCheckTitle(title);
//            		
//            		if(!titleFlag) {
//            			
//            			request.setAttribute("message" , "タイトルは10文字以下で入力して下さい");
//            			gotoPage(request, response, "/editTask.jsp");
//						return;
//            		}
//            		
//            		request.setAttribute("title" , title);
//            		
//            		String newDateString = isCheckDate(strDate);
//            		String newDate = checkDate(strDate);
//					
//            		Date date = null;
//					
//					if(newDateString == null && newDate == null) {
//						
//						request.setAttribute("message", "有効な期日を入力して下さい");
//						gotoPage(request, response, "/addTask.jsp");
//						return;
//						
//					}else if(newDate == null) {
//						
//						date = setDate(newDateString);
//						
//					}else if(newDateString == null) {
//						
//						date = setDate(newDate);
//					}
//					
//            		dao.updateTask(task , Integer.parseInt(category) , Integer.parseInt(user) , 
//            				title , date , Integer.parseInt(progress) , memo);
//            		
//            		List<TaskBean> list = dao.findAllTask(Integer.parseInt(user));
//            		int count = dao.countByCategory(Integer.parseInt(user));
//            		
//            		// 実行結果のbeanをリクエストスコープに入れて
//                	request.setAttribute("tasks", list);  
//                	request.setAttribute("count" , count);
//                	
//                	//JSPへフォーワードする
//                	gotoPage(request , response , "/list.jsp");
//                	
//            	}else if(user == null || user.length() == 0){
//            		request.setAttribute("message" , "ログインしてください");
//            		//JSPへフォーワードする
//                	gotoPage(request , response , "/login.jsp");
//                	
//            	}else if(category == null || category.length() == 0){
//            		request.setAttribute("message" , "カテゴリーを選択してください");
//            		//JSPへフォーワードする
//                	gotoPage(request , response , "/editTask.jsp");
//                	
//            	}else if(title == null || title.length() == 0){
//            		request.setAttribute("message" , "タイトルを入力して下さい");
//            		//JSPへフォーワードする
//                	gotoPage(request , response , "/editTask.jsp");
//                	
//            	}else if(strDate == null || strDate.length() == 0){
//            		request.setAttribute("message" , "期限を入力して下さい");
//            		//JSPへフォーワードする
//                	gotoPage(request , response , "/editTask.jsp");
//                	
//            	}else if(progress == null || progress.length() == 0){
//            		request.setAttribute("message" , "進捗状況を選択してください");
//            		//JSPへフォーワードする
//                	gotoPage(request , response , "/editTask.jsp");
//                	
//            	}
//            	
//            	            	
//            }else if(action.equals("delete")) {
//            	
//            	//actionの値が「delete」の場合
//            	int task = Integer.parseInt(request.getParameter("task"));
//            	String user = request.getParameter("name");
//            	
//            	if(user == null || user.length() == 0){
//            		request.setAttribute("message" , "ログインしてください");
//            		//JSPへフォーワードする
//                	gotoPage(request , response , "/login.jsp");
//                	
//            	}else {
//            		
//            		//DAOを使用するためにDAOオブジェクトの生成
//                	TaskDAO dao = new TaskDAO();
//                	
//                	dao.deleteTask(task , Integer.parseInt(user));
//                	
//                	List<TaskBean> list = dao.findAllTask(Integer.parseInt(user));
//            		int count = dao.countByCategory(Integer.parseInt(user));
//            		
//            		// 実行結果のbeanをリクエストスコープに入れて
//                	request.setAttribute("tasks", list);  
//                	request.setAttribute("count" , count);
//                	
//                	//JSPへフォーワードする
//                	gotoPage(request , response , "/list.jsp");
//            	}
//            	
//            }else if(action.equals("detail")) {
//            	
//            	String user = request.getParameter("name");
//            	if (user == null || user.length() == 0) {
//
//					request.setAttribute("message", "ログインしてください");
//					gotoPage(request, response, "/login.jsp");
//					return;
//
//				}
//            	
//            	//actionの値が「detail」の場合
//            	String taskId = request.getParameter("task");
//            	String categoryId = request.getParameter("categoryId");
//            	String title = request.getParameter("title");
//            	String date = request.getParameter("date");
//            	String progress = request.getParameter("progress");
//            	String memo = request.getParameter("memo");
//            	
//            	request.setAttribute("taskId" , taskId);
//            	request.setAttribute("categoryId" , categoryId);
//            	request.setAttribute("title" , title);
//            	request.setAttribute("date" , date);
//            	request.setAttribute("progress" , progress);
//            	request.setAttribute("memo" , memo);
//            	
//            	gotoPage(request , response , "/task.jsp");
//            	
//            }else {
//            	//パラメータのactionがtopでもlistでもない場合
//                request.setAttribute("message", "正しく操作してください。");
//                gotoPage(request, response, "/errInternal.jsp");
//            }
//        } catch (DAOException e) {
//        	
//        	//DAOのデータベース処理が失敗（エラー）となった場合
//            e.printStackTrace();
//            request.setAttribute("message", "内部エラーが発生しました。");
//            gotoPage(request, response, "/errInternal.jsp");
//        }
//    }
//
//    //JSPのフォワードを何度も書かなくて良いようにメソッドにして使用する
//    private void gotoPage(HttpServletRequest request,
//            HttpServletResponse response, String page) throws ServletException,
//            IOException {
//    	
//        RequestDispatcher rd = request.getRequestDispatcher(page);
//        rd.forward(request, response);
//    }
//
//    //サーバー起動時にサーブレットクラスのインスタンス（オブジェクト）が自動生成される
//    //自動生成されるときに「init」メソッドが内部で自動的に実行される
//    public void init() throws ServletException {
//        try {
//            // カテゴリ一覧は最初にアプリケーションスコープへ入れる
//        	//DAOオブジェクトの生成
//            TaskDAO dao = new TaskDAO();
//            
//            //DAOオブジェクトを使用して「findAllCategory」メソッドを実行           
//            List<CategoryBean> list = dao.findAllCategory();
//            
//            //カテゴリ情報の「list」はほとんどの画面で使用するため
//            //アプリケーションスコープにデータを保存
//            ServletContext application = getServletContext();
//            application.setAttribute("categories", list);
//            
//            //以下のコードは上2行の省略形
//            //getServletContext().setAttribute("categories", list);
//            
//        } catch (DAOException e) {
//            e.printStackTrace();
//            throw new ServletException();
//        }
//    }
//    
//    public boolean isCheckTitle(String title) {
//    	
//    	boolean flag = false;
//    	
//    	if(title.length() <= 10) {
//    		
//    		flag = true;
//    	}
//    	
//    	return flag;
//    }
//    
//    public String isCheckDate(String strDate) {
//    	
//    	String newDate = "";
//    	
//    	if(strDate.length() > 10 || strDate.length() < 6) {
//    		
//    		newDate = null;
//    		return newDate;
//    	}
//    	
//    	
//    	
//    	int year = 0;
//    	int month = 0;
//    	int day = 0;
//    	
//    	if(strDate.contains("-")) {
//    		
//    		List<String> date = Arrays.asList(strDate.split("-"));
//    		
//    		if(date.size() == 3) {
//    			
//    			year = Integer.parseInt(date.get(0));
//        		month = Integer.parseInt(date.get(1));
//        		day = Integer.parseInt(date.get(2));
//        		
//    		}else {
//    			
//    			newDate = null;
//    			return newDate;
//    		}
//    		
//    	}else if(strDate.contains("/")) {
//    		
//    		List<String> date = Arrays.asList(strDate.split("/"));
//
//    		if(date.size() == 3) {
//    			
//    			year = Integer.parseInt(date.get(0));
//        		month = Integer.parseInt(date.get(1));
//        		day = Integer.parseInt(date.get(2));
//        		
//    		}else {
//    			
//    			newDate = null;
//    			return newDate;
//    		}
//    		
//    	}else if(strDate.contains(".")) {
//    		
//    		List<String> date = Arrays.asList(strDate.split("\\."));
//
//    		if(date.size() == 3) {
//    			
//    			year = Integer.parseInt(date.get(0));
//        		month = Integer.parseInt(date.get(1));
//        		day = Integer.parseInt(date.get(2));
//        		
//    		}else {
//    			
//    			newDate = null;
//    			return newDate;
//    		}
//    		
//    	}else if(strDate.contains(",")) {
//    		
//    		List<String> date = Arrays.asList(strDate.split(","));
//
//    		if(date.size() == 3) {
//    			
//    			year = Integer.parseInt(date.get(0));
//        		month = Integer.parseInt(date.get(1));
//        		day = Integer.parseInt(date.get(2));
//        		
//    		}else {
//    			
//    			newDate = null;
//    			return newDate;
//    		}
//    		
//    	}
//    	
//    	if(year < 2025 || year > 9999) {
//    		
//    		newDate = null;
//    		return newDate;
//    	}
//    	
//    	if(month < 1 || month > 12) {
//    		
//    		newDate = null;
//    		return newDate;
//    	}
//    	
//    	if(day < 1 || day > 31) {
//    		
//    		newDate = null;
//    		return newDate;
//    	}
//    	
//    	newDate = year + "-" + month + "-" + day;
//    	
//    	return newDate;
//    }
//    
//    public String checkDate(String strDate) {
//    	
//    	String newDate = "";
//    	
//    	if(strDate.length() > 10 || strDate.length() < 6) {
//    		
//    		newDate = null;
//    		return newDate;
//    	}
//    	
//    	
//    	
//    	int year = 0;
//    	int month = 0;
//    	int day = 0;
//    	
//    	for(int i = 0 ; i < strDate.length() ; i++){
//    		
//    		if(Character.isDigit(strDate.charAt(i))) {
//    		
//    			continue;
//    			
//    		}else {
//    				
//				newDate = null;
//				return newDate;
//    		}
//    	}
//    		
//		year = Integer.parseInt(strDate.substring(0 , 4));
//		String monthDay = strDate.substring(4);
//		
//		if(monthDay.length() == 2) {
//			
//			month = Integer.parseInt(monthDay.substring(0 , 1));
//			day = Integer.parseInt(monthDay.substring(1));
//			
//		}else if(monthDay.length() == 3) {
//			
//			month = Integer.parseInt(monthDay.substring(0 , 2));
//			
//			if(month >= 1 && month <= 12) {
//	    		
//				day = Integer.parseInt(monthDay.substring(2));
//	    		
//	    	}else {
//	    		
//	    		month = Integer.parseInt(monthDay.substring(0 , 1));
//	    		day = Integer.parseInt(monthDay.substring(1));
//	    	}
//			
//		}else if(monthDay.length() == 4) {
//			
//			month = Integer.parseInt(monthDay.substring(0 , 2));
//			day = Integer.parseInt(monthDay.substring(2));
//		}
//    		
//		if(year < 2025 || year > 9999) {
//    		
//    		newDate = null;
//    		return newDate;
//    	}
//    	
//    	if(month < 1 || month > 12) {
//    		
//    		newDate = null;
//    		return newDate;
//    	}
//    	
//    	if(day < 1 || day > 31) {
//    		
//    		newDate = null;
//    		return newDate;
//    	}
//    	
//    	newDate = year + "-" + month + "-" + day;
//    	
//    	return newDate;
//    }
//    
////    public Date setDate(String strDate) {
////    	
////    	String dateFormat = "";
////    	
////    	if(strDate.contains("/")) {
////    		
////    		dateFormat = strDate.replace("/" , "-");
////    		
////    	}else if(strDate.contains(",")) {
////    		
////    		dateFormat = strDate.replace("," , "-");
////    		
////    	}else {
////    		
////    		dateFormat = strDate;
////    	}
////    	
////    	Date date = Date.valueOf(dateFormat);
////    	
////    	return date;
////    }
//    
//    //HTTPリクエストのPOSTリクエストが送信された場合実行される
//    protected void doPost(HttpServletRequest request,
//            HttpServletResponse response) throws ServletException, IOException {
//        doGet(request, response);
//    }
//}