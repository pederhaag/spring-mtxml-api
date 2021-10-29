package com.application.mtxml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.application.FieldRepository;
import com.application.TagData;
import com.application.TagField;
import com.application.TagRepository;

@Component
public class TagDataInitializer implements CommandLineRunner {
	private TagRepository tagRepository;
	private FieldRepository fieldRepository;

	public TagDataInitializer(TagRepository tagRepository, FieldRepository fieldRepository) {
		this.tagRepository = tagRepository;
		this.fieldRepository = fieldRepository;
	}

	@Override
	public void run(String... args) throws Exception {
		tagRepository.deleteAll();

		generateDatabaseRecords();

		System.out.println("---+++--- Initialized " + tagRepository.count() + " records ---+++---");

	}

	private void generateDatabaseRecords() throws IOException {
		TagDataGenerator tagData = new TagDataGenerator();
		ArrayList<TagData> tagDataCollection = tagData.getTagData();
		Map<String, ArrayList<String>> tagFieldCollection = tagData.getTagFields();

		String tagId;
		List<TagField> tagFields;

//		tagRepository.saveAll(tagDataCollection);

		for (TagData tagDataObj : tagDataCollection) {
			tagId = tagDataObj.getTag_id();

			// Attach field list to tag
			tagFields = new ArrayList<TagField>();
			for (String field : tagFieldCollection.get(tagId)) {
				tagFields.add(new TagField(field));
			}
			tagDataObj.setFields(tagFields);
		}

		tagRepository.saveAll(tagDataCollection);
	}

}
