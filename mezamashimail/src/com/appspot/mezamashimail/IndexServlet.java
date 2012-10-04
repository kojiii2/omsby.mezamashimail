package com.appspot.mezamashimail;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;

import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;

import javax.jdo.PersistenceManager;

@SuppressWarnings("serial")
public class IndexServlet extends HttpServlet {
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/* 入力情報の取得 */
		String email = req.getParameter("email");
		String nickname = req.getParameter("nickname");
		int currentYear = Integer.parseInt(req.getParameter("currentYear"));
		int year = Integer.parseInt(req.getParameter("year"));
		int month = Integer.parseInt(req.getParameter("month"));
		int date = Integer.parseInt(req.getParameter("date"));
		int hourOfDay = Integer.parseInt(req.getParameter("hourOfDay"));
		int minute = Integer.parseInt(req.getParameter("minute"));

		/* 入力チェック */
		boolean hasErrors = false;
		if (email == null || "".equals(email)) {
			req.setAttribute("email_error", true);
			hasErrors = true;
		}
		if (nickname == null || "".equals(nickname)) {
			req.setAttribute("nickname_error", true);
			hasErrors = true;
		}
		Calendar calendar = Calendar.getInstance(TimeZone
				.getTimeZone("Asia/Tokyo"));
		calendar.clear();
		calendar.setLenient(false);
		calendar.set(year, month, date, hourOfDay, minute);
		try {
			calendar.getTime();
		} catch (IllegalArgumentException e) {
			req.setAttribute("wakeupDate_error", true);
			hasErrors = true;
		}
		/* １つでもエラーがあったら元の画面を再表示する */
		if (hasErrors) {
			req.setAttribute("email", email);
			req.setAttribute("nickname", nickname);
			req.setAttribute("currentYear", currentYear);
			req.setAttribute("year", year);
			req.setAttribute("month", month);
			req.setAttribute("date", date);
			req.setAttribute("hourOfDay", hourOfDay);
			req.setAttribute("minute", minute);
			RequestDispatcher dispatcher = getServletContext()
					.getRequestDispatcher("/WEB-INF/index.jsp");
			dispatcher.forward(req, resp);
			return;
		}

		/* 登録処理 */
		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			Alarm alarm = new Alarm(email.toLowerCase(), nickname,
					calendar.getTime());
			pm.makePersistent(alarm);
			/* Alarmエンティティをリクエスト属性に保存 */
			req.setAttribute("alarm", alarm);
		} finally {
			pm.close();
		}
		/* 登録完了画面を表示する */
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/WEB-INF/index_post.jsp");
		dispatcher.forward(req, resp);

	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException {
		/* Calendar.getInstanceメソッドを使って、現在日時を日本時間として取得する */
		Calendar calendar = Calendar.getInstance(TimeZone
				.getTimeZone("Asia/Tokyo"));
		/* 年のドロップダウンリストを作成するための必要な情報をリクエスト属性に保存する */
		req.setAttribute("currentYear", calendar.get(Calendar.YEAR));

		/* 最も近い午前７時に初期値を設定する */
		if (calendar.get(Calendar.HOUR_OF_DAY) >= 7) {
			calendar.add(Calendar.DATE, 1);
		}
		req.setAttribute("year", calendar.get(Calendar.YEAR));
		req.setAttribute("month", calendar.get(Calendar.MONTH));
		req.setAttribute("date", calendar.get(Calendar.DATE));
		req.setAttribute("hourOfDay", 7);
		req.setAttribute("minute", 0);
		/* 画面を表示する */
		RequestDispatcher dispatcher = getServletContext()
				.getRequestDispatcher("/WEB-INF/index.jsp");
		dispatcher.forward(req, resp);

	}
}
