/**
 * @author Nayan Kumar G
 * purpose :Repository service for label
 * date    :07-03-2020
 */
package com.bridgelabz.fundoonotes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.bridgelabz.fundoonotes.entity.LabelEntity;

public interface LabelRepository extends JpaRepository<LabelEntity, Long>{

	LabelEntity findById(long labelId);
	
	@Query("from LabelEntity where user_id=:userId and label_name=:labelName")
	LabelEntity fetchLabel(long userId , String labelName);

	@Query("from LabelEntity where user_id=:userId")
	List<LabelEntity> fetchAllLabels(long userId);

	@Query("from LabelEntity where label_name=:name and user_id=:userId ")
	List<LabelEntity> fetchLabelsByName(long userId , String name);
}
