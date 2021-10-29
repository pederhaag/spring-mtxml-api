package com.application;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class TagData {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

//	@Column(name = "tag_id", nullable = false)
//	private String tag_id;
	@Column(name = "tag_id", nullable = false)
	private String tagId;

	public String getTagId() {
		return tagId;
	}

	public void setTagId(String tagId) {
		this.tagId = tagId;
	}

	@Column(name = "standard_representation", nullable = false)
	private String standard_representation;

	@Column(name = "content_regex", nullable = false, length = 400)
	private String content_regex;

	@OneToMany(mappedBy = "tagData", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<TagField> fields;

	protected TagData() {
	}

	public TagData(String tag_id, String standard_representation, String content_regex) {
		this.tagId = tag_id;
		this.standard_representation = standard_representation;
		this.content_regex = content_regex;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTag_id() {
		return tagId;
	}

	public void setTag_id(String tag_id) {
		this.tagId = tag_id;
	}

	public String getStandard_representation() {
		return standard_representation;
	}

	public void setStandard_representation(String standard_representation) {
		this.standard_representation = standard_representation;
	}

	public String getContent_regex() {
		return content_regex;
	}

	public void setContent_regex(String content_regex) {
		this.content_regex = content_regex;
	}

	public List<TagField> getFields() {
		return fields;
	}

	public void setFields(List<TagField> fields) {
		this.fields = fields;
		for (TagField field : fields) {
			field.setTagData(this);
		}
	}

}
