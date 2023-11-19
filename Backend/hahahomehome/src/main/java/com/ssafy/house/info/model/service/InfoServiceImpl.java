package com.ssafy.house.info.model.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ssafy.house.info.model.dto.PsyIndexDto;
import com.ssafy.house.info.model.mapper.InfoMapper;

@Service
public class InfoServiceImpl implements InfoService {

	private final InfoMapper infoMapper;

	public InfoServiceImpl(InfoMapper infoMapper) {
		this.infoMapper = infoMapper;
	}

	@Override
	public List<PsyIndexDto> getPsyIndexList() {
		return infoMapper.getPsyIndexList();
	}

}
