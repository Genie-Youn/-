package com.tcp.chr.entities;

import java.io.Serializable;
import java.util.List;

/**
 *  Store
 * 
 *  상점 정보를 저장하는 Entity
 *  @date 2016.05.18
 *  @author genie
 *  @sicne 1.0
 */

public class Store implements Serializable {
	
		private static final long serialVersionUID = 233543960259435382L;
	
		private Long seqStore;
		private String storeName;
		private String star;
		private StoreStatus status;
		private List<String> tag;
		private String imgUrl;
		
		public Long getSeqStore() {
			return seqStore;
		}
		public void setSeqStore(Long seqStore) {
			this.seqStore = seqStore;
		}
		public String getStoreName() {
			return storeName;
		}
		public void setStoreName(String storeName) {
			this.storeName = storeName;
		}
		public String getStar() {
			return star;
		}
		public void setStar(String star) {
			this.star = star;
		}
		public StoreStatus getStatus() {
			return status;
		}
		public void setStatus(StoreStatus status) {
			this.status = status;
		}
		public List<String> getTag() {
			return tag;
		}
		public void setTag(List<String> tag) {
			this.tag = tag;
		}
		public String getImgUrl() {
			return imgUrl;
		}
		public void setImgUrl(String imgUrl) {
			this.imgUrl = imgUrl;
		}
}
