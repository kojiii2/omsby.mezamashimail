package com.appspot.mezamashimail;

import java.io.IOException;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.jdo.PersistenceManager;

import javax.servlet.http.*;
import javax.servlet.ServletException;
import javax.servlet.RequestDispatcher;

//import javax.jdo.PersistenceManager;
import com.google.appengine.api.mail.MailService;
import com.google.appengine.api.mail.MailService.Message;
import com.google.appengine.api.mail.MailServiceFactory;

@SuppressWarnings("serial")
public class WakeupTaskServlet extends HttpServlet {
	private static final Logger logger = Logger
			.getLogger(WakeupTaskServlet.class.getName());

	/* 制御サーブレットで登録された送信サーブレットが、タスクキューから呼び出される */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		/* 処理全体をtry chatchで囲み、例外を呼び出し元に返さないようにする */
		try {
			updateAlarm(req);
		} catch (Exception e) {
			if (logger.isLoggable(Level.SEVERE)) {
				logger.severe(e.toString());
			}
		}

	}

	private void updateAlarm(HttpServletRequest req) throws IOException {
		String email = req.getParameter("email");
		String nickname = "";
		int count = 0;

		PersistenceManager pm = PMF.get().getPersistenceManager();
		try {
			/* 依頼情報を更新する */
			Alarm alarm = pm.getObjectById(Alarm.class, email);
			nickname = alarm.getNickname();
			count = alarm.getCount();
			/* Alarmエンティティーを更新する */
			if (count == 0) {
				/* 初回であれば5分後に再送する */
				alarm.setCount(count + 1);
				Calendar calendar = Calendar.getInstance(TimeZone
						.getTimeZone("Asia/Tokyo"));
				calendar.setTime(alarm.getWakeupDate());
				calendar.add(Calendar.MINUTE, 5);
				alarm.setWakeupDate(calendar.getTime());
				pm.makePersistent(alarm);
			} else {
				/* 2回め以降であれば依頼を削除する */
				pm.deletePersistent(alarm);
			}
			/* Alarmエンティティーが更新できたらメールを送信する */
			sendmail(email, nickname, count);
		} finally {
			pm.close();
		}
	}

	private void sendmail(String email, String nickname, int count)
			throws IOException {
		Message message = new Message();
		message.setSender("eri@mezamashimail.appspotmail.com");
		message.setTo(email);
		if (count == 0) {
			message.setSubject("時間だよ～");
			message.setTextBody(nickname + "\n" + "頼まれてた時間だよ～。\n"
					+ "予定があるんでしょ？　早くじゅんびしてね。");
		} else {
			message.setSubject("大変～");
			message.setTextBody(nickname + "\n" + "大変、大変～。\n"
					+ "時間過ぎてるよ！　早く、早く！");
		}
		MailService mailService = MailServiceFactory.getMailService();
		mailService.send(message);
	}
}
