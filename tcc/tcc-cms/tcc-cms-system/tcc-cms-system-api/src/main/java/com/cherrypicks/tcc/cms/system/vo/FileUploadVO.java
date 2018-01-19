package com.cherrypicks.tcc.cms.system.vo;

import java.io.Serializable;

public class FileUploadVO implements Serializable {

	private static final long serialVersionUID = 7433407692349920234L;

	private String fileName;
	private String fullName;

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public enum FileUploadType {

		MERCHANT(1, "merchant"), CAMPAIGN(2, "campaign"), STAMP(3, "stamp"),PROMOTION(4,"promotion"),DEFAULT_TYPE(0,"image");

		private Integer type;
		private String path;

		private FileUploadType(Integer type, String path) {
			this.type = type;
			this.path = path;
		}

		public Integer getType() {
			return type;
		}

		public String getPath() {
			return path;
		}

	}
}
