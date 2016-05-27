package com.tcp.chr.entities;

/**
 *  Account
 * 
 *  계정정보를 저장하는 Entity
 *  암호화 방식은 bCrypt를 사용한다.
 *  @date 2016.05.25
 *  @author genie
 *  @sicne 1.0
 */

public class Account {

		private Long seqAccount;
		private String name;
		private String email;
		private String bcPassword;
		private Long salt;
		
		public Long getSeqAccount() {
			return seqAccount;
		}
		public void setSeqAccount(Long seqAccount) {
			this.seqAccount = seqAccount;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public String getBcPassword() {
			return bcPassword;
		}
		public void setBcPassword(String bcPassword) {
			this.bcPassword = bcPassword;
		}
		public Long getSalt() {
			return salt;
		}
		public void setSalt(Long salt) {
			this.salt = salt;
		}
		
}
