package com.application;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.application.mtxml.MTComponent;
import com.application.mtxml.Mt;
import com.application.mtxml.Tag;
import com.application.mtxml.TagFactory;

@Controller
@RequestMapping(path = "/mtxml")
public class MTController {

	@Autowired
	private TagRepository tagRepository;

	@GetMapping(path = "/tagtoxml")
	public ResponseEntity<String> RequestTagToXml(@RequestParam String tag, @RequestParam String content) {
		String tagXml;
		try {
			tagXml = tagToXml(tag, content);
			return ResponseEntity.ok().body(tagXml);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	private String tagToXml(String tag, String content) throws Exception {
		TagData tagData = getTagData(tag);
		String regex = tagData.getContent_regex();

		List<String> fieldNames = getFieldList(tagData);

		Tag tagObj = TagFactory.createTag(tag, content, regex, fieldNames);

		return tagObj.toXml();
	}

	public List<String> getFieldList(String tagName) {
		return getFieldList(getTagData(tagName));
	}

	public List<String> getFieldList(TagData tagData) {
		List<TagField> fieldNamesList = tagData.getFields();
		List<String> fieldNames = new ArrayList<String>();

		for (TagField tf : fieldNamesList) {
			fieldNames.add(tf.getField_name());
		}
		return fieldNames;
	}

	public TagData getTagData(String tag) {
		return tagRepository.findByTagId(tag);
	}

	@GetMapping(path = "/mttoxml")
	public ResponseEntity<String> RequestMtToXml(@RequestParam String content) {
		try {
			String mtXml = MtToXml(content);
			return ResponseEntity.ok().body(mtXml);
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}

	private String MtToXml(String content) throws Exception {
		MTComponent mt = new Mt(content, this);
		return mt.toXml();
	}

}
