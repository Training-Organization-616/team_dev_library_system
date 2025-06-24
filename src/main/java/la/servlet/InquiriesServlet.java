package la.servlet;

import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import la.bean.InquiriesBean;
import la.dao.DAOException;
import la.dao.InquiriesDAO;


@WebServlet("/InquiriesServlet")
public class InquiriesServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
    	
    	request.setCharacterEncoding("UTF-8");
    	
        try {
        	//actionのリクエストパラメータを取得
            String action = request.getParameter("action");
            
            
            if(action.equals("top") || action == null) {
            	//actionが "top" or "なし" の時、top画面に遷移
                gotoPage(request, response, "/top/top.jsp");
                
                
            }else if(action.equals("return_add")){
            	//actionの値が「return」の場合
            	//お問い合わせ登録、編集画面から、お問い合わせ対応画面に戻る
            	gotoPage(request , response , "/other/other_inquiries_top.jsp");
            	
            }else if(action.equals("list")) {
            	//actionの値が「list」の場合
            	//未解決の問合せを取得する
            	
            	InquiriesDAO dao = new InquiriesDAO();
            	
            	List<InquiriesBean> list = new ArrayList<InquiriesBean>();
            	
            	list = dao.listInquiries();
            	
            	if(list == null) {
            		//検索結果が存在しなかった場合
            		request.setAttribute("message", "お問い合わせがありません");
            		
            	}else {
            		//検索結果が存在した場合
            		HttpSession session = request.getSession();
            		
            		session.setAttribute("inquiries", list);
            	}
            	
            	gotoPage(request , response , "/other/other_inquiries_top.jsp");
            	
        	}else if(action.equals("add")) {
            	//actionの値が「add」の場合
            	//問合せを追加する
            	
            	//パラメータ取得
            	String strReceptionDate = request.getParameter("reception_date");
            	String title = request.getParameter("title");
            	String contents = request.getParameter("contents");
            	String handling = request.getParameter("handling");
            	String memo = request.getParameter("memo");
            	
            	if((strReceptionDate != null && strReceptionDate.length() != 0)
            			&& (title != null && title.length() != 0)
            			&& (contents != null && contents.length() != 0)
            			&& (handling != null && handling.length() != 0)) {
            		
            		//問合せを追加するときに
            		//必要事項が全て入力されているとき
            		
            		//入力された問合せタイトルが正しいかを調べる
            		boolean flagTitle = isCheckTitle(title);
            		
            		if(flagTitle) {
            			//入力が正しくない場合
            			request.setAttribute("message", "タイトルは50文字以内で入力してください");
            			
            			//request.setAttribute("code", code);
            			request.setAttribute("receptionDate", strReceptionDate);
            			request.setAttribute("contents", contents);
            			request.setAttribute("handling", handling);
            			request.setAttribute("memo", memo);
                		gotoPage(request , response , "/other/other_inquiries_add.jsp");
                		return;
            		}
            		
            		//受付日の型をstringからsql.dateに変換する
            		Date receptionDate = setDate(strReceptionDate);
            		
            		InquiriesDAO dao = new InquiriesDAO();
            		
            		//お問い合わせListに入力内容を追加する
            		dao.addInquiries(receptionDate , title , contents , Integer.parseInt(handling) , memo);
            		
            		//追加されたListをセッションに反映
            		List<InquiriesBean> list = new ArrayList<InquiriesBean>();
                	
                	list = dao.listInquiries();
                	
                	HttpSession session = request.getSession();
            		
            		session.setAttribute("inquiries", list);
            		
            		//追加完了画面表示用
            		InquiriesBean bean = new InquiriesBean(strReceptionDate , title , contents ,
            				Integer.parseInt(handling) , memo);
            		
            		request.setAttribute("inquiry" , bean);
            		gotoPage(request , response , "/other/other_inquiries_add_complete.jsp");
            		
            	}else {
            		//資料を追加するときに
            		//必要事項が入力されていないとき
            		request.setAttribute("message", "備考以外は入力必須です");
            		request.setAttribute("receptionDate", strReceptionDate);
            		request.setAttribute("title", title);
        			request.setAttribute("contents", contents);
        			request.setAttribute("handing", handling);
        			request.setAttribute("memo", memo);
            		gotoPage(request , response , "/other/other_inquiries_add.jsp");
            	}
            	
            }else if(action.equals("edit_page")) {
            	//actionの値が「edit_page」の場合
            	//お問い合わせ編集画面に遷移する
        		String inquiriesId = request.getParameter("inquiries_id");
        		String strReceptionDate = request.getParameter("reception_date");
            	String title = request.getParameter("title");
            	String contents = request.getParameter("contents");
            	String handling = request.getParameter("handling");
            	String memo = request.getParameter("memo");
        		
        		//資料変更画面に渡す値をbeanに保存
        		InquiriesBean bean = new InquiriesBean
        				(Integer.parseInt(inquiriesId) , strReceptionDate , title,
        						contents ,Integer.parseInt(handling) , memo);
        		
        		//リクエストスコープで送る
        		request.setAttribute("inquiry", bean);
        		gotoPage(request , response , "/other/other_inquiries_edit.jsp");
            	
            }else if(action.equals("update")) {
            	//actionの値が「update」の場合
            	//問合せ内容を更新する
            	String inquiriesId = request.getParameter("inquiries_id");
        		String strReceptionDate = request.getParameter("reception_date");
            	String title = request.getParameter("title");
            	String contents = request.getParameter("contents");
            	String handling = request.getParameter("handling");
            	String memo = request.getParameter("memo");
            	
        		if(handling != null && handling.length() != 0) {
        			
        			//問合せを変更するときに
            		//必要事項が全て入力されているとき
            		
            		InquiriesDAO dao = new InquiriesDAO();
            		
            		//問合せListを入力内容で更新する
            		dao.updateInquiries(Integer.parseInt(inquiriesId) , Integer.parseInt(handling) , memo);
            		
            		//更新されたListをセッションに反映
            		List<InquiriesBean> list = new ArrayList<InquiriesBean>();
                	
                	list = dao.listInquiries();
                	
                	HttpSession session = request.getSession();
            		
            		session.setAttribute("inquiries", list);
            		
            		//更新完了画面表示用
            		InquiriesBean bean = new InquiriesBean(strReceptionDate , title , contents ,
            				Integer.parseInt(handling) , memo);
            		
            		request.setAttribute("inquiry" , bean);
            		gotoPage(request , response , "/other/other_inquiries_edit_complete.jsp");
            		
        		}else {
        			//資料を変更するときに
            		//必要事項が入力されていないとき
            		request.setAttribute("message", "対応有無を選択してください");
            		gotoPage(request , response , "/other/other_inquiries_edit.jsp");
        		}	
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
    
    //有効なタイトルが入力されたのかを確認するメソッド
    public boolean isCheckTitle(String title) {
    	
    	boolean flag = false;
    	
    	if(title.length() > 50) {
    		
    		flag = true;
    	}
    	
    	return flag;
    }

    //Stringの日付をsql.dateに変換するメソッド
    public Date setDate(String strDate) {
    	
    	Date date = Date.valueOf(strDate);
    	
    	return date;
    }
    
    //HTTPリクエストのPOSTリクエストが送信された場合実行される
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}