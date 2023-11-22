package com.ssafy.house.info.model.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.house.info.model.dto.PsyIndexDto;

@Mapper
public interface InfoMapper {
	List<PsyIndexDto> getPsyIndexList();
}
