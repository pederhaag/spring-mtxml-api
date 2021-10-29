package com.application;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TagRepository extends CrudRepository<TagData, Long> {
	@Query("SELECT t.content_regex FROM TagData t where t.id = :id")
	Optional<String> findContentRegexById(@Param("id") String id);
	
	TagData findByTagId(String tag_id);
}