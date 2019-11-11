package com.ngs.pojo;

public class BaseData {
    private Long baseId;

    private String baseName;

    private String baseDesc;

    private Long parentId;
    private String parentname;
    public Long getBaseId() {
        return baseId;
    }

    public String getParentname() {
		return parentname;
	}

	public void setParentname(String parentname) {
		this.parentname = parentname;
	}

	public void setBaseId(Long baseId) {
        this.baseId = baseId;
    }

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
    }

    public String getBaseDesc() {
        return baseDesc;
    }

    public void setBaseDesc(String baseDesc) {
        this.baseDesc = baseDesc;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
}