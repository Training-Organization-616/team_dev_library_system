package la.servlet;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import la.bean.OverdueBean;
import la.dao.DAOException;
import la.dao.OverdueDAO;


@WebServlet("/OverdueServlet")
public class OverdueServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	request.setCharacterEncoding("UTF-8");
    	
        try {
        	//actionのリクエストパラメータを取得
            String action = request.getParameter("action");
            
            
            if(action.equals("top") || action == null) {
            	//actionが "top" or "なし" の時、top画面に遷移
                gotoPage(request, response, "/top/top.jsp");
                
                
            }else if(action.equals("return")){
            	//actionの値が「return」の場合
            	//お問い合わせ登録、編集画面から、お問い合わせ対応画面に戻る
            	gotoPage(request , response , "/other/other_top.jsp");
            	
            }else if(action.equals("return_add")){
            	//actionの値が「return_add」の場合
            	//お問い合わせ登録、編集画面から、お問い合わせ対応画面に戻る
            	gotoPage(request , response , "/other/other_overdue_top.jsp");
            	
            }else if(action.equals("list")) {
            	//actionの値が「list」の場合
            	//延滞者一覧を取得する
            	
            	OverdueDAO dao = new OverdueDAO();
            	
            	dao.setReminder();
            	
            	List<OverdueBean> list10days = new ArrayList<OverdueBean>();
            	List<OverdueBean> list30days = new ArrayList<OverdueBean>();
            	
            	list10days = dao.findOverdue10Days();
            	list30days = dao.findOverdue30Days();
            	
            	if(list10days == null && list30days == null) {
            		//検索結果が存在しなかった場合
            		request.setAttribute("message", "延滞者はいません");
            		gotoPage(request , response , "/other/other_overdue_top.jsp");
            		
            	}else {
            		//検索結果が存在した場合
            		HttpSession session10days = request.getSession();
            		HttpSession session30days = request.getSession();
            		
            		session10days.setAttribute("overdue10days", list10days);
            		session30days.setAttribute("overdue30days", list30days);
            		gotoPage(request , response , "/other/other_overdue_top.jsp");
            	}
            	
        	}else if(action.equals("edit_page")) {
            	//actionの値が「edit_page」の場合
            	//延滞者対応編集画面に遷移する
        		String lendId = request.getParameter("lend_id");
        		String userId = request.getParameter("user_id");
            	String name = request.getParameter("name");
            	String email = request.getParameter("email");
            	String tel = request.getParameter("tel");
            	String bookId = request.getParameter("book_id");
            	String title = request.getParameter("title");
            	String strDueDate = request.getParameter("due_date");
            	String firstReminder = request.getParameter("first_reminder");
            	String secondReminder = request.getParameter("second_reminder");
            	String memo = request.getParameter("memo");
            	
            	//年月日システム用
            	strDueDate = makeDate(strDueDate);
            	
            	//現在の日付と返却期限を計算する
            	
            	//現在の日付を取得
            	LocalDate dateNow = LocalDate.now();
            	
            	//返却期限をLocalDate型に変換
            	LocalDate dueDate = LocalDate.parse(strDueDate , DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            	
            	//日にちの差を取得
            	long daysBetween = ChronoUnit.DAYS.between(dueDate , dateNow);
            	
            	//年月日表示用
            	OverdueDAO dao = new OverdueDAO();
            	strDueDate = dao.makeDate(strDueDate);
            	
            	//延滞者更新完了画面に渡す値をbeanに保存
        		OverdueBean bean = new OverdueBean
        				(Integer.parseInt(lendId) , Integer.parseInt(userId) , name ,
        						email , tel , Integer.parseInt(bookId) , title ,
        						strDueDate , Integer.parseInt(firstReminder) , Integer.parseInt(secondReminder) , memo);
        		
        		//リクエストスコープで送る
        		request.setAttribute("overdue", bean);
            	
            	if(daysBetween >= 30) {
            		
            		//リクエストスコープで送る
            		request.setAttribute("month", secondReminder);
            		
            	}else if(daysBetween >= 10){
            		
            		//リクエストスコープで送る
            		request.setAttribute("day", firstReminder);
            	}
            	
            	gotoPage(request , response , "/other/other_overdue_edit.jsp");
            	
            }else if(action.equals("update")) {
            	//actionの値が「update」の場合
            	//延滞者対応を更新する
        		String lendId = request.getParameter("lend_id");
        		String userId = request.getParameter("user_id");
            	String name = request.getParameter("name");
            	String email = request.getParameter("email");
            	String tel = request.getParameter("tel");
            	String bookId = request.getParameter("book_id");
            	String title = request.getParameter("title");
            	String strDueDate = request.getParameter("due_date");
            	String firstReminder = request.getParameter("first_reminder");
            	String secondReminder = request.getParameter("second_reminder");
            	String memo = request.getParameter("memo");
            	
            	
        		OverdueDAO dao = new OverdueDAO();
        		
        		//延滞者情報を入力内容で更新する
        		dao.updateOverdue(Integer.parseInt(lendId) , Integer.parseInt(firstReminder) ,
        				Integer.parseInt(secondReminder) , memo);
        		
        		//更新内容をセッションに反映
        		List<OverdueBean> list10days = new ArrayList<OverdueBean>();
            	List<OverdueBean> list30days = new ArrayList<OverdueBean>();
            	
            	list10days = dao.findOverdue10Days();
            	list30days = dao.findOverdue30Days();
            	
            	HttpSession session10days = request.getSession();
        		HttpSession session30days = request.getSession();
        		
        		session10days.setAttribute("overdue10days", list10days);
        		session30days.setAttribute("overdue30days", list30days);
        		
        		//年月日システム用
        		strDueDate = makeDate(strDueDate);
        		
        		//現在の日付と返却期限を計算する
            	
            	//現在の日付を取得
            	LocalDate dateNow = LocalDate.now();
            	
            	//返却期限をLocalDate型に変換
            	LocalDate dueDate = LocalDate.parse(strDueDate , DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            	
            	//日にちの差を取得
            	long daysBetween = ChronoUnit.DAYS.between(dueDate , dateNow);
            	
            	//年月日表示用
            	strDueDate = dao.makeDate(strDueDate);
            	
            	//延滞者更新完了画面に渡す値をbeanに保存
        		OverdueBean bean = new OverdueBean
        				(Integer.parseInt(lendId) , Integer.parseInt(userId) , name ,
        						email , tel , Integer.parseInt(bookId) , title ,
        						strDueDate , Integer.parseInt(firstReminder) , Integer.parseInt(secondReminder) , memo);
        		
        		//リクエストスコープで送る
        		request.setAttribute("overdue", bean);
            	
            	if(daysBetween >= 30) {
            		
            		//リクエストスコープで送る
            		request.setAttribute("month", secondReminder);
            		
            	}else if(daysBetween >= 10){
            		
            		//リクエストスコープで送る
            		request.setAttribute("day", firstReminder);
            	}
            	
            	gotoPage(request , response , "/other/other_overdue_edit_complete.jsp");
            }
        } catch (DAOException e) {
        	
        	//DAOのデータベース処理が失敗（エラー）となった場合
            e.printStackTrace();
            request.setAttribute("message", "内部エラーが発生しました。");
            gotoPage(request, response, "/errInternal.jsp");
        }
    }

    //JSPのフォワードを何度も書かなくて良いようにメソッドにして使用する
    private void gotoPage(HttpServletRequest request,
            HttpServletResponse response, String page) throws ServletException,
            IOException {
    	
        RequestDispatcher rd = request.getRequestDispatcher(page);
        rd.forward(request, response);
    }
    
    //
    
    //有効なタイトルが入力されたのかを確認するメソッド
    public boolean isCheckTitle(String title) {
    	
    	boolean flag = false;
    	
    	if(title.length() > 50) {
    		
    		flag = true;
    	}
    	
    	return flag;
    }
    
  //年月日表示用から-区切りに戻しメソッド
    public String makeDate(String strDate) {
    	
    	strDate = strDate.replace("年", "-");
		strDate = strDate.replace("月", "-");
		strDate = strDate.replace("日", "");
		
		return strDate;
    }

    //Stringの日付をsql.dateに変換するメソッド
    public Date setDate(String strDate) {
    	
    	if(strDate.contains("年")) {
    		
    		strDate = strDate.replace("年", "-");
    		strDate = strDate.replace("月", "-");
    		strDate = strDate.replace("日", "");
    	}
    	
    	Date date = Date.valueOf(strDate);
    	
    	return date;
    }
    
    //HTTPリクエストのPOSTリクエストが送信された場合実行される
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}