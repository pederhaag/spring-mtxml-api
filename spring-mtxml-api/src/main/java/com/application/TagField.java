package com.application;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class TagField {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@JoinColumn(name = "parent_tag")
	private TagData tagData;

	@Column(name = "field_name", nullable = false)
	private String field_name;

	protected TagField() {
	}

	public TagField(String field_name) {
		this.field_name = field_name;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public TagData getTagData() {
		return tagData;
	}

	public void setTagData(TagData tagData) {
		this.tagData = tagData;
	}

	public String getField_name() {
		return field_name;
	}

	public void setField_name(String field_name) {
		this.field_name = field_name;
	}

}
