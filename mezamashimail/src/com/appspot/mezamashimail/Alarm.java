package com.appspot.mezamashimail;

import java.util.*;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

/* データクラスとするためにPersistenceCapableアノテーションを付与*/
@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Alarm {
	/* 主キーフィールドとするために、PrimaryKEYアノテーションを付与 */
	@PrimaryKey
	private String email;

	/* データストアに保存するフィールドに、Persistentアノテーションを付与 */
	@Persistent
	private String nickname;
	@Persistent
	private Date wakeupDate;
	@Persistent
	private int count;
 
	/* コンストラクタ */
	public Alarm(String email, String nickname, Date wakeupDate) {
		this.email = email;
		this.nickname = nickname;
		this.wakeupDate = wakeupDate;
		this.count = 0;
	}

	/* getter setter */

	public String getEmail() {
		return email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public Date getWakeupDate() {
		return wakeupDate;
	}

	public void setWakeupDate(Date wakeupDate) {
		this.wakeupDate = wakeupDate;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
